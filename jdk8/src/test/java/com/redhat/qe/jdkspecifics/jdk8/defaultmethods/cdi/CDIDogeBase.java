package com.redhat.qe.jdkspecifics.jdk8.defaultmethods.cdi;

/**
 * @author Jan Martiska
 */
public interface CDIDogeBase {

    default public String sayWow() {
        return "wow";
    }

}
