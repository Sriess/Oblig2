package com.adriane.oblig2;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button activity2, insertFrag, nextFrag, bNotification, bToast, bVibrate, bStartCamera;
	Vibrator vib;
	Fragment fragment1 = new MainFragment1();
	Fragment fragment2 = new MainFragment2();
	int mId = 0;
	int biIndexAlternatorAssistantModule = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		activity2 = (Button)findViewById(R.id.button_other_activity);
		activity2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.adriane.oblig2.OtherActivity");
				startActivity(intent);
				
			}
		});
		
		
		insertFrag = (Button)findViewById(R.id.button_insert_frag);
		insertFrag.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Get the fragmentManager and transaction
				FragmentTransaction fragmentTransaction = 
						getFragmentManager().beginTransaction();
				
				// Add the first fragment
				fragmentTransaction.replace(R.id.fragment_container, fragment1);
				fragmentTransaction.addToBackStack(null);
				
				// Apply transition animation and commit
				fragmentTransaction.setTransition(4097);
				fragmentTransaction.commit();
				
				// Enables the "next fragment" button after "insert fragment" has been clicked once
				if(!nextFrag.isEnabled()){
					nextFrag.setEnabled(true);
				}
			}
		});
		
		nextFrag = (Button)findViewById(R.id.button_next_frag);
		nextFrag.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Get fragmentManager and transaction
				FragmentTransaction fragmentTransaction = 
						getFragmentManager().beginTransaction();
				
				// Replace first fragment with the second
				fragmentTransaction.replace(R.id.fragment_container, fragment2);
				fragmentTransaction.addToBackStack(null);
				
				// commit transaction
				fragmentTransaction.setTransition(4097);
				fragmentTransaction.commit();
			}
		});
		
		bNotification = (Button)findViewById(R.id.button_notification);
		bNotification.setOnClickListener(new View.OnClickListener(
				) {
			
			@Override
			public void onClick(View v) {
				mId++;
				NotificationCompat.Builder mBuilder =
				        new NotificationCompat.Builder(v.getContext());
				mBuilder.setSmallIcon(R.drawable.ic_launcher);
				mBuilder.setContentTitle("My Notification");
				mBuilder.setContentText("Notification nr."+mId);
				        
				NotificationManager mNotificationManager =
				    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				// mId allows you to update the notification later on.
				
				mNotificationManager.notify(mId, mBuilder.build());
				bToast.callOnClick();
			}
		});
		
		bToast = (Button)findViewById(R.id.button_toast);
		bToast.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Found two ways to implement a simple toast message
				if(biIndexAlternatorAssistantModule % 2 == 0){
					Context context = getApplicationContext();
					CharSequence text = "Short toast!";
					int duration = Toast.LENGTH_SHORT;
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
				else {
					Toast.makeText(getApplicationContext(), "Long toast!", Toast.LENGTH_LONG).show();
				}
				biIndexAlternatorAssistantModule++;
			}
		});
		
		bVibrate = (Button)findViewById(R.id.button_vibrate);
		bVibrate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				vib = (Vibrator) v.getContext().getSystemService(Context.VIBRATOR_SERVICE);
				if(vib.hasVibrator()){
					vib.cancel();
					vib.vibrate(500);
				}
			}
		});
		bVibrate.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				vib = (Vibrator) v.getContext().getSystemService(Context.VIBRATOR_SERVICE);
				if(vib.hasVibrator()){
					vib.cancel();
					long[] pattern = {0,200,100,200,100,200,200,350,200,350,200,350,100,200,100,200,100,200};
					vib.vibrate(pattern, -1);
				}
				return false;
			}
		});
		
		bStartCamera = (Button) findViewById(R.id.button_camera);
		bStartCamera.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivity(cameraIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
