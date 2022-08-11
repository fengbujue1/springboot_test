package com.zyj.springboot_test.test.java.inWork;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zyj.springboot_test.util.DateUtil;
import com.zyj.springboot_test.util.PDFUtil;
import com.zyj.springboot_test.util.TextFormat;
import com.zyj.springboot_test.util.XMLUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.jdom.Element;
import org.quartz.SchedulerContext;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author 周赟吉
 * @Date 2022/5/23 14:31
 * @Description :
 */
public class Test {
    public static final String SPECIFIC_CHARACTER = "&";
    public static final String SPECIFIC_CHARACTER_EXCHANGE = "SPECIFIC";

    public static String s_head;
    public static String s_2;
    public static String s_3;
    public static String s_4;
    public static String s_tail;

    public static void main(String[] args) throws Exception {
//        test1();
//        test2();
//        test3();
//        test4();
//        test5();
//        test6();
//        test7();
//        test8();
//        test9();
//        test10();
//        test11();
//        test12();
        test13();
    }

    /**
     * 日志分析
     */
    public static void test13() throws IOException {
        File file = new File("D:\\1.NSTC\\demand\\2022.8\\13【ID1335609】【科沃斯】bp3-支付宝-AP-交易明细及余额接口返回的数据文件需要新建ftp存放目录\\BP2022V01-需求规模估算.xlsx");
        String accountNo = "123";
        FTPClient ftpClient = new FTPClient();
        try {
            // 连接登录
            ftpClient.connect("192.168.22.234", 21);

            boolean loginResult = ftpClient.login("king", "huiyi1314");
            int returnCode = ftpClient.getReplyCode();
            // 如果登录成功
            if (loginResult && FTPReply.isPositiveCompletion(returnCode)) {
                System.out.println("连接ftp成功.......");
            } else {
                throw new Exception("连接ftp失败.......");
            }
            // 本地下载回单文件的路径
            String basePath = "/baseDir";
            // 设置ftp被动模式
            ftpClient.enterLocalPassiveMode();
            // 设置目录
            System.out.println("切换路径："+ftpClient.changeWorkingDirectory(basePath));
            ftpClient.setBufferSize(1024);
            // 设置文件类型（二进制）
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            // 设置编码
            ftpClient.setControlEncoding("gbk");
            String fileName = file.getName();
            if (!fileName.startsWith(accountNo)) {
                fileName = accountNo + "_" + fileName;
            }
            String remote = new String(fileName.getBytes("gbk"), "iso-8859-1");
            boolean success = ftpClient.storeFile(remote, new FileInputStream(file));
            if (!success) {
                throw new RuntimeException("FTP客户端上传文件失败");
            }
            ftpClient.logout();
        } catch (Exception e) {
            throw new RuntimeException("FTP客户端上传文件失败！", e);
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
    }

    /**
     * 日志分析
     */
    public static void test12() throws IOException {
        File file = new File("D:\\1.NSTC\\demand\\2022.8\\4.【ID1343417】【比亚迪】BP2各银行批量扣款时间过长，单个包扣款成功长达4分钟及以上\\20220725\\日志");
        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.startsWith("BOC.TRN1_86")) {
                    return true;
                }
                return false;
            }
        });
        int totalCount = 0;
        System.out.println("fileNum:" + files.length);
        Date lastTime = null;

        //统计检查时间相关参数
        Date checkStartTime = null;
        Date checkEndTime = null;
        boolean isCaclCheckTime = false;
        int checkTotalCost = 0;
        int checkCount = 0;

        //指令查询相关参数
        Date queryStartTime = null;
        Date queryEndTime = null;
        boolean isQuery = false;
        int queryTotalCost = 0;
        int queryCount = 0;

        int totalCostTime = 0;
        for (int i = 0; i < files.length; i++) {
            File file1 = files[i];
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file1), "UTF-8"));

            String line = null;
            int count = 0;
            int costTime = 0;
            while ((line = reader.readLine()) != null) {
                if (line.contains("-指令防重检查完成")) {
                    String time = line.substring(0, 23);
                    if (lastTime == null) {
                        lastTime = TextFormat.parseDate(time, "yyyy-MM-dd HH:mm:ss.SSS");
                    }
                    Date currentTime = TextFormat.parseDate(time, "yyyy-MM-dd HH:mm:ss");
                    costTime += currentTime.getTime() - lastTime.getTime();
                    totalCostTime += currentTime.getTime() - lastTime.getTime();
                    totalCount++;
                    count++;
                    lastTime = TextFormat.parseDate(time, "yyyy-MM-dd HH:mm:ss");
                }
                if (line.contains("-发送") && !isCaclCheckTime) {
                    String time = line.substring(0, 23);
                    checkStartTime = TextFormat.parseDate(time, "yyyy-MM-dd HH:mm:ss");
                    isCaclCheckTime = true;
                }
                if (line.contains("-指令防重检查完成") && isCaclCheckTime) {
                    String time = line.substring(0, 23);
                    checkEndTime = TextFormat.parseDate(time, "yyyy-MM-dd HH:mm:ss");
                    isCaclCheckTime = false;
                    checkTotalCost += checkEndTime.getTime() - checkStartTime.getTime();
                    checkCount++;
                }

                if (line.contains("-任务开始执行") && !isQuery) {
                    String time = line.substring(0, 23);
                    queryStartTime = TextFormat.parseDate(time, "yyyy-MM-dd HH:mm:ss");
                    isQuery = true;
                }
                if (line.contains("-过滤有效指令数") && isQuery) {
                    String time = line.substring(0, 23);
                    queryEndTime = TextFormat.parseDate(time, "yyyy-MM-dd HH:mm:ss");
                    isQuery = false;
                    queryTotalCost += queryEndTime.getTime() - queryStartTime.getTime();
                    queryCount++;
                }

                if (line.startsWith("-- 用时:")) {
                    lastTime = null;
                }
            }
            System.out.println("查完文件:" + file1.getName() + "总共" + count + "条指令，耗时：" + costTime / 1000 / 60 + "分" + (costTime / 1000) % 60 + "秒");
        }
        System.out.println("总计耗时：" + totalCostTime / 1000 / 60 + "分" + (totalCostTime / 1000) % 60 + "秒");
        System.out.println("总计处理了："+totalCount+"条指令");
        System.out.println("平均每条指令耗时："+totalCostTime/totalCount+"毫秒");
        System.out.println("检查指令数："+checkCount+"条");
        System.out.println("指令检查平均耗时：" + checkTotalCost / checkCount + "毫秒");
        System.out.println("发送银行平均耗时：" + (totalCostTime-checkTotalCost) / checkCount + "毫秒");
        System.out.println("查询数据库：" +queryCount + "次");
        System.out.println("查询数据库耗时：" + queryTotalCost/1000 + "秒");


    }

    public static void test11() throws UnsupportedEncodingException {
        String s = "阿斯蒂芬";
        s.getBytes();
    }
    public static void test10() {
        String text = PDFUtil.parsePDF(new File("D:\\1.NSTC\\demand\\2022.7\\排查问题\\4402256019100078262_20220726_22207000008_0000#2560#00307#01342231_00008.pdf"));
        String[] infos = text.split("\r\n");
        boolean isReceiveName = false;
        String payName = "";
        String payNo = "";
        String payBranchName = "";
        String recName = "";
        String recNo = "";
        String recBranchName = "";

        for(int j = 0; j < infos.length; ++j) {
            String info = infos[j];
            if (info.startsWith("电子回单号码:")) {
                info.substring(7).trim();
            } else if (info.startsWith("户　名") && !isReceiveName) {
                payName = info.substring(3).trim();
                isReceiveName = true;
            } else if (info.startsWith("户　名") && isReceiveName) {
                recName = info.substring(3).trim();
            } else if (info.startsWith("账　号")) {
                payNo = info.substring(3, info.lastIndexOf("账　号")).trim();
                recNo = info.substring(info.lastIndexOf("账　号") + 3).trim();
                if (payNo.equals("")) {
                } else {
                }
            } else if (info.startsWith("开户银行")) {
                payBranchName = info.substring(4, info.lastIndexOf("开户银行")).trim();
                recBranchName = info.substring(info.lastIndexOf("开户银行") + 4).trim();
            } else if (info.startsWith("金　额")) {
                String regex = "\\d+(\\.\\d+)?";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(info);
                if (info.contains("人民币")) {
                }

                while(matcher.find()) {
                }
            } else if (info.contains("摘　要")) {
            }
        }
    }

    public static void test9() throws NoSuchAlgorithmException {
        String s = "app_id=1001072&app_secret=A4365A95394E414C&client_id=181008110918153&account_id=2005840000210651start_time=20220729000000end_time=20220729102157A4365A95394E414C";
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        byte[] digest = messageDigest.digest(s.getBytes());
        String sha1Str = trans(digest);
        byte[] encode = Base64.getEncoder().encode(sha1Str.getBytes());
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] digest1 = md5.digest(encode);
        String md5Str = trans(digest1);
        System.out.println(md5Str);
    }
    public static void test8() {
        Calendar cl = Calendar.getInstance();
        cl.setTime(new Date());
        cl.set(10, 0);
        cl.set(11, 0);
        cl.set(12, 0);
        cl.set(13, 0);
        cl.set(14, 0);
        Date time = cl.getTime();
        String s = TextFormat.formatDate(time, "yyyy-MM-dd :HHmmss");
        if (s.endsWith("00")) {

        } else {
            System.out.println(s);
        }
    }
    public static void test7() throws Exception {
        String quote = Pattern.quote("&");
        String ileClassPath = "D:\\1.NSTC\\demand\\2022.7\\14【ID1336269】【兴业-迪欧家具项目】bps-农行ABC2-对银行返回报文的解，添加特殊字符的解析（CQRA10）\\out.res_2000018764344_20220713_113511.011.QRH";
        BufferedReader bufferedReader;
        StringBuilder sb = new StringBuilder();
        String s;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(ileClassPath)));
            while ((s = bufferedReader.readLine()) != null) {
                sb.append(new String(s.getBytes(),"gbk"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String s1 = sb.toString();
        String replace = s1.replace(SPECIFIC_CHARACTER, SPECIFIC_CHARACTER_EXCHANGE);
        Element rootElement = XMLUtil.getRootElement(replace);
        String fileData = rootElement.getChild("Cmp").getChildTextTrim("BatchFileData");
        fileData = fileData.replace(SPECIFIC_CHARACTER_EXCHANGE, SPECIFIC_CHARACTER);
        System.out.println(fileData);
    }
    public static void test6() throws MultiFieldException {
        String records = "serial_no|acno|cur_code|tr_acdt|tr_time|tr_time|host_serial_no|tr_bankno|acname|opp_acno|opp_cur_code|opp_acname|opp_bankname|opp_bankno|tr_type|csh_dra_flag|bank_flag|area_flag|fee_amt|tr_from|trans_flag|old_serial_no|old_acdt|cash_flag|crdr_flag|amt|balance|last_bal|freeze_amt|cert_type|cert_batchno|cert_no|tr_code|user_no|sub_no|purpose|postscript|tr_timestamp|reserved1|reserved2|tr_bankname|bank_no|bankname|printcount|payamt|rcvamt|\n" +
                "|114721000000861|01|20220329|002045|002045|20220329-999800090126061484-340|0037801||114715626000520000049|01|应付智能存款息||0037801|0||||0.00|101|0|||1|C|564258.79|799209.42|0.00|0.00||||02    |99980009||入息||00000340|||北京分行营业部|313100012010|大连银行股份有限公司北京分行||0.00|564258.79|";
        MultiFieldSet set = new MultiFieldSet(records, 46);
        while (set.next()) {
            System.out.println(set.getValue("opp_bankno"));
        }
    }
    public static void test5() {
        Date date = DateUtil.addMonth(new Date(), -12);
        String yyyyMMdd = TextFormat.formatDate8(date);
        System.out.println(yyyyMMdd);

    }
    public static void test4() {

        HashMap<String, String> map = new HashMap<>();
//        map.put("1", "1");
        Integer re = (Integer) Integer.parseInt(map.get("1"));
        System.out.println(re);
    }
    public static void test3() {
        String path = "D:\\1.NSTC\\demand\\2022.6\\tmp";
        File file = new File(path);
        String[] list = file.list();
        for (String fileName : list) {
            String[] name = fileName.split("_");
            String[] split = name[3].split("\\.");
            System.out.println(split);
        }
    }
    public static void test1() {
        String puspose = "代理收款, 代理结算: 代理测试 666622224450 收款";
        int i = puspose.indexOf("代理结算: ") + "代理结算: ".length();
        String nameAndNum = puspose.substring(i);
        String[] split = nameAndNum.split(" ");
        String name = split[0];
        String num = split[1];
        System.out.println("name:" + name);
        System.out.println("num:" + num);
    }
    public static void test2() {
        HashMap<String, String> map1 = new HashMap<>();
        HashMap<String, String> map2 = new HashMap<>();
        HashMap<String, String> map3 = new HashMap<>();
        map1.put("123", "123");
        map2.put("222", "222");
        map3.put("333", "333");
        ArrayList<Map> maps = new ArrayList<>();
        maps.add(map1);
        maps.add(map2);
        maps.add(map3);
        String s = JSON.toJSONString(maps);
        System.out.println("jsonStr:" + s);

        List o = (List<Map<String, String>>) JSON.parse(s);
        System.out.println(o);
    }
    public static String trans(byte[] digest) {
        StringBuilder ret=new StringBuilder(digest.length<<1);
        for(int i=0;i<digest.length;i++){
            ret.append(Character.forDigit((digest[i]>>4)&0xf,16));
            ret.append(Character.forDigit(digest[i]&0xf,16));
        }
        return ret.toString();
    }
}
