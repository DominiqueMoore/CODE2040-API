import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;


public class stringReversal {
	
	String stringOne = null;
	String reversedString = null;
	String tokenValue = new String("d4198c3d90103fdfeb3b4bf85e52620f");
	HttpClient client = HttpClientBuilder.create().build();

	public stringReversal() {
		this.sendString(this.reverseString(this.getString()));
	}

	public String getString() {
		String getStringEndpoint = new String("http://challenge.code2040.org/api/reverse");
		
		try {
			HttpPost request = new HttpPost(getStringEndpoint);
			request.setEntity((new StringEntity("{\"token\":\"" + tokenValue + "\"}", 
					ContentType.create("application/json"))));
			HttpResponse response = client.execute(request);
			stringOne = EntityUtils.toString(response.getEntity());
			
			reversedString = new StringBuilder(stringOne).reverse().toString();
			
			System.out.println("\nSending 'POST' request to URL : " + getStringEndpoint);
			System.out.println("Post parameters : " + request.getEntity());
			System.out.println("Response Code : " + response.getStatusLine().toString());
			System.out.println(stringOne);
			
		} catch (Exception e) {
			
		} finally {
			
		}
		
		return stringOne;
	}
	
	public String reverseString(String string) {
		reversedString = new StringBuilder(string).reverse().toString();
		System.out.println(reversedString);
		return reversedString;
	}
	
	public void sendString(String string) {
		String sendStringEndpoint = new String("http://challenge.code2040.org/api/reverse/validate");
	
		try {
			HttpPost request = new HttpPost(sendStringEndpoint);
			request.setEntity((new StringEntity("{\"token\":\"" + tokenValue + "\",\"string\":\"" + reversedString + "\"}", 
					ContentType.create("application/json"))));
			HttpResponse response = client.execute(request);
			
			System.out.println("\nSending 'POST' request to URL : " + sendStringEndpoint);
			System.out.println("Post parameters : " + request.getEntity());
			System.out.println("Response Code : " + response.getStatusLine().toString());
			System.out.println(reversedString);
		} catch (Exception e) {
			
		} finally {
			
		}
	
	}
}
