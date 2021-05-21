package com.letfit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author cjt
 * @date 2021/4/15 22:52
 */
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 支持跨域请求
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                //设置允许跨域的方法
                .allowedMethods("GET","POST","PUT","DELETE")
                .allowedHeaders("*")
                //是否允许证书
                .allowCredentials(true)
                //跨域允许时间
                .maxAge(3600);
    }
}
