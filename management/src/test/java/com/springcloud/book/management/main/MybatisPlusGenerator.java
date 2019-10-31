package com.springcloud.book.management.main;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.*;

public class MybatisPlusGenerator {
    private static final String filePath = "c:\\autoCreatPro";
    /**
     * <p>p
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
       // tableFillList.add(new TableFill("ASDD_SS", FieldFill.INSERT_UPDATE));

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator().setGlobalConfig(
                // 全局配置
                new GlobalConfig()
                        .setOutputDir(filePath)//输出目录
                        .setFileOverride(true)// 是否覆盖文件D
                        .setActiveRecord(true)// 开启 activeRecord 模式
                        .setEnableCache(false)// XML 二级缓存
                        .setBaseResultMap(true)// XML ResultMap
                        .setBaseColumnList(true)// XML columList
                        .setAuthor("grl")
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                 .setMapperName("%sDao")
                // .setXmlName("%sDao")
                 .setServiceName("%sService")
                // .setServiceImplName("%sServiceDiy")
                // .setControllerName("%sAction")
        ).setDataSource(
                // 数据源配置
                new DataSourceConfig()
                        .setDbType(DbType.MYSQL)// 数据库类型
                        .setTypeConvert(new MySqlTypeConvert() {
                            // 自定义数据库表字段类型转换【可选】
                            @Override
                            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                                System.out.println("转换类型：" + fieldType);
                                // if ( fieldType.toLowerCase().contains( "tinyint" ) ) {
                                //    return DbColumnType.BOOLEAN;
                                // }
                                return super.processTypeConvert(globalConfig,fieldType);
                            }
                        })
                        .setDriverName("com.mysql.cj.jdbc.Driver")
                        .setUsername("grl")
                        .setPassword("123456")
                        .setUrl("jdbc:mysql://127.0.0.1:3306/dev_book_foreign?characterEncoding=utf8&serverTimezone=GMT%2B8")
        ).setStrategy(
                // 策略配置
                new StrategyConfig()
                        .setTablePrefix(new String[]{"fd_"})// 此处可以修改为您的表前缀
                        .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                         .setInclude(new String[] {"fd_journal"}) // 需要生成的表
                         //.setExclude(new String[]{"dudget","purchase","study_pay"}) // 排除生成的表
                        // 自定义实体父类
                        .setSuperEntityClass("com.baomidou.mybatisplus.extension.activerecord.Model")
                        .setTableFillList(tableFillList)
                        //自定义Mapper接口父类
                        .setSuperMapperClass("com.baomidou.mybatisplus.core.mapper.BaseMapper")
                // 【实体】是否为lombok模型（默认 false）
                 .setEntityLombokModel(false)
                // Boolean类型字段是否移除is前缀处理
                 .setEntityBooleanColumnRemoveIsPrefix(true)
                 .setRestControllerStyle(true)
                 .setControllerMappingHyphenStyle(true)
                .setEntityTableFieldAnnotationEnable(true)
        ).setPackageInfo(
                // 包配置
                new PackageConfig()
                        .setParent("com.springcloud.book.management")// 自定义包路径
                        .setEntity("domain")
                        .setController("controller")// 这里是控制器包名，默认 web
                        .setService("service")
                        .setServiceImpl("service.impl")
                        .setMapper("dao")
        )
                .setCfg(
                // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
                new InjectionConfig() {
                    @Override
                    public void initMap() {
                        Map<String, Object> map = new HashMap<>();
                        map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                        this.setMap(map);
                    }
                }.setFileOutConfigList(Collections.<FileOutConfig>singletonList(new FileOutConfig("/templates/mapper.xml.vm") {
                    // 自定义输出文件目录
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        return filePath + "\\" + tableInfo.getEntityName() + "Mapper.xml";
                    }
                }))
        )
                .setTemplate(
                // 关闭默认 xml 生成，调整生成 至 根目录
                new TemplateConfig().setXml(null)
        );
        // 执行生成
        mpg.execute();
        // 打印注入设置，这里演示模板里面怎么获取注入内容【可无】
        System.err.println(mpg.getCfg().getMap().get("abc"));
    }
}