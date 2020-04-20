package com.example.poi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.poi.entity.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<User> selectByDepartmentId(Integer id);
}
