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

//    Knapsack getOptimal() {
////        Knapsack optimal =
////        while (!population.isEmpty()) {
////
////        }
//    }

    public static Population initial(List<Item> items, int populationSize, int maxWeight) {
        Queue<Knapsack> population = new PriorityQueue<>(Comparator.comparingInt(Knapsack::getValue));
        RandomGenerator knapsackRandom = new RandomGeneratorImpl(items.size());
        RandomGenerator populationRandom = new RandomGeneratorImpl(populationSize);

        while (population.size() < populationSize) {
            population.add(Knapsack.random(knapsackRandom, items, maxWeight));
        }

        return new Population(population, items, populationRandom, maxWeight);
    }

    public Knapsack getBestSolution() {
        return population.stream().max(Comparator.comparingInt(Knapsack::getValue)).get();
    }

    public void optimize() {
        int unchangedCount = 0;
        int max = 0;

        while(unchangedCount < 200) {
            evolve();

            int currentMax = getBestSolution().getValue();
            if(currentMax == max) {
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

        Knapsack evolved;
//        do {
            Object[] asArray = population.toArray();
            Knapsack first = (Knapsack) asArray[firstIndex];
            Knapsack second = (Knapsack) asArray[nextIndex];

            Knapsack crossover = first.crossover(second);
            evolved = crossover.mutate();
//        } while (evolved.isOverflowing());

        if (!evolved.isOverflowing() && evolved.getValue() > population.peek().getValue()) {
            population.remove();
            population.add(evolved);
            return true;
        }

        return false;
    }
}
