<%@ page import="com.redhat.qe.jdkspecifics.jdk8.lambda.common.AnswerToEverythingComputation" %>
<%@ page import="java.io.IOException" %>


<%
    try {
        response.getWriter().write("Answer to everything is: " + ((AnswerToEverythingComputation) () -> 42).compute());
    } catch (IOException ex) {
        throw new RuntimeException(ex);
    }
%>
