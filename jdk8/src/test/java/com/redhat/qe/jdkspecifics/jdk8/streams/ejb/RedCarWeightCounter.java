package com.redhat.qe.jdkspecifics.jdk8.streams.ejb;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.redhat.qe.jdkspecifics.jdk8.streams.common.Car;
import com.redhat.qe.jdkspecifics.jdk8.streams.common.Color;

/**
 * @author Jan Martiska
 */
@Stateless
@LocalBean
public class RedCarWeightCounter {

    public Integer countTotalWeightOfRedCars(List<Car> cars) {
        return cars.stream()
                .filter(e -> e.getColor() == Color.RED)
                .mapToInt(Car::getWeight)
                .sum();
    }
}
