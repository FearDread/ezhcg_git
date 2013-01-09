package com.ezhcg.util;

import java.util.Calendar;

import com.ezhcg.ui.EzhcgDateView.DateDialogFragmentListener;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;




@TargetApi(11)
public class DateDialogFragment extends DialogFragment {

	public static String TAG = "DateDialogFragment";
	
	static Context mContext; // Connect Fragment with Context
	
	static int mYear;
	static int mMonth;
	static int mDay;
	
	static DateDialogFragmentListener mListener;

	public static DateDialogFragment newInstance(Context context, DateDialogFragmentListener listener, Calendar now) {
		
		DateDialogFragment dialog = new DateDialogFragment();
		
		mContext = context;
		mListener = listener;

		mYear = now.get(Calendar.YEAR);
		mMonth = now.get(Calendar.MONTH);
		mDay = now.get(Calendar.DAY_OF_MONTH);
		
		
		Bundle args = new Bundle();
			   args.putString("title", "Set Date");
		
			   dialog.setArguments(args);
			   
		return dialog;
	}


	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new DatePickerDialog(mContext, mDateSetListener, mYear, mMonth, mDay);
	}


	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;

			mListener.updateChangedDate(year, monthOfYear, dayOfMonth);
		}
	};
}