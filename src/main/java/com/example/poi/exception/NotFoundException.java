package com.example.poi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class NotFoundException implements ErrorController {
    /*@Autowired
    private BasicErrorController basicErrorController;

    @RequestMapping(value = "/error",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object error(HttpServletRequest request) {
        ResponseEntity<Map<String, Object>> error =
                basicErrorController.error(request);
        log.error("not found -> {}",error);
        return error.getBody();
    }*/

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = {"/error"})
    @ResponseBody
    public Object error(HttpServletRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "not found");
        body.put("code", "404");
        log.error("not fund -> {}",body);
        return body;
    }


}
