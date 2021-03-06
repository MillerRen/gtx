package com.xxskb.gtx.view;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

import com.xxskb.gtx.R;
import com.xxskb.gtx.db.TrainDatabaseHelper;
import com.xxskb.gtx.net.TrainLoader;
import com.xxskb.gtx.provider.TrainProvider;

/**
 * Created by renyufei on 15-10-13.
 */
public class TrainActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private SimpleCursorAdapter mAdapter;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_line);

        mAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                null,
                new String[]{TrainDatabaseHelper.KEY_STATION},
                new int[]{android.R.id.text1}, 0);
        setListAdapter(mAdapter);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        getLoaderManager().initLoader(0, bd, this);
    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        String from = String.valueOf(bundle.getString("from"));
        String to = String.valueOf(bundle.get("to"));
        return new TrainLoader(this, bundle);
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor cursor) {
        int id = loader.getId();

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
