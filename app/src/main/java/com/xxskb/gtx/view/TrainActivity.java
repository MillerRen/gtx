package com.xxskb.gtx.view;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.xxskb.gtx.R;

/**
 * Created by renyufei on 15-10-13.
 */
public class TrainActivity extends ListActivity {
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.line);
        fetchData();
    }

    private void fetchData(){
        Intent intent = getIntent();
        String query = String.valueOf(intent.getStringExtra("query"));
        Log.d("query", query);
    }
}
