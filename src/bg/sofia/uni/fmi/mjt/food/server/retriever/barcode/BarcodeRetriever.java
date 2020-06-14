package bg.sofia.uni.fmi.mjt.food.server.retriever.barcode;

import bg.sofia.uni.fmi.mjt.food.server.retriever.FoodInfoRetriever;

public class BarcodeRetriever {

    private static final String START_PARAMETER_IMG = "--img=";
    private static final String START_PARAMETER_CODE = "--code=";

    private BarcodeRetriever() {

    }

    public static String getRequiredInformation(String[] commandParts) {
        String code;
        if (isCodeIncludedInCommand(commandParts)) {
            code = getParameter(commandParts, START_PARAMETER_CODE, START_PARAMETER_IMG);
        }
        else {
            String img = getParameter(commandParts, START_PARAMETER_IMG, START_PARAMETER_CODE);
            code = QRCodeReader.getQrCode(img);
        }
        return FoodInfoRetriever.getGtinUpcCache().get(code);
    }

    private static String getParameter(String[] commandParts, String parameterStart, String nextParameterStart) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < commandParts.length; i++) {
            if (commandParts[i].startsWith(parameterStart)) {
                stringBuilder.append(commandParts[i]);
                if (i + 1 < commandParts.length && !commandParts[i + 1].startsWith(nextParameterStart)) {
                    i++;
                    stringBuilder.append(" ").append(commandParts[i]);
                }
                break;
            }
        }
        return stringBuilder.toString().replace(parameterStart, "");
    }

    private static boolean isCodeIncludedInCommand(String[] commandParts) {
        for (int i = 1; i < commandParts.length; i++) {
            if (commandParts[i].startsWith(START_PARAMETER_CODE)) {
                return true;
            }
        }
        return false;
    }
}