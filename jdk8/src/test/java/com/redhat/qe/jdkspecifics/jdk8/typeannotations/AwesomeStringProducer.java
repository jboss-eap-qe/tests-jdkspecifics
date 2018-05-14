package com.redhat.qe.jdkspecifics.jdk8.typeannotations;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jboss.logging.Logger;

/**
 * @author Jan Martiska
 */
@Stateless
public class AwesomeStringProducer {

    @Inject Logger logger;

    public String getAwesomeString() {
        return new @Awesome String ("xx");
    }

}
