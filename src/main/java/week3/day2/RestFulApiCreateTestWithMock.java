package week3.day2;

import org.hamcrest.Matchers;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import io.restassured.RestAssured;

public class RestFulApiCreateTestWithMock {
	
	WireMockServer mockServer = new WireMockServer();
	
	String body = """			
			{
            "firstname" : "Jim",
            "lastname" : "Brown",
            "totalprice" : 111,
            "depositpaid" : true,
            "bookingdates" : {
              "checkin" : "2018-01-01",
              "checkout" : "2019-01-01"
            },
            "additionalneeds" : "Breakfast"
            }			
			""";
	
	String response = """
			{
            "bookingid": 2530,
            "booking": {
            "firstname": "Jim",
            "lastname": "Brown",
            "totalprice": 111,
            "depositpaid": true,
            "bookingdates": {
              "checkin": "2018-01-01",
              "checkout": "2019-01-01"
            } ,
            "additionalneeds": "Breakfast"
           }
          }
		""";
	
	@BeforeClass
	public void beforeClass() {
		mockServer.start();
	}
	
	@BeforeMethod
	public void beforeMethod() {
		mockServer.stubFor(
				  WireMock.post("/booking")
				          .withHeader("Content-Type", WireMock.equalTo("application/json"))
				          .withHeader("Accept", WireMock.equalTo("application/json"))
				          .withRequestBody(WireMock.equalToJson(body))
				          .willReturn(
				        		  WireMock.aResponse()
				        		          .withStatus(200)
				        		          .withHeader("Content-Type", "application/json")
				        		          .withBody(response)
				        		  )
				);
	}
	
	@Test
	public void createBooking() {
		RestAssured.given()
		           .baseUri("http://localhost:8080")
		           .basePath("/booking")
		           .header("Content-Type", "application/json")
		           .header("Accept", "application/json")
		           .log().all()
		           .when()
		           .body(body)
		           .post()
		           .then()
		           .log().all()
		           .assertThat()
		           .statusCode(200)
		           .statusLine(Matchers.containsString("OK"))
		           .body("booking.firstname", Matchers.equalTo("Jim"))
		           .body("booking.totalprice", Matchers.equalTo(111))
		           .body("booking.depositpaid", Matchers.equalTo(true));
		           
	}
	
	@AfterClass
	public void afterClass() {
		mockServer.stop();
	}
	

}