package com.example.poi.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {

    private String code;
    private String msg;
    private String cause;

    public  Result(String code,String msg){
        this.code=code;
        this.msg=msg;
    }

}