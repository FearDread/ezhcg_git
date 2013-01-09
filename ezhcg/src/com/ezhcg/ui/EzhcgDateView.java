// Author: Garrett Haptonstall //
// =========================== //
// ==================================================================================== //
// Design & Format code to use new industry programming standards: for Android 3.0 +    //
// *** Use new Fragment methods, such as LoadManager, FragmentManager etc..				//
// *** Use AsyncTask methods to run network tasks (JSON Call) in background threads.	//
// ==================================================================================== //

package com.ezhcg.ui;


import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import com.ezhcg.R;



@SuppressWarnings("deprecation")
public class EzhcgDateView extends Activity {
	
	protected static final int END_DATE_DIALOG_ID = 0;
	protected static final int START_DATE_DIALOG_ID = 1;
	protected static final int PROGRESS_BAR_ID = 3;
	
	private TextView mStartDate; 
	private TextView mEndDate;
    private String mStartDateString; 
    private String mEndDateString; 
    private String enteredApiString; 
	
	private int mYear;
	private int mMonth;
	private int mDay;
	
	private Button mPickStartDate;
	private Button mPickEndDate;
	private Button mListView;

	private ProgressDialog pDialog;
	private Bundle extras;
	 	 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dateview);
    	
        mStartDate = (TextView) findViewById(R.id.startDate);
        mEndDate = (TextView) findViewById(R.id.endDate);
        
        mPickStartDate = (Button) findViewById(R.id.startDateBtn);
        mPickEndDate = (Button) findViewById(R.id.endDateBtn);
        mListView = (Button) findViewById(R.id.Button1);
        
        extras = getIntent().getExtras();
    	
    		if (extras != null) {
    	    
    			final String enteredApi = extras.getString("enteredApi");
    			final TextView mReminder = (TextView)findViewById(R.id.api);
        
    			if (mReminder.getText().toString().equals("")) {

    				mReminder.setText(enteredApi);
       	   
        			} 
    			}
    		
         	mPickStartDate.setOnClickListener(new View.OnClickListener() {
    			@Override
    			public void onClick(View v) {

                    showDialog(START_DATE_DIALOG_ID);

    			}
    		});
        	
            // get the current date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            // display the current date
            updateStartDisplay();
            
        	mPickEndDate.setOnClickListener(new View.OnClickListener() {  			
    			@Override
    			public void onClick(View v) {

                    showDialog(END_DATE_DIALOG_ID);

    			}
    		});
        	
            // get the current date
            final Calendar c2 = Calendar.getInstance();
            mYear = c2.get(Calendar.YEAR);
            mMonth = c2.get(Calendar.MONTH);
            mDay = c2.get(Calendar.DAY_OF_MONTH);

            // display the current date
            updateEndDisplay();
    	   	  	        
        	mListView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // starting new Async Task
                    new LoadDataTask().execute();
                }
   			});
        
        
    }
	 private DatePickerDialog.OnDateSetListener mStartDateSetListener =
			    new DatePickerDialog.OnDateSetListener() {

			        public void onDateSet(DatePicker view, int year, 
			                              int monthOfYear, int dayOfMonth) {
			            mYear = year;
			            mMonth = monthOfYear;
			            mDay = dayOfMonth;
			            updateStartDisplay();
			        }
			    };
				private DatePickerDialog.OnDateSetListener mEndDateSetListener =
					    new DatePickerDialog.OnDateSetListener() {

					        public void onDateSet(DatePicker view, int year, 
					                              int monthOfYear, int dayOfMonth) {
					            mYear = year;
					            mMonth = monthOfYear;
					            mDay = dayOfMonth;
					            updateEndDisplay();
					        }
					    };
			    
				@Override
				protected Dialog onCreateDialog(int id) {
				    switch (id) {
				    case START_DATE_DIALOG_ID:
				        return new DatePickerDialog(this,
				        			mStartDateSetListener,
				                    mYear, mMonth, mDay);
				        
				    case END_DATE_DIALOG_ID:
				        return new DatePickerDialog(this,
				        			mEndDateSetListener,
				                    mYear, mMonth, mDay);
				        
		    		case PROGRESS_BAR_ID: // we set this to 3
		        		pDialog = new ProgressDialog(this);
		        		pDialog.setMessage("Downloading data. Please wait...");
		        		pDialog.setIndeterminate(false);
		        		pDialog.setMax(100);
		        		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		        		pDialog.setCancelable(true);
		        		pDialog.show();
		            
		        		return pDialog;
				    }
				    return null;
				}

	//updates the date we display in the TextView
	private void updateStartDisplay() {
		mStartDate.setText(
	        new StringBuilder()
	                // Month is 0 based so add 1
	        		.append(mYear).append("-")
	                .append(mMonth + 1).append("-")
	                .append(mDay));
	                
		}

	//updates the date we display in the TextView
	private void updateEndDisplay() {
		mEndDate.setText(
	      new StringBuilder()
	      		// Month is 0 based so add 1
				.append(mYear).append("-")
				.append(mMonth + 1).append("-")
				.append(mDay));
		}
	
	public interface DateDialogFragmentListener{
    	//this interface is a listener between the Date Dialog fragment and the activity to update the buttons date
    	public void updateChangedDate(int year, int month, int day);
    }
	
	
	// Loading Screen Async Task
	private class LoadDataTask extends AsyncTask<Void, Integer, Void> { 
	        //Before running code in separate thread  
	        @Override  
	        protected void onPreExecute()  
	        {  
	            super.onPreExecute();
	            showDialog(PROGRESS_BAR_ID);  
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
	        	pDialog.setProgress(values[0]);  
	        }  
	  
	        //after executing the code in the thread  
	        @Override  
	        protected void onPostExecute(Void result)  
	        {  
	            //close the progress dialog  
	        	pDialog.dismiss();  
	        	extras = getIntent().getExtras();
	            //initialize the View and ListView class in new thread
				Thread toRun = new Thread()
			       {
			              public void run()
			              {
			                     mStartDateString = mStartDate.getText().toString();
			                     mEndDateString = mEndDate.getText().toString();
			                     enteredApiString = extras.getString("enteredApi");
			                     
	  		            	     Bundle data = new Bundle();
	  		            	     
	  		            	     data.putStringArray("jsonData", new String[] {mStartDateString, mEndDateString, enteredApiString});
	  		                     Intent myIntent = new Intent (getApplicationContext(), EzhcgListView.class);
	  		                     
	  		                     myIntent.putExtras(data);
	  		                     
	  		                     startActivity(myIntent);
			            	              	  
			              }
			       };
			       toRun.start();
	          	  
	        }  
	    }  

	}

	