package com.example.poi;

import com.example.poi.annotation.Anno;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;

@SpringBootTest
@RunWith(value = SpringRunner.class)
public class AnnotationTest {
    @Test
    public void anno(){
        try {
            Class<?> aClass = Class.forName("com.example.poi.entity.AnnoTest");
            Method method = aClass.getMethod("test");
            if(method.isAnnotationPresent(Anno.class)){
                Anno annotation = method.getAnnotation(Anno.class);
                System.out.println(annotation.age()+":"+annotation.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
