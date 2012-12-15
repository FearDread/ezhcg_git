// Author: Garrett Haptonstall //
// =========================== //

package com.ezhcg.ui;

import com.ezhcg.Ezhcg;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;


public class EzhcgSplashScreen extends Activity {
		
	// A ProgressDialog object  //
    private ProgressDialog progressDialog;  
  
    /** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState)  
    {  
        super.onCreate(savedInstanceState); 
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy);
        
        //Initialize a LoadViewTask object and call the execute() method  
        new LoadViewTask().execute();         
  
    }  
  
    //To use the AsyncTask, it must be subclassed  
    private class LoadViewTask extends AsyncTask<Void, Integer, Void>  
    { 
        //Before running code in separate thread  
        @Override  
        protected void onPreExecute()  
        {  
            //Create a new progress dialog  
            progressDialog = new ProgressDialog(EzhcgSplashScreen.this);   
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
            progressDialog.setTitle("Loading...");   
            progressDialog.setMessage("Loading application, please wait...");  
            progressDialog.setCancelable(false);  
            progressDialog.setIndeterminate(false);  
            progressDialog.setMax(100);   
            progressDialog.setProgress(0);  
            progressDialog.show();  
        }  
  
        //The code to be executed in a background thread.  
        @Override  
        protected Void doInBackground(Void... params)  
        {  
            try  
            {  
                //Get the current thread's token  
                synchronized (this)  
                {  
                    //Initialize an integer (that will act as a counter) to zero  
                    int counter = 0;   
                    while(counter <= 4)  
                    {  
                        //Wait 850 milliseconds  
                        this.wait(850);  
                        counter++;  
                        publishProgress(counter*25);  
                    }  
                }  
            }  
            catch (InterruptedException e)  
            {  
                e.printStackTrace();  
            }  
            return null;  
        }  
  
        //Update the progress  
        @Override  
        protected void onProgressUpdate(Integer... values)  
        {  
            //set the current progress of the progress dialog  
            progressDialog.setProgress(values[0]);  
        }  
  
        //after executing the code in the thread  
        @Override  
        protected void onPostExecute(Void result)  
        {  
            //close the progress dialog  
            progressDialog.dismiss();  
            //initialize the View and Main class in new thread
			Thread toRun = new Thread()
		       {
		              public void run()
		              {
		                     Intent myIntent = new Intent (EzhcgSplashScreen.this, Ezhcg.class);
		                     EzhcgSplashScreen.this.startActivity(myIntent);
		              }
		       };
		       toRun.start();
          	  
        }  
    }  
} 




