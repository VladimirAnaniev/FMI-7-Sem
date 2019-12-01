package fmi.ai;

import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.unsupervised.attribute.Normalize;

public class Main {
    private static String datasetPath = "resources/iris.arff";

    public static void main(String[] args) throws Exception {
        ConverterUtils.DataSource source = new ConverterUtils.DataSource(datasetPath);
        Instances dataSet = source.getDataSet();
        dataSet.setClassIndex(dataSet.numAttributes() - 1);

        Normalize filterNorm = new Normalize();
        filterNorm.setInputFormat(dataSet);

        EuclideanDistance distance = new EuclideanDistance(dataSet);

        dataSet.randomize(new Random());

        int trainSetSize = (int) Math.round(dataSet.numInstances() * 0.9);
        int testSetSize = dataSet.numInstances() - trainSetSize;

        Instances train = new Instances(dataSet, 0, trainSetSize);
        Instances test = new Instances(dataSet, trainSetSize, testSetSize);

        int errors = 0;

        for (int i = 0; i < testSetSize; i++) {
            Instance current = test.instance(i);
            List<Instance> closestInstances = IntStream.range(0, trainSetSize)
                    .mapToObj(train::instance)
                    .sorted(Comparator.comparingDouble(a -> distance.distance(a, current)))
                    .limit(5)
                    .collect(toList());


            Double mostFrequent = closestInstances.stream()
                    .map(Instance::classValue)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet()
                    .stream()
                    .max(Comparator.comparing(Map.Entry::getValue))
                    .get()
                    .getKey();


            if (Double.compare(mostFrequent, current.classValue()) != 0) {
                System.out.println(String.format("Expected %.0f, but was %.0f", current.classValue(), mostFrequent));
                errors++;
            }
        }

        System.out.println(String.format("Number of errors: %d", errors));
    }
}
