package com.redhat.qe.jdkspecifics.jdk8.streams.common;

/**
 * @author Jan Martiska
 */
public class Car {

    private final Color color;
    private final Integer weight;

    public Car(Color color, Integer weight) {
        this.color = color;
        this.weight = weight;
    }

    public Integer getWeight() {
        return weight;
    }

    public Color getColor() {
        return color;
    }
}
