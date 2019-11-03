package fmi.ai;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Population {

    private final Queue<Knapsack> population;
    private final RandomGenerator rng;

    private Population(Queue<Knapsack> population, RandomGenerator rng) {
        this.population = population;
        this.rng = rng;
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

        return new Population(population, populationRandom);
    }

    public Knapsack getBestSolution() {
        return population.stream().max(Comparator.comparingInt(Knapsack::getValue)).get();
    }

    public void optimize() {
        int unchangedCount = 0;
        int max = 0;

        while (unchangedCount < 200) {
            evolve();

            int currentMax = getBestSolution().getValue();
            if (currentMax == max) {
                unchangedCount++;
            } else {
                unchangedCount = 0;
                max = currentMax;
            }
        }
    }

    boolean evolve() {
        int firstIndex = rng.nextInt();
        int nextIndex = rng.nextInt();

        Object[] asArray = population.toArray();
        Knapsack first = (Knapsack) asArray[firstIndex];
        Knapsack second = (Knapsack) asArray[nextIndex];

        Knapsack crossover = first.crossover(second);
        Knapsack evolved = crossover.mutate();

        if (!evolved.isOverflowing() && evolved.getValue() > population.peek().getValue()) {
            population.remove();
            population.add(evolved);
            return true;
        }

        return false;
    }
}
