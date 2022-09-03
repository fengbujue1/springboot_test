package com.zyj.springboot_test.test.java.zip;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.List;

/**
 * @Author 周赟吉
 * @Date 2022/6/7 14:43
 * @Description :
 */
public class ZipUtil {
    private static final int bufferSize = 1024;

    public static void main(String[] args) throws Exception {
//        unzip(new File("D:\\1.NSTC\\demand\\2022.7\\18【ID1329663】【华侨城集团】【农业银行】生产回单上线支持\\41002953400009373_20220621_CommonSingle_1.zip"),
//                "D:\\1.NSTC\\demand\\2022.7\\18【ID1329663】【华侨城集团】【农业银行】生产回单上线支持\\unzip");
        test();
//        unzip2(new File("D:\\1.NSTC\\demand\\2022.5\\tmpdir\\front_down\\202110151634281677551\\20220519\\detail.zip"),
//                "D:\\1.NSTC\\demand\\2022.5\\tmpdir\\front_down\\202110151634281677551\\20220519");
    }

    private static void test() throws Exception {
        String destDirPath = "D:\\1.NSTC\\demand\\2022.7\\18【ID1329663】【华侨城集团】【农业银行】生产回单上线支持\\unzip";
        File srcFile = new File("D:\\1.NSTC\\demand\\2022.7\\18【ID1329663】【华侨城集团】【农业银行】生产回单上线支持\\sftp_root1\\41002953400009373_20220622_CommonSingle_1.zip");
//        File srcFile = new File("D:\\1.NSTC\\demand\\2022.7\\18【ID1329663】【华侨城集团】【农业银行】生产回单上线支持\\sftp_root1\\41002953400009373_20220626_CommonSingle_1.zip");
//        File srcFile = new File("D:\\1.NSTC\\demand\\2022.7\\18【ID1329663】【华侨城集团】【农业银行】生产回单上线支持\\zip.zip");
        long start = System.currentTimeMillis();
        int BUFFER_SIZE = 1024;
        if (!srcFile.exists()) {
            System.out.println("文件不存在");
        } else {
            java.util.zip.ZipFile zipFile = null;

            try {
                zipFile = new java.util.zip.ZipFile(srcFile);
                Enumeration entries = zipFile.entries();

                while(entries.hasMoreElements()) {
                    java.util.zip.ZipEntry entry = (java.util.zip.ZipEntry)entries.nextElement();
                    if (entry.isDirectory()) {
                        String dirPath = destDirPath + "/" + entry.getName();
                        File dir = new File(dirPath);
                        dir.mkdirs();
                    } else {
                        File targetFile = new File(destDirPath + "/" + entry.getName());
                        if (!targetFile.getParentFile().exists()) {
                            targetFile.getParentFile().mkdirs();
                        }

                        targetFile.createNewFile();
                        InputStream is = zipFile.getInputStream(entry);
                        FileOutputStream fos = new FileOutputStream(targetFile);
                        byte[] buf = new byte[BUFFER_SIZE];

                        int len;
                        while((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                        }

                        fos.close();
                        is.close();
                    }
                }

                long end = System.currentTimeMillis();
            } catch (Exception var20) {
                throw new Exception("解压文件" + srcFile.getAbsolutePath() + "异常：" + var20.getMessage(), var20);
            } finally {
                if (zipFile != null) {
                    try {
                        zipFile.close();
                    } catch (IOException var19) {
                    }
                }

            }
        }
    }

    public static void doCompress(File srcFile, File destFile) throws IOException {

        ZipArchiveOutputStream out = null;

        InputStream is = null;

        try {

            is = new BufferedInputStream(new FileInputStream(srcFile), 1024);

            out = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(destFile), 1024));

            ZipArchiveEntry entry = new ZipArchiveEntry(srcFile.getName());

            entry.setSize(srcFile.length());

            out.putArchiveEntry(entry);

            IOUtils.copy(is, out);

            out.closeArchiveEntry();

        } finally {

            IOUtils.closeQuietly(is);

            IOUtils.closeQuietly(out);

        }

    }

    /**

     * 用于多文件压缩

     */

    public static void doCompressFiles( File destFile,File... srcFiles) throws IOException {

        ZipArchiveOutputStream out = null;

        InputStream is = null;

        try {

            out = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(destFile), bufferSize));

            for(File srcFile:srcFiles){

                is = new BufferedInputStream(new FileInputStream(srcFile), bufferSize);

                ZipArchiveEntry entry = new ZipArchiveEntry(srcFile.getName());

                entry.setSize(srcFile.length());

                out.putArchiveEntry(entry);

                IOUtils.copy(is, out);

            }

            out.closeArchiveEntry();

        } finally {

            IOUtils.closeQuietly(is);

            IOUtils.closeQuietly(out);

        }

    }

    public static void unZip(File file, String destPath, boolean delete) throws Exception {
        ZipFile zip = null;
        BufferedOutputStream dest = null;
        OutputStream out = null;
        InputStream in = null;

        try {
            zip = new ZipFile(file, "utf-8");
            Enumeration entries = zip.getEntries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                in = zip.getInputStream(entry);
                String path = (destPath + "/" + zipEntryName).replaceAll("\\*", "/");
                File f = new File(path);
                if (!f.getParentFile().exists()) {
                    f.getParentFile().mkdirs();
                }

                if (entry.isDirectory()) {
                    if (!f.exists()) {
                        f.mkdirs();
                    }

                    in.close();
                } else {
                    out = new FileOutputStream(f);
                    int BUFFER = 2048;
                    byte[] datas = new byte[BUFFER];
                    dest = new BufferedOutputStream(out, BUFFER);

                    int count;
                    while ((count = in.read(datas, 0, BUFFER)) != -1) {
                        dest.write(datas, 0, count);
                    }

                    dest.flush();
                    dest.close();
                    in.close();
                    out.close();
                    if (path.endsWith(".zip")) {
                        unZip(f, path.substring(0, path.indexOf(".zip")),delete);
                    }
                }
            }
        } catch (Exception var19) {
            delete = false;
            throw new Exception("解压文件" + file.getName() + "时异常：", var19);
        } finally {
            if (out != null) {
                out.close();
            }

            if (in != null) {
                in.close();
            }

            if (dest != null) {
                dest.close();
            }

            if (zip != null) {
                zip.close();
            }

            if (delete) {
                file.delete();
            }

        }

    }

    public static void unzip2(File zipFile, String descDir) throws Exception {
        try (ZipArchiveInputStream inputStream = getZipFile(zipFile)) {
            File pathFile = new File(descDir);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            ZipArchiveEntry entry = null;
            while ((entry = inputStream.getNextZipEntry()) != null) {
                if (entry.isDirectory()) {
                    File directory = new File(descDir, entry.getName());
                    directory.mkdirs();
                } else {
                    OutputStream os = null;
                    try {
                        os = new BufferedOutputStream(new FileOutputStream(new File(descDir, entry.getName())));
                        IOUtils.copy(inputStream, os);
                    } finally {
                        IOUtils.closeQuietly(os);
                    }
                }
            }
            System.out.println("******************解压完毕********************");

        } catch (Exception e) {
            System.out.println("[unzip] 解压zip文件出错" + e.getMessage());
            throw e;
        }
    }

    private static ZipArchiveInputStream getZipFile(File zipFile) throws Exception {
        return new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
    }
}
