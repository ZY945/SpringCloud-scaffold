package com.scaffold.generate.mybatis_plus;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.assertj.core.util.Lists;

import java.sql.Types;
import java.util.Collections;
import java.util.List;

public class CodeGenerator {
    private static final String URL = "jdbc:mysql://47.99.159.152:3306?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf-8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String OUTPUT_DIR = "";
    // 设置父包名
    private static final String PARENT_PACKAGE_NAME = "com.scaffold.generate.code.generator";
    // 设置父包模块名
    private static final String MODULE_NAME = "generator";
    // 设置mapperXml生成路径
    private static final String PARENT_XML_NAME = "generator";
    // 设置需要生成的表名
    private static final List<String> TABLES = Lists.list("message_job");
    // 设置过滤表前缀
    private static final List<String> TABLE_PREFIX = Lists.list("message_", "c_");

    public static void main(String[] args) {
        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                .globalConfig(builder -> {
                    builder.author("scaffold") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir(OUTPUT_DIR); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        builder.parent(PARENT_PACKAGE_NAME) // 设置父包名
                                .moduleName(MODULE_NAME) // 设置父包模块名
                                .pathInfo(Collections.singletonMap(OutputFile.xml, PARENT_XML_NAME)) // 设置mapperXml生成路径
                )
                .strategyConfig(builder ->
                        builder.addInclude(TABLES) // 设置需要生成的表名
                                .addTablePrefix(TABLE_PREFIX) // 设置过滤表前缀
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
