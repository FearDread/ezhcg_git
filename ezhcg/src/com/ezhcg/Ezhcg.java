// Author: Garrett Haptonstall //
// =========================== //

package com.ezhcg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.ezhcg.ui.EzhcgDateView;
import com.ezhcg.ui.EzhcgInstructionView;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SdCardPath")
@SuppressWarnings("unused")
public class Ezhcg extends Activity {
  /* REFACTOR ALL CODE AND UNDERSTAND IT */
	
  private Button btn1;
  private Button btn2;
  private EditText enteredApi;
  private String apiDefaultValue;

  private static final int ACTIVITY_CREATE = 0;
  private static final int DELETE_ID = Menu.FIRST + 1;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);	

    saveBtn  = (Button)findViewById(R.id.saveButton);
    clickBtn = (Button)findViewById(R.id.clickButton);

    apiDefaultValue = getString(R.string.defaultApi);

    enteredApi = (EditText)findViewById(R.id.editApi);                	
    enteredApi.setText(apiDefaultValue);
    enteredApi.setBackgroundColor(Color.WHITE);
    enteredApi.setOnTouchListener( new OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        if (enteredApi.getText().toString().equals(apiDefaultValue)) {

          enteredApi.setText("");
        }

        return false;
      }

    });

    saveBtn.setOnClickListener(new OnClickListener() {       		   	
      @Override
      public void onClick(View v) {
        // Make sure an API is entered
        if (enteredApi.getText().toString().equals("")) {      		   		

          // Show Toast error message
          makeToastError();
        } else {
        // If API is entered start date view in new thread
          Thread dateThread = new Thread() {

            public void run()  {

              Intent dateView = new Intent (getApplicationContext(), EzhcgDateView.class);
           
              dateView.putExtra("enteredApi", enteredApi.getText().toString());
         
              startActivity(dateView);
            }

          };

        dateThread.start();
        }
      } 	    		   
    });

  // Call API Instructions view
  btn2.setOnClickListener(new OnClickListener() {

@Override
public void onClick(View v) {

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

case R.id.menuPullApi:
pullApi();
return true;

}
return super.onOptionsItemSelected(item);
}

// Private Functions //
// ================= //

// Write Entered API to file on SD card
private void saveApi() {
// write on SD card file data in the text box
try {
File myApiFile = new File("/sdcard/myhcgapi.txt");

myApiFile.createNewFile();

FileOutputStream fOut = new FileOutputStream(myApiFile);
OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

myOutWriter.append(entApi.getText());
myOutWriter.close();

fOut.close();

Toast.makeText(getBaseContext(), "Done writing API to SD", Toast.LENGTH_LONG).show();

} catch (Exception e) {

Toast.makeText(getBaseContext(), e.getMessage(),
Toast.LENGTH_LONG).show();
}
}

// Pull API from file on SD card if there is one, put into text field
private void pullApi() {

try {
File mApiFile = new File("/sdcard/myhcgapi.txt");

FileInputStream mInput = new FileInputStream(mApiFile);
BufferedReader mReader = new BufferedReader(new InputStreamReader(mInput));

String aDataRow = "";
String aBuffer = "";

while ((aDataRow = mReader.readLine()) != null) {
aBuffer += aDataRow;
}

entApi.setText(aBuffer);

mReader.close();

Toast.makeText(getBaseContext(), "Done pulling your API", Toast.LENGTH_LONG).show();

} catch (Exception e) {

Toast.makeText(getBaseContext(), e.getMessage(),
Toast.LENGTH_LONG).show();
}
}

// custom Toast error with image
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
