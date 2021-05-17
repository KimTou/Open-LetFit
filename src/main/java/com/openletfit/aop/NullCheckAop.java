package com.openletfit.aop;

import com.openletfit.aop.annotation.NullCheck;
import com.openletfit.exception.ParamNullException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author cjt
 * @date 2021/4/15 23:26
 */
@Component
@Aspect
public class NullCheckAop {

    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(public * com.openletfit.controller.*.*(..))")
    public void pointcut(){}

    /**
     * 参数非空校验
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //拿到拦截的方法
        Method method = signature.getMethod();
        logger.info("对方法{}进行参数校验",method.getName());
        Annotation[][] annotations = method.getParameterAnnotations();
        if(annotations.length == 0){
            return;
        }
        //拿到方法参数名
        String[] parameterNames = signature.getParameterNames();
        //参数类型
        Class<?>[] parameterTypes = method.getParameterTypes();
        //获取参数值
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < annotations.length; i++) {
            for (int j = 0; j < annotations[i].length; j++) {
                //进性参数非空校验
                if(annotations[i][j] != null && annotations[i][j] instanceof NullCheck && ((NullCheck) annotations[i][j]).notNull()){
                    checkParam(parameterNames[i], parameterTypes[i].getName(), args[i]);
                    break;
                }
            }
        }
    }

    /**
     * 参数非空校验
     * @param paramName
     * @param paramType
     * @param value
     */
    public void checkParam(String paramName, String paramType, Object value){
        //将value转换为String并使用trim去掉空格
        if(value == null || "".equals(value.toString().trim())){
            throw new ParamNullException(paramName, paramType);
        }
    }

}
