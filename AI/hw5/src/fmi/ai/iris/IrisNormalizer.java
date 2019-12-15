package fmi.ai.iris;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import fmi.ai.Dataset;
import fmi.ai.FieldNormalizer;

public class IrisNormalizer {
    private final Dataset<Iris> dataset;
    private final FieldNormalizer sepalWidthNormalizer;
    private final FieldNormalizer sepalLengthNormalizer;
    private final FieldNormalizer petalLengthNormalizer;
    private final FieldNormalizer petalWidthNormalizer;

    private Dataset<Iris> normalized;

    public IrisNormalizer(Dataset<Iris> dataset) {
        this.dataset = dataset;

        sepalWidthNormalizer = getSepalWidthNormalizer();
        sepalLengthNormalizer = getSepalLengthNormalizer();
        petalLengthNormalizer = getPetalLengthNormalizer();
        petalWidthNormalizer = getPetalWidthNormalizer();
    }

    public Dataset<Iris> normalize() {
        if(normalized != null) {
            return normalized;
        }

        List<Iris> normalizedData = dataset.getData().stream()
                .map(this::normalize)
                .collect(toList());

        normalized = new Dataset<>(normalizedData);
        return normalized;
    }

    public Iris normalize(Iris iris) {
        return iris.normalize(sepalLengthNormalizer, sepalWidthNormalizer,
                petalLengthNormalizer, petalWidthNormalizer);
    }

    private FieldNormalizer getSepalLengthNormalizer() {
        Collection<Double> sepalLengths = dataset.getData().stream().map(Iris::getSepalLength).collect(toList());
        return getNormalizer(sepalLengths);
    }

    private FieldNormalizer getSepalWidthNormalizer() {
        Collection<Double> sepalWidths = dataset.getData().stream().map(Iris::getSepalWidth).collect(toList());
        return getNormalizer(sepalWidths);
    }

    private FieldNormalizer getPetalLengthNormalizer() {
        Collection<Double> petalLengths = dataset.getData().stream().map(Iris::getPetalLength).collect(toList());
        return getNormalizer(petalLengths);
    }

    private FieldNormalizer getPetalWidthNormalizer() {
        Collection<Double> petalWidths = dataset.getData().stream().map(Iris::getPetalWidth).collect(toList());
        return getNormalizer(petalWidths);
    }

    private FieldNormalizer getNormalizer(Collection<Double> data) {
        double min = Collections.min(data);
        double max = Collections.max(data);
        return new FieldNormalizer(min, max, 0, 1);
    }
}
