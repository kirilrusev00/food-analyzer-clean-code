package bg.sofia.uni.fmi.mjt.food.server.retriever.report.search;

public class FoodReport {
    private String gtinUpc;
    private String fdcId;
    private String description;
    private String ingredients;
    private LabelNutrients labelNutrients;

    public FoodReport(String gtinUpc, String fdcId, String description, String ingredients,
                      LabelNutrients labelNutrients) {
        this.gtinUpc = gtinUpc;
        this.fdcId = fdcId;
        this.description = description;
        this.ingredients = ingredients;
        this.labelNutrients = labelNutrients;
    }

    public String getGtinUpc() {
        return gtinUpc;
    }

    public void setGtinUpc(String gtinUpc) {
        this.gtinUpc = gtinUpc;
    }

    public String getFdcId() {
        return fdcId;
    }

    public void setTdcId(String fdcId) {
        this.fdcId = fdcId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public LabelNutrients getLabelNutrients() {
        return labelNutrients;
    }

    public void setLabelNutrients(LabelNutrients labelNutrients) {
        this.labelNutrients = labelNutrients;
    }

    @Override
    public String toString() {
        return "FoodReportBox [Description: " + description + ", FdcId: " + fdcId
                + ", Ingredients: " + ingredients + ", LabelNutrients: {" + labelNutrients + "} ]";
        /*return "FoodReportBox [" + System.lineSeparator() + "Description: " + description + System.lineSeparator()
                + "FdcId: " + fdcId + System.lineSeparator() + "Ingredients: " + ingredients
                + System.lineSeparator() + "LabelNutrients: {" + labelNutrients + "} ]";*/
    }
}
