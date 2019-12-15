package fmi.ai;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

public class Dataset<T> {

    private final List<T> data;

    public Dataset(List<T> data) {
        this.data = new ArrayList<>(data);
    }

    public List<T> getData() {
        return new ArrayList<>(data);
    }

    public int getSize() {
        return data.size();
    }

    public T getRandomEntity() {
        int index = new Random().nextInt(data.size());
        return data.get(index);
    }

    /**
     * Transforms the items in the dataset with the provided function
     *
     * @param function mapping function
     * @param <U> resulting type
     * @return new dataset with transformed items
     */
    public <U> Dataset<U> map(Function<? super T, ? extends U> function) {
        return new Dataset<>(data.stream().map(function).collect(toList()));
    }

    /**
     * Groups the items in the dataset using a provided function
     *
     * @param function to group the items by
     * @param <U> the type of the groups
     * @return a map with group as key and dataset as value
     */
    public <U> Map<U, Dataset<T>> groupBy(Function<? super T, ? extends U> function) {
        return data.stream().collect(groupingBy(function, collectingAndThen(toList(), Dataset::new)));
    }
}
