package fmi.ai;

import java.util.Random;

public class RandomGeneratorImpl implements RandomGenerator {
    private final Random random;
    private final int maxValue;

    public RandomGeneratorImpl(int maxValue) {
        this.random = new Random();
        this.maxValue = maxValue;
    }

    public int nextInt() {
        return random.nextInt(maxValue);
    }
}
