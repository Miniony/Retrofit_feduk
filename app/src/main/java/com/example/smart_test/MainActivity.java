package com.example.smart_test;


import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    //simple list
    ArrayAdapter<String> adapter;
    FloatingActionButton mAddPersonFab;

    //custom list
    ArrayList<MyItem>arrayListCustom;
    CustomAdapter adapterCustom;
    ListView customListView;

    Button buttonRemove;
    int positionRemove = -1;
    int counter = 0;
    int[] images = {android.R.drawable.ic_lock_silent_mode, android.R.drawable.ic_dialog_email, android.R.drawable.ic_dialog_alert, android.R.drawable.ic_delete};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAddPersonFab = findViewById(R.id.add_person_fab);
        mAddPersonFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Person added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), QRcode.class));
            }
        });

        //Simple List
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());

        //Custom List
        customListView = findViewById(R.id.listCustom);
        arrayListCustom = new ArrayList<MyItem>();
        adapterCustom = new CustomAdapter(this, R.layout.custom_item_list, arrayListCustom);
        customListView.setAdapter(adapterCustom);



        customListView.setOnItemClickListener(new MyItemClicked());

        customListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                customListView.setItemChecked(position, true);
                buttonRemove.setEnabled(true);
                return true;
            }
        });

        //add item function
        Button buttonAdd = (Button) findViewById(R.id.buttonAddItem);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                int position = counter%images.length;
                MyItem newItem = new MyItem(images[position], "custom item " + counter);

                //add directly in the adapter
                adapter.add("simple item " + counter);
                adapter.notifyDataSetChanged(); //refresh the view

                //or add in the array attached in the arrayList
                arrayListCustom.add(newItem);
                adapterCustom.notifyDataSetChanged();
            }
        });

        //remove item function
        buttonRemove = (Button) findViewById(R.id.buttonRemoveItem);
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //custom list
                if(customListView.getCheckedItemCount() > 0){
                    SparseBooleanArray selected = customListView.getCheckedItemPositions();
                    for(int i=0; i<selected.size(); i++){
                        arrayListCustom.remove(selected.keyAt(i));
                    }
                    adapterCustom.notifyDataSetChanged();
                }
                buttonRemove.setEnabled(false);
            }
        });

    }


    class MyItemClicked implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), arrayListCustom.get(position).text, Toast.LENGTH_SHORT).show();
        }
    }
}
