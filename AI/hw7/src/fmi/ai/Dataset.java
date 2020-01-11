package fmi.ai;

import static fmi.ai.Sex.FEMALE;
import static fmi.ai.Sex.MALE;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Dataset {
    private static final Set<Person> PEOPLE = new HashSet<>(Arrays.asList(
            new Person(MALE, 6, 180, 12),
            new Person(MALE, 5.92, 190, 11),
            new Person(MALE, 5.58, 170, 12),
            new Person(MALE, 5.92, 165, 10),
            new Person(FEMALE, 5, 100, 6),
            new Person(FEMALE, 5.5, 150, 8),
            new Person(FEMALE, 5.42, 130, 7),
            new Person(FEMALE, 5.75, 150, 9)
    ));

    public static Collection<Person> getPeople() {
        return PEOPLE;
    }

    public static Collection<Person> getClosestByHeight(double height, double radius) {
        return PEOPLE.stream()
//                .sorted(Comparator.comparingDouble(person -> Math.abs(person.getHeight() - height)))
                .filter(person -> Math.abs(person.getHeight() - height) <= radius)
                .collect(Collectors.toSet());
    }

    public static Collection<Person> getClosestByWeight(int weight, int radius) {
        return PEOPLE.stream()
//                .sorted(Comparator.comparingDouble(person -> Math.abs(person.getWeight() - weight)))
                .filter(person -> Math.abs(person.getWeight() - weight) <= radius)
                .collect(Collectors.toSet());
    }

    public static Collection<Person> getClosestByFootLength(int footLength, int radius) {
        return PEOPLE.stream()
//                .sorted(Comparator.comparingDouble(person -> Math.abs(person.getFootLength() - footLength)))
                .filter(person -> Math.abs(person.getFootLength() - footLength) <= radius)
                .collect(Collectors.toSet());
    }


//    public static Set<Person> getPeople() {
//        return new HashSet<>(PEOPLE);
//    }
//
//    public double getClassProbability(Sex sex) {
//        return ((double) PEOPLE.stream().filter(person -> person.getSex() == sex).count())/ PEOPLE.size();
//    }
//
//    public static Map<Integer, Set<Person>> split(int n, Function<Person, Comparable> getter) {
//        Map<Integer, Set<Person>> map = new HashMap<>();
//
//        List<Person> sorted = PEOPLE.stream().sorted(Comparator.comparing(getter)).collect(Collectors.toList());
//
//        int i = 0;
//        int current = 0;
//        int max = sorted.size() / n;
//        for (Person person : sorted) {
//            int x = Math.min(current, n);
//            map.computeIfAbsent(x, a -> new HashSet<>());
//            map.get(x).add(person);
//
//            i++;
//            if(i > max) {
//                current++;
//                i = 0;
//            }
//        }
//
//        return map;
//    }
}
