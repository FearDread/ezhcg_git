// Author: Garrett Haptonstall //
// =========================== //

package com.ezhcg.ui;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import com.ezhcg.R;
import com.ezhcg.web.JSONParser;

public class EzhcgListView extends ListActivity {
	
	// JSON Node names
	private static final String TAG_DATA = "data";
	private static final String TAG_WEEK = "week";
	private static final String TAG_DATE = "date";
	private static final String TAG_CLICKS = "clicks";
	private static final String TAG_CONVERSIONS = "conversions";
	private static final String TAG_PAYOUT = "payout";
	private static final String TAG_ERPC = "erpc";
	private static final String TAG_CPL = "cpl";


	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listviewholder);
        
        // Hashmap for ListView
        final ArrayList<HashMap<String, String>> jsonDataList = new ArrayList<HashMap<String, String>>();
        
        final Bundle urlData = getIntent().getExtras();
       						
       						String[] urlDataStrings = urlData.getStringArray("jsonData");
 		            			   
 		            		String apiKey = urlDataStrings[2];
 		            		String startdate = urlDataStrings[0];
 		            		String enddate = urlDataStrings[1];
 		            		
 		            		String url = "http://ezwellness.hasoffers.com/stats/stats.json?api_key="+apiKey+"&start_date="+startdate+"&end_date="+enddate+"&group[]=Stat.date&group[]=Stat.week";
       					    String formatedUrl = url.replace(" ", "%20");
 		            		
 	       					// Creating JSON Parser instance
 	       					JSONParser jParser = new JSONParser();
 	       					 
 	       					// getting JSON string from URL
 	       					JSONObject json = jParser.getJSONFromUrl(formatedUrl);
 	       					
 	       						try {
 	       						// Getting Array of Data
 	       						JSONArray data = json.getJSONArray(TAG_DATA);
       					 
 	       								// looping through All Data
 	       								for(int i = 0; i < data.length(); i++) {
       					        
 	       									JSONObject results = data.getJSONObject(i);
       					 
 	       									String week = results.getString(TAG_WEEK);
 	       									String date = results.getString(TAG_DATE);
 	       									String clicks = results.getString(TAG_CLICKS);
 	       									String conversions = results.getString(TAG_CONVERSIONS);
 	       									String payout = results.getString(TAG_PAYOUT);
 	       									String erpc = results.getString(TAG_ERPC);
 	       									String cpl = results.getString(TAG_CPL);
       					     	
 	       										// creating new HashMap
 	       										HashMap<String, String> jsonMap = new HashMap<String, String>();
       					     
 	       										// adding each child node to HashMap key => value
 	       										jsonMap.put(TAG_WEEK, "Week: " + week);
 	       										jsonMap.put(TAG_DATE, "Date: " + date);
 	       										jsonMap.put(TAG_CLICKS, "Clicks: " + clicks);
 	       										jsonMap.put(TAG_CONVERSIONS, "Conversions: " + conversions);
 	       										jsonMap.put(TAG_PAYOUT, "Payout: " + payout);
 	       										jsonMap.put(TAG_ERPC, "ERPC: " + erpc);
 	       										jsonMap.put(TAG_CPL, "CPL: " + cpl);
       					
       									// adding HashList to ArrayList
       									jsonDataList.add(jsonMap);
       					 
 	       								}
 	       								
       							} catch (JSONException e) {
       								Log.e("log_tag", "Error parsing data "+e.toString());
       								e.printStackTrace();
       							}
       	    				
 	       					ListAdapter adapter = new SimpleAdapter(this, jsonDataList , R.layout.listview, 
 	                               new String[] { TAG_WEEK, TAG_DATE, TAG_CLICKS, TAG_CONVERSIONS, TAG_PAYOUT, TAG_ERPC, TAG_CPL }, 
 	                               new int[] { R.id.week,
 	       									   R.id.date,
 	       									   R.id.clicks,
 	       									   R.id.conversions,
 	       									   R.id.payout,
 	       									   R.id.erpc,
 	       									   R.id.cpl }); 
 	               
 	       							setListAdapter(adapter);
 	               
 	               final ListView lv = getListView();
 	               
 	               lv.setTextFilterEnabled(true);
 	               
 	               lv.setOnItemClickListener(new OnItemClickListener() {
 	               	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {        		
 	          @SuppressWarnings("unchecked")
 	       	  HashMap<String, String> jsonDataList = (HashMap<String, String>) lv.getItemAtPosition(position);
 	               		
 	          Toast.makeText(EzhcgListView.this, "Week '" + jsonDataList.get("week") + "' was clicked.", Toast.LENGTH_SHORT).show(); 

        
       	   	}
       	});
    };
}
        
       
	
	
	
	
	
	
	
	
	