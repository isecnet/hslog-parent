package com.jz.bigdata.common.businessIntelligence.entity;

import com.jz.bigdata.util.Bean2Mapping;

public class Dashboard {
    private String id;//index _id
    private String title;//标题
    private String description;//描述
    private String option;//dashboard结构
    private String params;//查询参数
    private String data;//查询结果

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    public String toMapping() {
        String template = "\"dashboard\":{\n" + "\t\t\"properties\":{\n" + "\t\t{#}\n" + "\t\t\t\t}" + "}";
        String fieldString = Bean2Mapping.getClassMapping(this);
        template = template.replace("{#}", fieldString);
        return template;
    }

    public static void main(String [] args) {
        System.out.println(new Dashboard().toMapping());
    }
}
