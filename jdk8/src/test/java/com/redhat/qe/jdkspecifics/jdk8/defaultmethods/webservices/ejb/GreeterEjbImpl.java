package com.redhat.qe.jdkspecifics.jdk8.defaultmethods.webservices.ejb;

import javax.ejb.Stateless;
import javax.jws.WebService;

@WebService
@Stateless
public class GreeterEjbImpl implements GreeterEjbLocal
{
}
