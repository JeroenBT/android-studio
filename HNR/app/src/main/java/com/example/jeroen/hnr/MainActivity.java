package com.example.jeroen.hnr;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final String URL_IDS = "https://hacker-news.firebaseio.com/v0/topstories.json";
    final String URL_ART_START = "https://hacker-news.firebaseio.com/v0/item/";
    final String URL_ART_END = ".json";

    ListView listView;
    TextView voortgangTextView;
    ArrayAdapter<String> adapter;
    ArrayList<String> titelLijst;
    ArrayList<String> urlLijst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        voortgangTextView = (TextView) findViewById(R.id.voortgangTextView);
        titelLijst = new ArrayList<>();
        urlLijst = new ArrayList<>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titelLijst);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlLijst.get(position)));
                startActivity(browserIntent);
            }
        });

        new DownloadTaak().execute();
    }

    class DownloadTaak extends AsyncTask<Void, String, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            String resArray = "";
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;

            try {
                URL urlIds = new URL(URL_IDS);
                connection = (HttpURLConnection) urlIds.openConnection();
                inputStream = connection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();
                while (data != -1) {
                    char c = (char) data;
                    resArray += c;
                    data = inputStreamReader.read();
                }

                JSONArray jsonArray = new JSONArray(resArray);
                int index = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    String id = jsonArray.getString(i);
                    URL urlArtikel = new URL(URL_ART_START + id + URL_ART_END);
                    connection = (HttpURLConnection) urlArtikel.openConnection();
                    inputStream = connection.getInputStream();
                    inputStreamReader = new InputStreamReader(inputStream);
                    String jsonObjectString = "";
                    data = inputStreamReader.read();
                    while (data != -1) {
                        char c = (char) data;
                        jsonObjectString += c;
                        data = inputStreamReader.read();
                    }
                    JSONObject jsonObject = new JSONObject(jsonObjectString);
                    if (jsonObject.has("title") && jsonObject.has("url")) {
                        titelLijst.add(jsonObject.getString("title"));
                        urlLijst.add(jsonObject.getString("url"));
                        publishProgress("Downloaded: " + index + " articles");
                        index++;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            String update = values[0];
            voortgangTextView.setText(update);
            voortgangTextView.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
            voortgangTextView.setVisibility(View.GONE);
        }
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
