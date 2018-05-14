package com.redhat.qe.jdkspecifics.jdk8.defaultmethods.cdi.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * @author Jan Martiska
 */
@Interceptor
@DogeIntercepted
public class Doge implements DogeInterface {

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        return sayWow();
    }

}
