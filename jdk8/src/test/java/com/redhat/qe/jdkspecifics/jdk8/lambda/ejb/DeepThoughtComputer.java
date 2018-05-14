package com.redhat.qe.jdkspecifics.jdk8.lambda.ejb;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.redhat.qe.jdkspecifics.jdk8.lambda.common.AnswerToEverythingComputation;

import org.jboss.logging.Logger;

/**
 * @author Jan Martiska
 */
@Stateless
@LocalBean
public class DeepThoughtComputer {

    @Inject
    private Logger logger;

    public Integer computeTheAnswerToEverything() {
        logger.info("Deep Thought commencing computation, check back in 7.5 million years");
        return ((AnswerToEverythingComputation)() -> 42).compute();
    }

}
