package bg.sofia.uni.fmi.mjt.food.server.retriever.barcode;

import bg.sofia.uni.fmi.mjt.food.server.cache.FoodInfoCache;

public class FoodByBarcodeRetriever {

    private static final String START_PARAMETER_IMG = "--img=";
    private static final String START_PARAMETER_CODE = "--code=";

    private FoodByBarcodeRetriever() {

    }

    public static String getRequiredInformation(String[] commandArguments) {
        String code;
        if (isCodeIncludedInCommand(commandArguments)) {
            code = getParameter(commandArguments, START_PARAMETER_CODE, START_PARAMETER_IMG);
        }
        else {
            String img = getParameter(commandArguments, START_PARAMETER_IMG, START_PARAMETER_CODE);
            code = QRCodeReader.getQrCode(img);
        }
        return FoodInfoCache.checkInGtinUpcCache(code);
    }

    private static String getParameter(String[] commandArguments, String parameterStart, String nextParameterStart) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < commandArguments.length; i++) {
            if (commandArguments[i].startsWith(parameterStart)) {
                stringBuilder.append(commandArguments[i]);
                if (i + 1 < commandArguments.length && !commandArguments[i + 1].startsWith(nextParameterStart)) {
                    i++;
                    stringBuilder.append(" ").append(commandArguments[i]);
                }
                break;
            }
        }
        return stringBuilder.toString().replace(parameterStart, "");
    }

    private static boolean isCodeIncludedInCommand(String[] commandArguments) {
        for (int i = 0; i < commandArguments.length; i++) {
            if (commandArguments[i].startsWith(START_PARAMETER_CODE)) {
                return true;
            }
        }
        return false;
    }
}