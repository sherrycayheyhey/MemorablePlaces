package com.chromsicle.memorableplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    //listview, the 0th element will be the "add a new one" text and will take you to your current location on the map
    //if you longhold on the map, it adds a marker and the address name to the listview
    //if the address isn't available, save some other name/info to the listview






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find the view, create the ArrayList, add the first list item
        ListView locationsList  = findViewById(R.id.locationsListView);
        ArrayList<String> memorableLocations = new ArrayList<>();
        memorableLocations.add("Add a new location");

        //create an ArrayAdapter, connect it to the Listview
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, memorableLocations);
        locationsList.setAdapter(arrayAdapter);

        //set the onClick listener for when list items are clicked
        locationsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this, Integer.toString(i), Toast.LENGTH_SHORT).show();
                //use an intent to move over to the next activity
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                //use an extra let the next activity know which list item was selected
                intent.putExtra("myInfo", i);

                //now go to the other activity to handle receiving this info!!

                startActivity(intent);
            }
        });


    }
}
