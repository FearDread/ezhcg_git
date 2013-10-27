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

  private long ms = 0;
  private long splashTime = 4000;

  private boolean splashActive = true;
  private boolean paused = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);

    setContentView(R.layout.splash);

    Thread splashThread = new Thread() {
      
      public void run() {
      
        try {
          while(splashTime && ms < splashTime) {
            
            if(!paused) {

              ms = ms + 100;
              sleep(100);
            }
          }
        } catch(Exception e) {} 
        finally {
           
          Intent splash = new Intent(EzhcgSplashScreen.this, EzhcgSplashScreen.class);
          startActivity(splash);
        }
      }
    };

    splashThread.start();
  }
}
