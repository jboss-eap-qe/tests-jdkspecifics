package com.redhat.qe.jdkspecifics.jdk8.lambda.serialization;

import java.util.function.Supplier;
import javax.ejb.Remote;

/**
 * @author Jan Martiska
 */
@Remote
public interface ProcessorRemote {

    String evaluate(Supplier<String> supplier);

}
