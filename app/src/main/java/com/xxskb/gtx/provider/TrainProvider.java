package com.xxskb.gtx.provider;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import com.xxskb.gtx.db.TrainDatabase;

/**
 * Created by renyufei on 15-10-12.
 */
public class TrainProvider extends ContentProvider {
    public static final String AUTHORITY = "com.xxskb.gtx.provider.TrainProvider";
    public final static Uri  CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/search");

    private TrainDatabase trainDatabase;

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public boolean onCreate() {
        trainDatabase = new TrainDatabase(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String query = uri.getLastPathSegment();
        Cursor cursor = suggest(query);
        //Log.d("query", query);
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
        String[] columns = new String[]{
                BaseColumns._ID,
                TrainDatabase.KEY_PY,
                TrainDatabase.KEY_STATION,
                SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID
        };

        Cursor cursor = trainDatabase.suggest(query, columns);

        return cursor;
    }
}
