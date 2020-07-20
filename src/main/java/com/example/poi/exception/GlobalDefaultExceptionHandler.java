package com.example.poi.exception;

import com.example.poi.vo.Result;
import com.example.poi.vo.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/*
* @ResponseStatus设置Headers Status Code
* ResultEnum.NOT_FOUND.get()为自定义状态码
*自定义状态码在Response Body展示 HttpStatus状态码在Headers Status Code展示
* */
@RestControllerAdvice
@Slf4j
public class GlobalDefaultExceptionHandler {
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result handlerNoHandlerFoundException(NoHandlerFoundException e) {
        log.error("Not Found -> {} {}",e.getMessage(),e.getRequestURL());
        return ResultEnum.NOT_FOUND.get();
    }

    /**
     * HttpRequestMethodNotSupportedException 405 异常处理
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handlerHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        log.error("Method Not Allowed -> {} {}",e.getMessage(),e.getMethod());
        return ResultEnum.METHOD_NOT_ALLOWED.get();
    }

    /**
     * Exception 类捕获 500 异常处理
     */
    @ExceptionHandler(value = Exception.class)
    public Result handlerException(Exception e) {
        log.error("服务器内部错误",e);
        return ResultEnum.INTERNAL_SERVER_ERROR.get();
    }

    /**
     * HttpMessageNotReadableException 参数错误异常
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("参数序列化异常 -> {}",e.getMessage());
        return ResultEnum.PARAMETER_ERROR.get();
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public Result handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("请求参数错误 -> {}",e.getMessage());
        return ResultEnum.PARAMETER_ERROR.get();
    }


    /**
     * BindException 参数错误异常
     */
    @ExceptionHandler({BindException.class,MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleMethodArgumentNotValidException(Exception e) {
        log.error("请求参数错误",e.getMessage());
        return ResultEnum.PARAMETER_ERROR.get();
    }

    /**
     * BusinessException 类捕获
     */
    @ExceptionHandler(value = BussinessException.class)
    public Result handlerBusinessException(BussinessException e) {
        log.error("业务异常 -> {}",e);
        return new Result(e.getCode(),e.getMsg());
    }

}
