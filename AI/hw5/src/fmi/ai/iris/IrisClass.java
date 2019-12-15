package fmi.ai.iris;

import java.util.stream.Stream;

import fmi.ai.Entity;

public enum IrisClass implements Entity.EntityCategory {
    SETOSA("Iris-setosa"),
    VERSICOLOUR("Iris-versicolor"),
    VIRGINICA("Iris-virginica");

    private final String name;

    IrisClass(String name) {
        this.name = name;
    }

    public static IrisClass fromString(String asString) {
        return Stream.of(IrisClass.values()).filter(irisClass -> irisClass.name.equals(asString)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return name;
    }
}