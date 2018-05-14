package com.redhat.qe.jdkspecifics.jdk8.defaultmethods.multipleinheritance;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * @author Jan Martiska
 */
@Stateless
@LocalBean
public class EJBInheritingAMethodFromTwoInterfaces
    implements InterfaceWithDefaultMethod, InterfaceWithDefaultMethod2 {

    @Override
    public String sayWow() {
        return InterfaceWithDefaultMethod2.super.sayWow();
    }

}
