package week3.day2;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class CreateSimpleStubForJsonResponse {
	
	public static void main(String[] args) {
		stubFor(
				 //Request
				 get("/java/welcome/json")
				 //Response
				 .willReturn(
				    aResponse()
				    .withStatus(200)
				    .withHeader("Content-Type", "application/json")
				    .withBody("{\"message\": \"Hi! Welcome to Java\"}")
				  )
				);
		
		stubFor(
				 //Request
				 post("/java/welcome/add")	
				   .withHeader("Content-Type", equalTo("application/json"))
				   .withRequestBody(equalToJson("{\"name\": \"wiremock\"}"))
				 //Response
				 .willReturn(
				    aResponse()
				    .withStatus(201)
				    .withHeader("Content-Type", "application/json")
				    .withBodyFile("src\\main\\resources\\GetARecordJsonSchema.json")
				  )
				);
	}

}