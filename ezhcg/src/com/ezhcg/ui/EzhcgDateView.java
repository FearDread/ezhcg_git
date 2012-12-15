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
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import com.ezhcg.R;
import com.ezhcg.util.DateDialogFragment;
import com.ezhcg.util.EzhcgDateFragment;
import com.ezhcg.util.EzhcgDateFragment.EzhcgDateFragmentButtonListener;


/** public class EzhcgDateView extends FragmentActivity implements EzhcgDateFragmentButtonListener { **/
	public class EzhcgDateView extends Activity {
	
	protected static final int END_DATE_DIALOG_ID = 0;
	protected static final int START_DATE_DIALOG_ID = 1;
	
	public static final String BUNDLE_KEY_DATE_ID = "date_id";	
	private static final String DATE_PICKER_TAG = "DateDialogFragment";
	
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

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dateview);
    	
        mStartDate = (TextView) findViewById(R.id.startDate);
        mEndDate = (TextView) findViewById(R.id.endDate);
        
        mPickStartDate = (Button) findViewById(R.id.startDateBtn);
        mPickEndDate = (Button) findViewById(R.id.endDateBtn);
        mListView = (Button) findViewById(R.id.Button1);
        
    	final Bundle extras = getIntent().getExtras();
    	
    		if (extras != null) {
    	    
    			final String enteredApi = extras.getString("enteredApi");
    			final TextView mReminder = (TextView)findViewById(R.id.api);
        
    			if (mReminder.getText().toString().equals("")) {

    				mReminder.setText(enteredApi);
       	   
        			} 
    			}
    		
         	mPickStartDate.setOnClickListener(new View.OnClickListener() {

    			@SuppressWarnings("deprecation")
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

    			@SuppressWarnings("deprecation")
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
	}


/** NEW METHODS **/
/** Uses new Fragment Classes 
//-----------------------------------------------------//
// EzhcgDateFragment interface                         //
//-----------------------------------------------------//
@Override
public void onSetDateButtonClicked(Calendar date) {
	// TODO Auto-generated method stub
	//create new DateDialogFragment
	DateDialogFragment ddf = DateDialogFragment.newInstance(this, R.string.set_date, date);

		ddf.setDateDialogFragmentListener(new DateDialogFragmentListener() {

			@Override
			public void dateDialogFragmentDateSet(Calendar date) {
				EzhcgDateFragment edf = (EzhcgDateFragment)getSupportFragmentManager().findFragmentById(R.id.date_detail_fragment);
				// update the fragment
				edf.updateDate(date);
			}
		});

		ddf.show(getSupportFragmentManager(), DATE_PICKER_TAG);
	}

	@Override
	protected void onPause() {
		DateDialogFragment ddf = (DateDialogFragment)getSupportFragmentManager().findFragmentByTag(DATE_PICKER_TAG);
		
			if (ddf!=null){
				ddf.dismiss();

			}

			super.onPause();
		}
	}


	
}
	

/** OLD METHODS **?
	
/**

	

**/	
 
	