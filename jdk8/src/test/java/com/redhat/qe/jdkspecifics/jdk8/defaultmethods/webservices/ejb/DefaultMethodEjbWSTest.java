package com.redhat.qe.jdkspecifics.jdk8.defaultmethods.webservices.ejb;

import com.redhat.qe.jdkspecifics.jdk8.defaultmethods.webservices.GreeterDefaultInterface;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
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
 * Tests for default method implementation in EJB webservices - see https://bugzilla.redhat.com/show_bug.cgi?id=1196686
 */
@RunWith(Arquillian.class)
public class DefaultMethodEjbWSTest
{

   public static final String JAX_WS_TEST_EJB = "jax-ws-test-ejb";

   public static final String WSDL_URL = "http://localhost:8080/" + JAX_WS_TEST_EJB + "/GreeterEjbImpl?wsdl";

   public static final QName WS_QNAME = new QName(
         "http://ejb.webservices.defaultmethods.jdk8.jdkspecifics.qe.redhat.com/", "GreeterEjbImplService");

   @Deployment(name = JAX_WS_TEST_EJB, testable = false)
   public static WebArchive deploymentWar()
   {
      WebArchive archive = ShrinkWrap.create(WebArchive.class, JAX_WS_TEST_EJB + ".war");
      archive.addClass(GreeterEjbLocal.class);
      archive.addClass(GreeterEjbImpl.class);
      archive.addClass(GreeterDefaultInterface.class);
      archive.setManifest(new StringAsset("Dependencies: org.jboss.logging"));
      archive.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
      return archive;
   }

   @Test
   @RunAsClient
   public void testPojoDefaultMethod() throws MalformedURLException
   {
      Service greeterService = Service.create(new URL(WSDL_URL), WS_QNAME);

      Assert.assertNotNull(greeterService);
      GreeterEjbLocal greeter = greeterService.getPort(GreeterEjbLocal.class);

      Assert.assertEquals("Greeter service should inherit default method implementation from GreeterEjbLocal",
            GreeterEjbLocal.HELLO_MESSAGE, greeter.sayHello());
   }

   @Test
   @RunAsClient
   public void testPojoInheritedDefaultMethod() throws MalformedURLException
   {
      Service greeterService = Service.create(new URL(WSDL_URL), WS_QNAME);

      Assert.assertNotNull(greeterService);
      GreeterEjbLocal greeter = greeterService.getPort(GreeterEjbLocal.class);

      Assert.assertEquals("Greeter service should inherit default method implementation from GreeterDefaultInterface",
            GreeterDefaultInterface.HI_MESSAGE, greeter.sayHi());
   }

   @Test
   @RunAsClient
   public void testPojoOverridedInheritedDefaultMethod() throws MalformedURLException
   {
      Service greeterService = Service.create(new URL(WSDL_URL), WS_QNAME);

      Assert.assertNotNull(greeterService);
      GreeterEjbLocal greeter = greeterService.getPort(GreeterEjbLocal.class);

      Assert.assertNotEquals(
            "Greeter service must not inherit default method implementation from GreeterDefaultInterface",
            GreeterDefaultInterface.BYE_MESSAGE_DEFAULT, greeter.sayBye());
      Assert.assertEquals("Greeter service should inherit default method implementation from GreeterEjbLocal",
            GreeterEjbLocal.BYE_MESSAGE_OVERRIDED, greeter.sayBye());
   }
}
