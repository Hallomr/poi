package com.example.poi.vo;

import com.example.poi.annotation.ExcelTag;
import lombok.Data;

import java.util.Date;

@Data
@ExcelTag(tag = "路由信息")
public class RoutingExcelDto {
    @ExcelTag(tag = "服务编号")
    private String serviceCode;
    @ExcelTag(tag = "服务名称")
    private String serviceName;
    @ExcelTag(tag = "服务地址")
    private String servicePath;
    @ExcelTag(tag = "服务类型")
    private Integer serviceType;
    @ExcelTag(tag = "创建时间")
    private Date createTime;
}