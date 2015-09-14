package com.tidal.quake;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView lv;
    List<String> title_list = new ArrayList<String>();
    List<Properties> propertiesList = new ArrayList<Properties>();
    List<Geometry> geometryList = new ArrayList<Geometry>();
    ArrayAdapter<String> arrayAdapter;
    private static final String URL  = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getTitles(false);                                    // Get from local storage or fetch from API

        lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                title_list );

        this.arrayAdapter = arrayAdapter;

        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                List<Float> coords = geometryList.get(position).getCoordinates();

                Float longitude = coords.get(0);
                Float latitude  = coords.get(1);
                Float depth     = coords.get(2);

                String title = propertiesList.get(position).getTitle();
                Log.i(TAG, "## based on object WAS CLICKED: " + title);
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                Bundle b = new Bundle();

                //Pass along values
                b.putFloat("longitude", longitude);
                b.putFloat("latitude", latitude);
                b.putFloat("depth", depth);
                b.putString("title", title);

                intent.putExtras(b);
                startActivity(intent);
                finish();

            }
        });

    }

     public void getTitles(final Boolean showTsunami) {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(URL)
                .build();


        UsgsApi usgs_service = restAdapter.create(UsgsApi.class);
        String listType = "4.5_day.geojson";
        usgs_service.getProperties(listType, new Callback<QuakeModel>() {
            @Override
            public void success(QuakeModel quakeModel, Response response) {
                // Start with empty lists
                geometryList.clear();
                propertiesList.clear();
                title_list.clear();

                List<Feature> features = quakeModel.getFeatures();
                Collections.sort(features, new FeatureComparator());

                // Calculate list length
                int listLength = 0;
                if ( features.size() > 20 ) {
                    listLength = 19;
                } else {
                    listLength = features.size();
                }

                for (int i = 0; i < listLength; i ++) {

                    Properties properties   = features.get(i).getProperties();
                    Geometry geometry       = features.get(i).getGeometry();
                    if (showTsunami && properties.getTsunami() == 1) {
                        Log.i(TAG, "###### Adding to list with tsunami");
                        geometryList.add(geometry);
                        propertiesList.add(properties);
                        title_list.add(features.get(i).getProperties().getTitle());
                    }
                    if (showTsunami == false ) {
                        Log.i(TAG, "###### Adding to list without tsunami");
                        geometryList.add(geometry);
                        propertiesList.add(properties);
                        title_list.add(features.get(i).getProperties().getTitle());
                    }
                }

                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "API called failed " + error.getMessage());
                Toast.makeText(getApplicationContext(), "No response from API, using old data", Toast.LENGTH_LONG).show();

            }
        });

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

        switch (item.getItemId()) {
            case R.id.show_tsunami:
                getTitles(true);
                return true;
            case R.id.show_regular:
                getTitles(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


