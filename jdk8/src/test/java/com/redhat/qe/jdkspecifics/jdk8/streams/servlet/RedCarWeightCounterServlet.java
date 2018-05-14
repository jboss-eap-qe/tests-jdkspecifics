package com.redhat.qe.jdkspecifics.jdk8.streams.servlet;

import com.redhat.qe.jdkspecifics.jdk8.streams.common.Car;
import com.redhat.qe.jdkspecifics.jdk8.streams.common.Color;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="RedCarWeightCounterServlet", urlPatterns={"/RedCarWeightCounter"})
public class RedCarWeightCounterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("text/plain");
        writer.write("Total weight of red cars: " + countTotalWeightOfRedCars(getAListOfCars()));
    }


    private Integer countTotalWeightOfRedCars(List<Car> cars) {
        return cars.stream()
                .filter(e -> e.getColor() == Color.RED)
                .mapToInt(Car::getWeight)
                .sum();
    }

    private List<Car> getAListOfCars() {
        List<Car> output = new ArrayList<>();
        output.add(new Car(Color.BLUE, 1180));
        output.add(new Car(Color.GREEN, 1070));
        output.add(new Car(Color.RED, 1890));
        output.add(new Car(Color.RED, 2050));
        output.add(new Car(Color.WHITE, 2290));
        output.add(new Car(Color.BLUE, 1890));
        output.add(new Car(Color.GREEN, 2450));
        output.add(new Car(Color.BLUE, 3600));
        return output;
    }
}
