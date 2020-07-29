package ru.esina.widgets.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.esina.widgets.exception.WidgetException;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.web.context.request.RequestContextHolder.getRequestAttributes;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(public * ru.esina.widgets.controller.*.*(..))")
    public void controllerMethods() {
    }

    @Before("controllerMethods()")
    public void logRequest(JoinPoint joinPoint) {
        MDC.put("request.id", UUID.randomUUID());
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(requestAttributes).getRequest();
        LOGGER.info("RequestURL={}, method={}, requestBody={}",
                request.getRequestURL(),
                request.getMethod(),
                joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void logResponse(Object result) {
        LOGGER.info("Response={}", result);
        MDC.clear();
    }

    @AfterThrowing(pointcut = "controllerMethods()", throwing = "e")
    public void logException(Throwable e) {
        if (e instanceof WidgetException) {
            LOGGER.error(String.format("Handle exception=%s", e.toString()));
        } else {
            LOGGER.error("Handle exception=", e);
        }
    }
}
