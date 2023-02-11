package utilities;


import io.restassured.response.Response;
import org.junit.Assert;

import java.util.HashMap;

public class RestService {
	
	
	int SUCCESS=200;
	int CREATE=201;
	public HashMap<String,String> setDefaultHeaders()
	{
		HashMap<String,String> headersMap=new HashMap<>();
		headersMap.put("XAPISOURCE","MCA");
		headersMap.put("accept","application/json");
		return headersMap;
	}
	
	public void verifySuccessResponseStatusCode(Response response)
	{
		Assert.assertEquals("Unexpected response code from server !",SUCCESS,response.getStatusCode());
	}
	public void verifySuccessResponseStatusCodeForCreate(Response response)
	{
		Assert.assertEquals("Unexpected response code from server !", CREATE,response.getStatusCode());
	}
	public String getPetIDDetails(String petId)
	{
		String getPetIDEndpoint=null;
		getPetIDEndpoint=System.getProperty("GETPET");
		getPetIDEndpoint=getPetIDEndpoint.replace("{petID}",petId);
		return getPetIDEndpoint;
	}
}
