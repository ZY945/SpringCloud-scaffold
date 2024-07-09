package com.scaffold.commons.aop;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAOP {

    /**
     * 所属模块
     *
     * @return
     */
    String moduleName() default "日志模块名";

    /**
     * 动作描述
     *
     * @return
     */
    boolean printTime() default true;
}
