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
        String select = TrainDatabaseHelper.KEY_STATION + " MATCH ? ";
        String query = String.valueOf(bundle.getString("query"));//Log.d("query",getIntent().getDataString());
        String[] selectArgs = new String[]{query};
        Uri uri = Uri.withAppendedPath(TrainProvider.STATION_URI, query);
        return new CursorLoader(this, uri, new String[]{TrainDatabaseHelper.KEY_STATION}, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor o) {
        //Log.d("cursor", o.getString(o.getColumnIndex(TrainDatabaseHelper.KEY_STATION)));
        mAdapter.swapCursor(o);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
