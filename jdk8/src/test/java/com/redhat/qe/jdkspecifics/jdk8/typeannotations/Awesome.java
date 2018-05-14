package com.redhat.qe.jdkspecifics.jdk8.typeannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jan Martiska
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
public @interface Awesome {
}
