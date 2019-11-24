package fmi.ai.mock;

import java.util.List;

import fmi.ai.RandomGenerator;

public class MockRandom implements RandomGenerator {
    private List<Integer> values;
    private int currentIndex = 0;

    public MockRandom(List<Integer> values) {
        this.values = values;
    }

    @Override
    public int nextInt() {
        return currentIndex < values.size() ? values.get(currentIndex++) : 0;
    }
}
