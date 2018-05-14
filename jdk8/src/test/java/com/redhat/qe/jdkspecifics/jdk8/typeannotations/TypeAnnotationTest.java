package com.redhat.qe.jdkspecifics.jdk8.typeannotations;

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
 */
@RunWith(Arquillian.class)
public class TypeAnnotationTest {

    @Deployment(name = "stream-test")
    public static JavaArchive deployment() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "stream-test.jar");
        archive.addPackage(TypeAnnotationTest.class.getPackage());
        archive.addClass(LoggerProducer.class);
        archive.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        archive.setManifest(new StringAsset("Dependencies: org.jboss.logging"));
        return archive;
    }

    @Inject
    Logger logger;

    @EJB
    private AwesomeStringProducer ejb;

    @Test
    public void checkTypeAnnotationInConstructor() {
        Assert.assertEquals("xx", ejb.getAwesomeString());
    }

}
