package com.xxskb.gtx.provider;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xxskb.gtx.db.TrainDatabaseHelper;

/**
 * Created by renyufei on 15-10-12.
 */
public class TrainProvider extends ContentProvider {
    public static final String AUTHORITY = "com.xxskb.gtx.provider.TrainProvider";
    public final static Uri  CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/suggestion");

    private TrainDatabaseHelper trainDatabaseHelper;

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public boolean onCreate() {
        trainDatabaseHelper = new TrainDatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String query = uri.getLastPathSegment();
        Cursor cursor = suggest(query);
        Log.d("suggestion", "----"+query);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private Cursor suggest(String query){
        String[] fields = query.split(" ");
        if(fields.length>1){
            query = fields[1];
        }
        String[] columns = new String[]{
                BaseColumns._ID,
                TrainDatabaseHelper.KEY_PY,
                TrainDatabaseHelper.KEY_STATION,
                SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID
        };

        Cursor cursor = trainDatabaseHelper.suggest(query, columns);

        return cursor;
    }
}
