package week2.day2;

import static io.restassured.RestAssured.*;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.Gson;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ServiceNowE2ETest {
	
	String sysId;
	RequestSpecification requestSpecification;
	IncidentRequestBody body;
	
	// AAA --> Arrange, Act and Assert
	
	// Arrange
	@BeforeClass
	public void beforeClass() {
		requestSpecification = new RequestSpecBuilder()
		    .setBaseUri("https://dev262949.service-now.com")
		    .setBasePath("/api/now/table/")
		    .setAuth(basic("admin", "vW0eDfd+A0V-"))
		    .addPathParam("tableName", "incident")
		    .addHeader("Content-Type", "application/json")
		    .build();
	}
	
	@Test(priority = 1)
	public void creat_a_new_record() {	
		body = new IncidentRequestBody();
		body.setDescription("Create new Record for E2E Test");
		body.setShort_description("Service Now E2e Test");
		body.setCategory("software");
		// Act
		sysId = given()
		 .spec(requestSpecification)
		 .log().all()
	   .when()
		 .body(new Gson().toJson(body))
		 .post("/{tableName}")
		// Assert
		.then()
		 .log().all()
		 .assertThat()
		 .statusCode(201)
		 .extract()
		 .jsonPath()
		 .getString("result.sys_id");
		
	}
	
	@Test(priority = 2)
	public void get_a_record() {
		given()
		 .spec(requestSpecification)
		 .log().all()
	   .when()		 
		 .get("/{tableName}/"+sysId)
		.then()
		 .log().all()
		 .assertThat()
		 .statusCode(200)
		 .statusLine(Matchers.containsString("OK"))
		 .contentType(ContentType.JSON)
		 .body("result.sys_id", Matchers.equalTo(sysId));
	}
	
	@Test(priority = 3)
	public void update_a_record() {
		body = new IncidentRequestBody();
		body.setState("2");
		body.setUrgency("1");
		
		given()
		 .spec(requestSpecification)
		 .log().all()
	   .when()	
	     .body(new Gson().toJson(body))
		 .put("/{tableName}/"+sysId)
		.then()
		 .log().all()
		 .assertThat()
		 .statusCode(200)
		 .statusLine(Matchers.containsString("OK"))
		 .contentType(ContentType.JSON)
		 .body("result.sys_id", Matchers.equalTo(sysId))
		 .body("result.state", Matchers.equalTo(body.getState()))
		 .body("result.urgency", Matchers.equalTo(body.getUrgency()));
	}
	
	@Test(priority = 4)
	public void delete_a_record() {
		given()
		 .spec(requestSpecification)
		 .log().all()
	   .when()		 
		 .delete("/{tableName}/"+sysId)
		.then()
		 .log().all()
		 .assertThat()
		 .statusCode(204)
		 .statusLine(Matchers.containsString("No Content"));
	}
	
	@Test(priority = 5)
	public void is_record_deleted_successfully() {
		given()
		 .spec(requestSpecification)
		 .log().all()
	   .when()		 
		 .get("/{tableName}/"+sysId)
		.then()
		 .log().all()
		 .assertThat()
		 .statusCode(404)
		 .statusLine(Matchers.containsString("Not Found"))
		 .contentType(ContentType.JSON);
	}

}