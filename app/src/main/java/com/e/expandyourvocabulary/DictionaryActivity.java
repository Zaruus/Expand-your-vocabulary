package com.e.expandyourvocabulary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class DictionaryActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        ListView list = (ListView)findViewById(R.id.listvDefinitions);
        final ArrayAdapter<String> tableau = new ArrayAdapter<String>(list.getContext(), R.layout.listtext);

        final MyDatabase1 mydb = new MyDatabase1(this);
        /*mydb.insertData("people", "human beings making up a group or assembly or linked by a common interest");
        mydb.insertData("history", "a chronological record of significant events (such as those affecting a nation or institution) often including an explanation of their causes");
        mydb.insertData("way", "a thoroughfare for travel or transportation from place to place");
        mydb.insertData("art", "skill acquired by experience, study, or observation");
        mydb.insertData("world", "the earthly state of human existence");
        mydb.insertData("information", "knowledge obtained from investigation, study, or instruction");
        mydb.insertData("map", "a representation usually on a flat surface of the whole or a part of an area");
        mydb.insertData("two", "being one more than one in number");
        mydb.insertData("family", "the basic unit in society traditionally consisting of two parents rearing their children");
        mydb.insertData("government", "the body of persons that constitutes the governing authority of a political unit or organization");*/
        List itemWords = mydb.readData();

        String[] itemWordList = null;

        for (int i = 0; i < itemWords.size(); i++) {
            itemWordList = (String[]) itemWords.get(i);

            tableau.add(itemWordList[0] + " : " + itemWordList[1]);
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                final int positionToRemove = position;
                final String result = Objects.requireNonNull(tableau.getItem(positionToRemove)).split(" ")[0];
                AlertDialog.Builder adb=new AlertDialog.Builder(DictionaryActivity.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete the word \""  + result + "\"?");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mydb.deleteRow(result);
                        tableau.notifyDataSetChanged();
                    }});
                adb.show();
            }
        });

        tableau.sort(new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        });

        list.setAdapter(tableau);

    }


}
