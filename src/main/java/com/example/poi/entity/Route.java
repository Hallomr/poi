package com.example.poi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value ="route" )
public class Route {
    /*@TableId(type = IdType.AUTO)
    private Integer id;*/
    /*服务编号*/
    private String serviceCode;
    /*服务名称*/
    private String serviceName;
    /*服务地址*/
    private String servicePath;
    /*服务类型*/
    private Integer serviceType;
    /*创建时间*/
    private Date createTime;
}
