package com.example.myandroidtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

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
        String correctKeyword = null;
        try {
            JSONArray m_jArr = new JSONArray(loadJSONFromAsset());
            for (int i = 0; i <= m_jArr.length(); i++){
                correctKeyword = processKeyword(m_jArr.getString(i));
                mKeyword.add(correctKeyword);
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

    /**
     * Separate string into 2 sub strings (if need)
     * Require: Difference between length of 2 sub strings is minimum
     * Add character new line into string "\n" to separate
     */
    private String processKeyword(String inputKeyword){
        int len = inputKeyword.length(),
            lenSub1 = 0,
            lenSub2 = 0,
            posLine = 0,
            minDiff = len;
        //Count words from String
        String[] words = inputKeyword.split(" ");
        if(words.length == 1)
            return inputKeyword;
        else if(words.length == 2)
            return inputKeyword.replace(" ", "\n");
        else{
            for (String word : words){
                Log.d(TAG + "word", word);
                lenSub1 = lenSub1 + word.length();
                lenSub2 = len - lenSub1 - 1; //Remove space between 2 sub strings
                if(Math.abs(lenSub1 - lenSub2) <= minDiff){
                    minDiff = Math.abs(lenSub1 - lenSub2);
                    //Add amount of space between words
                    //Get position to replace character new line '\n'
                    posLine = lenSub1++;
                }
                else
                    return replaceCharAtIndex(inputKeyword, posLine, '\n');
            }
            return inputKeyword;
        }

    }

    /**
     * Replace character at specific index in String
     * @param oldString
     * @param pos
     * @param c
     * @return
     */
    private String replaceCharAtIndex(String oldString, int pos, char c){
        String newString = oldString.substring(0, pos) + c + oldString.substring(pos+1);
        return  newString;
    }
}
