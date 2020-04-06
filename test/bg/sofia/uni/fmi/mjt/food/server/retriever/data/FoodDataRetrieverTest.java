package bg.sofia.uni.fmi.mjt.food.server.retriever.data;

import bg.sofia.uni.fmi.mjt.food.server.retriever.FoodInfoRetriever;
import bg.sofia.uni.fmi.mjt.food.server.retriever.FoodInfoRetrieverFactory;
import bg.sofia.uni.fmi.mjt.food.server.retriever.InformationType;
import bg.sofia.uni.fmi.mjt.food.server.retriever.data.search.Food;
import bg.sofia.uni.fmi.mjt.food.server.retriever.data.search.FoodSearch;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FoodDataRetrieverTest {

    @Mock
    private HttpClient httpClientMock;

    @Mock
    private HttpResponse<String> httpResponseMock;

    private FoodInfoRetriever foodDataRetriever;

    @Before
    public void setUp() {
        foodDataRetriever = FoodInfoRetrieverFactory.getInstance(InformationType.DATA, httpClientMock, null);
    }

    @Test
    public void testGetRequiredInformation_EmptyFoodList() throws IOException, InterruptedException {
        final String testSearchInput = "test search input";
        when(httpClientMock.send(Mockito.any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenReturn(httpResponseMock);
        when(httpResponseMock.body()).thenReturn("{foods:[]}");
        FoodSearch actual = ((FoodDataRetriever) foodDataRetriever).getRequiredInformation(testSearchInput);
        assertEquals(0, actual.getFoods().size());
    }

    @Test
    public void testGetRequiredInformation_TwoFoodsInList() throws IOException, InterruptedException {
        final String testSearchInput = "test search input";
        when(httpClientMock.send(Mockito.any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenReturn(httpResponseMock);
        List<Food> testFoods = List.of(new Food("soup", "id1", "gtin1"),
                new Food("salad", "id2", "gtin2"));
        FoodSearch testFoodSearch = new FoodSearch(testSearchInput, testFoods);
        String json = new Gson().toJson(testFoodSearch);
        when(httpResponseMock.body()).thenReturn(json);

        FoodSearch actual = ((FoodDataRetriever) foodDataRetriever).getRequiredInformation(testSearchInput);

        assertEquals(2, actual.getFoods().size());
        assertEquals("soup", actual.getFoods().get(0).getDescription());
        assertEquals("id1", actual.getFoods().get(0).getFdcId());
        assertEquals("gtin2", actual.getFoods().get(1).getGtinUpc());
    }
}
