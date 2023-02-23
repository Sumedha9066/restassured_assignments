package restreq;

import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import java.security.PublicKey;

import org.json.simple.JSONObject;

import io.restassured.RestAssured;
import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.*;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class testreqres {
	@Test(priority = 0)
	public void list_users() {
		Response response = given().get("/api/users?page=2").then().statusCode(200).extract().response();
		String jsonresponse = response.asString();
		JsonPath responsebody = new JsonPath(jsonresponse);
		System.out.println("this is total number of users: " + responsebody.getString("total"));
		System.out.println("\n this is list_users: " + jsonresponse);
	}

	@Test(priority = 1)
	public void single_user() {
		Response response = given().get("/api/users/2").then().statusCode(200).extract().response();
		String jsonresponse = response.asString();
		JsonPath responsebody = new JsonPath(jsonresponse);
		System.out.println("single user id: " + responsebody.getString("data.id"));
		System.out.println("single user email: " + responsebody.getString("data.email"));
		System.out.println("single user firstname: " + responsebody.getString("data.first_name"));
		System.out.println("single user:" + jsonresponse);
	}

	@Test(priority = 2)
	public void single_user_not_found() {
		Response response = given().get("/api/users/23").then().statusCode(404).extract().response();
		String jsonresponsebody = response.asString();
		// JsonPath responsebody=new JsonPath(jsonresponsebody);
		System.out.println("users not found:" + jsonresponsebody);
	}

	@Test(priority = 3)
	public void list_resourse() {
		Response response = given().get("/api/unknown").then().statusCode(200).extract().response();
		String jsonresponsebody = response.asString();
		JsonPath responsebody = new JsonPath(jsonresponsebody);
		System.out.println("total_pages: " + responsebody.getString("total_pages"));
		System.out.println("list resource: " + jsonresponsebody);
	}

	@Test(priority = 4)
	public void list_single_resource() {
		Response response = given().get("/api/unknown/2").then().statusCode(200).extract().response();
		String jsonresponsebody = response.asString();
		// JsonPath responsebody=new JsonPath(jsonresponsebody);
		System.out.println("single resource: " + jsonresponsebody);
	}

	@Test(priority = 5)
	public void single_resource_not_found() {
		Response response = given().get("/api/unknown/23").then().statusCode(404).extract().response();
		String jsonresponsebody = response.asString();
		System.out.println("single resource not found: " + jsonresponsebody);
	}

	@Test(priority = 6)
	public void create() {
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "demo");
		requestParams.put("job", "software engineer");
		Response response = given().header("Content-type", "application/json").and().body(requestParams).when()
				.post("/api/users").then().statusCode(201).extract().response();
		String jsonresponsebody = response.asString();
		System.out.println("created user: " + jsonresponsebody);
	}

	@Test(priority = 7)
	public void update() {
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "DEMO");
		requestParams.put("job", "software");
		Response response = given().header("content-type", "application/json").and().body(requestParams).when()
				.put("/api/users/2").then().statusCode(200).extract().response();
		String jsonresponsebody = response.asString();
		System.out.println("updated user: " + jsonresponsebody);
	}

	@Test(priority = 8)
	public void patch_update() {
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "demo123");
		Response response = given().header("content-type", "application/json").and().body(requestParams).when()
				.patch("/api/users/2").then().statusCode(200).extract().response();
		String jsonresponsebody = response.asString();
		JsonPath responsebody = new JsonPath(jsonresponsebody);
		System.out.println("updated by using patch: " + jsonresponsebody);
	}

	@Test(priority = 9)
	public void delete_user() {
		given().delete("/api/users/2").then().statusCode(204);

	}

	@Test(priority = 10)
	public void register_succesful() {
		JSONObject requestParam = new JSONObject();
		requestParam.put("email", "eve.holt@reqres.in");
		requestParam.put("password", "pistol");
		Response response = given().header("content-type", "application/json").and().body(requestParam).when()
				.post("/api/register").then().statusCode(200).extract().response();
		String jsonresponsebody = response.asString();
		JsonPath responsebody = new JsonPath(jsonresponsebody);
		System.out.println("registration succesful: " + jsonresponsebody);

	}

	@Test(priority = 11)
	public void registration_unsuccesfull() {
		JSONObject requestParam = new JSONObject();
		requestParam.put("email", "demo@gmail.com");
		Response response = given().header("content-type", "application/json").and().body(requestParam).when()
				.post("/api/register").then().statusCode(400).extract().response();
		String jsonresponsebody = response.asString();
		JsonPath responsebody = new JsonPath(jsonresponsebody);
		System.out.println("registration unsuccesful: " + jsonresponsebody);
	}

	@Test(priority = 12)
	public void login_succesful() {
		JSONObject requestparam = new JSONObject();
		requestparam.put("email", "eve.holt@reqres.in");
		requestparam.put("password", "cityslicka");
		Response response = given().header("content-type", "application/json").and().body(requestparam).when()
				.post("/api/login").then().statusCode(200).extract().response();
		String jsonresponsebody = response.asString();
		JsonPath responsebody = new JsonPath(jsonresponsebody);
		System.out.println("login succesfull: " + jsonresponsebody);
	}

	@Test(priority = 13)
	public void login_unsuccesful() {
		JSONObject requestparam = new JSONObject();
		requestparam.put("email", "peter@klaven");
		Response response = given().header("content-typr", "application/json").and().body(requestparam).when()
				.post("/api/login").then().statusCode(400).extract().response();
		String jsonresponsebody = response.asString();
		System.out.println("login unsuccesfull: " + jsonresponsebody);
	}

	@Test(priority = 14)
	public void delayed_response() {
		Response response = given().get("/api/users?delay=3").then().statusCode(200).extract().response();
		String jsonresponsebody = response.asString();
		System.out.println("delayed response: " + jsonresponsebody);
	}

	@Test
	public void beforeTest() {
		RestAssured.baseURI = "https://reqres.in/";
	}

	@Test
	public void afterTest() {
	}

}
