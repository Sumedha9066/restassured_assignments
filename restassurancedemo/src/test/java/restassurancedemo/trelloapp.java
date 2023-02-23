package restassurancedemo;

import org.testng.annotations.Test;
import org.testng.xml.dom.Tag;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class trelloapp {
	public String url = "https://api.trello.com/"; // declared a url variable
	public String id; // created id globally we can use this url and id anywhere in program.

	@Test(priority = 0)
	public void create_a_board() {
		baseURI = url; // calling url from above.
		Response response = given().header("Content-Type", "application/json").queryParam("name", "demo")
				.queryParam("key", "ac5e9e216d85568c550baa974d7ad878")
				.queryParam("token", "c8aaccebfea9b6158c8c369079a1d41e5bbafee9c9fecbaec7d41ccba0f2f2d2").when().log()
				.all().post("/1/boards/").then().statusCode(200).extract().response();

		String jsonresponse = response.asString(); // coverting response into string formate
		System.out.println("\n" + jsonresponse); // printing the response.
		JsonPath responseBody = new JsonPath(jsonresponse); // from response we are taking responsebody
		id = responseBody.get("id"); // taking id from the responsebody.
		System.out.println("\n this is a id:" + id);
	}
	@Test(priority = 1)
	public void get_board() {
		baseURI = url;
		Response response = given().header("Content-Type", "application/json")
				.queryParam("key", "ac5e9e216d85568c550baa974d7ad878")
				.queryParam("token", "c8aaccebfea9b6158c8c369079a1d41e5bbafee9c9fecbaec7d41ccba0f2f2d2").when()
				.get("/1/boards/" + id).then().statusCode(200).extract().response();
		String jsonresponse = response.asString();
		// System.out.println("\n"+jsonresponse);
		JsonPath resposeBody = new JsonPath(jsonresponse);
		System.out.println("this is a get board:" + id);
	}
	@Test(priority = 2)
    public void update_board() {
    	baseURI=url;
    	Response response=given().header("Content-Type","application/json")
    			.queryParam("key","ac5e9e216d85568c550baa974d7ad878")
    			.queryParam("token", "c8aaccebfea9b6158c8c369079a1d41e5bbafee9c9fecbaec7d41ccba0f2f2d2").when()
    			.queryParam("name","sumedha").put("/1/boards/"+id).then().statusCode(200).extract()
    		    .response();
    	String jsonresponse = response.asString();
    	JsonPath resposeBody = new JsonPath(jsonresponse);
    	System.out.println("this is a update board:" + id);
    }
	@Test(priority = 3)
	public void delet_board() {
		baseURI=url;
		Response response=given().header("Content-Type","application/json")
				.queryParam("key","ac5e9e216d85568c550baa974d7ad878")
    			.queryParam("token", "c8aaccebfea9b6158c8c369079a1d41e5bbafee9c9fecbaec7d41ccba0f2f2d2").when()
	            .delete("/1/boards/"+id).then().statusCode(200).extract().response();
		String jsonresponse = response.asString();
    	JsonPath resposeBody = new JsonPath(jsonresponse);
    	System.out.println("this is a delete board:" + id);
	}
}	
		
	
	
	
	
	


