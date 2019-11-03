package fmi.ai;

import java.util.BitSet;
import java.util.List;
import java.util.stream.IntStream;

public final class Knapsack {

    private final BitSet includedItems;
    private final List<Item> items;
    private final RandomGenerator rng;
    private final int maxSize;

    private Knapsack(BitSet includedItems, List<Item> items, RandomGenerator rng, int maxSize) {
        this.includedItems = includedItems;
        this.items = items;
        this.rng = rng;
        this.maxSize = maxSize;
    }

    BitSet getIncludedItems() {
        return (BitSet) includedItems.clone();
    }

    private boolean isEmpty() {
        return includedItems.isEmpty();
    }

    public static Knapsack random(RandomGenerator rng, List<Item> items, int maxSize) {
        Knapsack knapsack;

        do {
            BitSet bitSet = new BitSet(items.size());

            int numberOfItems = rng.nextInt();

            IntStream.range(0, numberOfItems).map(i -> rng.nextInt()).forEach(bitSet::set);

            knapsack = new Knapsack(bitSet, items, rng, maxSize);
        } while (knapsack.isOverflowing() || knapsack.isEmpty());

        return knapsack;
    }

    public boolean isOverflowing() {
        return includedItems.stream().map(index -> items.get(index).getWeight()).sum() > maxSize;
    }

    public int getValue() {
        return includedItems.stream().map(index -> items.get(index).getValue()).sum();
    }

    public Knapsack mutate() {
        BitSet mutated = getIncludedItems();
        int index = rng.nextInt();
        mutated.flip(index);
        return new Knapsack(mutated, items, rng, maxSize);
    }

    public Knapsack crossover(Knapsack other) {
        BitSet result = new BitSet(items.size());

        for(int i = 0; i < items.size()/2; i++) {
            result.set(i, includedItems.get(i));
        }
        for(int i = items.size()/2; i < items.size(); i++) {
            result.set(i, other.includedItems.get(i));
        }

        return new Knapsack(result, items, rng, maxSize);
    }
}
