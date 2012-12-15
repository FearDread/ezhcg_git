// Author: Garrett Haptonstall //
// =========================== //
// ==================================================================================== //
// INFO:                                                                                //
// This is the new DatePickerFragment class that handles the onDateSet function 		//
// used for Android API 3.0 +                                 	                        //
// ==================================================================================== //

package com.ezhcg.util;

import java.text.ParseException;
import java.util.Calendar;

import com.ezhcg.R;
import com.ezhcg.util.sql.EzhcgDatesHelper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class EzhcgDateFragment extends Fragment {

	public static final String TAG = "EzhcgDateFragment";

		RelativeLayout mRootView;
		
		TextView mStartDateText;
		TextView mEndDateText;
		
		Button mStartDateButton;
		Button mEndDateButton;
		
		EzhcgDateFragmentButtonListener mButtonListener;

		Calendar mDate;
		
		long mDateId;


    @Override
    public void onAttach(Activity activity) {
    	super.onAttach(activity);
        	try {
        		mButtonListener = (EzhcgDateFragmentButtonListener) activity;
        
        	} catch (ClassCastException e) {
        		
        		throw new ClassCastException(activity.toString()
                    + " must implement EzhcgDateFragmentButtonListener in Activity");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    	mRootView = (RelativeLayout)inflater.inflate(R.layout.dateview, container);
    
    	mStartDateText = (TextView)mRootView.findViewById(R.id.startDate);
    	mEndDateText = (TextView)mRootView.findViewById(R.id.endDate);
    
    	mStartDateButton = (Button)mRootView.findViewById(R.id.startDateBtn);
    		
    		mStartDateButton.setOnClickListener(new View.OnClickListener() {	
    			@Override
    			public void onClick(View v) {
    				showDateDialog();
    	 			}
     		});
    
     return mRootView;
    }

    
    /**
     * Update the Ui and the db record the detail fragment is currently on
     * with this date
     * @param date - the new date
     */
    public void updateDate(Calendar date){	
    	//set this detail fragment's date property
    	setDate(date);
    	//update the display
    	updateUi(date);
    	//update the row in the db
    	setDbDate(date);
    }

    /**
     * Switch to the date from the db from specified row
     * @param dateId - id of row containing date to display
     */
    public void updateDateId(long dateId){	
    	mDateId = dateId;
    	String[] projection = {"_id","date"};
    	Uri queryUri = Uri.withAppendedPath(Uri.parse("content://"+EzhcgDatesProvider.AUTHORITY+"/dates"), String.valueOf(dateId));	
    	
    	//get the specified date from the db
    	Cursor dateCursor = getActivity().getContentResolver().query(queryUri,
    			projection, null, null,null);

    	Calendar date = getDate(dateCursor);	
    	
    		setDate(date);
    		updateUi(date);	
    }

    private void setDbDate(Calendar newDate){	
    	//get activity's content resolver and update
    	Uri updateUri = Uri.withAppendedPath(Uri.parse("content://"+EzhcgDatesProvider.AUTHORITY+"/dates"),String.valueOf(mDateId));
    	ContentValues newDateValues = new ContentValues();
    	
    		newDateValues.put("date", EzhcgDatesHelper.SHORT_DATE_FORMAT.format(newDate.getTime()));
    	
    	getActivity().getContentResolver().update(updateUri, newDateValues, "_id=?", new String[]{String.valueOf(mDateId)});
    }

    private Calendar getDate(Cursor dateCursor){
    	String shortDateStr = dateCursor.getString(dateCursor.getColumnIndex("date"));

    	Calendar date = Calendar.getInstance();
    		try {
    			date.setTime(EzhcgDatesHelper.SHORT_DATE_FORMAT.parse(shortDateStr));
    		
    		} catch (ParseException e) {

    			e.printStackTrace();
    			}
    		return date;
    }

	private void updateUi(Calendar date){	
		String shortDateStr = EzhcgDatesHelper.SHORT_DATE_FORMAT.format(date.getTime());
		mStartDateText.setText(shortDateStr);	

		String longDateStr = EzhcgDatesHelper.LONG_DATE_FORMAT.format(date.getTime());
		mEndDateText.setText(longDateStr);
	}

	public void setDate(Calendar newDate){
		mDate = newDate;
	}
    
		private void showDateDialog(){
			mButtonListener.onSetDateButtonClicked(mDate);
		}
    
		//------------------------------------------
		//Fragment's interface
		//-----------------------------------------
		public interface EzhcgDateFragmentButtonListener{
			
			public void onSetDateButtonClicked(Calendar date);
		
		}
}







