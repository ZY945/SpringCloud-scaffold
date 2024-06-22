package com.scaffold.generate.demo.utl;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * 代码生成器工具类
 *
 * @Author wang suo
 * @Date 2020/10/12 0012 18:30
 * @Version 1.0
 */
public class FreemarkerUtil {

    private static String ftlPath = "generator\\src\\main\\java\\com\\lsu\\generator\\ftl\\";
    private static Template temp;

    public static void initConfig(String ftlName) throws IOException {
        Configuration conf = new Configuration(Configuration.VERSION_2_3_29);
        conf.setDirectoryForTemplateLoading(new File(ftlPath));
        conf.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_29));
        temp = conf.getTemplate(ftlName + ".ftl");
    }

    public static void generator(Map<String, Object> map, String fileName) throws IOException, TemplateException {
        FileWriter fw = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fw);
        temp.process(map, bw);
        bw.flush();
        fw.close();
    }
}