package com.redhat.qe.jdkspecifics.jdk8.defaultmethods.withintermediary;

import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * @author Jan Martiska
 */
@Stateless
@Local(DogeWithIntermediaryLocal.class)
public class DogeWithIntermediaryEJB implements Intermediary {

}
