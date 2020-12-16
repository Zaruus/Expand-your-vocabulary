package com.e.expandyourvocabulary;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.loopj.android.http.AsyncHttpClient.log;

public class AsyncWordsDefinitionData extends AsyncTask<String, Void, String[]> {

    protected void onPreExecute() {

    }

    @Override
    protected String[] doInBackground(String... strings) {
        URL url;

        try {
            url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.addRequestProperty("x-rapidapi-key","6ff517a20cmshb30fcbba2825dedp1d85f6jsne612d4f148df");
            urlConnection.addRequestProperty("x-rapidapi-host","wordsapiv1.p.rapidapi.com");

            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                String s = readStream(in);

                log.i("Quentin", s);

                JSONObject json = new JSONObject(s);
                JSONObject json2 = json.getJSONArray("results").getJSONObject(0);

                String word = json.getString("word");
                String definition = json2.getString("definition");

                String[] listWord = {word, definition};

                return listWord;

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                String[] error = new String[]{"Sorry!", "definition not found"};
                return error;
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgressUpdate() {

    }

    protected void onPostExecute(String[] listWord) {
        log.i("Quentin", listWord[0] + " : " + listWord[1]);
    }

    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }

}

