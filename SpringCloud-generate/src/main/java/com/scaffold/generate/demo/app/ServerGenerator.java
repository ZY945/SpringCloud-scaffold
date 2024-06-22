package com.scaffold.generate.demo.app;

import com.scaffold.generate.demo.utl.DbUtil;
import com.scaffold.generate.demo.utl.Field;
import com.scaffold.generate.demo.utl.FreemarkerUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.*;

/**
 * 代码生成
 *
 * @Author wang suo
 * @Date 2020/10/12 0012 18:35
 * @Version 1.0
 */
public class ServerGenerator {

    private static String MODULE = "business";
    private static String toServicePath = "server\\src\\main\\java\\com\\lsu\\server\\service\\";
    private static String toDtoPath = "server\\src\\main\\java\\com\\lsu\\server\\dto\\";
    private static String toControllerPath = MODULE + "\\src\\main\\java\\com\\lsu\\" + MODULE + "\\controller\\admin\\";
    private static String generatorPath = "server\\src\\main\\resources\\generator\\generatorConfig.xml";

    public static void main(String[] args) throws Exception {
        String module = MODULE;

        /*
            读 generatorConfig.xml
        */
        File file = new File(generatorPath);
        SAXReader reader = new SAXReader();
        Document dom = reader.read(file);
        /*
        获取文件的根节点
         */
        Element rootElement = dom.getRootElement();
        Element contextElement = rootElement.element("context");
        /*
        获取第一个 table 节点
         */
        Element tableElement = contextElement.elementIterator("table").next();
        /*
        获得大驼峰类名
         */
        String bigDoMain = tableElement.attributeValue("domainObjectName");
        /*
        获取小驼峰类名
         */
        String domain = bigDoMain.substring(0, 1).toLowerCase() + bigDoMain.substring(1);
        /*
        获取表名
         */
        String tableName = tableElement.attributeValue("tableName");
        /*
        获取表的中文名: 备注名
         */
        String tableNameCn = DbUtil.getTableComment(tableName);
        /*
        将表名打印到控制台
         */
        System.out.println("表名 = " + tableName);
        System.out.println("Domain = " + bigDoMain);

        /*
        获取数据库表的属性列表
         */
        List<Field> fieldList = DbUtil.getColumnByTableName(tableName);

        /*
        获取要导入的Java包
         */
        Set<String> typeSet = getJavaTypes(fieldList);

        /*
        放到 Map 集合中供 freemarker 使用
         */
        Map<String, Object> map = new HashMap<>(10);
        map.put("Domain", bigDoMain);
        map.put("domain", domain);
        map.put("tableNameCn", tableNameCn);
        map.put("module", module);
        map.put("fieldList", fieldList);
        map.put("typeSet", typeSet);

        // 生成 dto
        FreemarkerUtil.initConfig("dto");
        FreemarkerUtil.generator(map, toDtoPath + bigDoMain + "Dto.java");

        // 生成 service
        FreemarkerUtil.initConfig("service");
        FreemarkerUtil.generator(map, toServicePath + bigDoMain + "Service.java");

        // 生成 controller
        FreemarkerUtil.initConfig("controller");
        FreemarkerUtil.generator(map, toControllerPath + bigDoMain + "Controller.java");
    }

    private static Set<String> getJavaTypes(List<Field> fieldList) {
        Set<String> set = new HashSet<>();
        for (Field field : fieldList) {
            set.add(field.getJavaType());
        }
        return set;
    }
}