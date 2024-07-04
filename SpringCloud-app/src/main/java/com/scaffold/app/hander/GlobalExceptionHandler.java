package com.scaffold.app.hander;


import com.scaffold.app.exception.BridgeBaseException;
import com.scaffold.commons.utils.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<Object> handleException(Exception e) {
        if (e instanceof BridgeBaseException) {
            BridgeBaseException baseException = (BridgeBaseException) e;
            return Result.error(baseException.getCode(), baseException.getMessage());
        } else if (e instanceof IllegalArgumentException) {
            return Result.error(400, e.getMessage());
        }
        return Result.error(500, e.getMessage());
    }
}
