package com.scaffold.commons.utils.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    //是否成功
    private Boolean success;
    //状态码
    private Integer code;
    //提示信息
    private String msg;
    //数据
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<T>(true, 200, "success", data);
    }

    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<T>(false, code, msg, null);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<T>(false, 500, msg, null);
    }
}
