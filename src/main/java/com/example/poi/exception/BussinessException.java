package com.example.poi.exception;

import com.example.poi.vo.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* 业务异常
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BussinessException extends RuntimeException{
    private String code;
    private String msg;

    public BussinessException(ResultEnum resultEnum){
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMessage();
    }
}
