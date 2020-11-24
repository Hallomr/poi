package com.example.poi.utils;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelExporter {


    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelExporter.class);

    private String[] headerNames;
    private SXSSFWorkbook workBook;
    private SXSSFSheet sheet;

    /**
     *
     * @param headerNames
     *            表头
     * @param sheetName
     *            sheet的名称
     */
    public ExcelExporter(String[] headerNames, String sheetName) {
        this.headerNames = headerNames;
        workBook = new SXSSFWorkbook();// 处理07版本，但是适用于大数据量，导出之后数据不会占用内存
        // 创建一个工作表sheet
        sheet = workBook.createSheet(sheetName);
        initHeader();
    }

    /**
     * 初始化表头信息
     */
    private void initHeader() {
        // 创建第一行
        Row row = sheet.createRow(0);
        Cell cell = null;
        // 创建表头
        for (int i = 0; i < headerNames.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(headerNames[i]);
            setCellStyle(cell);
        }
    }

    /**
     * 设置单元格样式
     *
     * @param cell
     *            单元格
     */
    public void setCellStyle(Cell cell) {
        // 设置样式
        CellStyle cellStyle = workBook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER); // 设置字体居中
        // 设置字体
        Font font = workBook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 13);

        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
    }

    /**
     * 创建行内容(每一行的数据装在list中)
     *
     * @param datas
     *            每一行的数据
     * @param rowIndex
     *            行号(从1开始)
     */
    public void createTableRow(List<String> datas, int rowIndex) {
        // 创建第i行
        SXSSFRow row = sheet.createRow(rowIndex);
        SXSSFCell cell = null;
        // 写入数据
        for (int index = 0, length = datas.size(); index < length; index++) {
            // 参数代表第几列
            cell = row.createCell(index);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(datas.get(index));
        }
    }

    /**
     *
     * @param datas
     *            数据,每一个map都是一行
     * @param keys
     *            key[i]代表从map中获取keys[i]的值作为第i列的值,如果传的是null默认取表头
     */
    public void createTableRows(List<Map<String, Object>> datas, String[] keys) {
        for (int i = 0, length_1 = datas.size(); i < length_1; i++) {
            if (ArrayUtils.isEmpty(keys)) {
                keys = headerNames;
            }
            // 创建行(从第二行开始)
            Map<String, Object> data = datas.get(i);
            SXSSFRow row = sheet.createRow(i + 1);
            for (int j = 0, length_2 = keys.length; j < length_2; j++) {
                // 单元格获取map中的key
                String key = keys[j];
                String value = MapUtils.getString(data, key, "");

                SXSSFCell cell = row.createCell(j);
                cell.setCellType(CellType.STRING);
                cell.setCellValue(value);
            }

        }
    }

    /**
     * 根据表头自动调整列宽度
     */
    public void autoAllSizeColumn() {
        if (sheet instanceof SXSSFSheet) {// 如果是SXSSFSheet，需要调用trackAllColumnsForAutoSizing方法一次
            SXSSFSheet tmpSheet =  sheet;
            tmpSheet.trackAllColumnsForAutoSizing();
        }
        for (int i = 0, length = headerNames.length; i < length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    /**
     * 将数据写出到excel中
     *
     * @param outputStream
     */
    public void exportExcel(OutputStream outputStream) {
        // 导出之前先自动设置列宽
        this.autoAllSizeColumn();
        try {
            workBook.write(outputStream);
        } catch (IOException e) {
            LOGGER.error(" exportExcel error", e);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    /**
     * 合并单元格(起始行列都包括在里面)
     *
     * @param startRow
     *            起始行
     * @param endRow
     *            结束行
     * @param startCol
     *            起始列
     * @param endCol
     *            结束列
     */
    public void mergeCell(int startRow, int endRow, int startCol, int endCol) {
        CellRangeAddress region = new CellRangeAddress(startRow, endRow, startCol, endCol);
        sheet.addMergedRegion(region);
    }

    /**
     * 将数据写出到excel中
     *
     * @param outputFilePath
     */
    public void exportExcel(String outputFilePath) {
        // 导出之前先自动设置列宽
        this.autoAllSizeColumn();
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(outputFilePath);
            workBook.write(outputStream);
        } catch (IOException e) {
            LOGGER.error(" exportExcel error", e);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    public static void main(String[] args) {
        test2();
    }

    private static void test2() {
        ExcelExporter hssfWorkExcel = new ExcelExporter(new String[] { "姓名", "年龄" }, "人员基本信息");
        List<Map<String, Object>> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map data = new HashMap<>();
            data.put("name", "tttttttttttttt" + i);
            data.put("age", "age" + i);
            datas.add(data);
        }
        hssfWorkExcel.createTableRows(datas, new String[] { "name", "age" });
        hssfWorkExcel.mergeCell(1, 2, 0, 1);

        try {
            hssfWorkExcel.exportExcel(new FileOutputStream(new File("C:\\Users\\zeng\\Desktop\\新建文件夹\\test1.xls")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
