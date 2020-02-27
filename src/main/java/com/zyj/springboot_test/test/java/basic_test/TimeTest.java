package com.zyj.springboot_test.test.java.basic_test;

import org.joda.time.DateTime;

import java.util.Calendar;

public class TimeTest {
    public static void main(String[] args) {
        System.out.println(DateTime.now().getMillis());
        System.out.println(calcDailyRenewTime(0));

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
