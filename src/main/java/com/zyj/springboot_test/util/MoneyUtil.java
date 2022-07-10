package com.zyj.springboot_test.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Author 周赟吉
 * @Date 2022/6/27 10:43
 * @Description :
 */
public class MoneyUtil {
    public MoneyUtil() {
    }

    public static BigDecimal toBigDecimal(String s, int scale) {
        if (null != s && s.trim().length() != 0) {
            if (scale < 0) {
                throw new RuntimeException("保留的小数位数不能为负值.");
            } else {
                BigDecimal amount = new BigDecimal(s.trim());
                s = amount.toPlainString();
                if (!s.contains(".")) {
                    s = s + ".0";
                }

                int n = s.indexOf(".") + scale - s.length();
                StringBuffer sb = new StringBuffer(s);
                if (n > 0) {
                    while(n > 0) {
                        sb.append("0");
                        --n;
                    }
                }

                s = sb.toString();
                if (n == 0) {
                    s = s + "0";
                } else {
                    s = s.substring(0, s.indexOf(".") + scale + 1);
                }

                return new BigDecimal(s);
            }
        } else {
            return null;
        }
    }

    public static BigDecimal toBigDecimal(String s) {
        return toBigDecimal(s, 2);
    }

    public static BigDecimal toAmount(String s) {
        if (null != s && s.trim().length() != 0) {
            BigDecimal amount = new BigDecimal(s.trim());
            return amount;
        } else {
            return null;
        }
    }

    public static String yuanToCent(BigDecimal amount) {
        if (amount == null) {
            throw new RuntimeException("单位为元的金额不能为空");
        } else {
            BigDecimal tmp = null;
            String amt = null;
            if (amount.scale() > 2) {
                tmp = new BigDecimal(amount.longValue());
                tmp = amount.subtract(tmp);
                amt = tmp.toPlainString();
                int i = amt.lastIndexOf(".");
                amt = "0.00" + amt.substring(i + 3);
                tmp = new BigDecimal(amt);
                double d = tmp.doubleValue();
                if (d != 0.0D) {
                    throw new RuntimeException("单位为元的金额不能超过2位小数");
                }
            }

            tmp = amount.setScale(2, RoundingMode.DOWN);
            amt = tmp.toPlainString();
            amt = amt.replace(".", "");
            boolean negative = false;
            if (amt.startsWith("-")) {
                negative = true;
                amt = amt.substring(1);
            }

            while(amt.startsWith("0") && amt.length() > 1) {
                amt = amt.substring(1);
            }

            if (negative) {
                amt = "-" + amt;
            }

            return amt;
        }
    }

    public static String centToYuan(BigDecimal amount) {
        if (amount == null) {
            throw new RuntimeException("单位为分的金额不能为空");
        } else {
            BigDecimal tmp = null;
            boolean negative = false;
            if (amount.doubleValue() < 0.0D) {
                negative = true;
            }

            if (amount.scale() > 0) {
                tmp = new BigDecimal(amount.longValue());
                tmp = amount.subtract(tmp);
                double d = tmp.doubleValue();
                if (d != 0.0D) {
                    throw new RuntimeException("单位为分的金额不能存在小数");
                }
            }

            tmp = new BigDecimal(amount.longValue());
            String amt = tmp.toPlainString();
            if (negative) {
                amt = amt.substring(1);
            }

            while(amt.length() < 3) {
                amt = "0" + amt;
            }

            amt = amt.substring(0, amt.length() - 2) + "." + amt.substring(amt.length() - 2);
            if (negative) {
                amt = "-" + amt;
            }

            return amt;
        }
    }
}
