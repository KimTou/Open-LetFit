package com.letfit.aop;

import com.letfit.util.DynamicDbUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author cjt
 * @date 2021/5/14 16:19
 */
@Component
@Aspect
@Slf4j
public class DataSourceAop {

    @Pointcut("@annotation(com.letfit.aop.annotation.ReadOnly)")
    public void readPointcut(){}

    /**
     * 配置前置通知，切换数据源为从数据库
     */
    @Before("readPointcut()")
    public void readAdvise(){
        log.info("切换数据源为从数据库");
        DynamicDbUtil.slave();
    }

}
