package bg.sofia.uni.fmi.mjt.food.server.retriever.barcode;

import bg.sofia.uni.fmi.mjt.food.server.cache.Cache;
import bg.sofia.uni.fmi.mjt.food.server.retriever.FoodInfoRetrieverImpl;

public class BarcodeRetriever {

    private BarcodeRetriever() {

    }

    public static String getRequiredInformation(String[] commandParts) {
        boolean isCodeIncluded = false;
        StringBuilder codeBuilder = new StringBuilder();
        StringBuilder imgBuilder = new StringBuilder();
        for (int i = 1; i < commandParts.length; i++) {
            if (commandParts[i].startsWith("--img=")) {
                imgBuilder.append(commandParts[i]);
                if (i + 1 < commandParts.length && !commandParts[i + 1].startsWith("--code=")) {
                    i++;
                    imgBuilder.append(" ").append(commandParts[i]);
                }
            }
            if (commandParts[i].startsWith("--code=")) {
                isCodeIncluded = true;
                codeBuilder.append(commandParts[i]);
                if (i + 1 < commandParts.length && !commandParts[i + 1].startsWith("--img=")) {
                    i++;
                    codeBuilder.append(" ").append(commandParts[i]);
                }
                break;
            }
        }
        String code;
        if (!isCodeIncluded) {
            String img = imgBuilder.toString().replace("--img=", "");
            code = QRCodeReader.getQrCode(img);
        } else {
            code = codeBuilder.toString().replace("--code=", "");
        }
        return FoodInfoRetrieverImpl.getGtinUpcCache().get(code);
    }
}
