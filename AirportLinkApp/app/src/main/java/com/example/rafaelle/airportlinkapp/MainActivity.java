package com.example.rafaelle.airportlinkapp;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
                    sleep(5000);//5 seconds
                    Intent mapsIntent = new Intent("android.intent.action.MAPS");
                    startActivity(mapsIntent);
                } catch (InterruptedException e) {
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
        super.onPause();
        introsound.release();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

/*
REFERENCES
https://cdn3.iconfinder.com/data/icons/glypho-transport/64/train-front-512.png
 */
