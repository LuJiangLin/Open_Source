package com.example.entity;

import java.io.Serializable;

/**
 * Created by Flower on 2017/5/12.
 */
public class User implements Serializable{
    private String testword;

    public String getTestword() {
        return testword;
    }

    public void setTestword(String testword) {
        this.testword = testword;
    }
}
