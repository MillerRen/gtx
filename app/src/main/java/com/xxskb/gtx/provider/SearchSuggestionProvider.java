package com.xxskb.gtx.provider;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xxskb.gtx.db.SuggestionDataBase;

/**
 * Created by renyufei on 15-10-12.
 */
public class SearchSuggestionProvider extends ContentProvider {
    public static final String AUTHORITY = "com.xxskb.gtx.provider.SearchSuggestionProvider";
    public final static Uri  CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/search");

    private SuggestionDataBase mSuggestionDataBase;

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public boolean onCreate() {
        mSuggestionDataBase = new SuggestionDataBase(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String query = uri.getLastPathSegment();
        String[] columns = new String[]{
                BaseColumns._ID,
                SuggestionDataBase.KEY_WORD,
                SuggestionDataBase.KEY_DEFINTION,
                SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID
        };
        Cursor cursor = mSuggestionDataBase.match(query, columns);
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
}
