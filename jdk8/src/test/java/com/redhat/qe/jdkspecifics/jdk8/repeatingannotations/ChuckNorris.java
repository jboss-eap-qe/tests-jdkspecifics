package com.redhat.qe.jdkspecifics.jdk8.repeatingannotations;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * @author Jan Martiska
 */
@Skill("Call methods on a null pointer")
@Skill("Use generics in java 1.0")
public class ChuckNorris {

    public void destroy() {
        throw new RuntimeException("owned!");
    }

}
