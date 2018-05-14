package com.redhat.qe.jdkspecifics.jdk8.tools;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.jboss.logging.Logger;

/**
 * @author Jan Martiska
 */
public class LoggerProducer {

    @Produces Logger getLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}
