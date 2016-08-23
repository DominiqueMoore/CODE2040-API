import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class Registration {
	
	private final String TOKEN_VALUE = "d4198c3d90103fdfeb3b4bf85e52620f";
	private final HttpClient CLIENT = HttpClientBuilder.create().build();
	
	private void handleAPIRequest() throws ClientProtocolException, IOException {
		makeRegistrationRequest();
	}
	
	
	//learned difference between try-catch and throws 
	private void makeRegistrationRequest() throws ClientProtocolException, IOException {
		final String GITHUB = "https://github.com/DominiqueMoore/CODE2040-API";
		final String REGISTRATION_ENDPOINT = "http://challenge.code2040.org/api/register";
	
		HttpPost postRequest = new HttpPost(REGISTRATION_ENDPOINT); //POST request
		postRequest.setEntity((new StringEntity("{\"token\":\"" + TOKEN_VALUE + "\",\"github\":\"" + GITHUB + "\"}", 
				ContentType.create("application/json"))));
		HttpResponse postResponse = CLIENT.execute(postRequest); //POST response
			
		System.out.println("\nSending 'POST' request to URL : " + REGISTRATION_ENDPOINT);
		System.out.println("Post parameters : " + postRequest.getEntity());
		System.out.println("Response Code : " + postResponse.getStatusLine().toString());
	
		
	}
	
	public static void main(String[] args) throws Exception {
		Registration obj = new Registration();
		obj.handleAPIRequest();
		StringReversal str = new StringReversal();
		str.handleAPIRequest();	
		NeedleInHaystack nis = new NeedleInHaystack();
		nis.handleAPIRequest();
		Prefix prefix = new Prefix();
		prefix.handleAPIRequest();
		TheDatingGame tdg = new TheDatingGame();
		tdg.handleAPIRequest();
	}

}
