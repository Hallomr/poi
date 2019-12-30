package com.example.poi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.poi.entity.Route;
import com.example.poi.mapper.RouteMapper;
import com.example.poi.utils.ExcelUtils;
import com.example.poi.vo.RoutingExcelDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/poi")
public class PoiController {
    @Autowired
    private RouteMapper routeMapper;

    @GetMapping("/download")
    public void download(HttpServletResponse response){
        try {
            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("导出", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            QueryWrapper<Route> routeQueryWrapper = new QueryWrapper<Route>();
            List<Route> routes = routeMapper.selectList(routeQueryWrapper);

            ExcelUtils.export(response.getOutputStream(),routes,RoutingExcelDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @PostMapping("/upload")
    public String upload(MultipartFile file) throws IOException {
        try {
            List<RoutingExcelDto> parse = ExcelUtils.parse(file.getInputStream(), RoutingExcelDto.class);
            //入数据库
            List<Route> collect = parse.stream().map(routingExcelDto -> {
                Route route = new Route();
                BeanUtils.copyProperties(routingExcelDto, route);
                return route;
            }).collect(Collectors.toList());
            System.out.println(collect);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
}
