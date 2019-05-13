package blacktv.tvacg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages = {
        "blacktv.tvacg.controller.reception",
        "blacktv.tvacg.controller.admin",
        "blacktv.tvacg.aoplog",
        "blacktv.tvacg.error",
        "blacktv.tvacg.database.service"
})
@Configuration
@EnableAutoConfiguration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@MapperScan(basePackages = "blacktv.tvacg.database.mapper")//将指定包内自动注册为mapper接口
@EnableTransactionManagement()//开启声明式事务
public class TvacgApplication {
    public static void main(String[] args) {
        SpringApplication.run(TvacgApplication.class, args);
    }
}

