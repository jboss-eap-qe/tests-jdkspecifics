package com.redhat.qe.jdkspecifics.jdk8.defaultmethods.multipleinheritance;

/**
 * @author Jan Martiska
 */
public interface InterfaceWithDefaultMethod2 {

    default public String sayWow() {
        return "wow from interface2";
    }
}
