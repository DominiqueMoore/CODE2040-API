import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * With this exercise, I learned how to use StringBuilder in order to reverse 
 * a String.
 */
public class StringReversal {
	
	private final String TOKEN_VALUE = "d4198c3d90103fdfeb3b4bf85e52620f";
	private final HttpClient CLIENT = HttpClientBuilder.create().build();
	private String originalString;
	private String reversedString;
	
	public void handleAPIRequest() throws ClientProtocolException, IOException {
		getString();
		reversedString = reverseString(originalString);
		sendString(reversedString);
	}

	private String getString() throws ClientProtocolException, IOException {
		final String ENDPOINT_FOR_RETRIEVAL = "http://challenge.code2040.org/api/reverse";
		final HttpPost request = new HttpPost(ENDPOINT_FOR_RETRIEVAL);
		request.setEntity((new StringEntity("{\"token\":\"" + TOKEN_VALUE + "\"}", 
			ContentType.create("application/json"))));
		HttpResponse response = CLIENT.execute(request);
		originalString = EntityUtils.toString(response.getEntity());
		return originalString;
	}
	
	private String reverseString(String string) {
		reversedString = new StringBuilder(string).reverse().toString();
		return reversedString;
	}
	
	private void sendString(String string) throws ClientProtocolException, IOException {
		final String ENDPOINT_FOR_VALIDATION = "http://challenge.code2040.org/api/reverse/validate";
		final HttpPost request = new HttpPost(ENDPOINT_FOR_VALIDATION);
		request.setEntity((new StringEntity("{\"token\":\"" + TOKEN_VALUE + "\",\"string\":\"" + reversedString + "\"}", 
			ContentType.create("application/json"))));
		final HttpResponse response = CLIENT.execute(request);
			
		System.out.println("\nSending 'POST' request to URL : " + ENDPOINT_FOR_VALIDATION);
		System.out.println("Post parameters : " + request.getEntity());
		System.out.println("Response Code : " + response.getStatusLine().toString());
	}
}
