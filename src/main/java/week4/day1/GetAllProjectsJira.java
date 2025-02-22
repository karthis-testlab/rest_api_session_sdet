package week4.day1;

import io.restassured.RestAssured;

public class GetAllProjectsJira {
	
	public static void main(String[] args) {
		RestAssured.given()
		           .baseUri("https://karthikeselene.atlassian.net")
		           .basePath("/rest/api/3")
		           .auth()	
		           .preemptive()
		           .basic("karthike.selene@gmail.com", "")
		           .log().all()
		           .when()
		           .get("/project/search")
		           .then()		           
		           .assertThat()
		           .statusCode(200);
	}

}