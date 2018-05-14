package com.redhat.qe.jdkspecifics.jdk8.defaultmethods.cdi.interceptor;

import javax.inject.Inject;

import com.redhat.qe.jdkspecifics.jdk8.tools.LoggerProducer;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jan Martiska
 */
@RunWith(Arquillian.class)
public class CDIInterceptorDefaultMethodTestCase {

    @Deployment(name = "cdi-default-method-test")
    public static JavaArchive deployment() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "cdi-default-method-test.jar");
        archive.addPackage(CDIInterceptorDefaultMethodTestCase.class.getPackage());
        archive.addClass(LoggerProducer.class);
        // FIXME why doesn't this work ? the monster after this comment is a workaround
//        archive.addAsManifestResource(CDIInterceptorDefaultMethodTestCase.class.getPackage(), "beans.xml",
//                "beans.xml");
        archive.addAsManifestResource(new StringAsset("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<beans xmlns=\"http://java.sun.com/xml/ns/javaee\"\n"
                + "       xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
                + "       xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee \n"
                + "                        http://java.sun.com/xml/ns/javaee/beans_1_0.xsd\">\n"
                + "\n"
                + "    <interceptors>\n"
                + "        <class>com.redhat.qe.jdkspecifics.jdk8.defaultmethods.cdi.interceptor.Doge</class>\n"
                + "    </interceptors>\n"
                + "\n"
                + "</beans>"), "beans.xml");
        archive.setManifest(new StringAsset("Dependencies: org.jboss.logging"));
        return archive;
    }

    @Inject
    JokerImpl joker;


    @Test
    public void testInterceptorWithDefaultMethod() {
        String s = joker.tellAJoke();
        Assert.assertEquals("wow", s);

    }

}
