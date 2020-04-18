package com.lec.android.a008_practice;

import java.io.Serializable;

public class Phone implements Serializable {
    String name;
    int age;
    String juso;

    public Phone() {
    }

    public Phone(String name, int age, String juso) {
        this.name = name;
        this.age = age;
        this.juso = juso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJuso() {
        return juso;
    }

    public void setJuso(String juso) {
        this.juso = juso;
    }
}
