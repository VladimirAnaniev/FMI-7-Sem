package fmi.ai.algorithms;

import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import fmi.ai.Dataset;
import fmi.ai.Entity;

/**
 * Implementation of K-Nearest Neighbours algorithm
 *
 * @param <T> type of the entities
 */
public class Classifier<T extends Entity> {

    private final Dataset<T> dataset;
    private final int kNearest;

    public Classifier(Dataset<T> dataset, int kNearest) {
        this.dataset = dataset;
        this.kNearest = kNearest;
    }

    public Entity.EntityCategory classify(T entity) {
        List<Entity> nearest = dataset.getData().stream()
                .sorted(Comparator.comparingDouble(a -> a.calculateDistance(entity)))
                .limit(kNearest)
                .collect(toList());

        return nearest.stream()
                .map(Entity::getCategory)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .get()
                .getKey();
    }
}
