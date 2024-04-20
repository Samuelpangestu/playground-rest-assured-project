import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(DataProviderRunner.class)
public class OptimizingCodeChapter4Test {

    private static RequestSpecification requestSpec;

    @BeforeClass
    public static void createRequestSpecification() {
        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://api.zippopotam.us").
                build();
    }

    public static ResponseSpecification responseSpec;

    @BeforeClass
    public static void createResponseSpecification() {

        responseSpec = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                build();
    }

    @DataProvider
    public static Object[][] zipCodesAndPlaces() {
        return new Object[][]{
                {"us", "90210", "Beverly Hills"},
                {"us", "12345", "Schenectady"},
                {"ca", "B2R", "Waverley"}
        };
    }

    @Test
    @UseDataProvider("zipCodesAndPlaces")
    public void validateWithTestData(String countryCode, String zipCode, String expectedPlace) {
        given().
                spec(requestSpec).
                pathParam("countryCode", countryCode).pathParam("zipCode", zipCode).
                when().
                get("/{countryCode}/{zipCode}").

                then().
                assertThat().
                body("places[0].'place name'", equalTo(expectedPlace)).
                and().
                spec(responseSpec).
                log().body();
    }
    @Test
    public void extractFromPath() {

        String placeName =

        given().
                spec(requestSpec).
                when().
                get("http://api.zippopotam.us/us/90210").

                then().
                extract().
                path("places[0].'place name'");

        Assert.assertEquals(placeName,"Beverly Hills");
    }
}
