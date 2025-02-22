package week4.day1;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class AttachScreenShotInBugJira {

	public static void main(String[] args) {
		RestAssured.given()
		.baseUri("https://karthikeselene.atlassian.net")
        .basePath("/rest/api/3")
        .auth()	
        .preemptive()
        .basic("karthike.selene@gmail.com", "")
        .header("X-Atlassian-Token", "no-check")
        .contentType(ContentType.MULTIPART)
        .log().headers()
        .when()
        .multiPart(new File("./images/Screenshot 2025-02-22 112552.png"))
        .post("/issue/TS-4/attachments")
        .then()
        .log().all()
        .assertThat()
        .statusCode(200);
	}

}