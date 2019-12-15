package fmi.ai.iris;

import java.util.Objects;

import fmi.ai.Entity;
import fmi.ai.FieldNormalizer;

public class Iris implements Entity<Iris> {

    private final double sepalLength;
    private final double sepalWidth;
    private final double petalLength;
    private final double petalWidth;
    private final IrisClass irisClass;

    public Iris(double sepalLength, double sepalWidth, double petalLength, double petalWidth,
            IrisClass irisClass) {
        this.sepalLength = sepalLength;
        this.sepalWidth = sepalWidth;
        this.petalLength = petalLength;
        this.petalWidth = petalWidth;
        this.irisClass = irisClass;
    }

    public Iris(double sepalLength, double sepalWidth, double petalLength, double petalWidth) {
        this.sepalLength = sepalLength;
        this.sepalWidth = sepalWidth;
        this.petalLength = petalLength;
        this.petalWidth = petalWidth;
        this.irisClass = null;
    }

    public Iris normalize(FieldNormalizer sepalLengthFieldNormalizer, FieldNormalizer sepalWidthFieldNormalizer,
            FieldNormalizer petalLengthFieldNormalizer, FieldNormalizer petalWidthFieldNormalizer) {
        return new Iris(
                sepalLengthFieldNormalizer.normalize(sepalLength),
                sepalWidthFieldNormalizer.normalize(sepalWidth),
                petalLengthFieldNormalizer.normalize(petalLength),
                petalWidthFieldNormalizer.normalize(petalWidth),
                irisClass
        );
    }

    public Iris classify(IrisClass irisClass) {
        return new Iris(sepalLength, sepalWidth, petalLength, petalWidth, irisClass);
    }

    public double getSepalLength() {
        return sepalLength;
    }

    public double getSepalWidth() {
        return sepalWidth;
    }

    public double getPetalLength() {
        return petalLength;
    }

    public double getPetalWidth() {
        return petalWidth;
    }

    @Override
    public double calculateDistance(Iris other) {
        return Math.abs(this.sepalLength - other.sepalLength) +
                Math.abs(this.sepalWidth - other.sepalWidth) +
                Math.abs(this.petalLength - other.sepalLength) +
                Math.abs(this.petalWidth - other.petalWidth);
    }

    @Override
    public IrisClass getCategory() {
        return irisClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Iris iris = (Iris) o;
        return Double.compare(iris.sepalLength, sepalLength) == 0 &&
                Double.compare(iris.sepalWidth, sepalWidth) == 0 &&
                Double.compare(iris.petalLength, petalLength) == 0 &&
                Double.compare(iris.petalWidth, petalWidth) == 0 &&
                irisClass == iris.irisClass;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sepalLength, sepalWidth, petalLength, petalWidth, irisClass);
    }

    @Override
    public String toString() {
        return "Iris{" +
                "sepalLength=" + sepalLength +
                ", sepalWidth=" + sepalWidth +
                ", petalLength=" + petalLength +
                ", petalWidth=" + petalWidth +
                ", irisClass=" + irisClass +
                '}';
    }
}
