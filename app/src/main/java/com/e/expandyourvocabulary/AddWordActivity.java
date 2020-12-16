package com.e.expandyourvocabulary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

public class AddWordActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addword);

        final TextView textvWord = (TextView)findViewById(R.id.textvWord);
        final TextView textvDefinition = (TextView)findViewById(R.id.textvDefinition);
        final EditText edtextSearch = (EditText) findViewById(R.id.edtextSearch);
        final MyDatabase1 mydb = new MyDatabase1(this);

        Button bSearch = (Button)findViewById(R.id.bSearch);
        Button bAddWord = (Button)findViewById(R.id.bAddWord);

        bSearch.setOnClickListener(new GetWordOnClickListener() {
            @Override
            public void onClick(View v) {
            String word = "";
            word = String.valueOf(edtextSearch.getText());

            final String url = "https://wordsapiv1.p.rapidapi.com/words/" + word;
            AsyncWordsDefinitionData wordsOutput = new AsyncWordsDefinitionData();

            try {
                String[] listWord = wordsOutput.execute(url).get();

                textvWord.setText(listWord[0]);
                textvDefinition.setText(listWord[1]);

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
            }
            }
        });

        bAddWord.setOnClickListener(new GetWordOnClickListener() {
            @Override
            public void onClick(View v) {
                if(textvWord.getText() != "" && textvWord.getText() != "Sorry!") {
                    mydb.insertData((String) textvWord.getText(), (String) textvDefinition.getText());

                    Toast.makeText(v.getContext(), "Word added to the dictionary", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(v.getContext(), "Impossible to add the word", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}