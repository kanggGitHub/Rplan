package com.cetc.plan;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname CodeEntityGeneration
 * @Description: TODO/**
 *  自定义生成实体
 *  按需修改  需要生成的文件目录只有model中的实体
 *  生成文件需要修改 文件生成位置、目录前缀 、表名称
 *
 * @author: kg
 * @Date: 2019/6/26 11:03
 */
public class CodeEntityGeneration {


        public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath +"/demands/src/main/java");//输出文件路径
        gc.setOpen(false); //创建完成后是否打开文件目录
        gc.setFileOverride(true);
        gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setAuthor("kg");// 作者

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        //demands需求目录  Satellite卫星目录
//            gc.setControllerName("DemandController");
//            gc.setServiceName("DemandService");
//            gc.setServiceImplName("DemandServiceImpl");
//            gc.setMapperName("DemandMapper");
            gc.setEntityName("SatelitesEntity");



        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.ORACLE);
        dsc.setDriverName("com.oscar.Driver");
        dsc.setUsername("sysdba");
        dsc.setPassword("szoscar55");
        dsc.setUrl("jdbc:oscar://172.19.9.254:2003/XQCHPT?serverTimezone=UTC");
        mpg.setDataSource(dsc);




        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.cetc.plan.demand");
//        pc.setController("controller");
//        pc.setService("service");
//        pc.setServiceImpl("service.impl");
//        pc.setMapper("dao");
//            pc.setXml("xml");
        pc.setEntity("model");

        mpg.setPackageInfo(pc);





        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();
        templateConfig.setService(null);
        templateConfig.setController(null);
        templateConfig.setServiceImpl(null);
        templateConfig.setMapper(null);
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);


        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setTablePrefix(new String[] { "sys_" });// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(new String[] { "XQCH_XQXX_MBXX_WXYQ" }); // 需要生成的表

        strategy.setSuperServiceClass(null);
        strategy.setSuperServiceImplClass(null);
        strategy.setSuperMapperClass(null);

        mpg.setStrategy(strategy);

        // 执行生成
        mpg.execute();

    }


}
