package com.zyj.springboot_test.util;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * @Author 周赟吉
 * @Date 2022/7/29 15:09
 * @Description :
 */
public class PDFUtil {
    public static String parsePDF(File file) {
        String result = null;
        FileInputStream is = null;
        PDDocument document = null;

        try {
            is = new FileInputStream(file);
            PDFParser parser = new PDFParser(is);
            parser.parse();
            document = parser.getPDDocument();
            PDFTextStripper stripper = new PDFTextStripper();
            result = stripper.getText(document);
        } catch (FileNotFoundException var21) {
            var21.printStackTrace();
        } catch (IOException var22) {
            var22.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException var20) {
                    var20.printStackTrace();
                }
            }

            if (document != null) {
                try {
                    document.close();
                } catch (IOException var19) {
                    var19.printStackTrace();
                }
            }

        }

        return result;
    }

}
