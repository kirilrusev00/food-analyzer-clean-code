package bg.sofia.uni.fmi.mjt.food.server.retriever.report.search;

public class LabelNutrientsInfo {
    private double value;

    public LabelNutrientsInfo(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
