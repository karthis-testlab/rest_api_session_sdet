package week3.day1;

import static io.restassured.RestAssured.*;

public class CreateOAuthToken {	
	
	public static void main(String[] args) {
		given()
		     .baseUri("https://dev262949.service-now.com")
		     .basePath("/oauth_token.do")
		     .header("Content-Type","application/x-www-form-urlencoded")
		     .formParam("grant_type", "password")
		     .formParam("client_id", "d3794ae03b3f121047e922e479b4f796")
		     .formParam("client_secret", "1!nNi:J8@G")
		     .formParam("username", "admin")
		     .formParam("password", "vW0eDfd+A0V-")
		     .log().all()
		.when()
		     .post()
		.then()
		     .log().all()
		     .assertThat()
		     .statusCode(200);
	}

}