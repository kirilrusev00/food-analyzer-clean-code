package bg.sofia.uni.fmi.mjt.food.server.retriever.report.search;

public class LabelNutrients {
    private LabelNutrientsInfo calories;
    private LabelNutrientsInfo protein;
    private LabelNutrientsInfo fat;
    private LabelNutrientsInfo carbohydrates;
    private LabelNutrientsInfo fiber;

    public LabelNutrients(LabelNutrientsInfo calories, LabelNutrientsInfo protein, LabelNutrientsInfo fat,
                          LabelNutrientsInfo carbohydrates, LabelNutrientsInfo fiber) {
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.fiber = fiber;
    }

    public LabelNutrientsInfo getCalories() {
        return calories;
    }

    public void setCalories(LabelNutrientsInfo calories) {
        this.calories = calories;
    }

    public LabelNutrientsInfo getProtein() {
        return protein;
    }

    public void setProtein(LabelNutrientsInfo protein) {
        this.protein = protein;
    }

    public LabelNutrientsInfo getFat() {
        return fat;
    }

    public void setFat(LabelNutrientsInfo fat) {
        this.fat = fat;
    }

    public LabelNutrientsInfo getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(LabelNutrientsInfo carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public LabelNutrientsInfo getFiber() {
        return fiber;
    }

    public void setFiber(LabelNutrientsInfo fiber) {
        this.fiber = fiber;
    }

    @Override
    public String toString() {
        return "Calories: " + calories + "; Protein: " + protein + "; Fat: " + fat + "; Carbohydrates: "
                + carbohydrates + "; Fiber: " + fiber;
    }
}
