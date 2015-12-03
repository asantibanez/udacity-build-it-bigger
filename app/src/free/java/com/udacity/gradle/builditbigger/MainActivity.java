package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.andressantibanez.jokepresenter.JokePresenterActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends ActionBarActivity {

    boolean mIsAppPaidVersion;

    InterstitialAd mInterstitialAd;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mIsAppPaidVersion = getResources().getBoolean(R.bool.is_paid_version);

        if (!mIsAppPaidVersion) {
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    requestNewInterstitial();
                    getAndDisplayJoke();
                }
            });
            requestNewInterstitial();
        }
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
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

    public void tellJoke(View view){
        if (!mIsAppPaidVersion && mInterstitialAd.isLoaded())
            mInterstitialAd.show();
        else {
            getAndDisplayJoke();
        }
    }

    private void getAndDisplayJoke() {
        mProgressBar.setVisibility(View.VISIBLE);

        new GetJokeAsyncTask() {
            @Override
            protected void onPostExecute(String s) {
                if (s != null) {
                    startActivity(JokePresenterActivity.launchIntent(MainActivity.this, s));
                } else {
                    Toast.makeText(MainActivity.this, "Error while retrieving joke. This is no joke!", Toast.LENGTH_LONG).show();
                }

                mProgressBar.setVisibility(View.GONE);
            }
        }.execute();
    }


}
