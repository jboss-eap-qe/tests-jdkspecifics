package com.redhat.qe.jdkspecifics.jdk8.methodreference.ejb;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import javax.ejb.EJB;

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
public class MethodReferenceTest {

    @Deployment(name = "ejb-method-reference-test")
    public static JavaArchive deployment() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "ejb-method-reference-test.jar");
        archive.addPackage(MethodReferenceTest.class.getPackage());
        archive.addClass(LoggerProducer.class);
        archive.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        archive.setManifest(new StringAsset("Dependencies: org.jboss.logging"));
        return archive;
    }

    @EJB
    private StringComparator comparator;

    /**
     * A non-JavaEE component uses a method reference to an EJB business method
     */
    @Test
    public void referenceToAnEJBBusinessMethod() {
        String[] names = new String[] {"Joe", "Adam", "Bob"};
        Arrays.sort(names, comparator::compare);
        Assert.assertEquals("Adam", names[0]);
        Assert.assertEquals("Bob", names[1]);
        Assert.assertEquals("Joe", names[2]);
    }

    @EJB
    private SortingEJBRemote sorter;

    /**
     * Passing a method reference as an argument in an EJB call
     */
    @Test
    public void passingMethodReferenceToARemoteEJB() {
        String[] names = new String[] {"Joe", "Adam", "Bob"};
        String[] stringsSorted = sorter.sortStringsUsingAComparator((Comparator<String> & Serializable) String::compareTo, names);
        Assert.assertEquals("Adam", stringsSorted[0]);
        Assert.assertEquals("Bob", stringsSorted[1]);
        Assert.assertEquals("Joe", stringsSorted[2]);
    }

}
