package com.emobi.diceapp.dicegame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.emobi.diceapp.R;

import java.util.Random;

public class EndingPhraseActivity extends AppCompatActivity {
    TextView textView4;
    String[] phrases={"Thank you! Enjoy your intimate moments!!","Thank you! Spice up your love!!"
                        ,"Thank you!It's the love that matter!!","Thank you!have a lovely night! "};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending_phrase);
        textView4= (TextView) findViewById(R.id.textView4);

        Random r = new Random();
        int i1 = r.nextInt(3 - 0) + 0;
        textView4.setText(phrases[i1]);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(EndingPhraseActivity.this,DiceActivity.class));
        finishAffinity();
    }
}
