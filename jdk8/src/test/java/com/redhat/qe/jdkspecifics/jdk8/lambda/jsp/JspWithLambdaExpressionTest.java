package com.redhat.qe.jdkspecifics.jdk8.lambda.jsp;

import com.redhat.qe.jdkspecifics.jdk8.JspJdk18ServerSetup;
import com.redhat.qe.jdkspecifics.jdk8.lambda.common.AnswerToEverythingComputation;
import org.apache.commons.io.IOUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.arquillian.api.ServerSetup;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.UrlAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@RunWith(Arquillian.class)
@RunAsClient
@ServerSetup(JspJdk18ServerSetup.class)
public class JspWithLambdaExpressionTest {

    private static final String DEPLOYMENT = "jsp-lambda-expression-test.war";

    @Deployment(name = DEPLOYMENT)
    public static WebArchive deployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, DEPLOYMENT)
                .addClass(AnswerToEverythingComputation.class)
                .add(new UrlAsset(JspWithLambdaExpressionTest.class.getResource("jsp-with-lambdas.jsp")), "jsp-with-lambdas.jsp")
                .add(new UrlAsset(JspWithLambdaExpressionTest.class.getResource("jsp-with-lambdas-implicit-variables.jsp")),
                        "jsp-with-lambdas-implicit-variables.jsp");
        war.as(ZipExporter.class).exportTo(new File("/tmp", war.getName()), true);
        return war;
    }


    @Test
    @OperateOnDeployment(DEPLOYMENT)
    public void testLambdaExpressionInJsp(@ArquillianResource URL url) throws IOException, InterruptedException {
        URL jspUrl = new URL(url.toString() + "jsp-with-lambdas.jsp");
        HttpURLConnection huc = (HttpURLConnection) jspUrl.openConnection();

        // if run after some other test (e.g. after DefaultMethodTest) it often fails with response code 500, adding this sleep prevents this happening
        Thread.sleep(5000);
        Assert.assertEquals("The jsp with lambda expression failed to be compiled, probably due https://bugzilla.redhat.com/show_bug.cgi?id=1193553",
                HttpURLConnection.HTTP_OK, huc.getResponseCode());
        String content = IOUtils.toString(jspUrl);
        Assert.assertEquals("Jsp doesn't contain valid output", "Answer to everything is: 42", content.trim());
    }

    @Test
    @OperateOnDeployment(DEPLOYMENT)
    public void testLambdaWithImplicitVariablesInJsp(@ArquillianResource URL url) throws IOException, InterruptedException {
        URL jspUrl = new URL(url.toString() + "jsp-with-lambdas-implicit-variables.jsp");
        HttpURLConnection huc = (HttpURLConnection) jspUrl.openConnection();

        // if run after some other test (e.g. after DefaultMethodTest) it often fails with response code 500, adding this sleep prevents this happening
        Thread.sleep(5000);
        Assert.assertEquals("The jsp with lambda expression using implicit variables failed to be compiled",
                HttpURLConnection.HTTP_OK, huc.getResponseCode());

    }
}
