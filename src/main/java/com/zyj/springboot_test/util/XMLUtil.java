package com.zyj.springboot_test.util;

import java.io.IOException;
import java.io.StringReader;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * Description:
 * 参考文档归档于：http://192.168.0.2/svn/doc/Doc.Dev/BP/开发备忘/Wiki/交易报文规范/TX0022-跨境人民币支付（CIPS）.xlsx
 *
 * @author 周赟吉
 * @since 2021/12/7
 */
public class XMLUtil {
    public static Element getRootElement(String s) throws Exception {
        StringReader in = null;

        Element var5;
        try {
            in = new StringReader(s);
            SAXBuilder builder = new SAXBuilder(false);
            Document document = builder.build(in);
            Element ret = document.getRootElement();
            var5 = ret;
        } finally {
            if (in != null) {
                in.close();
            }

        }

        return var5;
    }

    public static String buildText(Document document) throws IOException {
        XMLOutputter out = new XMLOutputter();
        Format format = Format.getPrettyFormat();
        format.setIndent("   ");
        format.setExpandEmptyElements(true);
        format.setLineSeparator("\r\n");
        out.setFormat(format);
        return out.outputString(document);
    }

    public static String buildText(Element element) throws IOException {
        Document document = new Document(element);
        XMLOutputter out = new XMLOutputter();
        Format format = Format.getPrettyFormat();
        format.setIndent("   ");
        format.setExpandEmptyElements(true);
        format.setLineSeparator("\r\n");
        out.setFormat(format);
        return out.outputString(document);
    }
}
