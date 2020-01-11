package fmi.ai;

import static fmi.ai.Sex.FEMALE;
import static fmi.ai.Sex.MALE;

import java.math.BigDecimal;
import java.util.Collection;

public class NaiveBayesClassifier {
    private final double heightRadius = 0.5;
    private final int weightRadius = 30;
    private final int footLengthRadius = 2;
//    private final int nClosest;



//    public NaiveBayesClassifier(int nClosest) {
//        this.nClosest = nClosest;
//    }

    public Sex classify(Person person) {
        BigDecimal maleProbability = calculateProbability(person, MALE);
        BigDecimal femaleProbability = calculateProbability(person, FEMALE);
        return maleProbability.compareTo(femaleProbability) > 0 ? MALE : FEMALE;
    }

    private BigDecimal calculateProbability(Person person, Sex sex) {
        return calculateHeightProbability(person.getHeight(), sex).multiply(
                calculateWightProbability(person.getWeight(), sex)).multiply(
                calculateFootLengthProbability(person.getFootLength(), sex));
    }

    private BigDecimal calculateHeightProbability(double height, Sex sex) {
        Collection<Person> closestByHeight = Dataset.getClosestByHeight(height, heightRadius);
        long ofTargetSex = closestByHeight.stream().filter(person -> person.getSex() == sex).count();
        return BigDecimal.valueOf(ofTargetSex / (double) closestByHeight.size());
    }

    private BigDecimal calculateWightProbability(int weight, Sex sex) {
        Collection<Person> closestByWeight = Dataset.getClosestByWeight(weight, weightRadius);
        long ofTargetSex = closestByWeight.stream().filter(person -> person.getSex() == sex).count();
        return BigDecimal.valueOf(ofTargetSex / (double) closestByWeight.size());
    }

    private BigDecimal calculateFootLengthProbability(int footLength, Sex sex) {
        Collection<Person> closestByFootLength = Dataset.getClosestByFootLength(footLength, footLengthRadius);
        long ofTargetSex = closestByFootLength.stream().filter(person -> person.getSex() == sex).count();
        return BigDecimal.valueOf(ofTargetSex / (double) closestByFootLength.size());
    }
}
