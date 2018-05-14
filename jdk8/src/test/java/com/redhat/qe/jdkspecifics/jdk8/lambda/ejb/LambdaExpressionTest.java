package com.redhat.qe.jdkspecifics.jdk8.lambda.ejb;

import javax.inject.Inject;

import com.redhat.qe.jdkspecifics.jdk8.lambda.common.AnswerToEverythingComputation;
import com.redhat.qe.jdkspecifics.jdk8.tools.LoggerProducer;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jan Martiska
 */
@RunWith(Arquillian.class)
public class LambdaExpressionTest {

    @Deployment(name = "ejb-lambda-expression-test")
    public static JavaArchive deployment() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "ejb-lambda-expression-test.jar");
        archive.addPackage(LambdaExpressionTest.class.getPackage());
        archive.addPackage(AnswerToEverythingComputation.class.getPackage());
        archive.addClass(LoggerProducer.class);
        archive.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        archive.setManifest(new StringAsset("Dependencies: org.jboss.logging"));
        return archive;
    }

    @Inject
    private DeepThoughtComputer deepThoughtComputer;

    /**
     * Verify that the Deep Thought supercomputer is able to find the
     * answer to the Ultimate Question using a lambda expression.
     */
    @Test
    public void test() {
        Assert.assertEquals((Integer)42, deepThoughtComputer.computeTheAnswerToEverything());
    }

}
