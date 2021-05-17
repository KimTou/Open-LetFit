package com.openletfit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author cjt
 * @date 2021/4/7
 */
@MapperScan("com.letfit.mapper")
@EnableScheduling
@EnableOpenApi
@EnableTransactionManagement
@SpringBootApplication
public class OpenLetFitApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenLetFitApplication.class, args);
    }

}
