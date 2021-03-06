package com.lec.android.a007_activity;

import java.io.Serializable;

//Intent 에 담아 보내는 객체는 반 드 시 Serializable 되어 있어야한다.
public class Person implements Serializable {
    String name;
    int age;
    //AndroidStudio에서 ALT+INSET 로 자동생성!

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
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
}
