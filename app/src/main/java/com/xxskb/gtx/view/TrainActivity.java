package com.xxskb.gtx.view;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;

import com.xxskb.gtx.R;

/**
 * Created by renyufei on 15-10-13.
 */
public class TrainActivity extends ListActivity {
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.line);
    }
}
