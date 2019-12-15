package fmi.ai;

import java.util.Map;

import fmi.ai.algorithms.Clusterizer;
import fmi.ai.iris.Iris;
import fmi.ai.iris.IrisNormalizer;
import fmi.ai.iris.IrisParser;

public class Main {

    public static void main(String[] args) {
        String datasetPath = "resources/iris.arff";
        Dataset<Iris> dataset = IrisParser.parse(datasetPath);
        IrisNormalizer normalizer = new IrisNormalizer(dataset);
        Dataset<Iris> normalized = normalizer.normalize();

        /**
         * Classification Example
         */
        //        Classifier<Iris> classifier = new Classifier<>(normalized, 5);
        //
        //        Iris random = normalized.getRandomEntity();
        //        Entity.EntityCategory classified = classifier.classify(random);
        //        System.out.println(random.getCategory() + " " + classified);

        /**
         * Clusterization example
         */
        Clusterizer clusterizer = new Clusterizer(normalized, 3);
        Map<Integer, Dataset<Iris>> grouped = clusterizer.clusterize();
        grouped.entrySet().forEach(entry -> {
            System.out.println(entry.getKey());
            entry.getValue().getData().forEach(item -> {
                System.out.println("    " + item.getCategory());
            });
        });
    }
}
