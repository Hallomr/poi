package com.example.poi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.poi.entity.Route;
import com.example.poi.mapper.RouteMapper;
import com.example.poi.service.RouteService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.List;

@Service
public class RouteServiceImpl extends ServiceImpl<RouteMapper,Route> implements RouteService {
    private static final String[] HEADER = {"服务编号","服务名称","服务地址","服务类型","创建时间"};

    private static final String[] METHOD = {"getServiceCode","getServiceName","getServicePath","getServiceType","getCreateTime"};
    @Override
    public void export(ServletOutputStream outputStream, List<Route> routes) {
        newExcelBook(outputStream,routes,HEADER,METHOD);
    }

    private void newExcelBook(OutputStream outputStream,List<Route> routes,String[] header,String[] methods){
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet sheet = hssfWorkbook.createSheet("sheet0");
        setHeader(sheet,header);
        setRow(sheet,routes,methods);
        try {
            hssfWorkbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * */
    private void setRow(HSSFSheet sheet, List<Route> routes, String[] methods) {
        int index = 1;
        for (Route route : routes) {
            HSSFRow row = sheet.createRow(index);
            setCell(row,route);
            index++;
        }
    }

    private void setCell(HSSFRow row, Route route) {
        for(int i = 0;i < METHOD.length;i++){
            try {
                Object o = getMethodValue(route,METHOD[i]);
                row.createCell(i).setCellValue(o == null ? "" : String.valueOf(o));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Object getMethodValue(Route route, String m) throws Exception{
        Method[] methods = route.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if(method.getName().equalsIgnoreCase(m)){
                return method.invoke(route);
            }
        }
        return null;
    }

    /**
     * 添加标题头
     * */
    private void setHeader(HSSFSheet sheet, String[] header) {
        HSSFRow row = sheet.createRow(0);
        for(int i = 0;i < header.length; i++){
            row.createCell(i).setCellValue(header[i]);
        }
    }
}
