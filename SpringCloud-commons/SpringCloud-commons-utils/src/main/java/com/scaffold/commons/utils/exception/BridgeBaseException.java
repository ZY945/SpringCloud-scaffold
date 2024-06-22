package com.scaffold.commons.utils.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BridgeBaseException extends RuntimeException {

    /**
     * 错误码
     */
    protected Integer code;

    /**
     * 报错信息
     */
    protected String msg;

    /**
     * 有参构造器，返回码在枚举类中，这里可以指定错误信息
     *
     * @param msg
     */
    public BridgeBaseException(String msg) {
        super(msg);
    }
}
