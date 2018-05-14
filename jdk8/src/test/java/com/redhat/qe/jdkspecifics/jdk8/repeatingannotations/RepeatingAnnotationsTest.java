package com.redhat.qe.jdkspecifics.jdk8.repeatingannotations;

import java.lang.reflect.AnnotatedElement;

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
public class RepeatingAnnotationsTest {

    @Deployment(name = "repeating-annotations-test")
    public static JavaArchive deployment() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "repeating-annotations-test.jar");
        archive.addPackage(RepeatingAnnotationsTest.class.getPackage());
        archive.addClass(LoggerProducer.class);
        archive.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        archive.setManifest(new StringAsset("Dependencies: org.jboss.logging"));
        return archive;
    }

    @Inject
    Logger logger;

    @Test
    public void test() {
        Skill[] skills = ChuckNorris.class.getAnnotationsByType(Skill.class);
        Assert.assertNotNull("Couldn't retrieve annotations", skills);

        for (Skill skill : skills) {
            logger.info("Chuck Norris can: " + skill.value());
        }
        Assert.assertEquals("There should be exactly two annotations", 2, skills.length);
    }

}
