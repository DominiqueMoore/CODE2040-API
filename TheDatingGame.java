import java.io.IOException;
import java.util.Date;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Prior to this exercise, I had never worked with dates and times. Consequently,
 * I learned a lot about JodaTime, Apache's DateUtils class, and Apache's
 * DateFormatUtil's class. I also now know about different ISO 8601 formats, and
 * the specifications that come with UTC/TimeZone differences when performing a
 * coding exercise like this.
 */
public class TheDatingGame {
	
	private final String TOKEN_VALUE = "d4198c3d90103fdfeb3b4bf85e52620f";
	private final HttpClient CLIENT = HttpClientBuilder.create().build();
	String strings;
	JSONObject myObject;
	Date resultDate;
	int additionalSeconds = 0;
	Date newDate;
	String finalDatestamp;
	
	public void handleAPIRequest() throws ClientProtocolException, IOException, JSONException {
		strings = retrieveDictionaryFromAPI();
		resultDate = convertDateStampToDate(strings);
		additionalSeconds = convertIntervalToInt(strings);
		newDate = addSecondsToDate(resultDate);
		finalDatestamp = finalDateToString(newDate);
		sendArray(finalDatestamp);
	}
	
	private String retrieveDictionaryFromAPI() throws ClientProtocolException, IOException {
		final String ENDPOINT_FOR_RETRIEVAL = "http://challenge.code2040.org/api/dating";
		final HttpPost request = new HttpPost(ENDPOINT_FOR_RETRIEVAL); //Send HTTP POST request
		request.setEntity((new StringEntity("{\"token\":\"" + TOKEN_VALUE + "\"}", 
			ContentType.create("application/json"))));
		final HttpResponse response = CLIENT.execute(request); //Retrieves JSON Dictionary
		final HttpEntity entity = response.getEntity();
		final byte[] bytes = (EntityUtils.toByteArray(entity)); //ToByteArray in order to turn elements into Strings
		strings = new String(bytes);
		return strings;
	}
	
	private Date convertDateStampToDate(String apiResponse) throws JSONException {
		myObject = new JSONObject(apiResponse); //JSONObject in order to turn into String[]
		String dateStampID = myObject.getString("datestamp"); //Retrieve prefixID for Substring to String comparison
		resultDate = new DateTime(dateStampID, DateTimeZone.UTC).toDate();
		return resultDate;
	}
	
	private Integer convertIntervalToInt(String apiResponse) throws JSONException {
		additionalSeconds = myObject.getInt("interval");
		return additionalSeconds;
	}
	
	private Date addSecondsToDate(Date originalDate) {
		newDate = DateUtils.addSeconds(originalDate, additionalSeconds);
		return newDate;
	}
	
	private String finalDateToString(Date date) {
		finalDatestamp = DateFormatUtils.formatUTC(date,"yyyy-MM-dd'T'HH:mm:ss");
		finalDatestamp = finalDatestamp + "Z";
		return finalDatestamp;
	}
	
	private void sendArray(String datestamp) throws ClientProtocolException, IOException {
		final String ENDPOINT_FOR_VALIDATION = "http://challenge.code2040.org/api/dating/validate";
		final HttpPost request = new HttpPost(ENDPOINT_FOR_VALIDATION);
		request.setEntity((new StringEntity("{\"token\":\"" + TOKEN_VALUE + "\",\"datestamp\":\"" + finalDatestamp + "\"}", 
			ContentType.create("application/json"))));
		final HttpResponse response = CLIENT.execute(request);
						
		System.out.println("\nSending 'POST' request to URL : " + ENDPOINT_FOR_VALIDATION);
		System.out.println("Post parameters : " + request.getEntity());
		System.out.println("Response Code : " + response.getStatusLine().toString());
	}
}
