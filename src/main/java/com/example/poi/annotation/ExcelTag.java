package com.example.poi.annotation;

import org.apache.poi.ss.usermodel.IndexedColors;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelTag {
    /**
     * 表头
     *
     * @return
     */
    String tag();
    /**
     * 字体颜色
     *
     * @return
     */
    IndexedColors fontColor() default IndexedColors.BLACK;
}