package com.redhat.qe.jdkspecifics.jdk8.streams.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import com.redhat.qe.jdkspecifics.jdk8.streams.common.Car;
import com.redhat.qe.jdkspecifics.jdk8.streams.common.Color;
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
public class StreamTest {

    @Deployment(name = "ejb-stream-test")
    public static JavaArchive deployment() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "ejb-stream-test.jar");
        archive.addPackage(StreamTest.class.getPackage());
        archive.addPackage(Car.class.getPackage());
        archive.addClass(LoggerProducer.class);
        archive.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        archive.setManifest(new StringAsset("Dependencies: org.jboss.logging"));
        return archive;
    }

    @Inject
    RedCarWeightCounter counter;

    @Test
    public void doTest() {
        List<Car> cars = getAListOfCars();
        Integer totalWeightOfRedCars = counter.countTotalWeightOfRedCars(cars);
        Assert.assertEquals((Integer)3940, totalWeightOfRedCars);
    }

    private List<Car> getAListOfCars() {
        List<Car> output = new ArrayList<>();
        output.add(new Car(Color.BLUE, 1180));
        output.add(new Car(Color.GREEN, 1070));
        output.add(new Car(Color.RED, 1890));
        output.add(new Car(Color.RED, 2050));
        output.add(new Car(Color.WHITE, 2290));
        output.add(new Car(Color.BLUE, 1890));
        output.add(new Car(Color.GREEN, 2450));
        output.add(new Car(Color.BLUE, 3600));
        return output;
    }

}
