package org.jboss.eap.qe.jdkspecifics.jdk10.vartypeinference;

import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.eap.qe.jdkspecifics.jdk10.tools.LoggerProducer;
import org.jboss.eap.qe.jdkspecifics.jdk10.vartypeinference.cdi.CDIVarInferenceTestBean;
import org.jboss.eap.qe.jdkspecifics.jdk10.vartypeinference.ejb.VarInferenceTestEJBLocal;
import org.jboss.eap.qe.jdkspecifics.jdk10.vartypeinference.pojo.VarInferenceUtil;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:mjurc@redhat.com">Michal Jurc</a> (c) 2018 Red Hat, Inc.
 */
@RunWith(Arquillian.class)
public class LocalVariableTypeInferenceTestCase {

    @Deployment(name = "local-variable-type-inference-test")
    public static JavaArchive deployment() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "convenience-collection-method-test.jar");
        archive.addPackage(LocalVariableTypeInferenceTestCase.class.getPackage());
        archive.addPackage(CDIVarInferenceTestBean.class.getPackage());
        archive.addPackage(VarInferenceTestEJBLocal.class.getPackage());
        archive.addPackage(VarInferenceUtil.class.getPackage());
        archive.addClass(LoggerProducer.class);
        archive.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        archive.setManifest(new StringAsset("Dependencies: org.jboss.logging"));
        return archive;
    }

    @EJB
    private VarInferenceTestEJBLocal testEjb;

    @Test
    public void testLocalVariableTypeInferenceWithEjb() {
        Assert.assertEquals(testEjb.varInferenceForReturn("Test input"), VarInferenceTestEJBLocal.VAR_INFERENCE_FOR_RETURN_PREFIX +
                "Test input");
        Assert.assertEquals(testEjb.varInferenceIterationIndex(0, 1, 2, 3), 6);
        Assert.assertEquals(testEjb.varInferenceIterationVar(0, 1, 2, 3), 6);
        Assert.assertEquals(testEjb.varInferenceFunctionReturn(), "Hello from function");
        Assert.assertEquals(testEjb.varInferenceGenerics(), Map.of("testKey", "testValue"));
        Assert.assertEquals(testEjb.varInferenceTryWithResources(), Set.of("hydrogen", "helium"));
    }

    @Inject
    private CDIVarInferenceTestBean testCdiBean;

    @Test
    public void testLocalVariableTypeInferenceWithCdi() {
        Assert.assertEquals(testCdiBean.varInferenceForReturn("Test input"),
                VarInferenceTestEJBLocal.VAR_INFERENCE_FOR_RETURN_PREFIX + "Test input");
        Assert.assertEquals(testCdiBean.varInferenceIterationIndex(0, 1, 2, 3), 6);
        Assert.assertEquals(testCdiBean.varInferenceIterationVar(0, 1, 2, 3), 6);
        Assert.assertEquals(testCdiBean.varInferenceFunctionReturn(), "Hello from function");
        Assert.assertEquals(testCdiBean.varInferenceGenerics(), Map.of("testKey", "testValue"));
        Assert.assertEquals(testCdiBean.varInferenceTryWithResources(), Set.of("hydrogen", "helium"));
    }

    @Test
    public void testLocalVariableTypeInferenceWithPojo() {
        Assert.assertEquals(new VarInferenceUtil().varInferenceForReturn("Test input"),
                VarInferenceTestEJBLocal.VAR_INFERENCE_FOR_RETURN_PREFIX + "Test input");
        Assert.assertEquals(new VarInferenceUtil().varInferenceIterationIndex(0, 1, 2, 3), 6);
        Assert.assertEquals(new VarInferenceUtil().varInferenceIterationVar(0, 1, 2, 3), 6);
        Assert.assertEquals(new VarInferenceUtil().varInferenceFunctionReturn(), "Hello from function");
        Assert.assertEquals(new VarInferenceUtil().varInferenceGenerics(), Map.of("testKey", "testValue"));
        Assert.assertEquals(new VarInferenceUtil().varInferenceTryWithResources(), Set.of("hydrogen", "helium"));
    }

}
