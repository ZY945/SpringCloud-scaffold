package com.scaffold.generate.demo.utl;

import lombok.Data;
import lombok.ToString;

/**
 * 属性
 *
 * @Author wang suo
 * @Date 2020/10/12 0012 22:03
 * @Version 1.0
 */
@Data
@ToString
public class Field {
    /**
     * 字段名;
     * 字段名小驼峰;
     * 字段名大驼峰;
     * 中文名;
     * 字段类型;
     * java类型;
     * 注释;
     * 是否可为空;
     * 字符串长度;
     * 是否是枚举;
     * 枚举常量 COURSE_LEVEL;
     */
    private String name;
    private String nameHump;
    private String nameBigHump;
    private String nameCn;
    private String type;
    private String javaType;
    private String comment;
    private Boolean nullAble;
    private Integer length;
    private Boolean enums;
    private String enumsConst;
}