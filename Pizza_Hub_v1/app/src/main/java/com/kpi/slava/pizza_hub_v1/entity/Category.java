package com.kpi.slava.pizza_hub_v1.entity;

public class Category {

    private String id_category;
    private String name;

    public Category(){
    }

    public Category(String id_category, String name) {
        this.id_category = id_category;
        this.name = name;
    }

    public String getIdCategory() {
        return id_category;
    }

    public String getName() {
        return name;
    }
}
