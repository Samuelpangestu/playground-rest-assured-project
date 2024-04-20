import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Chapter1Test {
    @Test
    public void request_check_expect() {
        given().
        when().get("http://api.zippopotam.us/us/90210").
        then().assertThat().body("places[0].'place name'",equalTo("Beverly Hills"));
    }

}
