package com.example.jeroen.valutacurrency2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    ListView listView;
    TextView datumTextView;

    final String[] KEYS = {"EUR", "USD", "GBP", "JPY", "AUD"};
    final String BASE_URL = "http://api.fixer.io/latest?base=";

    ArrayList<String> currencies;
    ArrayList<Double> rates;
    int currencySelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);
        listView = (ListView) findViewById(R.id.listView);
        datumTextView = (TextView) findViewById(R.id.datumTextView);
        currencies = new ArrayList<>();
        rates = new ArrayList<>();
        String packageName = getPackageName();
        for (String key: KEYS) {
            int resId = getResources().getIdentifier(key, "string", packageName);
            String s = getString(resId);
            currencies.add(s);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        currencySelected = 0;

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currencySelected = position;
                rates.clear();
                maakListView();
                String url = BASE_URL + KEYS[currencySelected];
                new DownloadTaak().execute(url);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        maakListView();
        String url = BASE_URL + KEYS[currencySelected];
        new DownloadTaak().execute(url);
    }

    private void maakListView() {
        ArrayList<String> listItems = new ArrayList<>();
        for (int i = 0; i < currencies.size(); i++) {
            if (i != currencySelected) {
                String item = currencies.get(i) + ": ";
                if (rates.isEmpty()) {
                    item += getString(R.string.msg);
                } else {
                    item += rates.get(i);
                }
                listItems.add(item);
            }
        }
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(listViewAdapter);

    }

    class DownloadTaak extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String urlString = params[0];
            String jsonString = "";
            HttpURLConnection connection = null;
            InputStream in = null;
            InputStreamReader reader = null;

            try {
                URL url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                in = connection.getInputStream();
                reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char letter = (char) data;
                    jsonString += letter;
                    data = reader.read();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return jsonString;
        }

        @Override
        protected void onPostExecute(String jsonString) {
            super.onPostExecute(jsonString);

            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                String datum = jsonObject.getString("date");
                datumTextView.setText(datum);
                JSONObject rtes = jsonObject.getJSONObject("rates");
                rates.clear();
                for (String key: KEYS) {
                    Double d;
                    try {
                        d = rtes.getDouble(key);
                    } catch (JSONException e) {
                        d = 0.0;
                    }
                    rates.add(d);
                }

                maakListView();

            } catch (JSONException e) {
                e.printStackTrace();
            }

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
