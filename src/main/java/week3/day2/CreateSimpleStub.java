package week3.day2;

import com.github.tomakehurst.wiremock.client.WireMock;

public class CreateSimpleStub {
	
	public static void main(String[] args) {
		WireMock.stubFor(
				 //Request
				 WireMock.get("/java/welcome/v1")
				 //Response
				 .willReturn(
				   WireMock.aResponse()
				    .withStatus(200)
				    .withBody("Hi! Welcome to Java")
				  )
				);
	}

}