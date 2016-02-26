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
public class TrainDatabaseHelper extends SQLiteOpenHelper {

    private final static String TAG = "suggestiondatabase";

    public final static String KEY_PY = SearchManager.SUGGEST_COLUMN_TEXT_1;
    public final static String KEY_STATION = SearchManager.SUGGEST_COLUMN_TEXT_2;
    public final static String KEY_CODE = SearchManager.SUGGEST_COLUMN_INTENT_EXTRA_DATA;
    public final static String KEY_PINYIN = "station_pinyin";

    private final static String DATABASE_NAME = "skb";
    private final static  int DATABASE_VERSION = 3;
    private final static String SUGGESTION_TABLE = "suggestion";
    private final static String CREATE_SUGGESTION_TABLE =
            "CREATE VIRTUAL TABLE " + SUGGESTION_TABLE +
                    " USING fts3 (" +
                    BaseColumns._ID + " INTERGER PRIMARY KEY AUTOINCREAMENT, " +
                    KEY_PY + "," +
                    KEY_STATION + "," +
                    KEY_CODE + "," +
                    KEY_PINYIN + "," + ");";

    private Context mHelperContext;
    private SQLiteDatabase mDatabase;

    public TrainDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mHelperContext = context;
    }

    public Cursor suggest(String query){
        String[] fields = query.split(" ");
        if(fields.length>1){
            query = fields[1];
        }
        String selection = SUGGESTION_TABLE + " MATCH ?";
        String[] selectArgs = new String[]{query+"*"};
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(SUGGESTION_TABLE);

        Cursor cursor = builder.query(getReadableDatabase(), null, selection, selectArgs, null, null, null, "5");

        return cursor;
    }

    private void initDatabase(){

        new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Resources resources = mHelperContext.getResources();
                       InputStream inputStream = resources.openRawResource(R.raw.station_name);
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String line;
                        mDatabase.beginTransaction();
                        while ((line = bufferedReader.readLine())!=null){
                            String[] fields = line.split("@");
                            for (int i = 1; i < fields.length-1; i++) {
                                String row = fields[i];
                                String[] columns = row.split("\\|");

                                ContentValues cv = new ContentValues();
                                cv.put(KEY_PY, columns[0]);
                                cv.put(KEY_STATION, columns[1]);
                                cv.put(KEY_CODE, columns[2]);
                                cv.put(KEY_PINYIN, columns[3]);
                                cv.put(BaseColumns._ID, columns[5]);

                                mDatabase.insert(SUGGESTION_TABLE, null, cv);
                            }
                            mDatabase.setTransactionSuccessful();
                        }
                        mDatabase.endTransaction();
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        mDatabase = sqLiteDatabase;
        sqLiteDatabase.execSQL(CREATE_SUGGESTION_TABLE);
        initDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SUGGESTION_TABLE);
        onCreate(sqLiteDatabase);
    }

}
