<%@ page language="java" pageEncoding="UTF-8" contentType="text/plain;charset=utf-8"%>
<%@ page import="java.io.IOException" %>
<%
String[] names = {"Joe", "Doe", "John", "Peter"};
java.util.List<String> namesAsList = java.util.Arrays.asList(names);

final JspWriter outFinal = out;
namesAsList.forEach((String x) -> {
        try {
            outFinal.println(x);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
);

%>


