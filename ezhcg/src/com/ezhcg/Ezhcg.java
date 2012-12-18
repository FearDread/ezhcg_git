// Author: Garrett Haptonstall //
// =========================== // ===================================================== //
// TO-DO:																				//
// Port for Android version 2.3.3 and below.  											//
// ==================================================================================== //
// Design & Format code to use new industry programming standards: for Android 3.0 +    //
// *** Use new Fragment methods, such as LoadManager, FragmentManager etc..								//
// *** Use AsyncTask methods to run network tasks (JSON Call) in background threads.				//
// ==================================================================================== //

package com.ezhcg;

import com.ezhcg.ui.EzhcgDateView;
import com.ezhcg.ui.EzhcgInstructionView;
import com.ezhcg.util.EzhcgContentProvider;
import com.ezhcg.util.sql.EzhcgApiTable;

import android.app.LoaderManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("unused")
public class Ezhcg extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
	
	private Button btn1;
	private Button btn2;
	
	private EditText enteredApi;
	private String apiDefaultValue;
	
	private static final int ACTIVITY_CREATE = 0;
	private static final int DELETE_ID = Menu.FIRST + 1;
	
	// private Cursor cursor;
	private SimpleCursorAdapter adapter;
	private ContentValues ContentValues;
	private Uri ApiTableUri;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ApiTableUri = EzhcgContentProvider.CONTENT_URI;
        	btn1 = (Button)findViewById(R.id.saveButton);
        	btn2 = (Button)findViewById(R.id.clickButton);
        
        		enteredApi = (EditText)findViewById(R.id.editApi);                	
        	    enteredApi.setText(apiDefaultValue);
        	    
        		
        	    // enteredApi.setOnTouchListener( new OnTouchListener() {
        		//	@Override
        		//	public boolean onTouch(View v, MotionEvent event) {
        		//		if (enteredApi.getText().toString().equals(apiDefaultValue)) {
        		//			enteredApi.setText("");
        		//		}
                //    return false;
        		//	}
        		// });
        		
        		
        	    //  getApi(ApiTableUri);
        		 
        	   btn1.setOnClickListener(new OnClickListener() {
        		   	
       				@Override
       				public void onClick(View v) {
       					// Make sure an API is entered
            		   	if (enteredApi.getText().toString().equals("")) {      		   		
            		   		// Show Toast error message
            		   		makeToastError();
            		 
            		   					} else {
            		   							// If API is entered start date view in new thread
            		   							Thread toRun = new Thread() {
            		   			
            		   								public void run()
            		   								{
            		   									Intent myIntent = new Intent (getApplicationContext(), EzhcgDateView.class);
         		                     
            		   									myIntent.putExtra("enteredApi", enteredApi.getText().toString());
         		                     
            		   									startActivity(myIntent);
            		   								}
            		   							};
            		   						
            		   							toRun.start();
            		   						}
       									} 	    		   
       								});
        	   
        	   				// Call API Instructions view
        	   				btn2.setOnClickListener(new OnClickListener() {

        	   					@Override
        	   					public void onClick(View v) {
        	   						// TODO Auto-generated method stub
		   							Thread toRun = new Thread() {
		            		   			
		   								public void run()
		   								{
		   									Intent myIntent = new Intent (getApplicationContext(), EzhcgInstructionView.class);	                     
		   									startActivity(myIntent);
		   								}
		   							};
		   							toRun.start();
				
        	   					}	   
        	   				});
        
    					}
    
    // Save API to SQL Table 
    // Settings / Options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }   
    // Reaction to the menu selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case R.id.menuSaveApi:
        saveApi();
        return true;
      }
      return super.onOptionsItemSelected(item);
    }
    
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String [] projection = { EzhcgApiTable.COLUMN_ID, EzhcgApiTable.COLUMN_API_KEY };
		
			CursorLoader cursorLoader = new CursorLoader(this,
					EzhcgContentProvider.CONTENT_URI, projection, null, null, null);
		
			return cursorLoader;
	}
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		adapter.swapCursor(data);	
	}
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	    // data is not available anymore, delete reference
	    adapter.swapCursor(null);	
	}
	  // Add ability to delete API once one is saved
	  @Override
	  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    menu.add(0, DELETE_ID, 0, R.string.menu_delete);
	  }

	  
	  
// Private Functions //
// ================= //
	
    	private void saveApi() {
    		String ApiKey = (String) enteredApi.getText().toString();
    		
    		ContentValues values = new ContentValues();
						  values.put(EzhcgApiTable.COLUMN_API_KEY, ApiKey);
				
				if (ApiTableUri == null) {
					
					ApiTableUri = getContentResolver().insert(EzhcgContentProvider.CONTENT_URI, values);
				
				} else {
					
					getContentResolver().update(ApiTableUri, values, null, null);
				}
		
    	}
    	
    	private void getApi(Uri uri) {
    		String [] projection = { EzhcgApiTable.COLUMN_API_KEY };
    		
    		Cursor cursor = getContentResolver().query(EzhcgContentProvider.CONTENT_URI,  projection,  null,  null,  null);
    		
    			if (cursor != null) {
    				cursor.moveToFirst();
    				
    				enteredApi.setText(cursor.getString(cursor
    						.getColumnIndexOrThrow(EzhcgApiTable.COLUMN_API_KEY)));
    				
    				cursor.close();
    				return;
    			} 
    	}
    
       
    	private void makeToastError() {
    		LayoutInflater inflater = getLayoutInflater();
        
    		View layout = inflater.inflate(R.layout.toastview,
    				
    				(ViewGroup) findViewById(R.id.toastErrorLayout));

    		// set a dummy image
    		ImageView image = (ImageView) layout.findViewById(R.id.toastImage);
				      image.setImageResource(R.drawable.ic_launcher);

				      // set a message
				      TextView text = (TextView) layout.findViewById(R.id.toastText);
				               text.setText(R.string.errorApi);

		// Toast...
		Toast toast = new Toast(getApplicationContext());
			  toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			  toast.setDuration(Toast.LENGTH_LONG);
			  toast.setView(layout);
			  toast.show();
    	
    	}
	


}
