package com.example.poi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.poi.entity.Route;

import javax.servlet.ServletOutputStream;
import java.util.List;

public interface RouteService extends IService<Route> {
    void export(ServletOutputStream outputStream, List<Route> routes);
}
