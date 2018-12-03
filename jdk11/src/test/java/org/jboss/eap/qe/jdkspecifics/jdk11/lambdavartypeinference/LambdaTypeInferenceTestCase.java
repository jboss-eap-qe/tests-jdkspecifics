package org.jboss.eap.qe.jdkspecifics.jdk11.lambdavartypeinference;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.eap.qe.jdkspecifics.jdk11.tools.LoggerProducer;
import org.jboss.eap.qe.jdkspecifics.jdk11.lambdavartypeinference.cdi.CDILambdaVarInferenceTestBean;
import org.jboss.eap.qe.jdkspecifics.jdk11.lambdavartypeinference.ejb.LambdaVarInferenceTestEJBLocal;
import org.jboss.eap.qe.jdkspecifics.jdk11.lambdavartypeinference.pojo.LambdaVarInferenceUtil;
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
public class LambdaTypeInferenceTestCase {

    public static final Set<String> LOWER_CASE_TEST_SET = Stream.of("hydrogen", "helium").collect(Collectors.toSet());
    public static final Set<String> UPPER_CASE_TEST_SET = Stream.of("HYDROGEN", "HELIUM").collect(Collectors.toSet());


    @Deployment(name = "local-variable-type-inference-test")
    public static JavaArchive deployment() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "convenience-collection-method-test.jar");
        archive.addPackage(LambdaTypeInferenceTestCase.class.getPackage());
        archive.addPackage(CDILambdaVarInferenceTestBean.class.getPackage());
        archive.addPackage(LambdaVarInferenceTestEJBLocal.class.getPackage());
        archive.addPackage(LambdaVarInferenceUtil.class.getPackage());
        archive.addClass(LoggerProducer.class);
        archive.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        archive.setManifest(new StringAsset("Dependencies: org.jboss.logging"));
        return archive;
    }

    @EJB
    private LambdaVarInferenceTestEJBLocal testEjb;

    @Test
    public void testLocalVariableTypeInferenceWithEjb() {
        Assert.assertEquals(testEjb.toUpperCaseWithVarInference(LOWER_CASE_TEST_SET), UPPER_CASE_TEST_SET);
    }

    @Inject
    private CDILambdaVarInferenceTestBean testCdiBean;

    @Test
    public void testLocalVariableTypeInferenceWithCdi() {
        Assert.assertEquals(testCdiBean.toUpperCaseWithVarInference(LOWER_CASE_TEST_SET), UPPER_CASE_TEST_SET);
    }

    @Test
    public void testLocalVariableTypeInferenceWithPojo() {
        Assert.assertEquals(new LambdaVarInferenceUtil().toUpperCaseWithVarInference(LOWER_CASE_TEST_SET), UPPER_CASE_TEST_SET);
    }

}
