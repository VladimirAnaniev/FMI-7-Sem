package fmi.ai.algorithms;

import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import fmi.ai.Dataset;
import fmi.ai.iris.Iris;

/**
 * Implementation of K-Means Clustering algorithm

 */
public class Clusterizer {

    private Dataset<ClusterItem> data;
    private List<ClusterItem> centroids;

    public Clusterizer(Dataset<Iris> data, int clusters) {
        Random rand = new Random();
        this.data = data.map(item -> new ClusterItem(item, rand.nextInt(clusters)));
        this.centroids = new ArrayList<>();
        for(int i = 0; i < clusters; i++) {
            centroids.add(new ClusterItem(data.getRandomEntity(), i));
        }
    }

    public Map<Integer, Dataset<Iris>> clusterize() {
        boolean changed = false;
        do {
            changed = false;
            for (ClusterItem clusterItem : data.getData()) {
                ClusterItem closest = clusterItem.closest(centroids);
                if (closest.getCluster() != clusterItem.getCluster()) {
                    clusterItem.setCluster(closest.getCluster());
                    changed = true;
                }
            }

            relocateCentroids();
        } while(changed);

        return getClusters();
    }

    private void relocateCentroids() {
        centroids.clear();
        getClusters().entrySet().forEach(entry -> {
            Dataset<Iris> dataset = entry.getValue();
            Iris summed = new Iris(0,0,0,0);

            for (Iris iris : entry.getValue().getData()) {
                summed = new Iris(
                        summed.getSepalLength() + iris.getSepalLength(),
                        summed.getSepalWidth() + iris.getSepalWidth(),
                        summed.getPetalLength() + iris.getPetalLength(),
                        summed.getPetalWidth() + iris.getSepalWidth());
            }

            summed = new Iris(
                    summed.getSepalLength() / dataset.getSize(),
                    summed.getSepalWidth() / dataset.getSize(),
                    summed.getPetalLength() / dataset.getSize(),
                    summed.getPetalWidth() / dataset.getSize()
            );

            centroids.add(new ClusterItem(summed, entry.getKey()));
        });
    }

    private Map<Integer, Dataset<Iris>> getClusters() {
        Map<Integer, Dataset<ClusterItem>> groups = data.groupBy(ClusterItem::getCluster);
        return groups.entrySet().stream().collect(
                toMap(Map.Entry::getKey, entry -> entry.getValue().map(ClusterItem::getEntity)));
    }

    public class ClusterItem {

        private final Iris entity;
        private int cluster;

        ClusterItem(Iris entity, int cluster) {
            this.entity = entity;
            this.cluster = cluster;
        }

        public Iris getEntity() {
            return entity;
        }

        public int getCluster() {
            return cluster;
        }

        public void setCluster(int cluster) {
            this.cluster = cluster;
        }

        public ClusterItem closest(List<ClusterItem> centroids) {
            return centroids.stream()
                    .min(Comparator.comparingDouble(centroid -> centroid.getEntity().calculateDistance(entity))).orElse(null);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ClusterItem that = (ClusterItem) o;
            return cluster == that.cluster &&
                    Objects.equals(entity, that.entity);
        }

        @Override
        public int hashCode() {
            return Objects.hash(entity, cluster);
        }
    }

}
