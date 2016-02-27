package com.xxskb.gtx.view;

import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

import com.xxskb.gtx.R;
import com.xxskb.gtx.db.TrainDatabaseHelper;
import com.xxskb.gtx.provider.TrainProvider;

/**
 * Created by renyufei on 15-10-13.
 */
public class TrainActivity extends ListActivity implements LoaderManager.LoaderCallbacks{
    private SimpleCursorAdapter mAdapter;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.line);
//        mAdapter = new SimpleCursorAdapter(this,
//                android.R.layout.simple_list_item_1,
//                null,
//                new String[]{TrainDatabaseHelper.KEY_STATION},
//                new String[]{android.R.id.text1});
        setListAdapter(mAdapter);
        fetchData();
    }

    private void fetchData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        getLoaderManager().initLoader(0, bundle, this);
        //Log.d("query", query);
    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        String select = TrainDatabaseHelper.KEY_STATION + " MATCH ? ";
        String query = String.valueOf(bundle.getString("query"));
        String[] selectArgs = new String[]{query};
        return new CursorLoader(this, TrainProvider.CONTENT_URI, null, select, selectArgs, null);
    }

    @Override
    public void onLoadFinished(Loader loader, Object o) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
