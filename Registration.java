import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * I didn't know anything about APIs or HTTP before completing this Registration class.
 * During my problem-solving process, I learned about the difference between GET, HEAD, POST,
 * and PUSH requests. I also learned about the Apache HttpComponents project, and how libraries,
 * in general, can make your life much easier as a programmer. (I also learned how to connect
 * via cURL; however, then I learned I was supposed to complete this challenge programmatically).
 * 
 * Through this entire process, I also learned about encapsulation and other important OOP design
 * principles. Lastly, I learned about "try/catch" versus throwing Exceptions. In these classes,
 * I chose to throw most of my exceptions, given that the programmer cannot do anything "useful" if,
 * for instance, the CODE2040 server is down.
 */
public class Registration {
	
	private final String TOKEN_VALUE = "d4198c3d90103fdfeb3b4bf85e52620f";
	
	private void handleAPIRequest() throws ClientProtocolException, IOException {
		makeRegistrationRequest();
	}
	
	private void makeRegistrationRequest() throws ClientProtocolException, IOException {
		final String GITHUB = "https://github.com/DominiqueMoore/CODE2040-API";
		final String ENDPOINT_FOR_RETRIEVAL = "http://challenge.code2040.org/api/register";
		final HttpClient CLIENT = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(ENDPOINT_FOR_RETRIEVAL); //POST request
		postRequest.setEntity((new StringEntity("{\"token\":\"" + TOKEN_VALUE + "\",\"github\":\"" + GITHUB + "\"}", 
			ContentType.create("application/json"))));
		HttpResponse postResponse = CLIENT.execute(postRequest); //POST response
			
		System.out.println("\nSending 'POST' request to URL : " + ENDPOINT_FOR_RETRIEVAL);
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
