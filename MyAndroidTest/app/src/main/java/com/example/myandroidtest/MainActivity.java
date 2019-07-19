package com.example.myandroidtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvKeyword;
    private MyRecyclerViewAdapter mAdapter;
    private ArrayList<String> mKeyword;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvKeyword = findViewById(R.id.rv_keyword);
        //Create keyword data
        mKeyword = new ArrayList<String>();
        createKeyword();
        mAdapter = new MyRecyclerViewAdapter(this, mKeyword);
        rvKeyword.setAdapter(mAdapter);
        rvKeyword.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    /**
     * Generate keywords for items from local json file
     */
    private void createKeyword(){
        try {
            JSONArray m_jArr = new JSONArray(loadJSONFromAsset());
            for (int i = 0; i <= m_jArr.length(); i++){
                mKeyword.add(m_jArr.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load JSON object from Assets folder and convert to String
     * @return
     */
    private String loadJSONFromAsset(){
        String json = null;
        try {
            InputStream is = getAssets().open("keywords.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
