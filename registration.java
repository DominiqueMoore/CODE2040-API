import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class registration {
	
	String tokenValue = new String("d4198c3d90103fdfeb3b4bf85e52620f");
	HttpClient client = HttpClientBuilder.create().build();
	
	public registration() {
		this.makeRegistrationRequest();
	}
	
	public void makeRegistrationRequest() {
		String github = new String("https://github.com/DominiqueMoore/CODE2040-API");
		String registrationEndpoint = new String("http://challenge.code2040.org/api/register");
		
		HttpClient client = HttpClientBuilder.create().build(); //create Client
		
		try {
			HttpPost request = new HttpPost(registrationEndpoint); //POST request
			request.setEntity((new StringEntity("{\"token\":\"" + tokenValue + "\",\"github\":\"" + github + "\"}", 
					ContentType.create("application/json"))));
			HttpResponse response = client.execute(request); //POST response
			
			System.out.println("\nSending 'POST' request to URL : " + registrationEndpoint);
			System.out.println("Post parameters : " + request.getEntity());
			System.out.println("Response Code : " + response.getStatusLine().toString());
		} catch (Exception e) {
			
		} finally {
			
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		registration obj = new registration();
		stringReversal str = new stringReversal();
		needleInHaystack nis = new needleInHaystack();
	}

}
