package fmi.ai;

public interface Entity<T extends Entity<T>> {

    double calculateDistance(T other);

    EntityCategory getCategory();

    interface EntityCategory {

    }
}
