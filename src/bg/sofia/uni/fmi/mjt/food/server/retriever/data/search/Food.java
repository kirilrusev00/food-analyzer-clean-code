package bg.sofia.uni.fmi.mjt.food.server.retriever.data.search;

public class Food {

    private String description;
    private String fdcId;
    private String gtinUpc;

    public Food(String description, String fdcId, String gtinUpc) {
        this.description = description;
        this.fdcId = fdcId;
        this.gtinUpc = gtinUpc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFdcId() {
        return fdcId;
    }

    public void setFdcId(String fdcId) {
        this.fdcId = fdcId;
    }

    public String getGtinUpc() {
        return gtinUpc;
    }

    public void setGtinUpc(String gtinUpc) {
        this.gtinUpc = gtinUpc;
    }

    @Override
    public String toString() {
        String printGtinUpc = gtinUpc == null ? "" : "; gtinUPC=" + gtinUpc;
        return "FoodInfoBox [Description: " + description + "; FdcId: " + fdcId + printGtinUpc + "]";
    }
}
