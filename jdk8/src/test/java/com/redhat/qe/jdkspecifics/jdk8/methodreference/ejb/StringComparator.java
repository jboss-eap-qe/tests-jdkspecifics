package com.redhat.qe.jdkspecifics.jdk8.methodreference.ejb;

import javax.ejb.Stateless;

/**
 * @author Jan Martiska
 */
@Stateless
public class StringComparator {

    public int compare(String s1, String s2) {
        return s1.compareTo(s2);
    }

}
