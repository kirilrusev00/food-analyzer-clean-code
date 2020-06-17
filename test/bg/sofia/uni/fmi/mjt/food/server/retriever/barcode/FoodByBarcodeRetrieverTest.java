package bg.sofia.uni.fmi.mjt.food.server.retriever.barcode;

import bg.sofia.uni.fmi.mjt.food.server.cache.FoodInfoCache;
import bg.sofia.uni.fmi.mjt.food.server.retriever.FoodInfoRetrieverFactory;
import bg.sofia.uni.fmi.mjt.food.server.retriever.FoodInfoRetriever;
import bg.sofia.uni.fmi.mjt.food.server.retriever.InformationType;
import bg.sofia.uni.fmi.mjt.food.server.retriever.report.search.FoodReport;
import bg.sofia.uni.fmi.mjt.food.server.retriever.report.search.LabelNutrients;
import bg.sofia.uni.fmi.mjt.food.server.retriever.report.search.LabelNutrientsInfo;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FoodByBarcodeRetrieverTest {
    @Mock
    private HttpClient httpClientMock;

    @Mock
    private HttpResponse<String> httpResponseMock;

    private FoodInfoRetriever foodReportRetriever;

    @Before
    public void setUp() {
        foodReportRetriever = FoodInfoRetrieverFactory.getInstance(InformationType.REPORT, httpClientMock, null);
    }

    @Test
    public void testGetRequiredInformationWhenImgAndCodeAreGiven() throws IOException, InterruptedException {
        final String argumentsLine = "--img=barcode1.gif --code=000010";
        final String testFdcId = "0000010";
        when(httpClientMock.send(Mockito.any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenReturn(httpResponseMock);
        LabelNutrients testLabelNutrients = new LabelNutrients(new LabelNutrientsInfo(10),
                new LabelNutrientsInfo(20), new LabelNutrientsInfo(30), new LabelNutrientsInfo(40),
                new LabelNutrientsInfo(50));
        FoodReport testFoodReport = new FoodReport("gtinUpc", testFdcId, "description",
                "ingredients", testLabelNutrients);
        String json = new Gson().toJson(testFoodReport);
        when(httpResponseMock.body()).thenReturn(json);

        foodReportRetriever.getRequiredInformationAsString(testFdcId);

        String expected = FoodByBarcodeRetriever.getRequiredInformation(argumentsLine);
        String actual = FoodByBarcodeRetriever.getRequiredInformation(argumentsLine);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetRequiredInformationWhenOnlyCodeIsGiven() throws IOException, InterruptedException {
        final String argumentsLine = "--code=000011";
        final String testFdcId = "0000011";
        when(httpClientMock.send(Mockito.any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenReturn(httpResponseMock);
        LabelNutrients testLabelNutrients = new LabelNutrients(new LabelNutrientsInfo(10),
                new LabelNutrientsInfo(20), new LabelNutrientsInfo(30), new LabelNutrientsInfo(40),
                new LabelNutrientsInfo(50));
        FoodReport testFoodReport = new FoodReport("gtinUpc", testFdcId, "description",
                "ingredients", testLabelNutrients);
        String json = new Gson().toJson(testFoodReport);
        when(httpResponseMock.body()).thenReturn(json);

        foodReportRetriever.getRequiredInformationAsString(testFdcId);

        String expected = FoodByBarcodeRetriever.getRequiredInformation(argumentsLine);
        String actual = FoodInfoCache.checkInGtinUpcCache(testFdcId);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetRequiredInformationWhenOnlyImgIsGivenAndNameHasSpaceInIt()
            throws IOException, InterruptedException {
        final String argumentsLine = "--img=barcode 2.gif";
        final String testFdcId = QRCodeReader.getQrCode("barcode 2.gif");
        when(httpClientMock.send(Mockito.any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenReturn(httpResponseMock);
        LabelNutrients testLabelNutrients = new LabelNutrients(new LabelNutrientsInfo(10),
                new LabelNutrientsInfo(20), new LabelNutrientsInfo(30), new LabelNutrientsInfo(40),
                new LabelNutrientsInfo(50));
        FoodReport testFoodReport = new FoodReport("gtinUpc", testFdcId, "description",
                "ingredients", testLabelNutrients);
        String json = new Gson().toJson(testFoodReport);
        when(httpResponseMock.body()).thenReturn(json);

        foodReportRetriever.getRequiredInformationAsString(testFdcId);

        String expected = FoodByBarcodeRetriever.getRequiredInformation(argumentsLine);
        String actual = FoodInfoCache.checkInGtinUpcCache(testFdcId);

        assertEquals(expected, actual);
    }
}
