package com.example.poi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class MylogAspect {
    @Pointcut("@annotation(com.example.poi.annotation.Mylog)")
    public void log(){};

    @Around("log()")
    public void logAround(ProceedingJoinPoint point){
        String name = point.getSignature().getName();
        Object[] args = point.getArgs();
        log.info("methodName:{},parameter:{}",name,args);
        try {
            point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
