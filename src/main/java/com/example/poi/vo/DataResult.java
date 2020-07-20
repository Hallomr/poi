package com.example.poi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataResult<T> extends Result {
    private T data;

    public DataResult(String code,String msg,T data){
        super(code,msg);
        this.data=data;
    }

    public DataResult(String code,String msg,String cause){
        super(code,msg,cause);
    }
}
