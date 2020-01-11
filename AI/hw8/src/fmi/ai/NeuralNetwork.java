package fmi.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NeuralNetwork {
    private final List<Neuron> inputLayer;
    private final List<Neuron> middleLayer;
    private final List<Neuron> outputLayer;

    public NeuralNetwork(List<Neuron> inputLayer, List<Neuron> middleLayer, List<Neuron> outputLayer) {
        this.inputLayer = inputLayer;
        this.middleLayer = middleLayer;
        this.outputLayer = outputLayer;
    }

    public static NeuralNetwork create(int inputNeurons, int hiddenNeurons, int outputNeurons) {
        List<Neuron> input = initializeLayer(inputNeurons, new ArrayList<>());
        List<Neuron> middle = initializeLayer(hiddenNeurons, input);
        List<Neuron> output = initializeLayer(outputNeurons, middle);

        return new NeuralNetwork(input, middle, output);
    }

    public void train(List<TrainData> trainData, int trainPasses) {
        for (int i = 0; i < trainPasses; i++) {
            for (TrainData data : trainData) {
                // populate activations for all neurons
                predict(data.getInput());

                // propagate error and modify connections
                List<Double> actual = data.getOutput();
                propagateError(actual);

                // Update connection weights
                updateWeights();
            }
        }
    }

    public List<Double> predict(List<Double> input) {
        for(int i = 0; i < input.size(); i++) {
            inputLayer.get(i).setActivation(input.get(i));
        }

        middleLayer.forEach(Neuron::calculateActivation);
        outputLayer.forEach(Neuron::calculateActivation);

        return outputLayer.stream().map(Neuron::getActivation).collect(Collectors.toList());
    }


    private static List<Neuron> initializeLayer(int neurons, Collection<Neuron> previousLayer) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        List<Neuron> layer = new ArrayList<>();
        for (int i = 0; i < neurons; i++) {
            Map<Neuron, Double> connections = previousLayer.stream().collect(
                    Collectors.toMap(Function.identity(), a -> random.nextDouble(1) - 0.5));
            Neuron neuron = new Neuron(connections);
            layer.add(neuron);
        }

        return layer;
    }

    private void propagateError(List<Double> actual) {
        for(int i = 0; i < actual.size(); i++) {
            outputLayer.get(i).setInitialError(actual.get(i));
        }
        middleLayer.forEach(neuron -> propagateErrorForNeuron(neuron, outputLayer));
        inputLayer.forEach(neuron -> propagateErrorForNeuron(neuron, middleLayer));
    }

    private void propagateErrorForNeuron(Neuron neuron, List<Neuron> nextLayer) {
        Double accumulatedError = nextLayer.stream()
                .map(n -> n.gtErrorFor(neuron))
                .reduce(0d, Double::sum);
        neuron.calculateError(accumulatedError);
    }

    private void updateWeights() {
        outputLayer.forEach(Neuron::updateWeights);
        middleLayer.forEach(Neuron::updateWeights);
    }


    public class TrainData {
        private final List<Double> input;
        private final List<Double> output;

        public TrainData(List<Double> input, List<Double> output) {
            this.input = input;
            this.output = output;
        }

        public List<Double> getInput() {
            return input;
        }

        public List<Double> getOutput() {
            return output;
        }
    }
}
