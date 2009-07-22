package com.jute.google.framework;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

/**
 * User: hugozhu
 * Date: Apr 25, 2009
 * Time: 3:59:15 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.TYPE, ElementType.METHOD })
@BindingAnnotation
public @interface Path {
    String id() default "/";     
}
