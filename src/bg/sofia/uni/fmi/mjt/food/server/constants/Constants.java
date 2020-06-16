package bg.sofia.uni.fmi.mjt.food.server.constants;

public class Constants {

    public static final String DISCONNECT_COMMAND_NAME = "disconnect";
    public static final String GET_FOOD_COMMAND_NAME = "get-food";
    public static final String GET_FOOD_REPORT_COMMAND_NAME = "get-food-report";
    public static final String GET_FOOD_BY_BARCODE_COMMAND_NAME = "get-food-by-barcode";

    public static final String UNKNOWN_COMMAND_MESSAGE = "Unknown command!";
    public static final String DISCONNECTED_MESSAGE = "Disconnected from server.";

    public static final String GET_FOOD_USAGE = "Usage: get-food <food_name>";
    public static final String GET_FOOD_BY_BARCODE_USAGE
            = "Usage: get-food-by-barcode --code=<gtinUpc_code>|--img=<barcode_image_file>";
    public static final String START_PARAMETER_IMG = "--img=";
    public static final String START_PARAMETER_CODE = "--code=";
    public static final String GET_FOOD_REPORT_USAGE = "Usage: get-food-report <food_fdcId>";

    public static final String NOT_FOUND_IN_CACHE_MESSAGE = "Item not found in cache";
    public static final String FOUND_IN_CACHE_MESSAGE = "Found in cache!";
    public static final String NOT_FOUND_MESSAGE = "No food matching the given input";

    public static final String SENDING_REQUEST_MESSAGE = "Sending request to FoodData Central!";

    public static final String RESOURCES_DIRECTORY = "resources";
}
