package fmi.ai;

import java.util.Objects;

public class Person {
    private final Sex sex;
    private final double height;
    private final int weight;
    private final int footLength;

    public Person(Sex sex, double height, int weight, int footLength) {
        this.sex = sex;
        this.height = height;
        this.weight = weight;
        this.footLength = footLength;
    }

    public Sex getSex() {
        return sex;
    }

    public double getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getFootLength() {
        return footLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return Double.compare(person.height, height) == 0 &&
                weight == person.weight &&
                footLength == person.footLength &&
                sex == person.sex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sex, height, weight, footLength);
    }

    @Override
    public String toString() {
        return "Person{" +
                "sex=" + sex +
                ", height=" + height +
                ", weight=" + weight +
                ", footLength=" + footLength +
                '}';
    }
}
