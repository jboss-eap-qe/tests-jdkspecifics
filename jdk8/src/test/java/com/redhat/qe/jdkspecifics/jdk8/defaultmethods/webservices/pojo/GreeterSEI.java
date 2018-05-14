package com.redhat.qe.jdkspecifics.jdk8.defaultmethods.webservices.pojo;

import com.redhat.qe.jdkspecifics.jdk8.defaultmethods.webservices.GreeterDefaultInterface;

import javax.jws.WebService;

@WebService
public interface GreeterSEI extends GreeterDefaultInterface
{
   String HELLO_MESSAGE = "Hello from GreeterSEI";

   default public String sayHello()
   {
      return HELLO_MESSAGE;
   }

   String BYE_MESSAGE_OVERRIDED = "Bye from GreeterSEI";

   default public String sayBye()
   {
      return BYE_MESSAGE_OVERRIDED;
   }

}
