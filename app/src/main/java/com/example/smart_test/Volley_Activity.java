package com.example.smart_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;

public class Volley_Activity extends AppCompatActivity {
    ListView musiclist;
    final String url = "192.168.137.232";
    List<Album> alist;
    MyAdapter adapter;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        musiclist = findViewById(R.id.musiclistview);
        alist = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);//This will take care of
        //background newtwork activities
        getdata();
        adapter = new MyAdapter(this,R.layout.customcell,alist);
        musiclist.setAdapter(adapter);
    }
    private void getdata()
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String albumname = jsonObject.getString("title");
                        String albumimageurl = jsonObject.getString("image");
                        alist.add(new Album(albumname,albumimageurl));
                    }
                    adapter.notifyDataSetChanged();//To prevent app from crashing when updating
                    //UI through background Thread
                }
                catch (Exception w)
                {
                    Toast.makeText(Volley_Activity.this,w.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Volley_Activity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}
