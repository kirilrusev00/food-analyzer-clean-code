package bg.sofia.uni.fmi.mjt.food.server.retriever.barcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class QRCodeReader {

    private static String decodeQRCode(Path qrCodeImage) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(qrCodeImage.toFile());
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            System.out.println("There is no QR code in the image");
            return null;
        }
    }

    static String getQrCode(String file) {
        try {
            Path path = Path.of(file);
            if (!path.isAbsolute()) {
                path = Path.of("resources" + File.separator + file);
            }
            String decodedText = decodeQRCode(path);
            if (decodedText != null) {
                System.out.println("Decoded text = " + decodedText);
            }
            return decodedText;
        } catch (IOException e) {
            System.out.println("Could not decode QR Code, IOException :: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        getQrCode("barcode1.gif");
        getQrCode("barcode2.gif");
        getQrCode("barcode3.gif");
    }
}
