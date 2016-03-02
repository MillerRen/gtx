package com.xxskb.gtx.net;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by renyufei on 16-3-1.
 */
public class TrainLoader extends AsyncTaskLoader<JSONArray> {
    private Bundle query;

    public TrainLoader(Context context, Bundle bundle){
        super(context);
        query = bundle;
    }

    @Override
    public JSONArray loadInBackground() {
        return null;
    }
}
