package fmi.ai.iris;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import fmi.ai.Dataset;

public class IrisParser {

    private IrisParser() {
    }

    public static Dataset<Iris> parse(String filePath) {
        Dataset<Iris> dataset = null;

        try {
            List<String> allLines = Files.readAllLines(Paths.get(filePath));
            List<String> dataRows = getDataRows(allLines);
            List<Iris> irises = dataRows.stream().map(IrisParser::parseRow).collect(toList());
            dataset = new Dataset<>(irises);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataset;
    }

    private static List<String> getDataRows(List<String> allRows) {
        int dataStartIndex = allRows.indexOf("@DATA");
        return allRows.stream().skip(dataStartIndex + 1).limit(allRows.size() - dataStartIndex - 4).collect(toList());
    }

    private static Iris parseRow(String row) {
        String[] parts = row.split(",");
        double sepalLength = Double.parseDouble(parts[0]);
        double sepalWidth = Double.parseDouble(parts[1]);
        double petalLength = Double.parseDouble(parts[2]);
        double petalWidth = Double.parseDouble(parts[3]);
        IrisClass irisClass = IrisClass.fromString(parts[4]);
        return new Iris(sepalLength, sepalWidth, petalLength, petalWidth, irisClass);
    }

}
