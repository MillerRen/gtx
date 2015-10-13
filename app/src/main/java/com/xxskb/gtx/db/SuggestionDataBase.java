package com.xxskb.gtx.db;

import android.app.SearchManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by renyufei on 15-10-13.
 */
public class SuggestionDataBase {

    private final static String KEY_WORD = SearchManager.SUGGEST_COLUMN_TEXT_1;
    private final static String KEY_DEFINTION = SearchManager.SUGGEST_COLUMN_TEXT_2;

    private final static String DATABASE_NAME = "skb";
    private final static  int DATABASE_VERSION = 1;
    private final static String SUGGESTION_TABLE = "suggestion";

    private static class SuggestionDatabaseHelper extends SQLiteOpenHelper{

        private SQLiteDatabase mDatabase;
        private final Context mHelperContext;

        private final static String CREATE_SUGGESTION_TABLE =
                "CREATE VIRTUAL TABLE " + SUGGESTION_TABLE +
                "USING fts3 (" +
                KEY_WORD + ","
                +KEY_DEFINTION + ");";

        public SuggestionDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mHelperContext = context;
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            mDatabase = db;
            mDatabase.execSQL(CREATE_SUGGESTION_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
