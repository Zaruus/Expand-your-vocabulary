package com.e.expandyourvocabulary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bScore = (Button)findViewById(R.id.bSearch);
        bScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoreIntent = new Intent(getApplicationContext(), AddWordActivity.class);
                startActivity(scoreIntent);
            }
        });

        Button bJouer = (Button)findViewById(R.id.bDictionary);
        bJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jouerIntent = new Intent(getApplicationContext(), DictionaryActivity.class);
                startActivity(jouerIntent);
            }
        });
    }
}