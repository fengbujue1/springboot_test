package com.zyj.springboot_test.test.java.basic_test;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeTest {
    public static void main(String[] args) {
//        System.out.println(DateTime.now().getMillis());
//        System.out.println(calcDailyRenewTime(0));
//        testcCalcYearDay();
        testTransFormart();
    }

    public static void testTransFormart() {
        Date date = new Date();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
        String format1 = df1.format(date);
        String format2 = df2.format(date);
        System.out.println(format1+"T"+format2);;
    }

    public static void testcCalcYearDay() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String strDateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String format = sdf.format(date);
        System.out.println(format);
        String regex = "-";
        String[] split = format.split(regex);
        System.out.println(split[0]);
        System.out.println(split[1]);
        System.out.println(split[2]);
        System.out.println("-----------------------");

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DATE);
        System.out.println(year);
        System.out.println(month);
        System.out.println(day);
    }

    public static long calcDailyRenewTime(int hour) {
        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(System.currentTimeMillis()+500)/1000*1000);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date = cal.get(Calendar.DATE);

        cal.set(year, month, date, hour, 0, 0);
        return cal.getTimeInMillis() / 1000 * 1000;
    }
}
