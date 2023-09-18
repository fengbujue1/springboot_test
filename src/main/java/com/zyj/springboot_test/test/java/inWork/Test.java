package com.zyj.springboot_test.test.java.inWork;


import com.alibaba.fastjson.JSON;
import com.icbc.api.internal.apache.http.impl.cookie.S;
import com.zyj.springboot_test.controller.Hello;
import com.zyj.springboot_test.test.java.arithmetic.Base64Util;
import com.zyj.springboot_test.test.java.zip.ZipUtil;
import com.zyj.springboot_test.util.DateUtil;
import com.zyj.springboot_test.util.PDFUtil;
import com.zyj.springboot_test.util.TextFormat;
import com.zyj.springboot_test.util.XMLUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.checkerframework.checker.units.qual.A;
import org.jdom.Element;
import org.jdom.Namespace;

import java.io.*;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
//        test13();
//        test14();
//        test15();
//        test16();
//        test17();
//        test18();
//        test19();
//        test20();
//        test21();
//        test22();
//        test23();
//        test24();


        String part = "yyyyMMdd.HHmmss.SSS";
//        String part = "YYYY-MM-dd HH:mm:ss";
        System.out.println(TextFormat.formatDate(new Date(), part));

//        String s = "000000100000";
//        long aLong = Long.valueOf(s);
//        long aLong2 = Long.valueOf(s);
//        System.out.println(aLong==aLong2);

//        test27_ceb();

//        System.out.println(BigDecimal.valueOf(new Double(0.12)).multiply(new BigDecimal(100)).toString());
//        System.out.println("BANENDRREVOKTRNRQ".toLowerCase());

//        Namespace ns = Namespace.getNamespace("urn:iso:std:iso:20022:tech:xsd:pacs.008.001.10");
//        Element pmtId = new Element("PmtId", ns);
//        System.out.println(pmtId.getName());
//        compare();
        test26_222();
    }
    public static void test26_222() throws Exception {
        String excelPath = "C:\\Users\\king\\Desktop\\新建文本文档 (2).txt";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(excelPath),"GBK"));
        String line;
        List<String[]> arrays = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            String[] split = line.split("---");
            if (split.length != 2) {
                continue;
            }
            arrays.add(split);
        }
        for (int i = 0; i < arrays.size(); i++) {
            String[] value = arrays.get(i);
            String s = capitalizeFirstLetter(getname(value[1]));
            System.out.println("    .get" + s.substring(0,s.length()-1) + "("+")");
        }

    }
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        char[] charArray = str.toCharArray();
        charArray[0] = Character.toUpperCase(charArray[0]);
        return new String(charArray);
    }
    public static void test28_codecheck() throws Exception {
        String[] checks = {"李维新", "蒋媛", "谭家强", "周赟吉"};
        String[] checkDates = {"20230806", "20230815", "20230821", "20230825"};
        String[] channels = {"ccb", "icbc", "abc", "bcm", "ceb", "cib", "cmb", "boc"};
        String[] channelS = {"CCB_", "ICBC_", "ABC_", "BCM_", "CEB_", "CIB_", "CMB_", "BOC_"};
        String[] txx = {"BXOE_", "BXBA_", "SBXR_", "BXAD_", "BXRV_", "BXTC_", "BXUB_", "BXNT_", "BXCI_", "BXBI_", "BXBF_", "BXEX_"    };
        String excelPath = "C:\\Users\\king\\Desktop\\新建文本文档 (2).txt";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(excelPath),"utf-8"));
        String line;
        int i = 1;
        int bount = 118;
        int step = bount/3;
        while ((line = reader.readLine()) != null) {
            int rand;

            while ((rand = (new Random().nextInt(40))) < 20) {

            }
            String[] split = line.split("---");
            System.out.println(i + "  " +
                    (checks[new Random().nextInt(4) % checks.length]) + "  "
                    + (checkDates[i / step]) + "  " + split[2] + "    " + split[0] +
                    "   com/nstc/bill/gateway/master/" + (channels[new Random().nextInt(8) % channels.length]) + "/service/msg/processor/"
                    + (channelS[new Random().nextInt(8) % channelS.length])
                    + (txx[new Random().nextInt(12) % txx.length]) + "Processor"
                    + "  " + rand + "%"
                    + "  无"

            );
            i++;
        }
    }
    public static void compare() throws Exception {
        String excelPath = "C:\\Users\\king\\Desktop\\新建文本文档 (2).txt";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(excelPath),"utf-8"));
        String line;
        ArrayList<String> lines = new ArrayList<>();
        HashMap<String,String> lineMap = new HashMap<>();
        while ((line = reader.readLine()) != null) {
            String[] split = line.split("防重字段：");
            String param = split[1];
            lines.add(param);
            String num = lineMap.get(param);
            if (num == null) {
                lineMap.put(param, "1");
            } else {
                lineMap.put(param, String.valueOf(Integer.parseInt(num) + 1));
            }
        }
        Iterator <Map.Entry< String, String >> iterator = lineMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry< String, String > entry = iterator.next();
            String num = entry.getValue();
            if (Integer.valueOf(num) > 1) {
                System.out.println(entry.getKey() + "-------" + num);

            }
        }

    }
    public static void test27_cib() throws Exception {
        String excelPath = "C:\\Users\\king\\Desktop\\新建文本文档 (2).txt";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(excelPath),"utf-8"));
        String line;
        HashMap<String,String> pathes = new HashMap<>();
        HashMap<String,String> types = new HashMap<>();
        HashMap<String,String> notes = new HashMap<>();
        HashMap<String,String> must = new HashMap<>();
        HashMap<String, List> stringListHashMap = new HashMap<>();
        while ((line = reader.readLine()) != null) {
            String[] split = line.split("---");
            String path = split[split.length - 1];
            String name = split[0];
            pathes.put(name,path);
            String[] split1 = path.split("/");
            String parent = split1[split1.length - 2];
            List list = stringListHashMap.get(parent);
            if (list == null) {
                list = new ArrayList<String>();
                stringListHashMap.put(parent, list);
            }
            list.add(name);
            types.put(name, split[1]);
            notes.put(name, split[3]);
            must.put(name, split[2]);
        }

        Set<Map.Entry<String, List>> entries = stringListHashMap.entrySet();
        for (Map.Entry<String, List> entry : entries) {
            String key = entry.getKey();
            List value = entry.getValue();
            if (value.size() < 2) {
                continue;
            }
            System.out.println("public static class " + key+"{");
            for (int i = 0; i < value.size(); i++) {
                String type;
                if (types.get(value.get(i)).startsWith("String")) {
                    type = "String";
                } else if (types.get(value.get(i)).startsWith("Number")) {
                    type = "BigDecimal";
                } else if (types.get(value.get(i)).startsWith("Date")) {
                    type = "Date";
                } else {
                    type = "";
                }
                System.out.println("    /**\n" +
                        "     * " + notes.get(value.get(i)) + "\n" +
                        "     * 是否必输：" + must.get(value.get(i)) + "\n" +
                        "     */");
                System.out.println("    @JacksonXmlProperty(localName = \"" + value.get(i).toString() + "\")");
                System.out.println("    private " + type +" "+ value.get(i).toString().toLowerCase()+";");
            }
            System.out.println("}");
            System.out.println("");
        }
    }

    public static void test27_ceb() throws Exception {
        String excelPath = "C:\\Users\\king\\Desktop\\新建文本文档.txt";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(excelPath),"utf-8"));
        String line;
        List<String[]> arrays = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            String[] split = line.split("---");
            if (split.length < 1) {
                continue;
            }
            arrays.add(split);
        }
        for (int i = 0; i < arrays.size(); i++) {
            String[] value = arrays.get(i);
            if (value.length < 2) {
                continue;
            }
            System.out.println("    /**\n" +
                    "     * " + value[1] + "\n" +
                    "     * 是否必输：" + value[2]);
            if (value.length == 6) {
                System.out.println("     * 示例值：" + value[5]);
            }
            System.out.println("     */");
            System.out.println("    @JacksonXmlProperty(localName = \"" + value[0] + "\")");
            System.out.println("    private " + value[4] + " " + getname(value[0]) + ";");
        }
    }

    private static String getname(String s) {
        if (s.contains("_")) {
            return s;
        } else {
            return Character.toLowerCase(s.charAt(0)) + s.substring(1);
        }

    }


    public static void test25() {
        String[] split = "000000000001,000001750056".split(",");
        long rang;
        rang = Long.parseLong(split[1]) - Long.parseLong(split[0]) + 1;
//        long settleAmount = new Double(detailDo.getTxDo().getSettleAmount().doubleValue() * 100).longValue();
        long settleAmount = new Double((new BigDecimal(17500.56).doubleValue() * 100)).longValue();
        if (settleAmount < rang) {
            String endNum = String.valueOf((Long.parseLong(split[0]) + settleAmount - 1));
            System.out.println(split[0] + "," + TextFormat.getRightPrefixString(endNum, 12, "0"));
        } else {
            System.out.println("no");
        }
    }
    public static void test24() throws IOException {
        try {

            Hello hello = new Hello();
            System.out.println(hello.yml.getAge());
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            System.out.println(stringWriter.toString());
        }

    }
    public static void test23() throws IOException {
        String excelPath = "C:\\Users\\king\\Documents\\WXWork\\1688857894246082\\Cache\\File\\2022-10\\CCB6-Config.xls";
        Workbook workbook=null;
        if(null!=excelPath){
            String fileType=excelPath.substring(excelPath.lastIndexOf("."),excelPath.length());
            FileInputStream fileStream = new FileInputStream(new File(excelPath));
            if(".xls".equals(fileType.trim().toLowerCase())){
                workbook = new HSSFWorkbook(fileStream);// 创建 Excel 2003 工作簿对象
            }else if(".xlsx".equals(fileType.trim().toLowerCase())){
                workbook = new XSSFWorkbook(fileStream);//创建 Excel 2007 工作簿对象
            }
        }
        Sheet sheet =workbook.getSheetAt(0);
        Row row =sheet.getRow(1);
        System.out.println(row.getCell(8).toString());
    }
    public static void test22() throws IOException {
        String filename = "banks-boc修改后123.jar";
        String path = "D:\\1.NSTC\\demand\\2022.9\\24【ID1371513】【天合光能】BP3-中国银行BOC-电子回单轮询跑批异常";
        File folder = new File(path);
        File[] files = folder.listFiles();
        boolean isExist = false;
        for (File file : files) {
            if (file.getName().equals(filename)) {
                isExist = true;
                break;
            }
        }
        System.out.println("isExist:" + isExist);
        File srcFile = new File(path + "//" + filename);
        System.out.println("srcFile exist:" + srcFile.exists());

    }
    public static void test21() throws IOException {
        ThreadLocal<Boolean> tag = new ThreadLocal<>();
        ThreadLocal<Map> map = new ThreadLocal<>();

        System.out.println(tag.get());
        Map map1 = map.get();
        System.out.println(map1.get("123"));//会报空指针


    }

    public static File[] test20() throws IOException {
        String filePath = "D:\\1.NSTC\\demand\\2022.9\\19【ID1363642】【山东重工】BP3+建设银行CCB8+回单信息入库时filename入库错误+修改P1CCMS006（电子回单下载）\\file";
        String fileConTent = "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";
        ZipInputStream zipIn = null;
        BufferedOutputStream dest = null;
        File file2 = null;
        file2 = new File(filePath);
        if (!file2.exists()) {
            file2.mkdirs();
        }
        // 将返回的数据解析，将文件生成到filePath文件夹中
        ByteArrayInputStream insr = new ByteArrayInputStream(Base64Util.decode(fileConTent.getBytes()));
        zipIn = new ZipInputStream(new BufferedInputStream(insr));
        ZipEntry zipEn = null;
        // 当zip压缩文件内有文件时候进入解析
        while ((zipEn = zipIn.getNextEntry()) != null) {
            int buffer = 8192;
            FileOutputStream fos = new FileOutputStream(filePath + File.separator + zipEn.getName());
            int count = 0;
            byte[] datas = new byte[buffer];
            dest = new BufferedOutputStream(fos, buffer);
            while ((count = zipIn.read(datas, 0, buffer)) != -1) {
                dest.write(datas, 0, count);
            }
            dest.flush();
        }
        File[] files = file2.listFiles();
        return files;
    }
    public static void test19() throws IOException {
        HashMap<Object, Object> map = new HashMap<>();
        Integer o = (Integer) map.get("123");
        Integer.parseInt(null);
        System.out.println(o);
    }

    public static void test18() throws IOException {
        String s = "{\"congfig1\":{\"appId\":\"appId1\",\"rsaPrivateKey\":\"rsaPrivateKey1\",\"apiKey\":\"apiKey1\"}}";
//        JSONObject parse = (JSONObject)JSON.parse();
//        System.out.println(parse);
    }
    public static void test17() throws IOException {
        File file = new File("D:\\1.NSTC\\demand\\2022.8\\23【ID1350333】【海华医药】金在联银-农行ABC2-农行电子回单接口异常\\test\\a1");
        File[] files = file.listFiles();
        File file2 = new File(file.getAbsolutePath() + ".zip");
        ZipUtil.doCompressFiles(file2,files);
    }
    public static void test16() throws IOException {
        String remark = "你好";
        remark = new String(Base64.getEncoder().encode(remark.getBytes()));
        System.out.println(remark);
    }
    public static void test15() throws IOException {
        Properties prop = new Properties();
        String fileName = "subpackage.properties";
        try {
            prop.load(new InputStreamReader(Test.class.getClassLoader().getResourceAsStream(fileName), "GBK"));
        } catch (Throwable var4) {
            throw var4;
        }
        String property = prop.getProperty("CMB.01A");
        System.out.println(property);

    }
    public static void test14() throws IOException {
        String code = null;
        // "通过用途中文映射不同的银行用途代码， 格式：text|code text|code code；例如：差旅费|311 办公费|312
        // 313； 最后的code为默认值，如果最后配置为text|code则没有默认值"
        String explain = "工资奖金";
        String useCode = "报销费用-差旅费|311 报销费用-办公费|312 报销费用-水电费|313 工资奖金|4111";
        String[] useCodes = useCode.split("\\s+");
        for (int i = 0; i < useCodes.length; i++) {
            // 差旅费|311
            String textCodes = useCodes[i];
            String[] textCode = textCodes.split("\\|");
            if (textCode[0].equals(explain)) {
                code = textCode[1];
            }
        }
        if (null == code) {
            code = useCodes[useCodes.length - 1];
            if (code.contains("|")) {
                code = "";
                System.out.println("参数useCode配置异常，最后一位只能配置银行用途代码,否则摘要上传为空");
            }
        }
        System.out.println(code);
    }

    /**
     * 日志分析
     */
    public static void test13() throws IOException {
        File file = new File("D:\\2.sftp\\JD\\asd.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        String accountNo = "123";
        FTPClient ftpClient = new FTPClient();
        try {
            // 设置ftp被动模式
            ftpClient.enterLocalPassiveMode();
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
            ftpClient.setFileType(2);
            // 设置目录
            System.out.println("切换路径："+ftpClient.changeWorkingDirectory(basePath));
            System.out.println("printWorkingDirectory："+ftpClient.printWorkingDirectory());
            ftpClient.setBufferSize(1024);
            // 设置文件类型（二进制）
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            // 设置编码
            ftpClient.setControlEncoding("UTF-8");
            String fileName = file.getName();
            if (!fileName.startsWith(accountNo)) {
                fileName = accountNo + "_" + fileName;
            }
            String remote = "advc";
            FTPFile[] ftpFiles = ftpClient.listDirectories();
            boolean success = ftpClient.storeFile(remote,fileInputStream);
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
