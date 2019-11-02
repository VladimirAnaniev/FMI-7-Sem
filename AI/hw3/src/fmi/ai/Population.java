package fmi.ai;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Population {

    private final Queue<Knapsack> population;
    private final List<Item> items;
    private final RandomGenerator rng;
    private final int maxWeight;

    private Population(Queue<Knapsack> population, List<Item> items, RandomGenerator rng, int maxWeight) {
        this.population = population;
        this.items = items;
        this.rng = rng;
        this.maxWeight = maxWeight;
    }

    List<Knapsack> getPopulation() {
        return new ArrayList<>(population);
    }

    public static Population initial(List<Item> items, int populationSize, int maxWeight) {
        Queue<Knapsack> population = new PriorityQueue<>(Comparator.comparingInt(Knapsack::getValue));
        RandomGenerator knapsackRandom = new RandomGeneratorImpl(items.size());
        RandomGenerator populationRandom = new RandomGeneratorImpl(populationSize);

        while (population.size() < populationSize) {
            population.add(Knapsack.random(knapsackRandom, items, maxWeight));
        }

        return new Population(population, items, populationRandom, maxWeight);
    }

    public boolean evolve() {
        int firstIndex = rng.nextInt();
        int nextIndex = rng.nextInt();

        Knapsack evolved;
        do {
            Knapsack[] asArray = (Knapsack[]) population.toArray();
            Knapsack first = asArray[firstIndex];
            Knapsack second = asArray[nextIndex];

            Knapsack crossover = first.crossover(second);
            evolved = crossover.mutate();
        } while (evolved.isOverflowing());

        if (evolved.getValue() > population.element().getValue()) {
            population.remove();
            population.add(evolved);
            return true;
        }

        return false;
    }
}
