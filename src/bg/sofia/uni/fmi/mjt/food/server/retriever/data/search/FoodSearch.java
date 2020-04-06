package bg.sofia.uni.fmi.mjt.food.server.retriever.data.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodSearch {
    @SerializedName("generalSearchInput")
    private String searchInput;

    private List<Food> foods;

    public FoodSearch(String searchInput, List<Food> foods) {
        this.searchInput = searchInput;
        this.foods = foods;
    }

    public String getSearchInput() {
        return searchInput;
    }

    public void setSearchInput(String searchInput) {
        this.searchInput = searchInput;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    @Override
    public String toString() {
        return "Search [searchInput=" + searchInput + ", foods=" + foods.toString() + "]";
    }
}
