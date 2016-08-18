import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


public class needleInHaystack {
	
	byte[] bytes = null;
	String needleID = null;
	int needleIndex = 0;
	
	String tokenValue = new String("d4198c3d90103fdfeb3b4bf85e52620f");
	HttpClient client = HttpClientBuilder.create().build();

	String retrieveDictionary = new String("http://challenge.code2040.org/api/haystack");
	String sendInt = new String("http://challenge.code2040.org/api/haystack/validate");
	
	public needleInHaystack() {
		this.sendIndex(this.retrieveDictionary());
	}
	
	public int retrieveDictionary() {
		
		try {
			HttpPost request = new HttpPost(retrieveDictionary);
			request.setEntity((new StringEntity("{\"token\":\"" + tokenValue + "\"}", 
					ContentType.create("application/json"))));
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			bytes = (EntityUtils.toByteArray(entity));
			String strings = new String(bytes);
			System.out.println(strings);

			
			JSONObject myObject = new JSONObject(strings);
			JSONArray jArray = myObject.getJSONArray("haystack");
			needleID = myObject.getString("needle");
			
				List<String> haystackList = new ArrayList<String>();
				for(int i=0;i<jArray.length();i++){
					haystackList.add(jArray.getString(i));
				}

				needleIndex = haystackList.indexOf(needleID);
				System.out.println(needleIndex);
				
				
		} catch (Exception e) {
			
		} finally {
			
		}
		return needleIndex;
	}
	
	public void searchDictionary(byte[] bytes) {
		
	}
	
	public void sendIndex(int index) {
		String sendIndexEndpoint = new String("http://challenge.code2040.org/api/haystack/validate");
		
		try {
			HttpPost request = new HttpPost(sendIndexEndpoint);
			request.setEntity((new StringEntity("{\"token\":\"" + tokenValue + "\",\"needle\":\"" + index + "\"}", 
					ContentType.create("application/json"))));
			HttpResponse response = client.execute(request);
			
			System.out.println("\nSending 'POST' request to URL : " + sendIndexEndpoint);
			System.out.println("Post parameters : " + request.getEntity());
			System.out.println("Response Code : " + response.getStatusLine().toString());
			System.out.println(index);
		} catch (Exception e) {
			
		} finally {
			
		}
	}

}
