package com.zyj.springboot_test.util;

import com.zyj.springboot_test.util.excelModel.Warrior;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {
    private static String Star = "";
    private static String Job = "";
    private static String HeroId = "";
    private static String Level = "";
    private static String Pos = "";
    private static String Coefficien = "";
    private static String AtkCoefficien = "";
    private static String DefCoefficien = "";
    private static String HpCoefficien = "";
    private static String leftHp = "";

    private static String failure = "";
    private static String win = "";
    private static String outOfTime = "";

    public static boolean start = false;
    public static boolean end =true;

    public static ArrayList<Warrior> list =new ArrayList<Warrior>();



    public static void main(String[] args) throws IOException, IllegalAccessException {
        FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\zyj\\temp\\无防御2\\10000无防御.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet1 = workbook.getSheet("sheet1");
        int num = sheet1.getLastRowNum();
        for (int i = 0; i < num; i++) {
            XSSFRow row = sheet1.getRow(i);
            if (row != null) {
                XSSFCell cell1 = row.getCell(0);
                XSSFCell cell2 = row.getCell(1);
                createModel(cell1, cell2);
            }
        }
        System.out.println("end");
        createBook();

    }
    private static void createBook() throws IllegalAccessException, IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        writeSheet("战士",workbook);
        writeSheet("肉坦",workbook);
        writeSheet("游侠",workbook);
        writeSheet("刺客",workbook);
        writeSheet("牧师",workbook);
        writeSheet("法师",workbook);
        File file = new File("C:\\Users\\Administrator\\Desktop\\zyj\\temp\\无防御2\\10000无防御trans.xlsx");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();
        System.out.println("end");
    }

    private static void writeSheet(String job,XSSFWorkbook workbook) throws IllegalAccessException, IOException {
        int rowNum = 0;
        XSSFSheet sheet = workbook.createSheet(job);
        XSSFRow row = sheet.createRow(rowNum++);
        Class<Warrior> warriorClass = Warrior.class;
        Field[] declaredFields = warriorClass.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(declaredFields[i].getName());
            if (declaredFields[i].getName().equals("failure")) {
                cell.setCellValue("失败场数为");
            } else if (declaredFields[i].getName().equals("win")) {
                cell.setCellValue("胜利场数为");
            } else if (declaredFields[i].getName().equals("outOfTime")) {
                cell.setCellValue("超时场数为");
            } else if (declaredFields[i].getName().equals("leftHp")) {
                cell.setCellValue("失败剩余血量百分比");
            }
            declaredFields[i].setAccessible(true);
        }

        for (Warrior warrior : list) {
            if (warrior.getJob().trim().equals(job)) {
                XSSFRow rowX = sheet.createRow(rowNum++);
                for (int i = 0; i < declaredFields.length; i++) {
                    XSSFCell cell = rowX.createCell(i);
                    cell.setCellType(CellType.STRING);
                    if (i != 1) {
                        double v;
                        if (!declaredFields[i].get(warrior).toString().isEmpty()) {
                            v = Double.parseDouble(declaredFields[i].get(warrior).toString());
                        } else {
                            v = 0;
                        }
                        cell.setCellValue(v);
                    } else {
                        cell.setCellValue(declaredFields[i].get(warrior).toString());
                    }


                }
            }
        }


    }
    private static void flush(){
        Star = "";
        Job = "";
        HeroId = "";
        Level = "";
        Pos = "";
        Coefficien = "";
        AtkCoefficien = "";
        HpCoefficien = "";
        DefCoefficien = "";
        failure = "";
        win = "";
        outOfTime = "";
        leftHp = "";
    }

    private static void createModel(XSSFCell cell1, XSSFCell cell2) {
        String title = "";
        String value = "";
        if (cell2 != null) {
            cell2.setCellType(CellType.STRING);
            value = cell2.getStringCellValue();
        }
        if (cell1 != null) {
            cell1.setCellType(CellType.STRING);
            title = cell1.getStringCellValue().trim();
        }
        if (cell1 != null && cell2 == null) {
            list.add(new Warrior(Star, Job, HeroId, Level, Pos, Coefficien, AtkCoefficien,DefCoefficien, HpCoefficien, failure, win, outOfTime,leftHp));
            flush();
        }


        if (end && (!start) && title.startsWith("Star")) {
            start = true;
        }
        if (start) {
            switch (title) {
                case "Star":
                    Star = value;
                    break;
                case "Job":
                    Job = value;
                    break;
                case "HeroId":
                    HeroId = value;
                    break;
                case "Level":
                    Level = value;
                    break;
                case "Pos":
                    Pos = value;
                    break;
                case "Coefficien":
                    Coefficien = value;
                    break;
                case "AtkCoefficien":
                    AtkCoefficien = value;
                    break;
                case "DefCoefficien":
                    DefCoefficien = value;
                    break;
                case "HpCoefficien":
                    HpCoefficien = value;
                    break;
            }
            if (title.startsWith("失败场数")) {
                failure = value;
            } else if (title.startsWith("胜利")) {
                win = value;
            } else if (title.startsWith("超时")) {
                outOfTime = value;
            } else if (title.startsWith("失败剩余")) {
                leftHp = value;
            }
        }


    }
}
