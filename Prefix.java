import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
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
 * With this exercise, I learned how to use Iterators, and that you cannot
 * remove all substrings of a string from an ArrayList by use the .remove() method.
 * This was also my first time removing Substrings from a String.
 */
public class Prefix {
	
	private final String TOKEN_VALUE = "d4198c3d90103fdfeb3b4bf85e52620f";
	private final HttpClient CLIENT = HttpClientBuilder.create().build();
	String strings;
	String prefixID;
	JSONArray jArray;
	String[] searchList;
	int count = 0;
	ArrayList<String> myList;
	JSONArray lastJSONArray;
	
	public void handleAPIRequest() throws ClientProtocolException, IOException, JSONException {
		strings = retrieveDictionaryFromAPI();
		jArray = convertAPIResponseToJSON(strings);
		searchList = convertJSONToStringArray(jArray);
		myList = convertArrayToArrayList(searchList);
		myList = removeStringsWithPrefix(myList);
		lastJSONArray = convertArrayListToArray(myList);
		sendArray(lastJSONArray);
	}
	
	private String retrieveDictionaryFromAPI() throws ClientProtocolException, IOException {
		final String ENDPOINT_FOR_RETRIEVAL = "http://challenge.code2040.org/api/prefix";
		final HttpPost request = new HttpPost(ENDPOINT_FOR_RETRIEVAL); //Send HTTP POST request
		request.setEntity((new StringEntity("{\"token\":\"" + TOKEN_VALUE + "\"}", 
			ContentType.create("application/json"))));
		HttpResponse response = CLIENT.execute(request); //Retrieves JSON Dictionary
		HttpEntity entity = response.getEntity();
		byte[] bytes = (EntityUtils.toByteArray(entity)); //ToByteArray in order to turn elements into Strings
		strings = new String(bytes);
		return strings;	
	}
	
	private JSONArray convertAPIResponseToJSON(String apiResponse) throws JSONException {
		JSONObject myObject = new JSONObject(apiResponse); //JSONObject in order to turn into String[]
		prefixID = myObject.getString("prefix"); //Retrieve prefixID for Substring to String comparison
		jArray = myObject.getJSONArray("array"); //Plug array into jArray in order to turn into String[]
		return jArray;
	}
	
	private String[] convertJSONToStringArray(JSONArray jArray) throws JSONException{
		searchList = new String[jArray.length()]; //turn into String[]
		for(int i=0;i<jArray.length();i++){
			searchList[i] = jArray.getString(i);
		}
		return searchList;
	}
	
	private ArrayList<String> convertArrayToArrayList(String[] array){
		myList = new ArrayList<String>(); //turn String[] into ArrayList in order to 
		//dynamically resize after deletions
		Collections.addAll(myList, searchList);
		return myList;
	}
	
	private ArrayList<String> removeStringsWithPrefix(ArrayList<String> myList) {
		Iterator<String> iter = myList.iterator();
        	while(iter.hasNext()){ //Iterate through ArrayList to delete
        	String nextString = iter.next();
			if (prefixID.equals(nextString.substring(0,4))) {
				iter.remove();
				count++;
			}
		}
        	return myList;
	}
	
	private JSONArray convertArrayListToArray(ArrayList<String> myList) {
		int finalLength = searchList.length - count; //Retrieve size of ArrayList after deletions
		String[] lastList = new String[finalLength]; //Create String[] with ArrayList size after deletions
        	myList.toArray(lastList); //Turn ArrayList into String[]
        	lastJSONArray = new JSONArray(Arrays.asList(lastList)); //Turn String[] into JSONArray
        	return lastJSONArray;
	}
	
	private void sendArray(JSONArray array) throws ClientProtocolException, IOException {
		final String ENDPOINT_FOR_VALIDATION = "http://challenge.code2040.org/api/prefix/validate";	
		final HttpPost request = new HttpPost(ENDPOINT_FOR_VALIDATION);
		request.setEntity((new StringEntity("{\"token\":\"" + TOKEN_VALUE + "\",\"array\":" + lastJSONArray + "}", 
			ContentType.create("application/json"))));
		final HttpResponse response = CLIENT.execute(request);
			
		System.out.println("\nSending 'POST' request to URL : " + ENDPOINT_FOR_VALIDATION);
		System.out.println("Post parameters : " + request.getEntity());
		System.out.println("Response Code : " + response.getStatusLine().toString());
	}
}
