import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * With this exercise, I knew that I wanted to turn the original dictionary into
 * a String. Consequently, I converted the original dictionary into a byte array,
 * and then I made a string from this byte array. I also learned about JSONObjects 
 * and JSONArrays.
 */
public class NeedleInHaystack {
	
	private final String TOKEN_VALUE = "d4198c3d90103fdfeb3b4bf85e52620f";
	private final HttpClient CLIENT = HttpClientBuilder.create().build();
	String needleID = null;
	int needleIndex = 0;
	String strings;
	JSONArray jArray;
	JSONObject myObject;
	ArrayList<String> haystackList;
	
	public void handleAPIRequest() throws JSONException, ClientProtocolException, IOException {
		retrieveDictionary();
		needleID = getNeedleID(strings);
		jArray = getJSONArray(myObject);
		haystackList = convertJArrayToArrayList(jArray);
		needleIndex = getNeedleIndex(haystackList, needleID);
		sendIndex(needleIndex);
	}
	
	private String retrieveDictionary() throws ClientProtocolException, IOException {
		final String ENDPOINT_FOR_RETRIEVAL = "http://challenge.code2040.org/api/haystack";
		final HttpPost request = new HttpPost(ENDPOINT_FOR_RETRIEVAL);
		request.setEntity((new StringEntity("{\"token\":\"" + TOKEN_VALUE + "\"}", 
			ContentType.create("application/json"))));
		final HttpResponse response = CLIENT.execute(request);
		final HttpEntity entity = response.getEntity();
		final byte[] bytes = (EntityUtils.toByteArray(entity));
		strings = new String(bytes);
		return strings;
	}
	
	
	private String getNeedleID(String string) throws JSONException {
		myObject = new JSONObject(strings);
		needleID = myObject.getString("needle");
		return needleID;
	}
	
	private JSONArray getJSONArray(JSONObject object) throws JSONException {
		jArray = object.getJSONArray("haystack");
		return jArray;
	}
	
	private ArrayList<String> convertJArrayToArrayList(JSONArray jsonArray) throws JSONException {
		haystackList = new ArrayList<String>();
		for(int i=0;i<jsonArray.length();i++){
			haystackList.add(jsonArray.getString(i));
		}
		return haystackList;
	}
	
	private Integer getNeedleIndex(ArrayList<String> list, String string) {
		needleIndex = haystackList.indexOf(needleID);
		return needleIndex;
	}
	
	private void sendIndex(int index) throws ClientProtocolException, IOException {
		final String ENDPOINT_FOR_VALIDATION = "http://challenge.code2040.org/api/haystack/validate";
		final HttpPost request = new HttpPost(ENDPOINT_FOR_VALIDATION);
		request.setEntity((new StringEntity("{\"token\":\"" + TOKEN_VALUE + "\",\"needle\":\"" + index + "\"}", 
				ContentType.create("application/json"))));
		final HttpResponse response = CLIENT.execute(request);
			
		System.out.println("\nSending 'POST' request to URL : " + ENDPOINT_FOR_VALIDATION);
		System.out.println("Post parameters : " + request.getEntity());
		System.out.println("Response Code : " + response.getStatusLine().toString());
	}
}
