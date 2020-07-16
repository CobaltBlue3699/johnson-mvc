package com.johnson.core.mvc.annotation;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    String pkg() default "";
    String cls() default "";
}