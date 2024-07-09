package com.scaffold.commons.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

@Aspect
@Component
public class LogAOPConfig {

    Logger logger = LoggerFactory.getLogger(LogAOPConfig.class);

    /**
     * 定义拦截注解
     */
    @Pointcut("@annotation(com.scaffold.commons.aop.LogAOP)")
    public void log() {
    }


    @Before("log()&&@annotation(logAOP)")
    public void doBefore(JoinPoint joinPoint, LogAOP logAOP) {
        String moduleName = logAOP.moduleName();

        Signature signature = joinPoint.getSignature(); // 获取被拦截方法签名
        Class<?> declaringType = signature.getDeclaringType(); // 获取类
        String packageName = declaringType.getPackage().getName(); // 获取包名
        String className = declaringType.getSimpleName(); // 获取类名
        String methodName = signature.getName(); // 获取方法名
        logger.info("=========================doBefore-start=========================");
        logger.info("moduleName: {}", moduleName);
        logger.info("packageName: {}, ", packageName);
        logger.info("className: {}", className);
        logger.info("methodName: {}", methodName);
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        logger.info("参数: {}", args);
//        for (int i = 0; i < args.length; i++) {
//            logger.info("第" + (i + 1) + "个参数为:" + args[i]);
//        }
        logger.info("被代理的对象: {}", joinPoint.getTarget());
        logger.info("代理对象自己: {}", joinPoint.getThis());
        logger.info("=========================doBefore-end=========================");
    }

    @Around("log()&&@annotation(logAOP)")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint, LogAOP logAOP) {
        boolean printTime = logAOP.printTime();
        Object result = null;
        Instant start = Instant.now();
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            // TODO 异常通知
            if (Objects.equals(Boolean.TRUE, printTime)) {
                Instant end = Instant.now();
                long time = Duration.between(start, end).toMillis();
                logger.info("执行时间: {} ms", time);
            }
            logger.error("异常信息: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        if (Objects.equals(Boolean.TRUE, printTime)) {
            Instant end = Instant.now();
            long time = Duration.between(start, end).toMillis();
            logger.info("执行时间: {} ms", time);
        }
        return result;
    }

    @After("log()&&@annotation(logAOP)")
    public void doAfter(JoinPoint joinPoint, LogAOP logAOP) {
        // TODO 后置通知
        logger.info("=========================doAfter=========================");
    }
}
