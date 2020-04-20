package com.example.poi.resp;

import com.example.poi.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class DepartmentResp {
    private Integer id;

    private Integer parentId;

    private String name;

    private List<User> users;

    private List<DepartmentResp> childrenList;
}
