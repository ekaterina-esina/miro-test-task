package ru.esina.widget.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Pointcut ("execution(public * ru.esina.widget.controller.*.*(..))")
    public void controllerMethods() {
    }

    @Pointcut ("execution(public * ru.esina.widget.service.impl.*.*(..))")
    public void serviceMethods() {
    }

    @Pointcut ("execution(protected * ru.esina.widget.exception.*.*(..))")
    public void exceptionHandlerMethods() {
    }

    @Before ("controllerMethods()")
    public void logRequestMethod(JoinPoint joinPoint) {
	log.info("Request: {} args: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    @AfterReturning (pointcut = "serviceMethods()", returning = "result")
    public void logResponseMethod(JoinPoint joinPoint, Object result) {
	log.info("Response: {} method: {} ", result, joinPoint.getSignature());
    }

    @AfterReturning (pointcut = "exceptionHandlerMethods()", returning = "result")
    public void logErrorResponseMethod(JoinPoint joinPoint, Object result) {
	log.error("Handle exception: {} method {}", result, joinPoint.getSignature());
    }
}
