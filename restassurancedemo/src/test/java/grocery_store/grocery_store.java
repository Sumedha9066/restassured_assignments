
package grocery_store;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;
import org.junit.runner.Request;
import org.testng.annotations.AfterClass;

public class grocery_store {
	String cartId;
	String itemId;

	@Test(priority = 0)
	public void api_status() {
		Response response = given().get("https://simple-grocery-store-api.glitch.me").then().statusCode(200).extract()
				.response();
		String jsonresponse = response.asString();
	}

	@Test(priority = 1)
	public void get_all_products() {
		Response response = given().get("https://simple-grocery-store-api.glitch.me/products").then().statusCode(200)
				.extract().response();
		String jsonresponse = response.asString();
		JsonPath responsebody = new JsonPath(jsonresponse);
		System.out.println("\n all products:" + jsonresponse);
	}

	@Test(priority = 2)
	public void get_single_product() {
		Response response = given().head("https://simple-grocery-store-api.glitch.me/products").then().statusCode(200)
				.extract().response();
		String josnresponse = response.asString();
		JsonPath responsebody = new JsonPath(josnresponse);
		System.out.println("single product:" + josnresponse);
	}

	@Test(priority = 3)
	public void create_new_cart() {
		Response response = given().post("https://simple-grocery-store-api.glitch.me/carts").then().statusCode(201)
				.extract().response();
		String jsonresponse = response.asString();
		response.body().prettyPrint();
		JsonPath responsebody = new JsonPath(jsonresponse);
		this.cartId = responsebody.getString("cartId");

	}

	@Test(priority = 4)
	public void get_cart() {
		Response resoponse = given().get("https://simple-grocery-store-api.glitch.me/carts/" + cartId).then()
				.statusCode(200).extract().response();
		String jsonresponse = resoponse.asString();
		JsonPath responsebody = new JsonPath(jsonresponse);
		System.out.println("cart:" + jsonresponse);
	}

	@Test(priority = 5)
	public void add_item_to_cart() {
		JSONObject requestParams = new JSONObject();
		requestParams.put("productId", "4646");
		Response response = given().header("content-type", "application/json").and().body(requestParams).when()
				.post("https://simple-grocery-store-api.glitch.me/carts/" + cartId + "/items").then().statusCode(201)
				.extract().response();
		response.body().prettyPrint();
		String jsonresponse = response.asString();
		JsonPath responsebody = new JsonPath(jsonresponse);
		this.itemId = responsebody.getString("itemId");
		System.out.println("added items in cart:" + jsonresponse);

	}

	@Test(priority = 6)
	public void get_cart_items() {
		Response response = given().get("https://simple-grocery-store-api.glitch.me/carts/" + cartId + "/items").then()
				.statusCode(200).extract().response();
		String jsonresponse = response.asString();
		JsonPath responsebody = new JsonPath(jsonresponse);
		// response.body().prettyPeek();
		System.out.println("items in cart:" + jsonresponse);

		// this.itemId= responsebody.getString("itemId");
	}

	@Test(priority = 7)
	public void update_quantity() {
		JSONObject requestParams = new JSONObject();
		System.out.println(cartId);
		System.out.println(itemId);
		requestParams.put("quantity", 6);
		Response response = given().header("content-type", "application/json").and().body(requestParams).when()
				.patch("https://simple-grocery-store-api.glitch.me/carts/" + cartId + "/items/" + itemId).then()
				.statusCode(204).extract().response();
		String jsonresponse = response.asString();
		JsonPath responsebody = new JsonPath(jsonresponse);
		System.out.println("updated quantity:" + jsonresponse);
	}

	@Test(priority = 8)
	public void replace_product_in_cart() {
		JSONObject Requestparams = new JSONObject();
		Requestparams.put("productId", 4643);
		Requestparams.put("quantity", 4);
		Response response = given().header("content-type", "application/json").and().body(Requestparams).when()
				.put("https://simple-grocery-store-api.glitch.me/carts/" + cartId + "/items/" + itemId).then()
				.statusCode(204).extract().response();
		String jsonresponse = response.asString();
		JsonPath responsebody = new JsonPath(jsonresponse);
		System.out.println("replaced product:" + jsonresponse);
	}

}
