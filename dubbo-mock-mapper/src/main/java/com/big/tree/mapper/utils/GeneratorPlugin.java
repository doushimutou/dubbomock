package com.big.tree.mapper.utils;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * Author ayt  on
 */
public class GeneratorPlugin {

    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<String>();

            // true:生成的文件覆盖之前的
            boolean overwrite = true;
            // 读取配置,构造 Configuration 对象.
            // 如果不想使用配置文件的话,也可以直接来 new Configuration(),然后给相应属性赋值.
            File configFile = new File("D:\\learn\\webui-service\\webui-mapper\\src\\main\\resources\\generator\\generatorConfig.xml");
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);

        for (String warning : warnings){
            System.out.println(warning);
        }


    }

    }
