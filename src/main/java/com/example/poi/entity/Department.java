package com.example.poi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "department")
public class Department {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer parentId;
}
