package com.scaffold.generate.demo.app;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CodeGenerator {

    public static void main(String[] args) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        // 在resources下的写法 /文件夹
//        cfg.setClassForTemplateLoading(CodeGenerator.class, "/templates");
        cfg.setClassForTemplateLoading(CodeGenerator.class, "/ftl");

        Map<String, Object> root = new HashMap<>();
        root.put("basePackage", "com.example");
        root.put("entityName", "User");
        root.put("hasDateColumn", true);
        root.put("columns", new Column[]{
                new Column("id", "Long"),
                new Column("username", "String"),
                new Column("birthdate", "LocalDate")
        });

        Template template = cfg.getTemplate("test.ftl");
        File file = new File("Entity.java");
        try (FileWriter writer = new FileWriter(file)) {
            template.process(root, writer);
        }
    }

    @Getter
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Column {
        String columnName;
        String javaType;

    }
}