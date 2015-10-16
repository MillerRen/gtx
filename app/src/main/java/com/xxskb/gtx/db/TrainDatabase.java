package com.xxskb.gtx.db;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;
import android.util.Log;

import com.xxskb.gtx.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by renyufei on 15-10-13.
 */
public class TrainDatabase {

    private final static String TAG = "suggestiondatabase";

    public final static String KEY_PY = SearchManager.SUGGEST_COLUMN_TEXT_1;
    public final static String KEY_STATION = SearchManager.SUGGEST_COLUMN_TEXT_2;

    private final static String DATABASE_NAME = "skb";
    private final static  int DATABASE_VERSION = 1;
    private final static String SUGGESTION_TABLE = "suggestion";

    private final SuggestionDatabaseHelper mDatabaseHelper;
    private final HashMap<String, String> mColumnMap = buildColumnMap();

    public TrainDatabase(Context context){
        this.mDatabaseHelper = new SuggestionDatabaseHelper(context);
    }

    private HashMap<String, String> buildColumnMap(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(KEY_PY, KEY_PY);
        map.put(KEY_STATION, KEY_STATION);
        map.put(BaseColumns._ID, "rowid as " + BaseColumns._ID);
        map.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID, "rowid as " + SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID);
        map.put(SearchManager.SUGGEST_COLUMN_SHORTCUT_ID, "rowid as "+SearchManager.SUGGEST_COLUMN_SHORTCUT_ID);

        return map;
    }

    public Cursor suggest(String query, String[] columns){
        String selection = SUGGESTION_TABLE + " MATCH ?";
        String[] selectArgs = new String[]{query+"*"};
        return query(selection, selectArgs, columns);
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
                " USING fts3 (" +
                        KEY_PY + "," +
                        KEY_STATION +
                        ");";

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

        private void loadSuggestion(){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        loadStations();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        private void loadStations() throws IOException{
            final Resources resources = mHelperContext.getResources();
            InputStream inputStream = resources.openRawResource(R.raw.station_name);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                String line;
                mDatabase.beginTransaction();
                while ((line = bufferedReader.readLine())!=null){
                    String[] fields = line.split("@");
                    for (int i = 1; i < fields.length-1; i++) {
                        String row = fields[i];
                        //Log.d(TAG, row);
                        String[] columns = row.split("\\|");
                        //Log.d(TAG, columns[0]+columns[1]+columns[2]);
                        Long id = addData(columns[0], columns[1]);
                        if(id<0){
                            Log.d(TAG, "unable to insert station");
                        }
                    }
                    mDatabase.setTransactionSuccessful();
                }
            } finally {
                mDatabase.endTransaction();
                bufferedReader.close();
            }

        }

        private Long addData(String word, String definition){
            //Log.d(TAG,word+definition);
            ContentValues cv = new ContentValues();
            cv.put(KEY_PY, word);
            cv.put(KEY_STATION, definition);
            return mDatabase.insert(SUGGESTION_TABLE, null, cv);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS" + SUGGESTION_TABLE);
            onCreate(db);
        }
    }

}
