package com.chromsicle.memorableplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    //listview, the 0th element will be the "add a new one" text and will take you to your current location on the map
    //if you longhold on the map, it adds a marker and the address name to the listview
    //if the address isn't available, save some other name/info to the listview

    //create the array lists for the map markers and the ListView
    static ArrayList<String> memorableLocations = new ArrayList<>();
    static ArrayList<LatLng> listLocations = new ArrayList<>();

    static ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get access to the SharedPreferences
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.chromsicle.memorableplaces", Context.MODE_PRIVATE);
        //make the arraylists for the lat and long
        ArrayList<String> latitudes = new ArrayList<>();
        ArrayList<String> longitudes = new ArrayList<>();

        //make sure you have fresh ArrayLists
        memorableLocations.clear();
        latitudes.clear();
        longitudes.clear();
        listLocations.clear();

        //pull this out of SharedPreferences
        try {
            //get the places/memorableLocations, latitudes, and longitudes from SharedPreferences
            memorableLocations = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places", ObjectSerializer.serialize(new ArrayList<String>())));
            latitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lat", ObjectSerializer.serialize(new ArrayList<String>())));
            longitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("long", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //check that latitudes and longitudes can go into listLocations
        if(memorableLocations.size() > 0 && latitudes.size() > 0 && longitudes.size() > 0) {
            //check that latitudes and longitudes are the same size as memorableLocations
            if(memorableLocations.size() == latitudes.size() && memorableLocations.size() == longitudes.size()) {
                //reconstruct: take the latitudes and longitudes and merge them into the arraylist for listLocations
                for(int i=0; i < latitudes.size(); i++) {
                    listLocations.add(new LatLng(Double.parseDouble(latitudes.get(i)), Double.parseDouble(longitudes.get(i))));
                }
            }
        } else {
            //if the lists aren't the same size,
            // it's probably the first time the app has been opened so add new stuff
            memorableLocations.add("Add a new location");
            listLocations.add(new LatLng(0,0));
        }



        //find the view
        ListView locationsList  = findViewById(R.id.locationsListView);

        //create an ArrayAdapter, connect it to the Listview
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, memorableLocations);
        locationsList.setAdapter(arrayAdapter);

        //set the onClick listener for when list items are clicked
        locationsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this, Integer.toString(i), Toast.LENGTH_SHORT).show();
                //use an intent to move over to the next activity
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                //use an extra let the next activity know which list item was selected
                intent.putExtra("placeNumber", i);

                //now go to the other activity to handle receiving this info!!

                startActivity(intent);
            }
        });


    }
}
