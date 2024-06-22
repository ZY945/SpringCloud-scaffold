package com.scaffold.generate.ftl;

<#-- 模板根目录 -->
<#setting number_format="0">

<#-- 导入包声明 -->
package ${basePackage}.${entityName?lower_case};

<#-- 引入必要的包 -->
import java.io.Serializable;
<#if hasDateColumn>
    import java.time.LocalDate;
</#if>

<#-- 实体类定义 -->
public class ${entityName} implements Serializable {

private static final long serialVersionUID = 1L;
<#list columns>
    <#items as column>


        <#if !column.nullAble>
        <#-- 字段声明 -->
            private ${column.javaType} ${column.columnName};

        <#-- Getter 方法 -->
            public ${column.javaType} get${column.columnName?capitalize}() {
            return ${column.columnName};
            }

        <#-- Setter 方法 -->
            public void set${column.columnName?capitalize}(${column.javaType} ${column.columnName}) {
            this.${column.columnName} = ${column.columnName};
            }
        </#if>
    <#--        <#if column_has_next>-->
    <#--            &lt;#&ndash; 分隔符 &ndash;&gt;-->
    <#--        </#if>-->
    <#--        -->
    </#items>
</#list>

<#-- 打印方法 -->
@Override
public String toString() {
return "${entityName}{" +
<#list columns as column>
    "${column.columnName}='" + ${column.columnName} + '\'' +
    <#if column_has_next>, </#if>
</#list>
"}";
}
}