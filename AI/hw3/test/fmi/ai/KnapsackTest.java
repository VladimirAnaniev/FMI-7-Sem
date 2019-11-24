package fmi.ai;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

import org.junit.Test;

import fmi.ai.mock.MockRandom;

public class KnapsackTest {

    @Test
    public void randomShouldGenerateKnapsackWithLength() {
        RandomGenerator random = new MockRandom(Arrays.asList(2, 3, 4));
        List<Item> items = Arrays.asList(Item.values());

        Knapsack knapsack = Knapsack.random(random, items, 5000);

        assertEquals(2, knapsack.getIncludedItems().stream().count());
    }

    @Test
    public void randomShouldGenerateKnapsackWithCorrectItems() {
        RandomGenerator random = new MockRandom(Arrays.asList(2, 3, 4));
        List<Item> items = Arrays.asList(Item.values());

        Knapsack knapsack = Knapsack.random(random, items, 5000);

        BitSet includedItems = knapsack.getIncludedItems();
        assertFalse(includedItems.get(0));
        assertFalse(includedItems.get(1));
        assertFalse(includedItems.get(2));
        assertTrue(includedItems.get(3));
        assertTrue(includedItems.get(4));
        assertFalse(includedItems.get(5));
    }

    @Test
    public void randomShouldRetryUntilNotOverflowing() {
        RandomGenerator random = new MockRandom(Arrays.asList(2, 2, 4, 2, 0, 1));
        List<Item> items = Arrays.asList(Item.values());

        Knapsack knapsack = Knapsack.random(random, items, 1500);

        assertFalse(knapsack.isOverflowing());
        assertEquals(190, knapsack.getValue());
    }

    @Test
    public void mutateShouldFlipBitToTrue() {
        RandomGenerator random = new MockRandom(Arrays.asList(1, 1, 3));
        List<Item> items = Arrays.asList(Item.values());

        Knapsack knapsack = Knapsack.random(random, items, 1500).mutate();

        assertFalse(knapsack.isOverflowing());
        assertEquals(200, knapsack.getValue());
    }

    @Test
    public void crossoverShouldMergeKnapsacks() {
        RandomGenerator random = new MockRandom(Arrays.asList(2, 1, 20, 2, 3, 15));
        List<Item> items = Arrays.asList(Item.values());

        Knapsack knapsack1 = Knapsack.random(random, items, 1500);
        Knapsack knapsack2 = Knapsack.random(random, items, 1500);
        Knapsack crossover = knapsack1.crossover(knapsack2);

        assertEquals(110, crossover.getValue());
    }
}
