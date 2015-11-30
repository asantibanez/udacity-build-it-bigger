package com.andressantibanez.jokepresenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokePresenterActivity extends AppCompatActivity {

    private static final String JOKE = "joke";

    public static Intent launchIntent(Context context, String joke) {
        Intent intent = new Intent(context, JokePresenterActivity.class);
        intent.putExtra(JOKE, joke);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_presenter);

        String joke = getIntent().getStringExtra(JOKE);

        TextView textView = (TextView) findViewById(R.id.joke);
        textView.setText(joke);
    }
}
