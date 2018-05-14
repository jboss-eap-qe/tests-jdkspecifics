package com.redhat.qe.jdkspecifics.jdk8.defaultmethods.webservices.ejb;

import com.redhat.qe.jdkspecifics.jdk8.defaultmethods.webservices.GreeterDefaultInterface;

import javax.ejb.Local;
import javax.jws.WebService;

@WebService
@Local
public interface GreeterEjbLocal extends GreeterDefaultInterface
{
   static final String HELLO_MESSAGE = "Hello from GreeterEjbLocal";

   default public String sayHello()
   {
      return HELLO_MESSAGE;
   }

   String BYE_MESSAGE_OVERRIDED = "Bye from GreeterEjbLocal";

   default public String sayBye()
   {
      return BYE_MESSAGE_OVERRIDED;
   }

}
