package com.example.poi.entity;

import com.example.poi.annotation.Anno;

public class AnnoTest {
    @Anno(name = "zxk")
    public void test(){
        System.out.println("----test----");
    }
}
