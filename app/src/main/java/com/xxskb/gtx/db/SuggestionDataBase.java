package com.xxskb.gtx.db;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;

import java.util.HashMap;

/**
 * Created by renyufei on 15-10-13.
 */
public class SuggestionDataBase {

    private final static String KEY_WORD = SearchManager.SUGGEST_COLUMN_TEXT_1;
    private final static String KEY_DEFINTION = SearchManager.SUGGEST_COLUMN_TEXT_2;

    private final static String DATABASE_NAME = "skb";
    private final static  int DATABASE_VERSION = 1;
    private final static String SUGGESTION_TABLE = "suggestion";

    private final SuggestionDatabaseHelper mDatabaseHelper;
    private final HashMap<String, String> mColumnMap = buildColumnMap();

    public SuggestionDataBase(Context context){
        this.mDatabaseHelper = new SuggestionDatabaseHelper(context);
    }

    private HashMap<String, String> buildColumnMap(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(KEY_WORD, KEY_WORD);
        map.put(KEY_DEFINTION, KEY_DEFINTION);
        map.put(BaseColumns._ID, "rowid as " + BaseColumns._ID);
        map.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID, "rowid as " + SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID);
        map.put(SearchManager.SUGGEST_COLUMN_SHORTCUT_ID, "rowid as "+SearchManager.SUGGEST_COLUMN_SHORTCUT_ID);

        return map;
    }

    public Cursor match(String query, String[] columns){
        return query();
    }

    private Cursor query(String selection, String[] selectArgs, String[] columns){
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(SUGGESTION_TABLE);
        builder.setProjectionMap(mColumnMap);

        Cursor cursor = builder.query(mDatabaseHelper.getReadableDatabase(), columns, selection, selectArgs, null, null, null);

        if(null == cursor){
            return null;
        }
        else if(!cursor.moveToFirst()){
            cursor.close();
            return null;
        }

        return cursor;
    }

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
            loadSuggestion();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS" + SUGGESTION_TABLE);
            onCreate(db);
        }
    }

}
