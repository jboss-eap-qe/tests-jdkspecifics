package com.redhat.qe.jdkspecifics.jdk8.defaultmethods.cdi.interceptor;

/**
 * @author Jan Martiska
 */
public interface DogeInterface {

    default public String sayWow() {
        return "wow";
    }
}
