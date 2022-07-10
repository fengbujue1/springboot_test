package com.zyj.springboot_test.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @Author 周赟吉
 * @Date 2022/6/27 10:38
 * @Description :
 */
public class DateUtil {
    private static final long MILLIS_PER_SECOND = 1000L;
    private static final long MILLIS_PER_MINUTE = 60000L;
    private static final long MILLIS_PER_HOUR = 3600000L;
    private static final long MILLIS_PER_DAY = 86400000L;
    private static final int SEMI_MONTH = 1001;
    private static final int[][] fields = new int[][]{{14}, {13}, {12}, {11, 10}, {5, 5, 9}, {2, 1001}, {1}, {0}};
    private static final int RANGE_WEEK_SUNDAY = 1;
    private static final int RANGE_WEEK_MONDAY = 2;
    private static final int RANGE_WEEK_RELATIVE = 3;
    private static final int RANGE_WEEK_CENTER = 4;
    private static final int RANGE_MONTH_SUNDAY = 5;
    private static final int RANGE_MONTH_MONDAY = 6;
    private static final int MODIFY_TRUNCATE = 0;
    private static final int MODIFY_ROUND = 1;
    private static final int MODIFY_CEILING = 2;

    public DateUtil() {
    }

    public static Date addDay(Date d, int n) {
        return dateAdd(d, 5, n);
    }

    public static Date addMonth(Date d, int n) {
        return dateAdd(d, 2, n);
    }

    public static Date addYear(Date d, int n) {
        return dateAdd(d, 1, n);
    }

    public static Date dateAdd(Date d, int mode, int n) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(d);
        cl.add(mode, n);
        return cl.getTime();
    }

    public static int dateDiff(Date d1, Date d2) {
        if (d1.after(d2)) {
            return 0 - dateDiff(d2, d1);
        } else {
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c1.setTime(d1);
            c2.setTime(d2);
            int d = c2.get(6) - c1.get(6);
            int y1 = getYear(d1);

            for(int y2 = getYear(d2); y1 < y2; y1 = c1.get(1)) {
                Date d0 = encodeDate(y1, 12, 31);
                c1.setTime(d0);
                d += c1.get(6);
                c1.add(1, 1);
            }

            return d;
        }
    }

    public static Date encodeDate(int y, int m, int d) {
        Calendar cl = Calendar.getInstance();
        cl.set(1, y);
        cl.set(2, m - 1);
        cl.set(5, d);
        cl.set(10, 0);
        cl.set(11, 0);
        cl.set(12, 0);
        cl.set(13, 0);
        cl.set(14, 0);
        return cl.getTime();
    }

    public static int getDay(Date d) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(d);
        return cl.get(5);
    }

    public static int getMonth(Date d) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(d);
        return cl.get(2) + 1;
    }

    public static Date getMonthEnd(Date date) {
        Date d = getMonthFirst(date);
        d = addMonth(d, 1);
        d = addDay(d, -1);
        return d;
    }

    public static Date getMonthFirst(Date date) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        int y = cl.get(1);
        int m = cl.get(2) + 1;
        return encodeDate(y, m, 1);
    }

    public static Date getYearEnd(Date date) {
        return encodeDate(getYear(date), 12, 31);
    }

    public static Date getYearFirst(Date date) {
        return encodeDate(getYear(date), 1, 1);
    }

    public static Date getLastWeekDay(Date date, int weekDay) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        int dw = cl.get(7);
        int n = toDayOfWeek(weekDay) - dw;
        if (n > 0) {
            n -= 7;
        }

        return addDay(date, n);
    }

    public static Date getNextWeekDay(Date date, int weekDay) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        int dw = cl.get(7);
        int n = toDayOfWeek(weekDay) - dw;
        if (n < 0) {
            n += 7;
        }

        return addDay(date, n);
    }

    public static int toDayOfWeek(int weekDay) {
        switch(weekDay) {
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 5;
            case 5:
                return 6;
            case 6:
                return 7;
            case 7:
                return 1;
            default:
                return 0;
        }
    }

    public static int getDayOfWeek(Date date) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        int dw = cl.get(7);
        return dw;
    }

    public static Date getLastSunday(Date date) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        int dw = cl.get(7);
        int n = 1 - dw;
        if (n > 0) {
            n -= 7;
        }

        return addDay(date, n);
    }

    public static int getYear(Date d) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(d);
        return cl.get(1);
    }

    public static Date truncate(Date date, int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else {
            Calendar gval = Calendar.getInstance();
            gval.setTime(date);
            modify(gval, field, 0);
            return gval.getTime();
        }
    }

    public static Calendar truncate(Calendar date, int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else {
            Calendar truncated = (Calendar)date.clone();
            modify(truncated, field, 0);
            return truncated;
        }
    }

    public static Date truncate(Object date, int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else if (date instanceof Date) {
            return truncate((Date)date, field);
        } else if (date instanceof Calendar) {
            return truncate((Calendar)date, field).getTime();
        } else {
            throw new ClassCastException("Could not truncate " + date);
        }
    }

    private static void modify(Calendar val, int field, int modType) {
        if (val.get(1) > 280000000) {
            throw new ArithmeticException("Calendar value too large for accurate calculations");
        } else if (field != 14) {
            Date date = val.getTime();
            long time = date.getTime();
            boolean done = false;
            int millisecs = val.get(14);
            if (0 == modType || millisecs < 500) {
                time -= (long)millisecs;
            }

            if (field == 13) {
                done = true;
            }

            int seconds = val.get(13);
            if (!done && (0 == modType || seconds < 30)) {
                time -= (long)seconds * 1000L;
            }

            if (field == 12) {
                done = true;
            }

            int minutes = val.get(12);
            if (!done && (0 == modType || minutes < 30)) {
                time -= (long)minutes * 60000L;
            }

            if (date.getTime() != time) {
                date.setTime(time);
                val.setTime(date);
            }

            boolean roundUp = false;

            for(int i = 0; i < fields.length; ++i) {
                int offset;
                for(offset = 0; offset < fields[i].length; ++offset) {
                    if (fields[i][offset] == field) {
                        if (modType == 2 || modType == 1 && roundUp) {
                            if (field == 1001) {
                                if (val.get(5) == 1) {
                                    val.add(5, 15);
                                } else {
                                    val.add(5, -15);
                                    val.add(2, 1);
                                }
                            } else if (field == 9) {
                                if (val.get(11) == 0) {
                                    val.add(11, 12);
                                } else {
                                    val.add(11, -12);
                                    val.add(5, 1);
                                }
                            } else {
                                val.add(fields[i][0], 1);
                            }
                        }

                        return;
                    }
                }

                offset = 0;
                boolean offsetSet = false;
                switch(field) {
                    case 9:
                        if (fields[i][0] == 11) {
                            offset = val.get(11);
                            if (offset >= 12) {
                                offset -= 12;
                            }

                            roundUp = offset >= 6;
                            offsetSet = true;
                        }
                        break;
                    case 1001:
                        if (fields[i][0] == 5) {
                            offset = val.get(5) - 1;
                            if (offset >= 15) {
                                offset -= 15;
                            }

                            roundUp = offset > 7;
                            offsetSet = true;
                        }
                }

                if (!offsetSet) {
                    int min = val.getActualMinimum(fields[i][0]);
                    int max = val.getActualMaximum(fields[i][0]);
                    offset = val.get(fields[i][0]) - min;
                    roundUp = offset > (max - min) / 2;
                }

                if (offset != 0) {
                    val.set(fields[i][0], val.get(fields[i][0]) - offset);
                }
            }

            throw new IllegalArgumentException("The field " + field + " is not supported");
        }
    }
}
