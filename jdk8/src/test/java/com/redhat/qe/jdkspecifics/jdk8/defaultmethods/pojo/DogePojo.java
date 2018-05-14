package com.redhat.qe.jdkspecifics.jdk8.defaultmethods.pojo;

/**
 * @author Jan Martiska
 */
public interface DogePojo {

    default public String sayWow() {
        return "wow";
    }
}
