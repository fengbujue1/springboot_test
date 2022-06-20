package com.zyj.springboot_test.sftp;

import com.jcraft.jsch.*;
import com.sshtools.net.SocketTransport;
import com.sshtools.publickey.SshPrivateKeyFile;
import com.sshtools.publickey.SshPrivateKeyFileFactory;
import com.sshtools.sftp.SftpClient;
import com.sshtools.sftp.SftpFile;
import com.sshtools.sftp.SftpFileAttributes;
import com.sshtools.ssh.*;
import com.sshtools.ssh.components.SshKeyPair;
import com.sshtools.ssh.components.SshPublicKey;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author 周赟吉
 * @Date 2022/6/15 15:51
 * @Description :
 */
public class NstcSftpClient {
    /**
     * jsch
     */
    public static final String JSCH = "jsch";

    /**
     * sshtools
     */
    public static final String SSHTOOLS = "sshtools";

    /**
     * 文件系统目录分隔符
     */
    public static final String SEPARATOR = System.getProperty("file.separator");

    /**
     * sftp服务主机地址
     */
    private String host = "127.0.0.1";

    /**
     * sftp服务端口
     */
    private int port = 22;

    /**
     * sftp用户名
     */
    private String userName;

    /**
     * sftp密码
     */
    private String userPwd;

    /**
     * 超时时间毫秒
     */
    private int timeOut = 60000;

    /**
     * 客户端类型（目前支持sshtools和jsch）
     */
    private String clientType = SSHTOOLS;

    /**
     * ssh证书
     */
    private String sshKeyPath;

    /**
     * ssh证书密码
     */
    private String sshKeyPassword;

    /**
     * 是否使用秘钥登录
     */
    private boolean loginByKey = false;
    private SshClient sshClient = null;
    /**
     * SFTP客户端（sshtools）
     */
    private SftpClient sshSftpClient = null;
    private Session jschSession = null;
    /**
     * SFTP客户端（jsch）
     */
    private ChannelSftp jschClient = null;

    /**
     * 构造函数（使用用户名密码登录sftp服务）使用完成后务必调用disconnect()断开连接。
     *
     * @param host
     * @param port
     * @param userName
     * @param userPwd
     */
    public NstcSftpClient(String host, int port, String userName, String userPwd) {
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.userPwd = userPwd;
    }

    /**
     * 构造函数（使用密匙登录sftp服务）使用完成后务必调用disconnect()断开连接。
     *
     * @param host
     * @param port
     * @param userName
     * @param userPwd
     * @param sshKeyPath
     */
    public NstcSftpClient(String host, int port, String userName, String userPwd, String sshKeyPath,
                          String sshKeyPassword) {
        this(host, port, userName, userPwd);
        this.sshKeyPath = sshKeyPath;
        this.sshKeyPassword = sshKeyPassword;
        loginByKey = true;
    }

    /**
     * 设置客户端类型（目前支持sshtools和jsch）的值
     *
     * @param clientType
     *            客户端类型（目前支持sshtools和jsch）
     */
    public void setClientType(String clientType) {
        if (!SSHTOOLS.equalsIgnoreCase(clientType) && !JSCH.equalsIgnoreCase(clientType)) {
             
            throw new IllegalArgumentException("目前ClientType仅支持sshtools或jsch");
        }
        this.clientType = clientType;
         
    }

    /**
     * 上传当前工作目录下文档至远程工作目录中
     *
     * @param loaclFileName
     *            本地当前工作目录下的文件
     * @param remoteFileName
     *            远程当前工作目录下待上传的文件
     * @return
     * @author xiecongshu
     * @since：2018年10月19日 下午1:45:25
     */
    public boolean upload(String loaclFileName, String remoteFileName) {
        if (loaclFileName == null || remoteFileName == null) {
             
            return false;
        }
        conncect();
        try {
            if (SSHTOOLS.equalsIgnoreCase(clientType)) {
                sshSftpClient.put(loaclFileName, encode(remoteFileName), false);
                 
                return true;
            } else if (JSCH.equalsIgnoreCase(clientType)) {
                jschClient.put(loaclFileName, encode(remoteFileName), ChannelSftp.RESUME);
                 
                return true;
            }
        } catch (Exception e) {
             
        }
        return false;
    }

    /**
     * 上传文档至目标目录<br>
     *
     * @param remoteDir
     *            远程目录
     * @param remoteFileName
     *            远程待创建的文件名称
     *            待上传的数据
     * @author xiecongshu
     * @since：2018年10月18日 下午6:21:14
     */
    public boolean upload(String remoteDir, String remoteFileName, File loaclFile) {
        if (loaclFile == null || loaclFile.isDirectory() || !loaclFile.exists()) {
            String fileName = null;
            if (loaclFile != null) {
                fileName = loaclFile.getPath();
            }
            return false;
        }
        InputStream in = null;
        try {
            if (!mkdir(remoteDir)) {
                return false;
            }
            in = new BufferedInputStream(new FileInputStream(loaclFile));
            if (SSHTOOLS.equalsIgnoreCase(clientType)) {
                sshSftpClient.put(in, encode(remoteFileName));
                return true;
            } else if (JSCH.equalsIgnoreCase(clientType)) {
                jschClient.put(in, encode(remoteFileName));
                return true;
            }
        } catch (Exception e) {
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }
        return false;
    }

    /**
     * 上传文档至目标目录<br>
     *
     * @param remoteDir
     *            远程目录
     * @param remoteFileName
     *            远程待创建的文件名称
     * @param data
     *            待上传的数据
     * @author xiecongshu
     * @since：2018年10月18日 下午6:21:14
     */
    public boolean upload(String remoteDir, String remoteFileName, byte[] data) {
        if (data == null) {
            return false;
        }
        ByteArrayInputStream in = null;
        try {
            if (!mkdir(remoteDir)) {
                return false;
            }
            in = new ByteArrayInputStream(data);
            if (SSHTOOLS.equalsIgnoreCase(clientType)) {
                sshSftpClient.put(in, encode(remoteFileName));
                return true;
            } else if (JSCH.equalsIgnoreCase(clientType)) {
                jschClient.put(in, encode(remoteFileName));
                 
                return true;
            }
        } catch (Exception e) {
             
        } finally {
            if (in != null) {
                try {
                    // Closing a ByteArrayInputStream has no effect.
                    in.close();
                } catch (Exception e) {
                     
                }
            }
        }
        return false;
    }

    /**
     * 下载文档数据并返回内容字节数组<br>
     *
     * @param remoteDir
     *            远程目录
     * @param remoteFileName
     *            远程文件名称
     * @param rm
     *            下载完成后是否删除远程文件
     * @return 返回内容字节数组 返回空时表示下载失败
     * @author xiecongshu
     * @since：2018年10月18日 下午6:27:12
     */
    public byte[] download(String remoteDir, String remoteFileName, boolean rm) {
        byte[] data = null;
        ByteArrayOutputStream os = null;
        conncect();
        try {
            remoteDir = encode(remoteDir);
            remoteFileName = encode(remoteFileName);
            os = new ByteArrayOutputStream();
            if (SSHTOOLS.equalsIgnoreCase(clientType)) {
                sshSftpClient.cd(remoteDir);
                sshSftpClient.cd(remoteDir);
                SftpFileAttributes attrs = sshSftpClient.get(remoteFileName);
                if (attrs != null && !attrs.isDirectory()) {
                    sshSftpClient.get(remoteFileName, os);
                    data = os.toByteArray();
                     
                    if (rm) {
                        sshSftpClient.rm(remoteFileName, false, false);
                         
                    }
                    return data;
                }
            } else if (JSCH.equalsIgnoreCase(clientType)) {
                jschClient.cd(remoteDir);
                SftpATTRS attrs = jschClient.stat(remoteFileName);
                if (attrs != null && !attrs.isDir()) {
                    jschClient.get(remoteFileName, os);
                    data = os.toByteArray();
                     
                    if (rm) {
                        jschClient.rm(remoteFileName);
                         
                    }
                    return data;
                }
            }
        } catch (Exception e) {
             
        } finally {
            if (os != null) {
                try {
                    // Closing a ByteArrayOutputStream has no effect
                    os.close();
                } catch (Exception e) {
                     
                }
            }
        }
         
        return null;
    }

    /**
     * 下载文档至本地目录<br>
     *
     * @param remoteDir
     *            远程目录
     * @param remoteFileName
     *            远程文件名称
     * @param loaclDir
     *            本地目录
     * @param loaclFileName
     *            不为空则覆盖remoteFileName的值
     * @param rm
     *            下载完成后是否删除远程文件
     * @return 返回本地文件全路径 返回空时表示下载失败
     * @author xiecongshu
     * @since：2018年10月18日 下午6:27:12
     */
    public String download(String remoteDir, String remoteFileName, String loaclDir, String loaclFileName, boolean rm) {
        String filePath = null;
        conncect();
        try {
            if (loaclFileName != null && !loaclFileName.trim().isEmpty()) {
                filePath = formatPath(loaclDir) + loaclFileName;
            } else {
                filePath = formatPath(loaclDir) + remoteFileName;
            }
            // 注释以下代码，原因是FileUtils工具的forceMkdirParent方法是在common-io-2.5.jar才支持，但是该包和业务系统的对应的包冲突，导致报错，
            // 由于该class所在的jar包是部署在业务系统的，所以依赖业务业务系统的commmo-io-1.4.jar。所以bp做调整，适应1.4版本
            // FileUtils.forceMkdirParent(new File(filePath));
            forceMkdirParent(new File(filePath));
            remoteDir = encode(remoteDir);
            remoteFileName = encode(remoteFileName);
            if (SSHTOOLS.equalsIgnoreCase(clientType)) {
                sshSftpClient.cd(remoteDir);
                SftpFileAttributes attrs = sshSftpClient.get(remoteFileName);
                if (attrs != null && !attrs.isDirectory()) {
                    sshSftpClient.get(remoteFileName, filePath, false);
                     
                    if (rm) {
                        sshSftpClient.rm(remoteFileName, false, false);
                         
                    }
                    return filePath;
                }
            } else if (JSCH.equalsIgnoreCase(clientType)) {
                jschClient.cd(remoteDir);
                SftpATTRS attrs = jschClient.stat(remoteFileName);
                if (attrs != null && !attrs.isDir()) {
                    jschClient.get(remoteFileName, filePath);
                     
                    if (rm) {
                        jschClient.rm(remoteFileName);
                         
                    }
                    return filePath;
                }
            }
        } catch (Exception e) {
             
        }
         
        return null;
    }

    /**
     * 檢查文件的上级目录是否为文件夹
     *
     * @Description:
     * @param file
     * @throws IOException
     * @author gaoyanguo
     * @since：2020年4月5日 上午10:26:40
     */
    public void forceMkdirParent(File file) throws IOException {
        File parent = file.getParentFile();
        if (parent == null) {
            return;
        }
        if (parent.exists()) {
            if (!parent.isDirectory()) {
                String message = "File " + parent + " exists and is " + "not a directory. Unable to create directory.";
                throw new IOException(message);
            }
        } else if (!parent.mkdirs()) {
            if (!parent.isDirectory()) {
                String message = "Unable to create directory " + parent;
                throw new IOException(message);
            }
        }
    }

    /**
     * 查询目录文件列表<br>
     *
     * @param remoteDir
     *            远程文件目录
     * @return 目录下文件名列表（包含文件和目录名称）
     * @author xiecongshu
     * @since：2018年10月18日 下午7:17:18
     */
    public List<String> listFiles(String remoteDir) {
        conncect();
        List<String> list = new ArrayList<String>();
        try {
            remoteDir = encode(remoteDir);
            if (SSHTOOLS.equalsIgnoreCase(clientType)) {
                sshSftpClient.cd(remoteDir);
                SftpFile[] remoteFiles = sshSftpClient.ls();
                if (remoteFiles != null) {
                    for (SftpFile f : remoteFiles) {
                        if (".".equals(f.getFilename()) || "..".equals(f.getFilename())) {
                            continue;
                        }
                        list.add(decode(f.getFilename()));
                    }
                }
            } else if (JSCH.equalsIgnoreCase(clientType)) {
                jschClient.cd(remoteDir);
                Vector<?> files = jschClient.ls("./");
                ChannelSftp.LsEntry entry = null;
                for (Object obj : files) {
                    entry = (ChannelSftp.LsEntry) obj;
                    if (".".equals(entry.getFilename()) || "..".equals(entry.getFilename())) {
                        continue;
                    }
                    list.add(decode(entry.getFilename()));
                }
            }
             
        } catch (Exception e) {
             
            throw new RuntimeException("sftp查询目录文件列表时异常.remoteDir=" + remoteDir);
        }
        return list;
    }

    /**
     * 检测子目录是否存在<br>
     *
     * @param remoteDirName
     * @return
     * @author xiecongshu
     * @since：2018年10月18日 下午7:34:44
     */
    public boolean subDirExists(String remoteDirName) {
        conncect();
        try {
            remoteDirName = encode(remoteDirName);
            if (SSHTOOLS.equalsIgnoreCase(clientType)) {
                SftpFile[] remoteFiles = sshSftpClient.ls();
                if (remoteFiles != null) {
                    SftpFileAttributes attrs = null;
                    for (SftpFile f : remoteFiles) {
                        attrs = f.getAttributes();
                        if (attrs.isDirectory() && f.getFilename().equals(remoteDirName)) {
                            return true;
                        }
                    }
                }
            } else if (JSCH.equalsIgnoreCase(clientType)) {
                Vector<?> files = jschClient.ls("./");
                ChannelSftp.LsEntry entry = null;
                SftpATTRS attrs = null;
                for (Object obj : files) {
                    entry = (ChannelSftp.LsEntry)obj;
                    attrs = entry.getAttrs();
                    if (attrs.isDir() && entry.getFilename().equals(remoteDirName)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
             
            throw new RuntimeException("sftp检测子目录是否存在时异常.remoteDirName=" + remoteDirName);
        }
        return false;
    }

    /**
     * 检测文件是否存在<br>
     *
     * @param remoteFileName
     * @return
     * @author xiecongshu
     * @since：2018年10月18日 下午7:34:44
     */
    public boolean fileExists(String remoteFileName) {
        conncect();
        try {
            remoteFileName = encode(remoteFileName);
            if (SSHTOOLS.equalsIgnoreCase(clientType)) {
                SftpFile[] remoteFiles = sshSftpClient.ls();
                if (remoteFiles != null) {
                    SftpFileAttributes attrs = null;
                    for (SftpFile f : remoteFiles) {
                        attrs = f.getAttributes();
                        if (!attrs.isDirectory() && f.getFilename().equals(remoteFileName)) {
                            return true;
                        }
                    }
                }
            } else if (JSCH.equalsIgnoreCase(clientType)) {
                Vector<?> files = jschClient.ls("./");
                ChannelSftp.LsEntry entry = null;
                SftpATTRS attrs = null;
                for (Object obj : files) {
                    entry = (ChannelSftp.LsEntry) obj;
                    attrs = entry.getAttrs();
                    if (!attrs.isDir() && entry.getFilename().equals(remoteFileName)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
             
            throw new RuntimeException("sftp检测文件是否存在时异常.remoteFileName=" + remoteFileName);
        }
        return false;
    }

    /**
     * 检测文件是否存在<br>
     *
     * @param remoteDirName
     * @return
     * @author xiecongshu
     * @since：2018年10月18日 下午7:34:44
     */
    public boolean dirExists(String remoteDirName) {
        conncect();
        try {
            remoteDirName = encode(remoteDirName);
            if (SSHTOOLS.equalsIgnoreCase(clientType)) {
                SftpFile[] remoteFiles = sshSftpClient.ls();
                if (remoteFiles != null) {
                    SftpFileAttributes attrs = null;
                    for (SftpFile f : remoteFiles) {
                        attrs = f.getAttributes();
                        if (attrs.isDirectory() && f.getFilename().equals(remoteDirName)) {
                            sshSftpClient.cd(remoteDirName);
                            return true;
                        }
                    }
                }
            } else if (JSCH.equalsIgnoreCase(clientType)) {
                Vector<?> files = jschClient.ls("./");
                ChannelSftp.LsEntry entry = null;
                SftpATTRS attrs = null;
                for (Object obj : files) {
                    entry = (ChannelSftp.LsEntry) obj;
                    attrs = entry.getAttrs();
                    if (attrs.isDir() && entry.getFilename().equals(remoteDirName)) {
                        jschClient.cd(remoteDirName);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
             
            throw new RuntimeException("sftp检测文件目录是否存在时异常.remoteDirName=" + remoteDirName);
        }
        return false;
    }

    /**
     * 切换当前工作目录 <br>
     *
     * @param remoteDir
     *            远程目录（为空时不切换）
     * @param loaclDir
     *            本地目录（为空时不切换）
     * @author xiecongshu
     * @since：2018年10月18日 下午7:09:31
     */
    public void cd(String remoteDir, String loaclDir) {
        conncect();
        try {
            if (SSHTOOLS.equalsIgnoreCase(clientType)) {
                if (remoteDir != null && !remoteDir.trim().isEmpty()) {
                    sshSftpClient.cd(encode(remoteDir));
                }
                if (loaclDir != null && !loaclDir.trim().isEmpty()) {
                    sshSftpClient.lcd(loaclDir);
                }
            } else if (JSCH.equalsIgnoreCase(clientType)) {
                if (remoteDir != null && !remoteDir.trim().isEmpty()) {
                    jschClient.cd(encode(remoteDir));
                }
                if (loaclDir != null && !loaclDir.trim().isEmpty()) {
                    jschClient.lcd(loaclDir);
                }
            }
        } catch (Exception e) {
             
            throw new RuntimeException("sftp切换工作目录时异常.remoteDir=" + remoteDir + ",loaclDir=" + loaclDir);
        }
    }

    /**
     * 连接并登录Sftp服务 <br>
     *
     * @author xiecongshu
     * @since：2018年10月18日 下午4:54:53
     */
    private void conncect() {
        if (isConnected()) {
            return;
        }
         
        if (SSHTOOLS.equalsIgnoreCase(clientType)) {
            sshConncect();
        } else if (JSCH.equalsIgnoreCase(clientType)) {
            jschConncect();
        } else {
            throw new IllegalArgumentException("目前SFTP的ClientType仅支持sshtools或jsch");
        }
    }

    /**
     * 断开sftp连接<br>
     *
     * @author xiecongshu
     * @since：2018年10月18日 下午5:06:16
     */
    public void disconnect() {
         
        try {
            if (SSHTOOLS.equalsIgnoreCase(clientType)) {
                if (sshSftpClient != null) {
                    if (!sshSftpClient.isClosed()) {
                        sshSftpClient.quit();
                    }
                    sshSftpClient = null;
                }
                if (sshClient != null) {
                    if (sshClient.isConnected()) {
                        sshClient.disconnect();
                    }
                    sshClient = null;
                }
            } else if (JSCH.equalsIgnoreCase(clientType)) {
                if (jschClient != null) {
                    if (jschClient.isConnected()) {
                        jschClient.exit();
                        jschClient.disconnect();
                    }
                    jschClient = null;
                }
                if (jschSession != null) {
                    if (jschSession.isConnected()) {
                        jschSession.disconnect();
                    }
                    jschSession = null;
                }
            }
        } catch (Exception e) {
             
        }
    }

    /**
     * 连接sftp<br>
     *
     * @author xiecongshu
     * @since：2018年10月18日 下午6:10:21
     */
    private void sshConncect() {
        try {
            if (loginByKey) {
                SshConnector con = SshConnector.createInstance();
                HostKeyVerification hkv = new HostKeyVerification() {
                    public boolean verifyHost(String hostname, SshPublicKey key) {
                        return true;
                    }
                };
                con.getContext().setHostKeyVerification(hkv);
                con.getContext().setSocketTimeout(timeOut);
                SocketTransport socketTransport = new SocketTransport(host, port);
                socketTransport.setSoTimeout(timeOut);
                socketTransport.setTcpNoDelay(true);
                socketTransport.setSoLinger(true, 60);
                sshClient = con.connect(socketTransport, userName);
                PublicKeyAuthentication pk = new PublicKeyAuthentication();
                do {
                    SshPrivateKeyFile pkfile = SshPrivateKeyFileFactory.parse(new FileInputStream(sshKeyPath));
                    SshKeyPair pair;
                    if (pkfile.isPassphraseProtected()) {
                        pair = pkfile.toKeyPair(sshKeyPassword);
                    } else {
                        pair = pkfile.toKeyPair(null);
                    }
                    pk.setPrivateKey(pair.getPrivateKey());
                    pk.setPublicKey(pair.getPublicKey());
                } while (sshClient.authenticate(pk) != SshAuthentication.COMPLETE && sshClient.isConnected());
            } else {
                SshConnector con = SshConnector.createInstance();
                con.getContext().setSocketTimeout(timeOut);
                SocketTransport transport = new SocketTransport(host, port);
                transport.setSoTimeout(timeOut);
                transport.setTcpNoDelay(true);
                transport.setSoLinger(true, 60);
                sshClient = con.connect(transport, userName);
                PasswordAuthentication pwd = new PasswordAuthentication();
                do {
                    pwd.setPassword(userPwd);
                } while (sshClient.authenticate(pwd) != SshAuthentication.COMPLETE && sshClient.isConnected());
            }
            if (sshClient.isAuthenticated()) {
                sshSftpClient = new SftpClient(sshClient);
                sshSftpClient.setTransferMode(SftpClient.MODE_BINARY);
                sshSftpClient.setRegularExpressionSyntax(SftpClient.GlobSyntax);
            } else {
                throw new Exception("登录鉴权失败.");
            }
        } catch (Exception e) {
             
            throw new RuntimeException("连接并登录sftp服务时异常.");
        }
    }

    /**
     * 打印当前工作目录（远程目录）<br>
     *
     * @return
     * @author xiecongshu
     * @since：2018年10月19日 下午3:14:58
     */
    public String pwd() {
        conncect();
        try {
            if (SSHTOOLS.equalsIgnoreCase(clientType)) {
                return decode(sshSftpClient.pwd());
            } else if (JSCH.equalsIgnoreCase(clientType)) {
                return decode(jschClient.pwd());
            }
            return null;
        } catch (Exception e) {
             
            throw new RuntimeException("sftp查询当前工作目录.");
        }
    }

    /**
     * 打印当前工作目录（本地目录）<br>
     *
     * @return
     * @author xiecongshu
     * @since：2018年10月19日 下午3:14:58
     */
    public String lpwd() {
        conncect();
        try {
            if (SSHTOOLS.equalsIgnoreCase(clientType)) {
                return sshSftpClient.lpwd();
            } else if (JSCH.equalsIgnoreCase(clientType)) {
                return jschClient.lpwd();
            }
            return null;
        } catch (Exception e) {
             
            throw new RuntimeException("sftp查询当前工作目录.");
        }
    }

    /**
     * 创建远程目录<br>
     *
     * @param remoteDir
     * @return
     * @author xiecongshu
     * @since：2018年10月19日 下午2:40:26
     */
    public boolean mkdir(String remoteDir) {
        conncect();
        try {
            if (remoteDir == null || remoteDir.trim().isEmpty()) {
                return false;
            }
            LinkedList<String> dirs = formatDirs(remoteDir);
            if (SSHTOOLS.equalsIgnoreCase(clientType)) {
                for (String dir : dirs) {
                    if ("/".equals(dir)) {
                        sshSftpClient.cd("/");
                        continue;
                    }
                    if (!dirExists(dir)) {
                        sshSftpClient.mkdir(encode(dir));
                        sshSftpClient.cd(encode(dir));
                    }
                }
                return true;
            } else if (JSCH.equalsIgnoreCase(clientType)) {
                for (String dir : dirs) {
                    if ("/".equals(dir)) {
                        jschClient.cd("/");
                        continue;
                    }
                    if (!dirExists(dir)) {
                        jschClient.mkdir(encode(dir));
                        jschClient.cd(encode(dir));
                    }
                }
                return true;
            }
        } catch (Exception e) {
             
            throw new RuntimeException("sftp创建目录时异常.remoteDir=" + remoteDir);
        }
        return false;
    }

    /**
     * 格式化目录并返回目录层级名称列表<br>
     *
     * @param pathName
     * @return
     * @author xiecongshu
     * @since：2018年10月19日 下午3:04:40
     */
    private LinkedList<String> formatDirs(String pathName) {
        LinkedList<String> dirs = new LinkedList<String>();
        File file = new File(pathName);
        while (file.getParent() != null) {
            dirs.addFirst(file.getName());
            file = file.getParentFile();
        }
        dirs.addFirst(file.getPath().replaceAll("\\\\", "/"));
        return dirs;
    }

    /**
     * 连接sftp<br>
     *
     * @author xiecongshu
     * @since：2018年10月18日 下午6:10:21
     */
    private void jschConncect() {
        try {
            if (loginByKey) {
                JSch jsch = new JSch();
                jsch.getSession(userName, host, port);
                jsch.addIdentity(sshKeyPath, sshKeyPassword);
                jschSession = jsch.getSession(userName, host, port);
                if (userPwd != null) {
                    jschSession.setPassword(userPwd);
                }
                Properties sshConfig = new Properties();
                sshConfig.put("StrictHostKeyChecking", "no");
                sshConfig.put("UseDNS", "no");
                jschSession.setConfig(sshConfig);
                jschSession.setTimeout(timeOut);
                jschSession.connect();
                Channel channel = jschSession.openChannel("sftp");
                channel.connect();
                jschClient = (ChannelSftp)channel;
                // jschClient.setFilenameEncoding("iso8859-1");
            } else {
                JSch jsch = new JSch();
                jsch.getSession(userName, host, port);
                jschSession = jsch.getSession(userName, host, port);
                if (userPwd != null) {
                    jschSession.setPassword(userPwd);
                }
                Properties sshConfig = new Properties();
                sshConfig.put("StrictHostKeyChecking", "no");
                sshConfig.put("UseDNS", "no");
                sshConfig.put("PreferredAuthentications", "password");
                jschSession.setConfig(sshConfig);
                jschSession.setTimeout(timeOut);
                jschSession.connect(100);
                Channel channel = jschSession.openChannel("sftp");
                channel.connect();
                jschClient = (ChannelSftp)channel;
                // jschClient.setFilenameEncoding("iso8859-1");
            }
        } catch (Exception e) {
             
            throw new RuntimeException("连接并登录sftp服务时异常.");
        }
    }

    /**
     * 删除远程文件<br>
     *
     * @param remotefilePath
     * @return
     * @author xiecongshu
     * @since：2018年10月19日 下午2:03:10
     */
    public boolean deleteFile(String remotefilePath) {
        try {
            conncect();
            if (remotefilePath == null || remotefilePath.trim().isEmpty()) {
                return false;
            }
            if (SSHTOOLS.equalsIgnoreCase(clientType)) {
                sshSftpClient.rm(encode(remotefilePath), false, false);
                return true;
            } else if (JSCH.equalsIgnoreCase(clientType)) {
                jschClient.rm(encode(remotefilePath));
                return true;
            }
        } catch (Exception e) {
             
        }
        return false;
    }

    /**
     * 连接是否关闭<br>
     *
     * @return
     * @author xiecongshu
     * @since：2018年10月18日 下午4:58:39
     */
    private boolean isConnected() {
        if (SSHTOOLS.equalsIgnoreCase(clientType)) {
            if (sshSftpClient != null && sshClient != null) {
                return (!sshSftpClient.isClosed() && sshClient.isConnected());
            }
        } else if (JSCH.equalsIgnoreCase(clientType)) {
            if (jschClient != null && jschSession != null) {
                return (jschClient.isConnected() && jschSession.isConnected());
            }
        }
        return false;
    }

    /**
     * 客户端信息 <br>
     *
     * @return
     * @author xiecongshu
     * @since：2018年10月18日 下午4:39:55
     */
    private String clientInfo(String msg) {
        StringBuilder builder = new StringBuilder();
        builder.append("NstcSftpClient客户端信息:").append(System.getProperty("line.separator"));
        builder.append("[host=");
        builder.append(host);
        builder.append(", port=");
        builder.append(port);
        builder.append(", userName=");
        builder.append(userName);
        builder.append(", userPwd=");
        builder.append(userPwd);
        builder.append(", clientType=");
        builder.append(clientType);
        if (loginByKey) {
            builder.append(", sshKeyPath=");
            builder.append(sshKeyPath);
            builder.append(", sshKeyPassword=");
            builder.append(sshKeyPassword);
        }
        builder.append("]").append(System.getProperty("line.separator"));
        builder.append(msg);
        return builder.toString();
    }

    /**
     * 设置超时时间毫秒的值
     *
     * @param timeOut
     *            超时时间毫秒
     */
    public void setTimeOut(int timeOut) {
        if (timeOut < 1000) {
            return;
        }
        this.timeOut = timeOut;
    }

    /**
     * 格式化文件目录路径
     *
     * @param path
     * @return 路径结尾包含目录分隔符
     * @author xiecongshu
     * @since：2018年6月7日 下午3:08:22
     */
    private String formatPath(String path) {
        File dir = new File(path);
        path = dir.getPath();
        if (!path.endsWith(SEPARATOR)) {
            path += SEPARATOR;
        }
        return path;
    }

    /**
     * 编码utf8字符串为iso8859-1字符串<br>
     *
     * @param s
     * @return
     * @author xiecongshu
     * @since：2018年10月19日 上午10:24:58
     */
    private String encode(String s) {
        try {
            if (JSCH.equalsIgnoreCase(clientType)) {
                return s;
            }
            if (s == null) {
                return s;
            }
            return new String(s.getBytes("utf8"), "iso8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解码iso8859-1字符串为utf8字符串<br>
     *
     * @param s
     * @return
     * @author xiecongshu
     * @since：2018年10月19日 上午10:24:58
     */
    private String decode(String s) {
        try {
            if (JSCH.equalsIgnoreCase(clientType)) {
                return s;
            }
            if (s == null) {
                return s;
            }
            return new String(s.getBytes("iso8859-1"), "utf8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取SFTP客户端（sshtools）的值
     *
     * @return SFTP客户端（sshtools）
     */
    public SftpClient getSshSftpClient() {
        conncect();
        return sshSftpClient;
    }

    /**
     * 获取SFTP客户端（jsch）的值
     *
     * @return SFTP客户端（jsch）
     */
    public ChannelSftp getJschSftpClient() {
        conncect();
        return jschClient;
    }

    /**
     * 删除JSCH连接方式时远程sftp目录
     *
     * @param remoteDirectoryPath
     *            远程目录路径
     * @return
     */
    public boolean deleteDirectory(String remoteDirectoryPath) {
        try {
            conncect();
            if (StringUtils.isBlank(remoteDirectoryPath)) {
                return false;
            }
            if (JSCH.equalsIgnoreCase(clientType)) {
                jschClient.rmdir(encode(remoteDirectoryPath));
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 设置编码格式
     */
    public void setJschClientFilenameEncoding(String filenameEncoding) throws Exception {
        conncect();
        if (jschClient == null) {
            throw new RuntimeException("非jsch模式，请检查连接模式");
        }
        Class<?> cl = ChannelSftp.class;
        Field f =cl.getDeclaredField("server_version");
        f.setAccessible(true);
        f.set(jschClient, 2);
        jschClient.setFilenameEncoding(filenameEncoding);
    }
}
