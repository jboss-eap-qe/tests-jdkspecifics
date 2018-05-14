package com.redhat.qe.jdkspecifics.jdk8.defaultmethods;

import javax.ejb.EJB;
import javax.inject.Inject;

import com.redhat.qe.jdkspecifics.jdk8.defaultmethods.cdi.CDIDogeImpl;
import com.redhat.qe.jdkspecifics.jdk8.defaultmethods.direct.DogeEJBImpl;
import com.redhat.qe.jdkspecifics.jdk8.defaultmethods.direct.DogeEJBLocal;
import com.redhat.qe.jdkspecifics.jdk8.defaultmethods.fromclass.DogeInheritingFromClass;
import com.redhat.qe.jdkspecifics.jdk8.defaultmethods.fromclass.DogeInheritingFromClassImpl;
import com.redhat.qe.jdkspecifics.jdk8.defaultmethods.pojo.DogePojoImpl;
import com.redhat.qe.jdkspecifics.jdk8.defaultmethods.withintermediary.DogeWithIntermediaryEJB;
import com.redhat.qe.jdkspecifics.jdk8.defaultmethods.withintermediary.DogeWithIntermediaryLocal;
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
public class DefaultMethodTest {

    @Deployment(name = "default-method-test")
    public static JavaArchive deployment() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "default-method-test.jar");
        archive.addPackage(DefaultMethodTest.class.getPackage());
        archive.addPackage(DogeWithIntermediaryEJB.class.getPackage());
        archive.addPackage(DogeEJBImpl.class.getPackage());
        archive.addPackage(DogePojoImpl.class.getPackage());
        archive.addPackage(DogeInheritingFromClass.class.getPackage());
        archive.addPackage(CDIDogeImpl.class.getPackage());
        archive.addClass(LoggerProducer.class);
        archive.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        archive.setManifest(new StringAsset("Dependencies: org.jboss.logging"));
        return archive;
    }

    /*********** EJB inheriting from an interface, directly (the code is in the @Local interface) *********/
    @EJB
    private DogeEJBLocal doge;

    // FAILS
    @Test
    public void testEJB() {
        Assert.assertEquals("wow", doge.sayWow());
    }

    /*********** EJB inheriting from an interface, with an intermediate (the code is in an interface which is in the middle of the inheritance tree) *********/
    @EJB
    private DogeWithIntermediaryLocal dogeWithIntermediary;

    // FAILS
    @Test
    public void testEJBWithIntermediary() {
        Assert.assertEquals("wow", dogeWithIntermediary.sayWow());
    }

    /*********** EJB Inheriting from a class. Obviously this has nothing to do with JDK 8. *********/
    @EJB
    private DogeInheritingFromClassImpl dogeInheritingFromClass;

    @Test
    public void testInheritanceFromClass() {
        Assert.assertEquals("wow", dogeInheritingFromClass.sayWow());
    }

    /*********** Check that a Pojo can inherit a default method. *********/
    @Test
    public void testPojo() {
        Assert.assertEquals("wow", new DogePojoImpl().sayWow());
    }

    /*********** Check that a CDI bean can inherit a default method. *********/
    @Inject
    private CDIDogeImpl cdiDoge;

    @Test
    public void testCDIBean() {
        Assert.assertEquals("wow", cdiDoge.sayWow());
    }

}
