package com.scaffold.generate.demo.app;

import com.scaffold.generate.demo.utl.DbUtil;
import com.scaffold.generate.demo.utl.Field;
import com.scaffold.generate.demo.utl.FreemarkerUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Vue 代码生成
 *
 * @Author wang suo
 * @Date 2020/10/12 0012 18:35
 * @Version 1.0
 */
public class VueGenerator {

    private static String MODULE = "business";
    private static String toVuePath = "admin\\src\\views\\admin\\";
    private static String generatorPath = "server\\src\\main\\resources\\generator\\generatorConfig.xml";

    public static void main(String[] args) throws Exception {
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
        获取数据库表的属性列表
         */
        List<Field> fieldList = DbUtil.getColumnByTableName(tableName);

        /*
        放到 Map 集合中供 freemarker 使用
         */
        Map<String, Object> map = new HashMap<>(10);
        map.put("domain", domain);
        map.put("module", MODULE);
        map.put("fieldList", fieldList);

        // 生成 vue
        FreemarkerUtil.initConfig("vue");
        FreemarkerUtil.generator(map, toVuePath + domain + ".vue");
    }
}