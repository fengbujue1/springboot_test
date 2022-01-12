package com.zyj.springboot_test.test.java.basic_test;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeTest {
    public static void main(String[] args) throws ParseException {
//        System.out.println(DateTime.now().getMillis());
//        System.out.println(calcDailyRenewTime(0));
//        testcCalcYearDay();
        testTransFormart();
//        testDemoInCompany();
    }

    public static void testTransFormart() throws ParseException {
        Date date = new Date();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS");
        SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
        String format2 = df2.format(date);
        String format = df1.format(date);
        System.out.println(format);
        Date parse = df1.parse("2022-01-05-15.00.07.366000");
//        Date parse = df1.parse(format);
        System.out.println(parse.toString());
    }

    public static void testDemoInCompany() {
        Calendar cl = Calendar.getInstance();
        cl.setTime(new Date());
        cl.set(10, 0);//Calendar.HOUR
        cl.set(11, 0);//Calendar.HOUR_OF_DAY
        cl.set(12, 0);//Calendar.MINUTE
        cl.set(13, 0);//Calendar.SECOND
        cl.set(14, 0);//Calendar.MILLISECOND
        System.out.println(cl.getTime().toString());;
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
