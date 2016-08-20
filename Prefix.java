import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

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


public class Prefix {
	byte[] bytes = null;
	String prefixID = null;
	String[] lastList = null;
	String[] searchList = null;
	String strings = null;
	byte[][] finalBytes = null;
	int count = 0;
	JSONArray jArray = null;
	String apiResponse = null;
	ArrayList<String> myList = null;
	JSONArray lastJSONArray = null;

	
	HttpClient client = HttpClientBuilder.create().build();
	String tokenValue = "d4198c3d90103fdfeb3b4bf85e52620f";
	String retrieveDictionary = "http://challenge.code2040.org/api/prefix";
	String sendArray = "http://challenge.code2040.org/api/prefix/validate";
	
	public void handleAPIRequest() {
		String strings = retrieveDictionaryFromAPI();
		JSONArray jArray = convertAPIResponseToJSON(strings);
		searchList = convertJSONToStringArray(jArray);
		myList = convertArrayToArrayList(searchList);
		myList = removeStringsWithPrefix(myList);
		lastJSONArray = convertArrayListToArray(myList);
		sendArray(lastJSONArray);
	}
	
	public String retrieveDictionaryFromAPI() {
		try {
		HttpPost request = new HttpPost(retrieveDictionary); //Send HTTP POST request
		request.setEntity((new StringEntity("{\"token\":\"" + tokenValue + "\"}", 
				ContentType.create("application/json"))));
		HttpResponse response = client.execute(request); //Retrieves JSON Dictionary
		HttpEntity entity = response.getEntity();
		bytes = (EntityUtils.toByteArray(entity)); //ToByteArray in order to turn elements into Strings
		strings = new String(bytes);
		System.out.println("This is what the API gave me : " + strings); //prints what the API gave
		return strings;
		
		} catch (Exception e) {
			
		} finally {
			
		}
		return "";
		
	}
	
	public JSONArray convertAPIResponseToJSON(String apiResponse) {
		apiResponse = strings;
		try {
		JSONObject myObject = new JSONObject(apiResponse); //JSONObject in order to turn into String[]
		prefixID = myObject.getString("prefix"); //Retrieve prefixID for Substring to String comparison
		jArray = myObject.getJSONArray("array"); //Plug array into jArray in order to turn into String[]
		} catch (Exception e) {
			
		} finally {
			
		}
		System.out.println("This is my jArray : " + jArray);
		return jArray;
	}
	
	public String[] convertJSONToStringArray(JSONArray jArray){
		try {
		searchList = new String[jArray.length()]; //turn into String[]
		for(int i=0;i<jArray.length();i++){
			searchList[i] = jArray.getString(i);
		}
		} catch (Exception e) {
			
		} finally {
			
		}
		System.out.println("This is my search List : " + Arrays.toString(searchList));
		return searchList;
	}
	
	public ArrayList<String> convertArrayToArrayList(String[] array){
		myList = new ArrayList<String>(); //turn String[] into ArrayList in order to 
		//dynamically resize after deletions
		Collections.addAll(myList, searchList);
		
		System.out.println("This is my Array List : " + myList);
		return myList;
	}
	
	public ArrayList<String> removeStringsWithPrefix(ArrayList<String> myList) {
		//System.out.println("This is my prefix length : " + prefixID.length());
		System.out.println("prefixID: " + prefixID);
		

		Iterator<String> iter = myList.iterator();
        while(iter.hasNext()){ //Iterate through ArrayList to delete
        	String nextString = iter.next();
        	
			if (prefixID.equals(nextString.substring(0,4))) {
				iter.remove();
				count++;
			}
		}
        
        System.out.println("This is my Array List after removing strings : " + myList);
        return myList;
	}
	
	public JSONArray convertArrayListToArray(ArrayList myList) {
		int finalLength = searchList.length - count; //Retrieve size of ArrayList after deletions
        lastList = new String[finalLength]; //Create String[] with ArrayList size after deletions
        myList.toArray(lastList); //Turn ArrayList into String[]
        
        System.out.println("This is my final list : " + Arrays.toString(lastList));
        lastJSONArray = new JSONArray(Arrays.asList(lastList)); //Turn String[] into JSONArray
        
        return lastJSONArray;
	}
	
	public void sendArray(JSONArray array) {
		
		try {
			HttpPost request = new HttpPost(sendArray);
			request.setEntity((new StringEntity("{\"token\":\"" + tokenValue + "\",\"array\":" + lastJSONArray + "}", 
					ContentType.create("application/json"))));
			//System.out.println(("{\"token\":\"" + tokenValue + "\",\"array\":" + Arrays.toString(array) + "}"));
			HttpResponse response = client.execute(request);
			
			
			System.out.println("\nSending 'POST' request to URL : " + sendArray);
			System.out.println("Post parameters : " + request.getEntity());
			System.out.println("Response Code : " + response.getStatusLine().toString());
		} catch (Exception e) {
			
		} finally {
			
		}
	}

}
