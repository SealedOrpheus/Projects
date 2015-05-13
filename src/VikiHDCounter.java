import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class VikiHDCounter {
	// Variable initizialization
	private static int perPage = 10;
	private static int page = 1;
	private static int HDTrue = 0;
	private static int HDFalse = 0;
	private static boolean isMore = false;
	private static HttpGet request = null;
	private static HttpResponse URLResponse = null;
	private static JSONObject responseJson = null;
	private static String jsonString =null;
	private static JSONArray responses = null;
	private static JSONObject flags = null;
	
	
	public static void main(String[] args) throws Exception {
 

		 HttpClient httpClient = HttpClientBuilder.create().build(); 
		 
		    try {
		    	do{
		    		//Form the url based on the dynamic parameter
		    		request = new HttpGet("http://api.viki.io/v4/videos.json?app=100250a&per_page="+perPage+"&page="+page);
			        request.addHeader("content-type", "application/x-www-form-urlencoded");
			        URLResponse = httpClient.execute(request);
			        
			        // Convert the response into a JSON object
			        jsonString = EntityUtils.toString(URLResponse.getEntity());
			        responseJson = new JSONObject(jsonString);
			        isMore = responseJson.getBoolean("more");
			        responses = responseJson.getJSONArray("response");
			        
			        //For each response, look at the flag
			        for (int i = 0; i < responses.length(); i++) {
			            JSONObject response  = responses.getJSONObject(i);
			            
			            // Get the flag Json object
			            flags = response.getJSONObject("flags");
			            
			            // Get the HD value
			            boolean isHD = flags.getBoolean("hd");
			            
			            //Increment the HD counter based on the value
			            if(isHD){
			            	HDTrue++;
			            }else{
			            	HDFalse++;
			            }
			        }		
			        
			        // Go to the next page			        
			        page++;
			        
		    	}while(isMore);
		    	
		       //Print out the result
		       System.out.println("Page : "+--page);
		       System.out.println("HD True : "+HDTrue);
		       System.out.println("HD False : "+HDFalse);

		        
		    }catch (Exception ex) {
		    	System.out.println(ex.getMessage());
		    	System.out.println("Something Wrong with our request");
		    } finally {
		        httpClient.getConnectionManager().shutdown();
		    }
 
	}
 
}
