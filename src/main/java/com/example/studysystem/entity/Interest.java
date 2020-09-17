package com.example.studysystem.entity;

import java.util.List;

public class Interest {

    private int author_id;
    private List<String> years;
    private List<String> names; // 领域名称，最多10个
    private List<List<Integer>> nums; //代表买个领域的论文数，累计到某年份。

    public Interest(){}
    public Interest(int id,List<String> years,List<String> names,List<List<Integer>> nums){
        this.setAuthor_id(id);
        this.setYears(years);
        this.setNames(names);
        this.setNums(nums);
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public List<String> getYears() {
        return years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<List<Integer>> getNums() {
        return nums;
    }

    public void setNums(List<List<Integer>> nums) {
        this.nums = nums;
    }
}
