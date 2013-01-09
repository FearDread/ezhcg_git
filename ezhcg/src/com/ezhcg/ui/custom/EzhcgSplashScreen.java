// Author: Garrett Haptonstall //
// =========================== //

package com.ezhcg.ui.custom;

import com.ezhcg.Ezhcg;
import com.ezhcg.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;


public class EzhcgSplashScreen extends Activity {
	//how long until we go to the next activity
	protected int _splashTime = 5000;
		 
	private Thread splashTread;
		 
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
		     super.onCreate(savedInstanceState);
		        setContentView(R.layout.splash);
		 
		        final EzhcgSplashScreen sPlashScreen = this;
		 
		        // thread for displaying the SplashScreen
		        Thread splashTread = new Thread() {
		              
		        	public void run()
		            {
		                try {                  
		                    synchronized(this) {	 
		                        //wait 5 sec
		                        wait(_splashTime);
		                    }
		 
		                } catch(InterruptedException e) {}

		                    //start a new activity
		                    Intent mainIntent = new Intent();
		                    	   mainIntent.setClass(sPlashScreen, Ezhcg.class);
		                    	   
		                    	   startActivity(mainIntent);

		            		}
		        		};
		        splashTread.start();
		    	}
		 
		    //Function that will handle the touch
		    @Override
		    public boolean onTouchEvent(MotionEvent event) {
		        if (event.getAction() == MotionEvent.ACTION_DOWN) {
		            synchronized(splashTread) {
		                splashTread.notifyAll();
		            }
		        }
		        return true;
		    }
}
