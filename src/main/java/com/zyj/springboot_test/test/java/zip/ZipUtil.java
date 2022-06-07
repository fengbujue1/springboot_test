package com.zyj.springboot_test.test.java.zip;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;

/**
 * @Author 周赟吉
 * @Date 2022/6/7 14:43
 * @Description :
 */
public class ZipUtil {

    public static void main(String[] args) throws Exception {
//        unZip(new File("D:\\1.NSTC\\demand\\2022.5\\tmpdir\\front_down\\202110151634281677551\\20220519\\detail.zip"),
//                "D:\\1.NSTC\\demand\\2022.5\\tmpdir\\front_down\\202110151634281677551\\20220519", false);

        unzip2(new File("D:\\1.NSTC\\demand\\2022.5\\tmpdir\\front_down\\202110151634281677551\\20220519\\detail.zip"),
                "D:\\1.NSTC\\demand\\2022.5\\tmpdir\\front_down\\202110151634281677551\\20220519");
    }

    public static void unZip(File file, String destPath, boolean delete) throws Exception {
        ZipFile zip = null;
        BufferedOutputStream dest = null;
        OutputStream out = null;
        InputStream in = null;

        try {
            zip = new ZipFile(file, "GBK");
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

    public static void unzip2(File zipFile, String descDir) {
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
        }
    }

    private static ZipArchiveInputStream getZipFile(File zipFile) throws Exception {
        return new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
    }
}
