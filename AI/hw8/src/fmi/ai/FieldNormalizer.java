package fmi.ai;

public class FieldNormalizer {

    private final double dataMin;
    private final double dataMax;
    private final double normalizedMin;
    private final double normalizedMax;

    public FieldNormalizer(double dataMin, double dataMax, double normalizedMin, double normalizedMax) {
        this.dataMin = dataMin;
        this.dataMax = dataMax;
        this.normalizedMin = normalizedMin;
        this.normalizedMax = normalizedMax;
    }

    /**
     * Normalize value.
     * @param value The value to be normalized.
     * @return The result of the normalization.
     */
    public double normalize(double value) {
        return ((value - dataMin) / (dataMax - dataMin)) * (normalizedMax - normalizedMin) + normalizedMin;
    }
}
