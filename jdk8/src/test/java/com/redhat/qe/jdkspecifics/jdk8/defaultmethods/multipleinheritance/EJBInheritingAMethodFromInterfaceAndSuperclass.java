package com.redhat.qe.jdkspecifics.jdk8.defaultmethods.multipleinheritance;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * @author Jan Martiska
 */
@Stateless
@LocalBean
public class EJBInheritingAMethodFromInterfaceAndSuperclass extends AbstractSuperClass implements InterfaceWithDefaultMethod {


}
