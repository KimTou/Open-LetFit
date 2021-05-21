package com.letfit.aop.annotation;

import java.lang.annotation.*;

/**
 * @author cjt
 * @date 2021/4/15 23:17
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface NullCheck {

    boolean notNull() default true;

}
