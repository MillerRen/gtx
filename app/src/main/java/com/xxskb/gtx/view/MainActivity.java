package com.xxskb.gtx.view;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.xxskb.gtx.R;
import com.xxskb.gtx.util.QueryParser;

public class MainActivity extends Activity {

    private String from_code;
    private String to_code;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handleIntent(getIntent());

        String[] array = getResources().getStringArray(R.array.help);
        final TextView help_text = (TextView) findViewById(R.id.help);
        help_text.setText(TextUtils.join("\n\n", array));

    }

    @Override
    protected void  onNewIntent(Intent intent){
        super.onNewIntent(intent);
        handleIntent(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        setUpSearchView();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_update:
                gotoUpdate();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void gotoUpdate(){
        Intent intent = new Intent(this, UpdateActivity.class);
        startActivity(intent);
    }

    private void handleIntent(Intent intent){
        //Log.d("suggestion", intent.getAction());
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);

//            Intent newIntent = new Intent(this, TrainActivity.class);
//            newIntent.setData(intent.getData());
//            startActivity(newIntent);
        }else if(Intent.ACTION_VIEW.equals(intent.getAction())){
            String from = intent.getStringExtra(SearchManager.EXTRA_DATA_KEY);
            Log.d("suggestion", from);
        }
    }





    private void setUpSearchView(){
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnSuggestionListener(suggestionListener);

        int magId = getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView magImage = (ImageView) searchView.findViewById(magId);
        magImage.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
    }

    private SearchView.OnSuggestionListener suggestionListener = new SearchView.OnSuggestionListener(){
        @Override
        public boolean onSuggestionSelect(int position) {
            return true;
        }

        @Override
        public boolean onSuggestionClick(int position) {
            //Log.d("suggestion", String.valueOf(position));
            Cursor item = (Cursor) searchView.getSuggestionsAdapter().getItem(position);
            String station = item.getString(item.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_2));
            searchView.setQuery(QueryParser.changeQuery(station, searchView.getQuery().toString()), false);

            return true;
        }
    };

}
