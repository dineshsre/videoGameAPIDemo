package com.VideoGameAPITest;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import java.util.HashMap;
import static org.hamcrest.core.IsEqual.*;

import org.apache.groovy.json.internal.JsonStringDecoder;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class VideoGameAPITest {
	@Test(priority = 1)
	public void getAllVideoGames_Get() {
		given()
		.when()
		.get("http://localhost:8080/app/videogames")
		.then()
		.statusCode(200);
		
	}
	
	@Test(priority = 2)
	public void addVideoGame_Post() {
		
		HashMap<String,String> data = new HashMap<String,String>();
		data.put("id", "101");
		data.put("name", "Mahabharata");
		data.put("releaseDate", "2022-01-03T02:22:37.412Z");
		data.put("reviewScore", "10");
		data.put("category", "History");
		data.put("rating", "Excellent");
		
		Response response = 
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(data)
		.when()
			.post("http://localhost:8080/app/videogames")
		.then()
			.statusCode(200)
			.log().body()
			.extract().response();
		
		String jsonString = response.asString();
		Assert.assertEquals(jsonString.contains("Record Added Successfully"),true);
		
	}
	@Test(priority = 3)
	public void getVideoGame_Get() {
		
		given()
		.when()
			.get("http://localhost:8080/app/videogames/101")
		.then()
			.statusCode(200)
			.log().body()
			.body("videoGame.id", equalTo("101"))
			.body("videoGame.name", equalTo("Mahabharata"));
		
	}
	@Test(priority = 4)
	public void updateVideoGame_Put() {
		
		HashMap data = new HashMap();		
		data.put("id", "101");
		data.put("name", "Geeta");
		data.put("releaseDate", "2022-01-02T00:00:00-08:00");
		data.put("reviewScore", "10");
		data.put("category", "History");
		data.put("rating", "Excellent");
		
		
		given()
			.contentType("application/json")
			.body(data)
		.when()
			.put("http://localhost:8080/app/videogames/101")
		.then()
			.statusCode(200)
			.log().body()
			.body("videoGame.id", equalTo("101"))
			.body("videoGame.name", equalTo("Geeta"));
	}
	
	@Test(priority= 5)
	public void deleteVideoGame_delete() {
		
		Response response = 
		given()
		.when()
			.delete("http://localhost:8080/app/videogames/101")
		.then()
			.statusCode(200)
			.log().body()
			.extract().response();
		
		String jsonstring = response.asString();
		Assert.assertEquals(jsonstring.contains("Record Deleted Successfully"),true);
	}

}
