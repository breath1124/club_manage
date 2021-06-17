package cn.zucc.edu.club;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;

public class CodeGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setServiceName("%sService");    //去掉Service接口的首字母I
        gc.setIdType(IdType.AUTO); //主键策略
        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");     // 代码生成路径
        gc.setAuthor("tengfei");
        gc.setOpen(false);
        gc.setSwagger2(true);
        gc.setFileOverride(false); //覆盖原来生成的文件
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql:///club?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("cn.zucc.edu.club");
//        pc.setModuleName("aaff");    // 根包名为 com.myboy.order
        pc.setController("controller"); // controller包名为：com.myboy.order.controller
        pc.setService("service");
        pc.setMapper("mapper");
        pc.setEntity("entity");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel); //实体类名称规则
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//实体类属性规则
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(new TableFill("create_time", FieldFill.INSERT)); //自动填充策略
        tableFills.add(new TableFill("public_time", FieldFill.INSERT_UPDATE)); //自动填充策略
        tableFills.add(new TableFill("commit_time", FieldFill.INSERT)); //自动填充策略
        tableFills.add(new TableFill("update_time", FieldFill.INSERT_UPDATE));
        tableFills.add(new TableFill("top_time", FieldFill.INSERT));
        strategy.setTableFillList(tableFills);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(
                "activity",
                "admin",
                "club",
                "notice",
                "stu_activity",
                "stu_club",
                "student",
                "role",
                "user_role"
        );  // 设置要生成代码的表
        //strategy.setTablePrefix("t_");  //去掉表前缀

        //strategy.setControllerMappingHyphenStyle(true);  //设置controller的@RequestMapper()中值驼峰转连字符
        //strategy.setTablePrefix("");  //样式为@RequestMapper("service_video")


        mpg.setStrategy(strategy);

        mpg.execute();
    }

}