package com.redhat.qe.jdkspecifics.jdk8.streams.servlet;

import com.redhat.qe.jdkspecifics.jdk8.streams.common.Car;
import com.redhat.qe.jdkspecifics.jdk8.streams.common.Color;
import org.apache.commons.io.IOUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@RunWith(Arquillian.class)
@RunAsClient
public class ServletWithStreamsTest {

    @Deployment(name = "servlet-lambda-expression-test")
    public static WebArchive deployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "streams-in-servlet.war");
        war.addPackage(ServletWithStreamsTest.class.getPackage());
        war.addClasses(Car.class, Color.class);
        return war;
    }

    @Test
    public void streamInServletTest(@ArquillianResource URL url) throws IOException {
        URL servletUrl = new URL(url.toString() + "RedCarWeightCounter");
        String content = IOUtils.toString(servletUrl);
        Assert.assertEquals("Total weight of red cars: 3940", content);
    }
}
