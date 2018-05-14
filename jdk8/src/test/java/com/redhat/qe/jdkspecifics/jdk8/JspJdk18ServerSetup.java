package com.redhat.qe.jdkspecifics.jdk8;

import org.jboss.as.arquillian.api.ServerSetupTask;
import org.jboss.as.arquillian.container.ManagementClient;
import org.wildfly.extras.creaper.core.online.ModelNodeResult;
import org.wildfly.extras.creaper.core.online.OnlineManagementClient;
import org.wildfly.extras.creaper.core.online.OnlineOptions;
import org.wildfly.extras.creaper.core.online.operations.Address;
import org.wildfly.extras.creaper.core.online.operations.Operations;
import org.wildfly.extras.creaper.core.online.operations.Parameters;
import org.wildfly.extras.creaper.core.online.operations.admin.Administration;

public class JspJdk18ServerSetup implements ServerSetupTask {

    private static ModelNodeResult origSourceVm;
    private static ModelNodeResult origTargetVm;
    private static OnlineManagementClient mgmtClient;
    private static Operations ops;
    private static Administration administration;
    private static final Address UNDERTOW_JSP_CONFIG_ADDR = Address.subsystem("undertow").and("servlet-container", "default").and("setting", "jsp");
    private static final Address WEB_JSP_CONFIG_ADDR = Address.subsystem("web").and("configuration", "jsp-configuration");
    private static Address jspConfigAddr;
    private static String DELETE_WORK_DIR_ON_CONTEXTDESTROY_PROP_NAME = "org.jboss.as.web.deployment.DELETE_WORK_DIR_ONCONTEXTDESTROY";
    private static Address DELETE_WORK_DIR_ON_CONTEXTDESTROY_PROP = Address.of("system-property", DELETE_WORK_DIR_ON_CONTEXTDESTROY_PROP_NAME);

    @Override
    public void setup(ManagementClient managementClient, String s) throws Exception {
        mgmtClient = org.wildfly.extras.creaper.core.ManagementClient.onlineLazy(OnlineOptions.standalone().hostAndPort(managementClient.getMgmtAddress(), managementClient.getMgmtPort()).build());
        ops = new Operations(mgmtClient);
        administration = new Administration(mgmtClient);

        if (ops.exists(WEB_JSP_CONFIG_ADDR)) {
            jspConfigAddr = WEB_JSP_CONFIG_ADDR;
        } else {
            jspConfigAddr = UNDERTOW_JSP_CONFIG_ADDR;
        }

        origSourceVm = ops.readAttribute(jspConfigAddr, "source-vm");
        origTargetVm = ops.readAttribute(jspConfigAddr, "target-vm");
        ops.writeAttribute(jspConfigAddr, "source-vm", "1.8");
        ops.writeAttribute(jspConfigAddr, "target-vm", "1.8");
        ops.removeIfExists(DELETE_WORK_DIR_ON_CONTEXTDESTROY_PROP);
        ops.add(DELETE_WORK_DIR_ON_CONTEXTDESTROY_PROP, Parameters.of("value", "true"));
        administration.reloadIfRequired();
    }

    @Override
    public void tearDown(ManagementClient managementClient, String s) throws Exception {
        if (origSourceVm != null) {
            ops.writeAttribute(jspConfigAddr, "source-vm", origSourceVm.value());
        }
        if (origTargetVm != null) {
            ops.writeAttribute(jspConfigAddr, "target-vm", origTargetVm.value());
        }
        ops.removeIfExists(DELETE_WORK_DIR_ON_CONTEXTDESTROY_PROP);

        administration.reloadIfRequired();

    }
}
