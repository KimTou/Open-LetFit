package com.letfit.aop;

import com.letfit.aop.annotation.CheckRepeatSubmit;
import com.letfit.util.RedisUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @author cjt
 * @date 2021/4/17 11:05
 */
@Component
@Aspect
public class CheckRepeatSubmitAop {

    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("@annotation(com.letfit.aop.annotation.CheckRepeatSubmit)")
    public void pointcut(){}

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 根据ip拒绝短时间内重复提交
     * @param joinPoint
     */
    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        //接收请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        //记录请求内容
        logger.info("URL: " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD: " + request.getMethod());
        logger.info("IP: " + request.getRemoteAddr());
        logger.info("CLASS_METHOD: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS: " + Arrays.toString(joinPoint.getArgs()));
        logger.info("QUERY: " + request.getQueryString());
        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String name = parameterNames.nextElement();
            logger.info("name:{}, value:{}", name, request.getParameter(name));
        }

        //获取请求方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CheckRepeatSubmit annotation = method.getAnnotation(CheckRepeatSubmit.class);
        String addr = request.getRemoteAddr().concat("-").concat(request.getRequestURI());
        if(redisUtil.get(addr) != null){
            Integer value = (Integer) redisUtil.get(addr);
            //多次提交超过规定次数
            if(value >= annotation.maximum()){
                logger.warn("ip:{}正在频繁请求方法{}",request.getRemoteAddr(),request.getRequestURI());
                throw new RuntimeException("提交过于频繁，请稍作等待");
            }else{
                //提交次数+1
                redisUtil.incr(addr,1);
            }
        }else{
            //一秒内不能超过最大提交次数
            redisUtil.set(addr,1,1);
        }
    }

}
