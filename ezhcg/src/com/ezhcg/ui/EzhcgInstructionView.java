// Author: Garrett Haptonstall //
// =========================== //

package com.ezhcg.ui;

import com.ezhcg.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

@SuppressWarnings("unused")
public class EzhcgInstructionView extends Activity {

	
	private ImageView mHeader;
	private ScrollView myScrollView;
	
	private String mInst;
	
	private String mInstTitle;
	private String mInstLine1;
	private String mInstLine2;
	private String mInstLine3;
	private String mInstLine4;

	private String mEmailMe;
	private String mThankYou;
	private String mFooter;
	
	
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			

			
			mInst = getString(R.string.instructions);
			
			mInstTitle = getString(R.string.instTitle);
			mInstLine1 = getString(R.string.instLine1);
			mInstLine2 = getString(R.string.instLine2);
			mInstLine3 = getString(R.string.instLine3);
			mInstLine4 = getString(R.string.instLine4);

			mEmailMe = getString(R.string.emailMe);
			mThankYou = getString(R.string.thankYou);
			mFooter = getString(R.string.footer);
			
			
				// Programmatically display instructions screen
				ImageView iv1 = new ImageView(this);
				iv1.setImageResource(R.drawable.logo);
				iv1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				
				  	myScrollView = new ScrollView(this);
				    myScrollView.setBackgroundColor(0xfff00fff);
				    myScrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));

				TextView tv1 = new TextView(this);
				tv1.setText(mInst);
				tv1.setTextSize(30);
				tv1.setTextColor(Color.parseColor("#000000"));
				tv1.setGravity(Gravity.CENTER);
				
				TextView tv2 = new TextView(this);
				tv2.setTextSize(20);
				tv2.setTextColor(Color.parseColor("#000000"));
				tv2.setGravity(Gravity.LEFT);
				tv2.setText(mInstTitle);
				
				TextView tv3 = new TextView(this);
				tv3.setTextSize(20);
				tv3.setTextColor(Color.parseColor("#000000"));
				tv3.setGravity(Gravity.LEFT);
				tv3.setText(mInstLine1);
				
				TextView tv4 = new TextView(this);
				tv4.setTextSize(20);
				tv4.setTextColor(Color.parseColor("#000000"));
				tv4.setGravity(Gravity.LEFT);
				tv4.setText(mInstLine2);
				
				TextView tv5 = new TextView(this);
				tv5.setTextSize(20);
				tv5.setTextColor(Color.parseColor("#000000"));
				tv5.setGravity(Gravity.LEFT);
				tv5.setText(mInstLine3);
				
				TextView tv6 = new TextView(this);
				tv6.setTextSize(20);
				tv6.setTextColor(Color.parseColor("#000000"));
				tv6.setGravity(Gravity.LEFT);
				tv6.setText(mInstLine4);			
				
				TextView tv7 = new TextView(this);
				tv7.setTextSize(25);
				tv7.setTextColor(Color.parseColor("#1874CD"));
				tv7.setGravity(Gravity.LEFT);
				tv7.setText(mEmailMe); 
				
				TextView tv8 = new TextView(this);
				tv8.setTextSize(20);
				tv8.setTextColor(Color.parseColor("#000000"));
				tv8.setGravity(Gravity.LEFT);
				tv8.setText(mThankYou);
				
				TextView tv9 = new TextView(this);
				tv9.setTextSize(15);
				tv9.setTextColor(Color.parseColor("#000000"));
				tv9.setGravity(Gravity.LEFT);
				tv9.setText(mFooter);
				
						myScrollView.addView(tv1);
						myScrollView.addView(tv2);
						myScrollView.addView(tv3);
						myScrollView.addView(tv4);
						myScrollView.addView(tv5);
						myScrollView.addView(tv6);
						myScrollView.addView(tv7);
						myScrollView.addView(tv8);
						myScrollView.addView(tv9);

					LinearLayout ll = new LinearLayout(this);		
				
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
					layoutParams.setMargins(30, 20, 30, 0);
								
							ll.setOrientation(LinearLayout.VERTICAL );
							ll.setLayoutParams(layoutParams);
				
							ll.setGravity(Gravity.TOP);
							
					ll.addView(iv1);
					ll.addView(myScrollView);

				setContentView(ll);
		}

}