package com.scaffold.generate.demo.utl;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据库字段工具类
 *
 * @Author wang suo
 * @Date 2020/10/12 0012 22:16
 * @Version 1.0
 */
public class DbUtil {

    /**
     * 正则表达式规则: 匹配: _字母
     */
    private static final Pattern PATTERN = Pattern.compile("_(\\w)");

    /**
     * 获取数据库连接
     *
     * @return 返回连接对象
     */
    private static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/online_course?useUnicode=false&characterEncoding=UTF8&serverTimezone=UTC";
            String user = "root";
            String password = "root";
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 获取表的中文名称
     *
     * @param tableName 表名
     * @return 返回表的注释
     */
    public static String getTableComment(String tableName) throws SQLException {
        Connection conn = getConnection();
        Statement state = conn.createStatement();
        ResultSet res = state
                .executeQuery("select table_comment from information_schema.TABLES where TABLE_NAME = '"
                        + tableName + "';");
        String tableNameCh = "";
        while (res.next()) {
            tableNameCh = res.getString("table_comment");
        }
        res.close();
        state.close();
        conn.close();
        System.out.println("表名 = " + tableNameCh);
        return tableNameCh;
    }

    /**
     * 获取所有列信息
     *
     * @param tableName 表名
     * @return 返回属性的Field集合
     * @throws Exception 抛出异常
     */
    public static List<Field> getColumnByTableName(String tableName) throws Exception {
        List<Field> fieldList = new ArrayList<>();
        Connection conn = getConnection();
        Statement state = conn.createStatement();
        ResultSet res = state.executeQuery("show full columns from " + tableName);
        while (res.next()) {
            String columnName = res.getString("Field");
            String type = res.getString("Type");
            String comment = res.getString("Comment");
            String nullAble = res.getString("Null");
            Field field = new Field();
            field.setName(columnName);
            field.setNameHump(lineToHump(columnName));
            field.setNameBigHump(lineToBigHump(columnName));
            field.setComment(comment);
            field.setJavaType(sqlTypeToJavaType(type));
            field.setType(type);
            if (comment.contains("|")) {
                field.setNameCn(comment.substring(0, comment.indexOf("|")));
            } else {
                field.setNameCn(comment);
            }
            field.setNullAble("YES".equals(nullAble));
            if (type.toUpperCase().contains("VARCHAR")) {
                String lengthStr = type.substring(type.indexOf("(") + 1, type.length() - 1);
                field.setLength(Integer.valueOf(lengthStr));
            } else {
                field.setLength(0);
            }
            if (comment.contains("枚举")) {
                field.setEnums(true);

                // 以课程等级为例：从注释中的“枚举[CourseLevelEnum]”，得到COURSE_LEVEL
                int start = comment.indexOf("[");
                int end = comment.indexOf("]");
                String enums = comment.substring(start + 1, end);
//                String enumsConst = EnumGenerator.toUnderline(enums);
//                field.setEnumsConst(enumsConst);
            } else {
                field.setEnums(false);
            }
            fieldList.add(field);
        }
        res.close();
        state.close();
        conn.close();
        System.out.println("列信息 = " + fieldList);
        return fieldList;
    }

    /**
     * 下划线转小驼峰
     *
     * @param str 字符串
     * @return 小驼峰
     */
    private static String lineToHump(String str) {
        Matcher matcher = PATTERN.matcher(str.toLowerCase());
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 下划线转大驼峰
     *
     * @param str 字符串
     * @return 大驼峰
     */
    private static String lineToBigHump(String str) {
        String s = lineToHump(str);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 数据库类型转为 Java 类型
     *
     * @param sqlType 数据库类型
     * @return 返回 Java 类型
     */
    private static String sqlTypeToJavaType(String sqlType) {
        final String varchar = "VARCHAR";
        final String chr = "CHAR";
        final String text = "TEXT";
        final String datetime = "DATETIME";
        final String integer = "INT";
        final String aLong = "LONG";
        final String decimal = "DECIMAL";
        if (sqlType.toUpperCase().contains(varchar)
                || sqlType.toUpperCase().contains(chr)
                || sqlType.toUpperCase().contains(text)) {
            return "String";
        } else if (sqlType.toUpperCase().contains(datetime)) {
            return "Date";
        } else if (sqlType.toUpperCase().contains(integer)) {
            return "Integer";
        } else if (sqlType.toUpperCase().contains(aLong)) {
            return "Long";
        } else if (sqlType.toUpperCase().contains(decimal)) {
            return "BigDecimal";
        } else {
            return "String";
        }
    }
}