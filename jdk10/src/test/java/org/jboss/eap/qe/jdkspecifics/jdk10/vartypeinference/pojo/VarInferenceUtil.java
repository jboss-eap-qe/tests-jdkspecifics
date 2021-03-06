package org.jboss.eap.qe.jdkspecifics.jdk10.vartypeinference.pojo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:mjurc@redhat.com">Michal Jurc</a> (c) 2018 Red Hat, Inc.
 */
public class VarInferenceUtil {

    public static final String VAR_INFERENCE_FOR_RETURN_PREFIX = "Successfully processed and result type inferred by local " +
            "variable type for input: ";

    public String varInferenceForReturn(String input) {
        var result = VAR_INFERENCE_FOR_RETURN_PREFIX + input;
        return result;
    }

    public int varInferenceIterationVar(int... input) {
        int result = 0;
        for (var currentInput : input) {
            result += currentInput;
        }
        return result;
    }

    public int varInferenceIterationIndex(int... input) {
        int result = 0;
        for (var i = 0; i < input.length; i++) {
            result += input[i];
        }
        return result;
    }

    public String varInferenceFunctionReturn() {
        var functionResult = VarInferenceUtil.function();
        return functionResult;
    }

    public Map<String, String> varInferenceGenerics() {
        var result = new HashMap<String,String>();
        result.put("testKey", "testValue");
        return result;
    }

    public Set<String> varInferenceTryWithResources() {
        Set<String> result = new HashSet<>();
        try (var stream = Stream.of("hydrogen", "helium")) {
            result = stream.collect(Collectors.toSet());
        } catch (Exception e) {
            throw new RuntimeException("Ooops, this really should not be happening.");
        }
        return result;
    }

    public static String function() {
        return "Hello from function";
    }

}
