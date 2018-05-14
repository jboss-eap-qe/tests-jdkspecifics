package com.redhat.qe.jdkspecifics.jdk8.defaultmethods.webservices;

public interface GreeterDefaultInterface
{
   static final String HI_MESSAGE = "Hi from DefaultInterface";

   default public String sayHi()
   {
      return HI_MESSAGE;
   }

   String BYE_MESSAGE_DEFAULT = "Bye from DefaultInterface";

   default public String sayBye()
   {
      return BYE_MESSAGE_DEFAULT;
   }
}
