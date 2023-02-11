package utilities;

import StepDefinitions.Hooks;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.junit.Assert;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;




public class Helper {

	public Map<String, String> authenticationHeaders;


	//private static io.restassured.response.Response response1;



	public Response PostMessageByMessageBody(String ServiceURLPart, String messagebody,
			HashMap<String, String> authenticationDetails) {
		Response response = null;
		try {
			//if (System.getProperty("ENVIRONMENT").equals("ST")) {
				//setKeyStoreAndTrusStoreforHTTPS();


				Hooks.writeToLog("------------------------------- Post URL Start -------------------------");
				Hooks.writeToLog("posting URL =" + ServiceURLPart);
				Hooks.writeToLog("------------------------------- Post URL END -------------------------"
				);

				Hooks.writeToLog("------------------------------- Request Body Start -------------------------");
				Hooks.writeToLog("posting Request as -\n\n =" + messagebody);
				Hooks.writeToLog("------------------------------- Request Body END -------------------------"
				);
				System.out.println("Before Requesting the API Request Payload : " + messagebody);
				//response = given().headers(authenticationDetails).log().all().contentType(ContentType.JSON).body(messagebody).post(ServiceURLPart);
				response = io.restassured.RestAssured.given().log().all().contentType("application/json\r\n").log().all().body(messagebody).log().all().post(ServiceURLPart);
			//response1 =io.restassured.RestAssured.given().contentType("application/json\r\n").log().all().body(messagebody).log().all().post(ServiceURLPart);
				//response=given().contentType(ContentType.TEXT).body(messagebody).relaxedHTTPSValidation().when().post(ServiceURLPart);
				System.out.println("Response from Server inside Post Message Body : ----" + response.getBody().asString());
				Hooks.writeToLog("------------------------------- Response Body Start -------------------------");
				Hooks.writeToLog("Response Generated successfully and value =\n\n" + response.asString());
				Hooks.writeToLog("------------------------------- Request Body END -------------------------"
				);

			//}
		} catch (Throwable t) {
			System.out.println("Error Occured inside PostmessageBody fuction while posting at URL= " + ServiceURLPart
					+ ", Messagebody=" + messagebody);
			Assert.assertTrue("Unable to connect to server or error occured inside postmessageByMessageBody function",
					0 > 1);
		}
		return response;

	}
	public Response PUTMessageByMessageBody(String ServiceURLPart, String messagebody,
											 HashMap<String, String> authenticationDetails) {
		Response response = null;
		try {
			if (System.getProperty("ENVIRONMENT").equals("ST")) {
				//setKeyStoreAndTrusStoreforHTTPS();


				//Hooks.writeToLog("------------------------------- Post URL Start -------------------------");
				Hooks.writeToLog("posting URL =" + ServiceURLPart);
				//Hooks.writeToLog("------------------------------- Post URL END -------------------------"	);

				//Hooks.writeToLog("------------------------------- Request Body Start -------------------------");
				Hooks.writeToLog("posting Request as -\n\n =" + messagebody);
			   //Hooks.writeToLog("------------------------------- Request Body END -------------------------"	);

				// response = given().headers(authenticationDetails).contentType(ContentType.JSON).body(messagebody).relaxedHTTPSValidation().post(ServiceURLPart);
				response = io.restassured.RestAssured.given().contentType("application/json\r\n").body(messagebody).post(ServiceURLPart);

				//response=given().contentType(ContentType.TEXT).body(messagebody).relaxedHTTPSValidation().when().post(ServiceURLPart);

				//Hooks.writeToLog("------------------------------- Response Body Start -------------------------");
				Hooks.writeToLog("Response Generated successfully and value =\n\n" + response.asString());
				//Hooks.writeToLog("------------------------------- Request Body END -------------------------"	);

			}
		} catch (Throwable t) {
			System.out.println("Error Occured inside PostmessageBody fuction while posting at URL= " + ServiceURLPart
					+ ", Messagebody=" + messagebody);
			Assert.assertTrue("Unable to connect to server or error occured inside postmessageByMessageBody function",
					0 > 1);
		}
		return response;

	}

	public Response GetMesaage(String ServiceURLPart, HashMap<String, String> authenticationDetails) {
		Response response = null;
		try {

			 if(System.getProperty("ENVIRONMENT").equals("ST")) {
				 // setKeyStoreAndTrusStoreforHTTPS();

				 //Hooks.writeToLog("------------------------------- GET URL Start -------------------------" );
				 Hooks.writeToLog("GET URL *****************************************="
						 + ServiceURLPart);
				 //Hooks.writeToLog("------------------------------- GET URL END -------------------------" );


				 response = io.restassured.RestAssured.given().contentType("application/json\r\n").when().get(ServiceURLPart);

				 //Hooks.writeToLog("------------------------------- Response Body Start -------------------------" );
				 Hooks.writeToLog("Response Generated successfully and value =\n\n" + response.
						 asString());

				 //Hooks.writeToLog("------------------------------- Request Body END -------------------------" );
			 }

		} catch (Throwable t) {
			System.out.println("Error Occured inside Get Mesage  fuction while posting at URL=" + ServiceURLPart
					+ " ,Error=" + t.getMessage());
			Assert.assertTrue("Unable to connect to server or error occured inside GET Message Function function",
					0 > 1);
		}
		return response;

	}

	public String returnFileASASingleString(String FilePath) {
		BufferedReader br;
		StringBuilder sb;
		String line;
		String everything = "";
		try {
			br = new BufferedReader(new FileReader(FilePath));
			sb = new StringBuilder();
			line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			everything = sb.toString();
			br.close();
			// logman.info("File Read successfully ,file path="+FilePath);
		} catch (Throwable t) {
			System.out.println("Error occured inside returning FileASAString function for ,file path=" + FilePath);
		}
		return everything;
	}

	public void setKeyStoreAndTrusStoreforHTTPS() {
		KeyStore keyStore = null;
		KeyStore trustStore = null;
		SSLConfig config = null;
		String keyStorecertpath = System.getProperty("user.dir") + "\\src\\test\\resources\\certs\\keyStore.jks";
		String password = "changeit";
		String trustStorepath = System.getProperty("user.dir") + "\\src\\test\\resources\\certs\\trustStore.jks";

		try {
			keyStore = keyStore.getInstance(keyStore.getDefaultType());
			trustStore = keyStore.getInstance(keyStore.getDefaultType());
			keyStore.load(new FileInputStream(keyStorecertpath), password.toCharArray());
			trustStore.load(new FileInputStream(trustStorepath), password.toCharArray());
			SSLSocketFactory clientAuthFactory= new SSLSocketFactory(keyStore,password,trustStore);
			clientAuthFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			config = new SSLConfig().with().sslSocketFactory(clientAuthFactory).and().allowAllHostnames();
			RestAssured.config = RestAssured.config.sslConfig(config);

		} catch (Exception ex) {
			System.out.println("Error while loading Keystore or truststore >>>>>>>>>>");
			ex.printStackTrace();
		}
	}
}
