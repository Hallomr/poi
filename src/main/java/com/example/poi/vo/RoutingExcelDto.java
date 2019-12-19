package com.example.poi.vo;

import com.example.poi.annotation.ExcelTag;
import lombok.Data;
import org.apache.poi.ss.usermodel.IndexedColors;

@Data
@ExcelTag(tag = "路由信息")
public class RoutingExcelDto {
    @ExcelTag(tag = "服务编号", fontColor = IndexedColors.RED)
    private String serviceCode;
    @ExcelTag(tag = "服务名称")
    private String serviceName;
    @ExcelTag(tag = "服务地址", fontColor = IndexedColors.RED)
    private String servicePath;
    @ExcelTag(tag = "服务类型", fontColor = IndexedColors.RED)
    private Integer serviceType;
}