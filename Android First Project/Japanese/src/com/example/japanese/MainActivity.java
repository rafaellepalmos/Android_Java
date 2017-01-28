package com.example.japanese;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {
	
	MediaPlayer introsound; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		introsound = MediaPlayer.create(MainActivity.this, R.raw.pin);
		introsound.start();
		Thread intro = new Thread(){
			public void run(){
				try{
					sleep(5000);
					Intent homeIntent = new Intent("android.intent.action.HOME");
					startActivity(homeIntent);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					finish();
				}
			}
			
		};
		intro.start();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		introsound.release();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
