package com.zyj.springboot_test.util.excelModel;

public class Warrior {
    private String Star;
    private String Job;
    private String HeroId;
    private String Level;
    private String Pos;
    private String Coefficien;
    private String AtkCoefficien;
    private String HpCoefficien;

    private String failure;
    private String win;

    public Warrior(String star, String job, String heroId, String level, String pos, String coefficien, String atkCoefficien, String hpCoefficien, String failure, String win) {
        Star = star;
        Job = job;
        HeroId = heroId;
        Level = level;
        Pos = pos;
        Coefficien = coefficien;
        AtkCoefficien = atkCoefficien;
        HpCoefficien = hpCoefficien;
        this.failure = failure;
        this.win = win;
    }

    public String getStar() {
        return Star;
    }

    public String getJob() {
        return Job;
    }

    public String getHeroId() {
        return HeroId;
    }

    public String getLevel() {
        return Level;
    }

    public String getPos() {
        return Pos;
    }

    public String getCoefficien() {
        return Coefficien;
    }

    public String getAtkCoefficien() {
        return AtkCoefficien;
    }

    public String getHpCoefficien() {
        return HpCoefficien;
    }

    public String getFailure() {
        return failure;
    }

    public String getWin() {
        return win;
    }
}
