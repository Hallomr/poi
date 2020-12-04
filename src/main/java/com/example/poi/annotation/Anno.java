package com.example.poi.annotation;

import java.lang.annotation.*;

@Target(value = {ElementType.TYPE,ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface Anno {
    String name();
    int age() default 18;
}
