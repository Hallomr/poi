package com.example.poi.utils;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MultiThreadExcelUtils {
    private static final String BASE_PATH = "";
    
    public static void main(String[] args) {
        
    }
    private static void multi() throws InterruptedException{
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(30));
        File file = new File(BASE_PATH);
        FileFilter fileFilter = f -> f.isFile() && f.getName().endsWith("");
        File[] files = file.listFiles(fileFilter);
        if(files.length > 0){
            CountDownLatch countDownLatch = new CountDownLatch(files.length);
            for (File file1 : files) {
               final String path = file1.getPath();
               threadPoolExecutor.execute(()->{
                   cakaData4File(path);
                   countDownLatch.countDown();
               });
            }
            countDownLatch.await();
            threadPoolExecutor.shutdown();
        }
    }

    private static void cakaData4File(String path) {
        File file = new File(path);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ExcelReader reader = ExcelUtil.getReader(fileInputStream, 0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
