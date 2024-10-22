package api.test;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	Faker faker;
	User userPayload;
	
	@BeforeClass
	public void setupData() {
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
	}
	
	@Test(priority = 1)
	public void testPostUser() {
		Response response = UserEndPoints.createUser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority = 2)
	public void getUserByName() {
		Response response = UserEndPoints.ReadUser(this.userPayload.getUsername());
		response.then().log().all();
		JSONObject jo = new JSONObject(response.asString());
		Assert.assertEquals(response.getStatusCode(), 200);
		
		String Strid = jo.get("id").toString();
		int id = Integer.parseInt(Strid);
		System.out.println("**************************************** "+ id +" ***********************************************************");
		Assert.assertEquals(id,this.userPayload.getId());
		
		String username = jo.get("username").toString();
		System.out.println("**************************************** "+ username +" ***********************************************************");
		Assert.assertEquals(username,this.userPayload.getUsername());
		
		String firstname = jo.get("firstName").toString();
		System.out.println("**************************************** "+ firstname +" ***********************************************************");
		Assert.assertEquals(firstname,this.userPayload.getFirstName());
		
		String lastName = jo.get("lastName").toString();
		System.out.println("**************************************** "+ lastName +" ***********************************************************");
		Assert.assertEquals(lastName,this.userPayload.getLastName());
		
		String email = jo.get("email").toString();
		System.out.println("**************************************** "+ email +" ***********************************************************");
		Assert.assertEquals(email,this.userPayload.getEmail());
		
		String password = jo.get("password").toString();
		System.out.println("**************************************** "+ password +" ***********************************************************");
		Assert.assertEquals(password,this.userPayload.getPassword());
		
		String phoneNo = jo.get("phone").toString();
		System.out.println("**************************************** "+ phoneNo +" ***********************************************************");
		Assert.assertEquals(phoneNo,this.userPayload.getPhone());
		
		
		
	}
	
	@Test(priority = 3)
	public void testUpdateUserByName() {
		
		
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndPoints.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//checking data after update
		Response responseAfterUpdate = UserEndPoints.ReadUser(this.userPayload.getUsername());
		responseAfterUpdate.then().log().all();
		JSONObject jo = new JSONObject(responseAfterUpdate.asString());
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		
		String firstname = jo.get("firstName").toString();
		System.out.println("**************************************** "+ firstname +" ***********************************************************");
		Assert.assertEquals(firstname,this.userPayload.getFirstName());
		
		String lastName = jo.get("lastName").toString();
		System.out.println("**************************************** "+ lastName +" ***********************************************************");
		Assert.assertEquals(lastName,this.userPayload.getLastName());
		
		String email = jo.get("email").toString();
		System.out.println("**************************************** "+ email +" ***********************************************************");
		Assert.assertEquals(email,this.userPayload.getEmail());
		
		
	}
	
	@Test(priority = 4)
	public void testDeleteUserByName() {
		Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	
	
	

}
