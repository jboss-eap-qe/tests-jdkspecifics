package com.redhat.qe.jdkspecifics.jdk8.defaultmethods.direct;

import javax.ejb.Local;

/**
 * @author Jan Martiska
 */
@Local(DogeEJBImpl.class)
public interface DogeEJBLocal {

    default public String sayWow() {
        return "wow";
    }

}
