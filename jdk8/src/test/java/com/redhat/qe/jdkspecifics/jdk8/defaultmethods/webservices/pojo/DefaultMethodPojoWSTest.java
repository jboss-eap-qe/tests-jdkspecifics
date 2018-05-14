package com.redhat.qe.jdkspecifics.jdk8.defaultmethods.webservices.pojo;

import com.redhat.qe.jdkspecifics.jdk8.defaultmethods.webservices.GreeterDefaultInterface;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Tests for default method implementation in POJO JAX-WS webservices - see https://bugzilla.redhat.com/show_bug.cgi?id=1196686
 */
@RunWith(Arquillian.class)
public class DefaultMethodPojoWSTest
{

   public static final String JAX_WS_TEST_POJO = "jax-ws-test-pojo";

   public static final String WSDL_URL = "http://localhost:8080/" + JAX_WS_TEST_POJO + "/GreeterImpl?wsdl";

   public static final QName WS_QNAME = new QName(
         "http://pojo.webservices.defaultmethods.jdk8.jdkspecifics.qe.redhat.com/", "GreeterImplService");

   @Deployment(name = JAX_WS_TEST_POJO, testable = false)
   public static WebArchive deploymentWar()
   {
      WebArchive archive = ShrinkWrap.create(WebArchive.class, JAX_WS_TEST_POJO + ".war");
      archive.addClass(GreeterSEI.class);
      archive.addClass(GreeterImpl.class);
      archive.addClass(GreeterDefaultInterface.class);
      archive.setManifest(new StringAsset("Dependencies: org.jboss.logging"));
      return archive;
   }

   @Test
   @RunAsClient
   public void testPojoDefaultMethod() throws MalformedURLException
   {
      Service greeterService = Service.create(new URL(WSDL_URL), WS_QNAME);

      Assert.assertNotNull(greeterService);
      GreeterSEI greeter = greeterService.getPort(GreeterSEI.class);

      Assert.assertEquals("Greeter service should inherit default method implementation from GreeterSEI",
            GreeterSEI.HELLO_MESSAGE, greeter.sayHello());
   }

   @Test
   @RunAsClient
   public void testPojoInheritedDefaultMethod() throws MalformedURLException
   {
      Service greeterService = Service.create(new URL(WSDL_URL), WS_QNAME);

      Assert.assertNotNull(greeterService);
      GreeterSEI greeter = greeterService.getPort(GreeterSEI.class);

      Assert.assertEquals("Greeter service should inherit default method implementation from GreeterDefaultInterface",
            GreeterDefaultInterface.HI_MESSAGE, greeter.sayHi());
   }

   @Test
   @RunAsClient
   public void testPojoOverridedInheritedDefaultMethod() throws MalformedURLException
   {
      Service greeterService = Service.create(new URL(WSDL_URL), WS_QNAME);

      Assert.assertNotNull(greeterService);
      GreeterSEI greeter = greeterService.getPort(GreeterSEI.class);

      Assert.assertNotEquals(
            "Greeter service must not inherit default method implementation from GreeterDefaultInterface",
            GreeterDefaultInterface.BYE_MESSAGE_DEFAULT, greeter.sayBye());
      Assert.assertEquals("Greeter service should inherit default method implementation from GreeterSEI",
            GreeterSEI.BYE_MESSAGE_OVERRIDED, greeter.sayBye());
   }
}
