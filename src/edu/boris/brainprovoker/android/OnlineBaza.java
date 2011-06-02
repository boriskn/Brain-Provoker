package edu.boris.brainprovoker.android;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.boris.brainprovoker.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class OnlineBaza extends Activity {
/** Called when the activity is first created. */
	public static final String KEY_121 = "http://brainprovoker.zxq.net/script.php"; 
   TextView txt;
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.onlinebaza);
    // Create a crude view - this should really be set via the layout resources 
    // but since its an example saves declaring them in the XML. 
    LinearLayout rootLayout = new LinearLayout(getApplicationContext()); 
    txt = new TextView(getApplicationContext()); 
    rootLayout.addView(txt); 
    setContentView(rootLayout); 

    // Set the text and call the connect function. 
    txt.setText("Connecting...");
  //call the method to run the data retreival
    txt.setText(getServerData(KEY_121));
    


} 



private String getServerData(String returnString) {
   
   InputStream is = null;
   
   String result = "";
    //the year data to send
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    nameValuePairs.add(new BasicNameValuePair("year","1970"));

    //http post
    try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(KEY_121);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

    }catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
    }

    //convert response to string
    try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
            }
            is.close();
            result=sb.toString();
            System.out.println(result);
    }catch(Exception e){
            Log.e("log_tag", "Error converting result "+e.toString());
    }
    //parse json data
    try{
            JSONArray jArray = new JSONArray(result);
            for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    Log.i("log_tag","id: "+json_data.getInt("id")+
                            ", name: "+json_data.getString("name")+
                            ", sex: "+json_data.getInt("sex")+
                            ", birthyear: "+json_data.getInt("birthyear")
                    );
                    //Get an output to the screen
                    returnString += "\n\t" + jArray.getJSONObject(i);
            }
    }catch(JSONException e){
            Log.e("log_tag", "Error parsing data "+e.toString());
    }
    return returnString;
}   
   
}
