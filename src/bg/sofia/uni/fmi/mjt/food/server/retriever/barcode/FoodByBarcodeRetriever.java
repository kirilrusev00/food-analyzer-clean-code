package bg.sofia.uni.fmi.mjt.food.server.retriever.barcode;

import bg.sofia.uni.fmi.mjt.food.server.cache.FoodInfoCache;

import static bg.sofia.uni.fmi.mjt.food.server.constants.Constants.START_PARAMETER_CODE;
import static bg.sofia.uni.fmi.mjt.food.server.constants.Constants.START_PARAMETER_IMG;

public class FoodByBarcodeRetriever {

    public static String getRequiredInformation(String argumentsLine) {
        String code;
        if (isCodeIncludedInCommand(argumentsLine)) {
            code = getParameter(argumentsLine, START_PARAMETER_CODE, START_PARAMETER_IMG);
        }
        else {
            String img = getParameter(argumentsLine, START_PARAMETER_IMG, START_PARAMETER_CODE);
            code = QRCodeReader.getQrCode(img);
        }
        return FoodInfoCache.checkInGtinUpcCache(code);
    }

    private static String getParameter(String argumentsLine, String parameterStart, String nextParameterStart) {
        String[] commandArguments = argumentsLine.trim().split(" ");

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < commandArguments.length; i++) {
            if (commandArguments[i].startsWith(parameterStart)) {
                stringBuilder.append(commandArguments[i]);
                while (i + 1 < commandArguments.length && !commandArguments[i + 1].startsWith(nextParameterStart)) {
                    i++;
                    stringBuilder.append(" ").append(commandArguments[i]);
                }
                break;
            }
        }
        return stringBuilder.toString().replace(parameterStart, "");
    }

    private static boolean isCodeIncludedInCommand(String argumentsLine) {
        return argumentsLine.contains(START_PARAMETER_CODE);
    }
}