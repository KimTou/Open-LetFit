package com.openletfit.aop.annotation;

import java.lang.annotation.*;

/**
 * @author cjt
 * @date 2021/4/17 11:01
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckRepeatSubmit {

   //最大提交次数默认为1
   int maximum() default 1;

}
