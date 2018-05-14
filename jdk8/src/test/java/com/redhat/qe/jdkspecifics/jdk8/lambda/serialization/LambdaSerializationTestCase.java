package com.redhat.qe.jdkspecifics.jdk8.lambda.serialization;

import java.io.Serializable;
import java.util.function.Supplier;
import javax.ejb.EJB;
import javax.inject.Inject;

import com.redhat.qe.jdkspecifics.jdk8.tools.LoggerProducer;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jan Martiska
 * Client sends a serialized lambda expression over the wire to an EJB,
 * the EJB evaluates it and returns the result.
 */
@RunWith(Arquillian.class)
public class LambdaSerializationTestCase {

    @Deployment(name = "lambda-expression-serialization-test")
    public static JavaArchive deployment() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "lambda-expression-serialization-test.jar");
        archive.addPackage(LambdaSerializationTestCase.class.getPackage());
        archive.addClass(LoggerProducer.class);
        archive.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        archive.setManifest(new StringAsset("Dependencies: org.jboss.logging"));
        return archive;
    }

    @EJB
    ProcessorRemote processor;

    @Inject
    Logger logger;

    @Test
    public void sendLambdaOverTheWireToServerAndEvaluateItThere() {
        Assert.assertEquals("wow", processor.evaluate((Supplier<String> & Serializable)() -> "wow"));
    }

}
