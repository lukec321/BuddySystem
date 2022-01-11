package com.example.buddysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private RequestQueue requestqueue;
    private TextView osebe;
    private String url = "https://buddy-system.azurewebsites.net/Posts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestqueue = Volley.newRequestQueue(getApplicationContext());
        osebe = (TextView) findViewById(R.id.osebe);
    }


    public void prikaziPoste(View view){
        if(view != null){
            JsonArrayRequest request = new JsonArrayRequest(url, jsonArrayListener, errorListener);
            requestqueue.add(request);
        }
    }
private Response.Listener<JSONArray> jsonArrayListener = new Response.Listener<JSONArray>() {
        @Override
    public void onResponse(JSONArray response){
            ArrayList<String> data = new ArrayList<>();

            for(int i = 0; i< response.length(); i++){
                try{
                    JSONObject object = response.getJSONObject(i);
                    String userid = object.getString("UserID");
                    String name = object.getString("content");
                    String time = object.getString("PostTime");
                    data.add(name +" "+ time + " " + userid);


                }catch (JSONException e){
                    e.printStackTrace();
                    return;
                }
            }

            osebe.setText("");

            for(String row: data){
                String currentText=osebe.getText().toString();
                osebe.setText(currentText + "\n\n"+row);
            }
        }
};

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("REST error", error.getMessage());

        }
    };

}