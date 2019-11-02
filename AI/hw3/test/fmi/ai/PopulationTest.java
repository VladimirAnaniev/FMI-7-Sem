package fmi.ai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class PopulationTest {

    @Test
    public void initialPopulationShouldHaveSize() {
        Population population = Population.initial(Item.getAll(), 5, 1500);
        assertEquals(5, population.getPopulation().size());
    }

    @Test
    public void initialPopulationShouldNotBeOverflowing() {
        Population population = Population.initial(Item.getAll(), 5, 1500);
        population.getPopulation().forEach(knapsack -> assertFalse(knapsack.isOverflowing()));
    }



}
