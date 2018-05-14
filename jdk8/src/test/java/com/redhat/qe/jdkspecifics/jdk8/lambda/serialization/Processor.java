package com.redhat.qe.jdkspecifics.jdk8.lambda.serialization;

import java.util.function.Supplier;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jboss.logging.Logger;

/**
 * @author Jan Martiska
 */
@Stateless
public class Processor implements ProcessorRemote {

    @Inject
    Logger logger;

    @Override
    public String evaluate(Supplier<String> supplier) {
        String s = supplier.get();
        logger.info("Evaluated the lambda to: " + s);
        return supplier.get();
    }

}
