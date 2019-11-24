package fmi.ai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


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

    @Test
    public void bestSolutionShouldHaveValueOver1400() {
        Population population = Population.initial(Item.getAll(), 30, 5000);

        population.optimize();

        Knapsack bestSolution = population.getBestSolution();
        assertTrue(bestSolution.getValue() + " should be greater than 1400", bestSolution.getValue() > 1400);
    }


    @Test
    public void bestSolutionShouldNotOverflow() {
        Population population = Population.initial(Item.getAll(), 30, 5000);

        population.optimize();

        Knapsack bestSolution = population.getBestSolution();
        assertFalse(bestSolution.isOverflowing());
    }

}
