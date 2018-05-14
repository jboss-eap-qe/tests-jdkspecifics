package com.redhat.qe.jdkspecifics.jdk8.defaultmethods.multipleinheritance;

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
public class DefaultMethodMultipleInheritanceTest {

    @Deployment(name = "default-method-multiple-inheritance-test")
    public static JavaArchive deployment() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "default-method-multiple-inheritance-test.jar");
        archive.addPackage(DefaultMethodMultipleInheritanceTest.class.getPackage());
        archive.addClass(LoggerProducer.class);
        archive.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        archive.setManifest(new StringAsset("Dependencies: org.jboss.logging"));
        return archive;
    }

    @Inject
    Logger logger;

    @EJB
    private EJBInheritingAMethodFromInterfaceAndSuperclass ejbFromAClassAndInterface;

    /**
     * When an EJB inherits the same method from an interface (a default implementation) and a superclass,
     * the superclass should always have precedence.
     */
    @Test
    public void fromInterfaceAndClassAtOnce() {
        String s = ejbFromAClassAndInterface.sayWow();
        logger.info("received: " + s);
        Assert.assertEquals("wow from class", s);
    }

    @EJB
    private EJBInheritingAMethodFromTwoInterfaces ejbFromTwoInterfaces;

    /**
     * An EJB inherits the same method from two interfaces - it overrides it in order to call the version from InterfaceWithDefaultMethod2
     */
    @Test
    public void fromTwoInterfaces() {
        String s = ejbFromTwoInterfaces.sayWow();
        logger.info("received: " + s);
        Assert.assertEquals("wow from interface2", s);
    }




}
