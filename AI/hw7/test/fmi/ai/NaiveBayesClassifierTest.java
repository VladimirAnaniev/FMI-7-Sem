package fmi.ai;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NaiveBayesClassifierTest {
    private static final NaiveBayesClassifier classifier = new NaiveBayesClassifier();

    @Test
    public void shouldClassifyOriginalValuesProperly() {
        Dataset.getPeople().forEach(person -> {
            assertEquals(person.getSex(), classifier.classify(person));
        });
    }

    @Test
    public void shouldClassifyNewWoman() {
        Person woman = new Person(Sex.FEMALE, 5.3, 120, 7);
        assertEquals(Sex.FEMALE, classifier.classify(woman));
    }

    @Test
    public void shouldClassifyNewMan() {
        Person man = new Person(Sex.MALE, 6.3, 190, 10);
        assertEquals(Sex.MALE, classifier.classify(man));
    }

}
