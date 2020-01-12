package fmi.ai;

import java.util.Map;
import java.util.Objects;

public class Neuron {
    private Map<Neuron, Double> connections;
    private Double activation;
    private Double error;

    public Neuron(Map<Neuron, Double> connections) {
        this.connections = connections;
    }

    public void setActivation(Double activation) {
        this.activation = activation;
    }

    public Double getActivation() {
        return activation;
    }

//    public void updateConnection(Neuron neuron, Double value) {
//        connections.put(neuron, value);
//    }

    public void calculateActivation() {
        double sum = connections.entrySet().stream()
                .map(entry -> entry.getKey().getActivation() * entry.getValue())
                .reduce(0d, Double::sum);

        setActivation(1 / (1 + Math.pow(Math.E, -sum)));
    }

    public void setInitialError(Double actualValue) {
        error = activation * (1 - activation) * (actualValue - activation);
    }

    public Double gtErrorFor(Neuron neuron) {
        return connections.get(neuron) * error;
    }

    public void calculateError(Double accumulatedError) {
        error = activation * (1 - activation) * accumulatedError;
    }

    public void updateWeights() {
        connections.forEach((neuron, value) -> {
            Double newValue = value + 0.1 * error * neuron.getActivation();
            connections.put(neuron, newValue);
        });
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        Neuron neuron = (Neuron) o;
//        return Objects.equals(connections, neuron.connections) &&
//                Objects.equals(activation, neuron.activation);
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(connections, activation);
//    }
}
