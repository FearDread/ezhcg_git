// Author: Garrett Haptonstall //
// =========================== // ===================================================== //
// INFO:																				//
// This View is done entirely programmatically for both performance testing and         //
// to learn how to do it in the first place. :)                                         //
// ==================================================================================== //

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

			mEmailMe  = getString(R.string.emailMe);
			mThankYou = getString(R.string.thankYou);
			mFooter   = getString(R.string.footer);
			
			
				// programmatically display instructions screen
				ImageView iv1 = new ImageView(this);
				iv1.setImageResource(R.drawable.logo);
				iv1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				
				  	myScrollView = new ScrollView(this);
				    myScrollView.setBackgroundResource(R.drawable.ezhcg_background);
				    myScrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));

				TextView tv1 = new TextView(this);
				tv1.setText(mInst);
				tv1.setTextSize(30);
				tv1.setTextColor(Color.parseColor("#FFFFFF"));
				tv1.setGravity(Gravity.CENTER);
				
				TextView tv2 = new TextView(this);
				tv2.setTextSize(20);
				tv2.setTextColor(Color.parseColor("#FFFFFF"));
				tv2.setGravity(Gravity.LEFT);
				tv2.setText(mInstTitle);
				
				TextView tv3 = new TextView(this);
				tv3.setTextSize(20);
				tv3.setTextColor(Color.parseColor("#FFFFFF"));
				tv3.setGravity(Gravity.LEFT);
				tv3.setText(mInstLine1);
				
				TextView tv4 = new TextView(this);
				tv4.setTextSize(20);
				tv4.setTextColor(Color.parseColor("#FFFFFF"));
				tv4.setGravity(Gravity.LEFT);
				tv4.setText(mInstLine2);
				
				TextView tv5 = new TextView(this);
				tv5.setTextSize(20);
				tv5.setTextColor(Color.parseColor("#FFFFFF"));
				tv5.setGravity(Gravity.LEFT);
				tv5.setText(mInstLine3);
				
				TextView tv6 = new TextView(this);
				tv6.setTextSize(20);
				tv6.setTextColor(Color.parseColor("#FFFFFF"));
				tv6.setGravity(Gravity.LEFT);
				tv6.setText(mInstLine4);			
				
				TextView tv7 = new TextView(this);
				tv7.setTextSize(25);
				tv7.setTextColor(Color.parseColor("#FFFFDD"));
				tv7.setGravity(Gravity.LEFT);
				tv7.setText(mEmailMe); 
				
				TextView tv8 = new TextView(this);
				tv8.setTextSize(20);
				tv8.setTextColor(Color.parseColor("#FFFFFF"));
				tv8.setGravity(Gravity.LEFT);
				tv8.setText(mThankYou);
				
				TextView tv9 = new TextView(this);
				tv9.setTextSize(15);
				tv9.setTextColor(Color.parseColor("#FFFFFF"));
				tv9.setGravity(Gravity.LEFT);
				tv9.setText(mFooter);
				
					LinearLayout ll = new LinearLayout(this);		
				
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
					layoutParams.setMargins(30, 20, 30, 0);
								
							ll.setOrientation(LinearLayout.VERTICAL );
							ll.setLayoutParams(layoutParams);
							
							ll.setBackgroundResource(R.drawable.ezhcg_background);
							ll.setGravity(Gravity.TOP);
							// Scroll View can only have 1 child, so put the linear layout and its children inside the ScrollView //
							myScrollView.addView(ll);
							
								ll.addView(iv1);
								ll.addView(tv1);
								ll.addView(tv2);
								ll.addView(tv3);
								ll.addView(tv4);
								ll.addView(tv5);
								ll.addView(tv6);
								ll.addView(tv7);
								ll.addView(tv8);
								ll.addView(tv9);

				setContentView(myScrollView);
		}

}