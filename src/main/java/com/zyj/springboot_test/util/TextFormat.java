package com.zyj.springboot_test.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 * @Author 周赟吉
 * @Date 2022/6/27 10:40
 * @Description :
 */
public class TextFormat {
    public static final String ZERO = "0";
    public static final String FILLSTR = "00";

    public TextFormat() {
    }

    public static String enclose(Calendar d) {
        return d != null && d.getTime() != null ? enclose(d.getTime()) : null;
    }

    public static String enclose(Date d) {
        if (d == null) {
            return null;
        } else {
            StringBuffer s = new StringBuffer("'");
            s.append(formatDate(d)).append("'");
            return new String(s);
        }
    }

    public static String enclose(String s) {
        return s == null ? null : "'" + s + "'";
    }

    public static String enclose_todate(Calendar d) {
        return d != null && d.getTime() != null ? enclose_todate(d.getTime()) : null;
    }

    public static String enclose_todate(Date d) {
        return d == null ? null : "to_date('" + formatDate(d) + "','YYYY-MM-DD')";
    }

    public static String formatCurrency(double amount) {
        String ret = formatCurrency0(amount);
        if (ret.equals("0.00")) {
            ret = "";
        }

        return ret;
    }


    public static String formatCurrency(BigDecimal amount) {
        return amount == null ? "" : formatCurrency(amount.doubleValue());
    }

    public static String formatCurrency0(BigDecimal amount) {
        return amount == null ? "" : formatCurrency0(amount.doubleValue());
    }

    public static String formatCurrency(Double amount) {
        return amount == null ? "" : formatCurrency(amount);
    }

    public static String formatCurrency0(Double amount) {
        return amount == null ? "" : formatCurrency0(amount);
    }


    public static String formatCurrency0(double amount) {
        String pattern = "#,###,###,###,##0.00";
        String ret = formatNumber(amount, pattern);
        return ret;
    }

    public static String formatDate(Calendar date) {
        return date != null && date.getTime() != null ? formatDate(date.getTime()) : null;
    }

    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
    }

    public static String formatDate8(Date d) {
        return formatDate(d, "yyyyMMdd");
    }

    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatInteger(int n) {
        return formatNumber((double)n, "00000000");
    }

    public static String formatInteger(int n, String pattern) {
        return formatNumber((double)n, pattern);
    }

    public static String formatLongDate(Calendar date) {
        return date != null && date.getTime() != null ? formatLongDate(date.getTime()) : null;
    }

    public static String formatLongDate(Date date) {
        return formatDate(date, "yyyy年MM月dd日");
    }

    public static String formatNumber(double amount, String pattern) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        DecimalFormat df = (DecimalFormat)nf;
        df.setDecimalSeparatorAlwaysShown(true);
        df.applyPattern(pattern);
        return df.format(amount);
    }

    public static String formatNumber(Double amount, String pattern) {
        return amount == null ? "" : formatNumber(amount, pattern);
    }

    public static String formatNumber(BigDecimal amount, String pattern) {
        return amount == null ? "" : formatNumber(amount.doubleValue(), pattern);
    }


    public static String formatNumber(Number amount, String pattern) {
        return amount == null ? "" : formatNumber(amount.doubleValue(), pattern);
    }

    public static String formatPlainCurrency(double amount) {
        String pattern = "############0.00";
        String ret = formatNumber(amount, pattern);
        return ret;
    }

    public static String formatPlainCurrency(Double amount) {
        return amount == null ? "" : formatPlainCurrency(amount);
    }


    public static String formatTime6(Date d) {
        return (new SimpleDateFormat("HHmmss")).format(d);
    }

    public static Calendar getCalendar() {
        Calendar ret = Calendar.getInstance();
        ret.setTime(getDate());
        return ret;
    }

    public static Calendar getCalendar(Date date) {
        if (date == null) {
            return null;
        } else {
            Calendar cl = Calendar.getInstance();
            cl.setTime(date);
            return cl;
        }
    }

    public static Date getDate() {
        return getDate(new Date());
    }

    public static Date getDate(Date date) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.set(10, 0);
        cl.set(11, 0);
        cl.set(12, 0);
        cl.set(13, 0);
        cl.set(14, 0);
        return cl.getTime();
    }

    public static String getDateText() {
        Date d = new Date();
        return formatDate(d);
    }

    public static String getDigitByIndex(double val, int i) {
        return getDigitByIndex(val, i, "￥");
    }


    public static String getDigitByIndex(double val, int i, String prefix) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        DecimalFormat df = (DecimalFormat)nf;
        df.setDecimalSeparatorAlwaysShown(true);
        df.applyPattern("##############0.00");
        String text = df.format(val);
        int point = text.indexOf(".");
        int index = point - i;
        String ret = "";
        if (index == -1) {
            ret = prefix;
        } else if (index >= 0 && index < text.length()) {
            ret = text.substring(index, index + 1);
        }

        return ret;
    }

    public static Calendar parseCalendar(String s) {
        return getCalendar(parseDate(s));
    }

    public static Calendar parseCalendarTime(String s) {
        return getCalendar(parseDateTime(s));
    }

    public static String toFirstCapString(String s) {
        if (s == null) {
            return null;
        } else if (s.length() < 1) {
            return "";
        } else {
            String ch = s.substring(0, 1);
            String ss = s.substring(1);
            return ch.toUpperCase() + ss;
        }
    }

    public static String toFirstLowerString(String s) {
        if (s == null) {
            return null;
        } else if (s.length() < 1) {
            return "";
        } else {
            String ch = s.substring(0, 1);
            String ss = s.substring(1);
            return ch.toLowerCase() + ss;
        }
    }

    public static Date parseDate(String s) {
        if (s == null) {
            return null;
        } else {
            try {
                SimpleDateFormat df = new SimpleDateFormat("y-M-d");
                return df.parse(s);
            } catch (Exception var2) {
                throw new RuntimeException(var2);
            }
        }
    }

    public static Date parseDate(Object o, String pattern) {
        if (o == null) {
            return null;
        } else {
            try {
                SimpleDateFormat df = new SimpleDateFormat(pattern);
                return df.parse(o.toString());
            } catch (Exception var3) {
                throw new RuntimeException(var3);
            }
        }
    }

    public static Date parseDateTime(String s) {
        if (s == null) {
            return null;
        } else if (s.length() < 8) {
            return null;
        } else {
            try {
                String stime = "";
                int loc3 = s.indexOf(" ");
                if (loc3 > 0) {
                    stime = s.substring(loc3 + 1);
                    s = s.substring(0, loc3);
                }

                int loc1 = 0;
                int loc2 = s.indexOf("-");
                int y = Integer.parseInt(s.substring(loc1, loc2));
                loc1 = s.indexOf("-", loc2);
                loc2 = s.indexOf("-", loc1 + 1);
                int m = Integer.parseInt(s.substring(loc1 + 1, loc2));
                int d = Integer.parseInt(s.substring(loc2 + 1));
                int h = 0;
                int min = 0;
                int sec = 0;
                loc1 = stime.indexOf(":");
                if (loc1 > 0) {
                    h = Integer.parseInt(stime.substring(0, loc1));
                    loc2 = stime.indexOf(":", loc1 + 1);
                    min = Integer.parseInt(stime.substring(loc1 + 1, loc2));
                    loc1 = stime.indexOf(":", loc2);
                    loc2 = stime.indexOf(".");
                    String secStr = loc2 > 0 ? stime.substring(loc1 + 1, loc2) : stime.substring(loc1 + 1);
                    sec = Integer.parseInt(secStr);
                }

                Calendar cl = Calendar.getInstance();
                cl.set(y, m - 1, d, h, min, sec);
                return cl.getTime();
            } catch (Exception var12) {
                throw var12;
            }
        }
    }

    public static Date parseDateTimeThrowException(String s) {
        if (s == null) {
            return null;
        } else if (s.length() < 8) {
            return null;
        } else {
            try {
                String stime = "";
                int loc3 = s.indexOf(" ");
                if (loc3 > 0) {
                    stime = s.substring(loc3 + 1);
                    s = s.substring(0, loc3);
                }

                int loc1 = 0;
                int loc2 = s.indexOf("-");
                int y = Integer.parseInt(s.substring(loc1, loc2));
                loc1 = s.indexOf("-", loc2);
                loc2 = s.indexOf("-", loc1 + 1);
                int m = Integer.parseInt(s.substring(loc1 + 1, loc2));
                int d = Integer.parseInt(s.substring(loc2 + 1));
                int h = 0;
                int min = 0;
                int sec = 0;
                loc1 = stime.indexOf(":");
                if (loc1 > 0) {
                    h = Integer.parseInt(stime.substring(0, loc1));
                    loc2 = stime.indexOf(":", loc1 + 1);
                    min = Integer.parseInt(stime.substring(loc1 + 1, loc2));
                    loc1 = stime.indexOf(":", loc2);
                    loc2 = stime.indexOf(".");
                    String secStr = loc2 > 0 ? stime.substring(loc1 + 1, loc2) : stime.substring(loc1 + 1);
                    sec = Integer.parseInt(secStr);
                }

                Calendar cl = Calendar.getInstance();
                cl.set(y, m - 1, d, h, min, sec);
                return cl.getTime();
            } catch (Exception var12) {
                throw new RuntimeException(var12);
            }
        }
    }

    public static double parseDouble(String numberString) {
        try {
            double amount = Double.parseDouble(getDigit(numberString));
            return amount;
        } catch (Exception var3) {
            return 0.0D;
        }
    }

    public static int parseInt(String s) {
        if (s == null) {
            return 0;
        } else {
            String v = "";
            String num = "0123456789-";

            for(int i = 0; i < s.length(); ++i) {
                String ch = s.charAt(i) + "";
                if (num.indexOf(ch) >= 0) {
                    v = v + ch;
                }
            }

            if (v.length() < 1) {
                return 0;
            } else {
                return Integer.parseInt(v);
            }
        }
    }

    public static Integer parseInteger(String s) {
        return new Integer(parseInt(s));
    }

    public static String toHEXString(byte[] b) {
        StringBuffer ret = new StringBuffer();

        for(int i = 0; i < b.length; ++i) {
            int v = b[i] & 255;
            if (v < 16) {
                ret.append('0');
            }

            ret.append(Integer.toString(v, 16).toUpperCase());
        }

        return new String(ret);
    }

    private static String getDigit(String s) {
        String num = "0123456789-.";
        String ret = "";
        if (s != null) {
            for(int i = 0; i < s.length(); ++i) {
                String ch = s.charAt(i) + "";
                if (num.indexOf(ch) >= 0) {
                    ret = ret + ch;
                }
            }
        }

        return ret;
    }

    public static String replace(String s, String oldStr, String newStr) {
        int ei = 0;
        int si;
        if (newStr != null) {
            do {
                si = s.indexOf(oldStr, ei);
                if (si >= 0) {
                    s = s.substring(0, si) + newStr + s.substring(si + oldStr.length());
                    ei = si + newStr.length();
                }
            } while(si >= 0);
        }

        return s;
    }

    public static String getMarkedText(String s, String mark) {
        String begin = "<" + mark + ">";
        String end = "</" + mark + ">";
        int loc1 = s.indexOf(begin);
        int loc2 = s.indexOf(end);
        return loc1 >= 0 && loc2 >= loc1 ? s.substring(loc1 + begin.length(), loc2) : null;
    }

    public static String[] splitByLength(String str, int len) {
        if (str == null) {
            return null;
        } else if (str.length() <= len) {
            return new String[]{str};
        } else {
            int count = (int)(Double.valueOf((double)str.length()) / (double)len + 0.5D);
            String[] result = new String[count];

            for(int i = 0; i < count; ++i) {
                int start = i * len;
                int end = (i + 1) * len < str.length() ? (i + 1) * len : str.length();
                result[i] = str.substring(start, end);
            }

            return result;
        }
    }

    public static String[] split(String s, String tag) {
        int index = 0;
        int offset = tag.length();
        Vector v = new Vector();

        while(true) {
            String ss = "";
            int loc = s.indexOf(tag, index);
            if (loc < 0) {
                ss = s.substring(index);
                v.add(ss);
                String[] ret = new String[v.size()];
                v.copyInto(ret);
                return ret;
            }

            ss = s.substring(index, loc);
            v.add(ss);
            index = loc + offset;
        }
    }

    public static int[] mergeIntArray(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];

        int i;
        for(i = 0; i < a.length; ++i) {
            c[i] = a[i];
        }

        for(i = 0; i < b.length; ++i) {
            c[a.length + i] = b[i];
        }

        return c;
    }

    public static String getLeftPrefixString(String base, int length, String c) {
        StringBuffer sb = new StringBuffer(base);
        if (base.length() > length) {
            sb = sb.delete(length, base.length());
        } else {
            for(int i = base.length(); i < length; ++i) {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static String getRightPrefixString(String base, int length, String c) {
        StringBuffer sb = new StringBuffer();
        if (base.length() > length) {
            sb = new StringBuffer(base.substring(length));
        } else {
            for(int i = base.length(); i < length; ++i) {
                sb.append(c);
            }

            sb.append(base);
        }

        return sb.toString();
    }

    public static int length(String value) {
        int valueLength = 0;
        String chinese = "[Α-￥]";

        for(int i = 0; i < value.length(); ++i) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                ++valueLength;
            }
        }

        return valueLength;
    }

    public static String subStringByByte(String s, int n) {
        byte[] c = s.getBytes();
        int len = c.length > n ? n : c.length;
        byte[] b = new byte[len];

        for(int i = 0; i < b.length; ++i) {
            b[i] = c[i];
        }

        try {
            return new String(b);
        } catch (RuntimeException var6) {
            return "";
        }
    }

    public static boolean isLastChinese(String value) {
        String chinese = ".*[Α-￥]$";
        return value.matches(chinese);
    }

    public static boolean isInChinese(String value, int index) {
        String chinese = ".*[Α-￥]$";
        String temp1 = subStringByByte(value, index);
        String temp2 = subStringByByte(value, index + 1);
        return !temp1.matches(chinese) && temp2.matches(chinese);
    }

    public static String fixString(String str, int len, String elide) {
        if (str == null) {
            str = "";
        }

        if (str.getBytes().length > len) {
            if (isInChinese(str, len)) {
                str = subStringByByte(str, len - 1);
            } else {
                str = subStringByByte(str, len);
            }
        }

        StringBuffer sb = new StringBuffer(str);

        for(int i = 0; i < len - str.getBytes().length; ++i) {
            sb.append(elide);
        }

        return sb.toString();
    }

    public static String fixStringEndWithELide(String str, int len, String elide) {
        if (elide == null) {
            elide = "";
        }

        if (len < elide.getBytes().length) {
            return fixString(elide, len, "");
        } else {
            len -= elide.getBytes().length;
            return fixString(str, len, elide) + elide;
        }
    }

    public static String shiftRight(BigDecimal num) {
        return num == null ? "0" : MoneyUtil.yuanToCent(num);
    }

    public static String shiftLeft(BigDecimal num) {
        return num == null ? "0" : MoneyUtil.centToYuan(num);
    }

}
