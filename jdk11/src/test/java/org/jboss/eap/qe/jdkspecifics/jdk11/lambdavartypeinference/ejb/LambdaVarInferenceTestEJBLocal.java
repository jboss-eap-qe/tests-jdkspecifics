package org.jboss.eap.qe.jdkspecifics.jdk11.lambdavartypeinference.ejb;

import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:mjurc@redhat.com">Michal Jurc</a> (c) 2018 Red Hat, Inc.
 */
@Local(LambdaVarInferenceTestEJBImpl.class)
public interface LambdaVarInferenceTestEJBLocal {

    default public Set<String> toUpperCaseWithVarInference(Set<String> inputSet) {
        return inputSet.stream().map((@NotNull var s) -> s.toUpperCase()).collect(Collectors.toSet());
    }

}
