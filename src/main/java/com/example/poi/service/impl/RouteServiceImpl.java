package com.example.poi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.poi.entity.Route;
import com.example.poi.mapper.RouteMapper;
import com.example.poi.service.RouteService;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImpl extends ServiceImpl<RouteMapper,Route> implements RouteService {
}
