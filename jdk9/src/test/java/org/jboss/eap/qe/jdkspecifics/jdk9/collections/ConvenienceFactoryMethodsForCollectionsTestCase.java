package org.jboss.eap.qe.jdkspecifics.jdk9.collections;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.eap.qe.jdkspecifics.jdk9.collections.cdi.CDICollectionsTestBean;
import org.jboss.eap.qe.jdkspecifics.jdk9.collections.ejb.CollectionsTestEJBLocal;
import org.jboss.eap.qe.jdkspecifics.jdk9.collections.pojo.TestCollectionUtilities;
import org.jboss.eap.qe.jdkspecifics.jdk9.tools.LoggerProducer;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:mjurc@redhat.com">Michal Jurc</a> (c) 2018 Red Hat, Inc.
 */
@RunWith(Arquillian.class)
public class ConvenienceFactoryMethodsForCollectionsTestCase {

    public static final String ELEMENT_1 = "hydrogen";
    public static final String ELEMENT_2 = "helium";
    public static final String ELEMENT_3 = "lithium";
    public static final String ELEMENT_4 = "beryllium";
    public static final String ELEMENT_5 = "boron";
    public static final String ELEMENT_6 = "carbon";
    public static final String ELEMENT_7 = "nitrogen";
    public static final String ELEMENT_8 = "oxygen";
    public static final String ELEMENT_9 = "fluorine";
    public static final String ELEMENT_10 = "neon";
    public static final String ELEMENT_11 = "sodium";
    public static final String ELEMENT_12 = "magnesium";
    public static final String ELEMENT_13 = "aluminium";

    public static final Set<String> SET_OF_TWO_ELEMENTS = Stream.of(ELEMENT_1, ELEMENT_2).collect(Collectors.toSet());
    public static final Set<String> SET_OF_THIRTEEN_ELEMENTS = Stream.of(ELEMENT_1, ELEMENT_2, ELEMENT_3, ELEMENT_4, ELEMENT_5,
            ELEMENT_6, ELEMENT_7, ELEMENT_8, ELEMENT_9, ELEMENT_10, ELEMENT_11, ELEMENT_12, ELEMENT_13)
            .collect(Collectors.toSet());

    public static final List<String> LIST_OF_TWO_ELEMENTS = Stream.of(ELEMENT_1, ELEMENT_2).collect(Collectors.toList());
    public static final List<String> LIST_OF_THIRTEEN_ELEMENTS = Stream.of(ELEMENT_1, ELEMENT_2, ELEMENT_3, ELEMENT_4, ELEMENT_5,
            ELEMENT_6, ELEMENT_7, ELEMENT_8, ELEMENT_9, ELEMENT_10, ELEMENT_11, ELEMENT_12, ELEMENT_13)
            .collect(Collectors.toList());

    public static final Map<Integer, String> MAP_OF_TWO_ELEMENTS = Stream.of(
            new AbstractMap.SimpleEntry<>(1, ELEMENT_1),
            new AbstractMap.SimpleEntry<>(2, ELEMENT_2)
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    public static final Map<Integer, String> MAP_OF_THIRTEEN_ELEMENTS = Stream.of(
            new AbstractMap.SimpleEntry<>(1, ELEMENT_1),
            new AbstractMap.SimpleEntry<>(2, ELEMENT_2),
            new AbstractMap.SimpleEntry<>(3, ELEMENT_3),
            new AbstractMap.SimpleEntry<>(4, ELEMENT_4),
            new AbstractMap.SimpleEntry<>(5, ELEMENT_5),
            new AbstractMap.SimpleEntry<>(6, ELEMENT_6),
            new AbstractMap.SimpleEntry<>(7, ELEMENT_7),
            new AbstractMap.SimpleEntry<>(8, ELEMENT_8),
            new AbstractMap.SimpleEntry<>(9, ELEMENT_9),
            new AbstractMap.SimpleEntry<>(10, ELEMENT_10),
            new AbstractMap.SimpleEntry<>(11, ELEMENT_11),
            new AbstractMap.SimpleEntry<>(12, ELEMENT_12),
            new AbstractMap.SimpleEntry<>(13, ELEMENT_13)
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    @Deployment(name = "convenience-collection-method-test")
    public static JavaArchive deployment() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "convenience-collection-method-test.jar");
        archive.addPackage(ConvenienceFactoryMethodsForCollectionsTestCase.class.getPackage());
        archive.addPackage(CDICollectionsTestBean.class.getPackage());
        archive.addPackage(CollectionsTestEJBLocal.class.getPackage());
        archive.addPackage(TestCollectionUtilities.class.getPackage());
        archive.addClass(LoggerProducer.class);
        archive.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        archive.setManifest(new StringAsset("Dependencies: org.jboss.logging"));
        return archive;
    }

    @EJB
    private CollectionsTestEJBLocal testEjb;

    @Test
    public void testConvenienceMethodsWithEJb() {
        Assert.assertEquals(testEjb.setOfTwoElements(ELEMENT_1, ELEMENT_2), SET_OF_TWO_ELEMENTS);
        Assert.assertEquals(testEjb.setOfManyElements(ELEMENT_1, ELEMENT_2, ELEMENT_3, ELEMENT_4, ELEMENT_5, ELEMENT_6, ELEMENT_7,
                ELEMENT_8, ELEMENT_9, ELEMENT_10, ELEMENT_11, ELEMENT_12, ELEMENT_13), SET_OF_THIRTEEN_ELEMENTS);
        Assert.assertEquals(testEjb.listOfManyElements(ELEMENT_1, ELEMENT_2), LIST_OF_TWO_ELEMENTS);
        Assert.assertEquals(testEjb.listOfManyElements(ELEMENT_1, ELEMENT_2, ELEMENT_3, ELEMENT_4, ELEMENT_5, ELEMENT_6, ELEMENT_7,
                ELEMENT_8, ELEMENT_9, ELEMENT_10, ELEMENT_11, ELEMENT_12, ELEMENT_13), LIST_OF_THIRTEEN_ELEMENTS);
        Assert.assertEquals(testEjb.mapOfTwoKeysAndTwoValues(1, ELEMENT_1, 2, ELEMENT_2), MAP_OF_TWO_ELEMENTS);
        Assert.assertEquals(testEjb.mapOfManyEntries(new AbstractMap.SimpleEntry<>(1, ELEMENT_1),
                new AbstractMap.SimpleEntry<>(2, ELEMENT_2),
                new AbstractMap.SimpleEntry<>(3, ELEMENT_3),
                new AbstractMap.SimpleEntry<>(4, ELEMENT_4),
                new AbstractMap.SimpleEntry<>(5, ELEMENT_5),
                new AbstractMap.SimpleEntry<>(6, ELEMENT_6),
                new AbstractMap.SimpleEntry<>(7, ELEMENT_7),
                new AbstractMap.SimpleEntry<>(8, ELEMENT_8),
                new AbstractMap.SimpleEntry<>(9, ELEMENT_9),
                new AbstractMap.SimpleEntry<>(10, ELEMENT_10),
                new AbstractMap.SimpleEntry<>(11, ELEMENT_11),
                new AbstractMap.SimpleEntry<>(12, ELEMENT_12),
                new AbstractMap.SimpleEntry<>(13, ELEMENT_13)), MAP_OF_THIRTEEN_ELEMENTS);
    }

    @Inject
    private CDICollectionsTestBean testCdiBean;

    @Test
    public void testConvenienceMethodsWithCdi() {
        Assert.assertEquals(testCdiBean.setOfTwoElements(ELEMENT_1, ELEMENT_2), SET_OF_TWO_ELEMENTS);
        Assert.assertEquals(testCdiBean.setOfManyElements(ELEMENT_1, ELEMENT_2, ELEMENT_3, ELEMENT_4, ELEMENT_5, ELEMENT_6,
                ELEMENT_7, ELEMENT_8, ELEMENT_9, ELEMENT_10, ELEMENT_11, ELEMENT_12, ELEMENT_13), SET_OF_THIRTEEN_ELEMENTS);
        Assert.assertEquals(testCdiBean.listOfManyElements(ELEMENT_1, ELEMENT_2), LIST_OF_TWO_ELEMENTS);
        Assert.assertEquals(testCdiBean.listOfManyElements(ELEMENT_1, ELEMENT_2, ELEMENT_3, ELEMENT_4, ELEMENT_5, ELEMENT_6,
                ELEMENT_7, ELEMENT_8, ELEMENT_9, ELEMENT_10, ELEMENT_11, ELEMENT_12, ELEMENT_13), LIST_OF_THIRTEEN_ELEMENTS);
        Assert.assertEquals(testCdiBean.mapOfTwoKeysAndTwoValues(1, ELEMENT_1, 2, ELEMENT_2), MAP_OF_TWO_ELEMENTS);
        Assert.assertEquals(testCdiBean.mapOfManyEntries(new AbstractMap.SimpleEntry<>(1, ELEMENT_1),
                new AbstractMap.SimpleEntry<>(2, ELEMENT_2),
                new AbstractMap.SimpleEntry<>(3, ELEMENT_3),
                new AbstractMap.SimpleEntry<>(4, ELEMENT_4),
                new AbstractMap.SimpleEntry<>(5, ELEMENT_5),
                new AbstractMap.SimpleEntry<>(6, ELEMENT_6),
                new AbstractMap.SimpleEntry<>(7, ELEMENT_7),
                new AbstractMap.SimpleEntry<>(8, ELEMENT_8),
                new AbstractMap.SimpleEntry<>(9, ELEMENT_9),
                new AbstractMap.SimpleEntry<>(10, ELEMENT_10),
                new AbstractMap.SimpleEntry<>(11, ELEMENT_11),
                new AbstractMap.SimpleEntry<>(12, ELEMENT_12),
                new AbstractMap.SimpleEntry<>(13, ELEMENT_13)), MAP_OF_THIRTEEN_ELEMENTS);
    }

    @Test
    public void testConvenienceMeethodsWithPojo() {
        Assert.assertEquals(new TestCollectionUtilities().setOfTwoElements(ELEMENT_1, ELEMENT_2), SET_OF_TWO_ELEMENTS);
        Assert.assertEquals(new TestCollectionUtilities().setOfManyElements(ELEMENT_1, ELEMENT_2, ELEMENT_3, ELEMENT_4, ELEMENT_5,
                ELEMENT_6, ELEMENT_7, ELEMENT_8, ELEMENT_9, ELEMENT_10, ELEMENT_11, ELEMENT_12, ELEMENT_13),
                SET_OF_THIRTEEN_ELEMENTS);
        Assert.assertEquals(new TestCollectionUtilities().listOfManyElements(ELEMENT_1, ELEMENT_2), LIST_OF_TWO_ELEMENTS);
        Assert.assertEquals(new TestCollectionUtilities().listOfManyElements(ELEMENT_1, ELEMENT_2, ELEMENT_3, ELEMENT_4,
                ELEMENT_5, ELEMENT_6, ELEMENT_7, ELEMENT_8, ELEMENT_9, ELEMENT_10, ELEMENT_11, ELEMENT_12, ELEMENT_13),
                LIST_OF_THIRTEEN_ELEMENTS);
        Assert.assertEquals(new TestCollectionUtilities().mapOfTwoKeysAndTwoValues(1, ELEMENT_1, 2, ELEMENT_2),
                MAP_OF_TWO_ELEMENTS);
        Assert.assertEquals(new TestCollectionUtilities().mapOfManyEntries(new AbstractMap.SimpleEntry<>(1, ELEMENT_1),
                new AbstractMap.SimpleEntry<>(2, ELEMENT_2),
                new AbstractMap.SimpleEntry<>(3, ELEMENT_3),
                new AbstractMap.SimpleEntry<>(4, ELEMENT_4),
                new AbstractMap.SimpleEntry<>(5, ELEMENT_5),
                new AbstractMap.SimpleEntry<>(6, ELEMENT_6),
                new AbstractMap.SimpleEntry<>(7, ELEMENT_7),
                new AbstractMap.SimpleEntry<>(8, ELEMENT_8),
                new AbstractMap.SimpleEntry<>(9, ELEMENT_9),
                new AbstractMap.SimpleEntry<>(10, ELEMENT_10),
                new AbstractMap.SimpleEntry<>(11, ELEMENT_11),
                new AbstractMap.SimpleEntry<>(12, ELEMENT_12),
                new AbstractMap.SimpleEntry<>(13, ELEMENT_13)), MAP_OF_THIRTEEN_ELEMENTS);
    }

}
