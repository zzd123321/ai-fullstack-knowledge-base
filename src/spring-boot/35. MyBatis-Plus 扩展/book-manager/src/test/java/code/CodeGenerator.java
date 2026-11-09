package code;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create(
                        "jdbc:postgresql://localhost:5432/bookdb3",
                        "postgres",
                        "admin"
                )
                .globalConfig(builder -> builder
                        .author("BookManager Team")
                        .enableSwagger()
                        .outputDir(System.getProperty("user.dir") + "/code/src/main/java")
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig(builder -> builder
                        .parent("com.example.bookmanager")
                        .moduleName("")
                        .entity("model")
                        .service("service")
                        .serviceImpl("service.impl")
                        .mapper("mapper")
                        .controller("controller")
                        .pathInfo(Collections.singletonMap(
                                OutputFile.xml, System.getProperty("user.dir") + "/code/src/main/resources/mapper"
                        ))
                )
                .strategyConfig(builder -> builder
                        .addInclude("book", "orders", "order_item")  // 要生成的表
                        .addTablePrefix("t_", "tb_")                 // 表前缀过滤
                        .serviceBuilder()
                        .formatServiceFileName("%sService")
                        .formatServiceImplFileName("%sServiceImpl")
                        .entityBuilder()
                        .enableLombok()                          // 使用 Lombok
                        .enableTableFieldAnnotation()
                        .logicDeleteColumnName("deleted")        // 逻辑删除字段
                        .controllerBuilder()
                        .enableRestStyle()                       // 生成 @RestController
                        .mapperBuilder()
                        .enableMapperAnnotation()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
