package com.example.studysystem.entity;

public class History {
    private String year;
    private int num;
    public History(){}
    public History(String year,int num){
        this.setYear(year);
        this.setNum(num);
    }
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }



}
