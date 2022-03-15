package com.example.smart_test;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONArrayDemo extends AppCompatActivity {
    //Initialize variable
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonarray_demo);

        //Assign variable
        listView = findViewById(R.id._list_view);

        String students_array = "{\"students\": [\n" +
                "   {\"id\":\"1\",\name\":\"abcd\",\"email\":abcd@gmail.com\"}\n" +
                "   {\"id\":\"2\",\name\":\"efgh\",\"email\":efgh@gmail.com\"}\n" +
                "   {\"id\":\"3\",\name\":\"ijkl\",\"email\":ijkl@gmail.com\"}\n" +
                "}";

        //Fetch JSON array
            try {
                JSONObject jsonObject = new JSONObject(students_array);
                JSONArray jsonArray = jsonObject.getJSONArray("students");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String studentID = object.getString("id");
                    String studentName = object.getString("name");
                    String studentEmail = object.getString("email");

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Initialize Array Adapter
            arrayAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, arrayList);
            //Set Array Adapter to ListView
            listView.setAdapter(arrayAdapter);

            //Displayed toast message OnItemClack
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Toast.makeText(getApplicationContext()
                            , arrayList.get(position), Toast.LENGTH_SHORT).show();
                }
            });



    }
}