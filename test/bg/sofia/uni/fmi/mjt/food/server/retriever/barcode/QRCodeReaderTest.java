package bg.sofia.uni.fmi.mjt.food.server.retriever.barcode;

import org.junit.Test;

import static bg.sofia.uni.fmi.mjt.food.server.retriever.barcode.QRCodeReader.getQrCode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class QRCodeReaderTest {

    @Test
    public void testGetQRCodeWhenFileExistsAndContainsQRCode() {
        final String testFileName = "barcode1.gif";

        String expected = "725272730706";
        String actual = getQrCode(testFileName);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetQRCodeWhenFileExistsAndDoesNotContainsQRCode() {
        final String testFileName = "empty.gif";

        String actual = getQrCode(testFileName);

        assertNull(actual);
    }

    @Test
    public void testGetQRCodeWhenFileDoesNotExists() {
        final String testFileName = "barcode.gif";

        String actual = getQrCode(testFileName);

        assertNull(actual);
    }
}
