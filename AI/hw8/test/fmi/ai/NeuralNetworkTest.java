package fmi.ai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class NeuralNetworkTest {

    private static List<NeuralNetwork.TrainData> trainData = Arrays.asList(
            new NeuralNetwork.TrainData(Arrays.asList(6d, 180d, 12d), Arrays.asList(1d, 0d)),
            new NeuralNetwork.TrainData(Arrays.asList(5.92, 190d, 11d), Arrays.asList(1d, 0d)),
            new NeuralNetwork.TrainData(Arrays.asList(5.58, 170d, 12d), Arrays.asList(1d, 0d)),
            new NeuralNetwork.TrainData(Arrays.asList(5.92, 165d, 10d), Arrays.asList(1d, 0d)),
            new NeuralNetwork.TrainData(Arrays.asList(5d, 100d, 6d), Arrays.asList(0d, 1d)),
            new NeuralNetwork.TrainData(Arrays.asList(5.5, 150d, 8d), Arrays.asList(0d, 1d)),
            new NeuralNetwork.TrainData(Arrays.asList(5.42, 130d, 7d), Arrays.asList(0d, 1d)),
            new NeuralNetwork.TrainData(Arrays.asList(5.75, 150d, 9d), Arrays.asList(0d, 1d))
    );

    @Test
    public void shouldClassifyOriginalValuesCorrectly() {
        NeuralNetwork neuralNetwork = NeuralNetwork.create(3, 10, 2);

        List<FieldNormalizer> normalizers = getNormalizers(trainData);
        List<NeuralNetwork.TrainData> data = trainData.stream()
                .map(trainData1 -> new NeuralNetwork.TrainData(normalize(trainData1.getInput(), normalizers),
                        trainData1.getOutput())).collect(
                        Collectors.toList());

        neuralNetwork.train(data, 10000);

        data.forEach(trainData -> {
            List<Double> prediction = neuralNetwork.predict(trainData.getInput());

            boolean malePrediction = prediction.get(0) > prediction.get(1);
            boolean maleActually = trainData.getOutput().get(0) > trainData.getOutput().get(1);

            assertEquals(maleActually, malePrediction);
        });
    }

    @Test
    public void shouldClassifyNewValueCorrectly() {
        NeuralNetwork neuralNetwork = NeuralNetwork.create(3, 10, 2);

        List<FieldNormalizer> normalizers = getNormalizers(trainData);
        List<NeuralNetwork.TrainData> data = trainData.stream()
                .map(trainData1 -> new NeuralNetwork.TrainData(normalize(trainData1.getInput(), normalizers),
                        trainData1.getOutput())).collect(
                        Collectors.toList());

        neuralNetwork.train(data, 10000);

        List<Double> newData = Arrays.asList(5.4, 140d, 10d);
        List<Double> normalizedNewData = normalize(newData, normalizers);


        List<Double> prediction = neuralNetwork.predict(normalizedNewData);
        boolean malePrediction = prediction.get(0) > prediction.get(1);
        assertFalse(malePrediction);
    }

    private List<FieldNormalizer> getNormalizers(List<NeuralNetwork.TrainData> trainData) {
        List<FieldNormalizer> normalizers = new ArrayList<>();

        List<List<Double>> trainFeatures =
                trainData.stream().map(NeuralNetwork.TrainData::getInput).collect(Collectors.toList());
        int size = trainFeatures.get(0).size();

        return IntStream.range(0, size)
                .mapToObj(i -> {
                    List<Double> featureData =
                            trainFeatures.stream().map(features -> features.get(i)).collect(Collectors.toList());
                    return getNormalizer(featureData);
                })
                .collect(Collectors.toList());
    }

    private FieldNormalizer getNormalizer(List<Double> values) {
        Collections.sort(values);

        Double largest = values.get(0);
        Double smallest = values.get(values.size() - 1);

        return new FieldNormalizer(smallest, largest, 0, 1);
    }

    private List<Double> normalize(List<Double> values, List<FieldNormalizer> normalizers) {
        return IntStream.range(0, values.size())
                .mapToObj(i -> normalizers.get(i).normalize(values.get(i)))
                .collect(Collectors.toList());
    }
}
