package com.xxskb.gtx.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.widget.TextView;

import com.xxskb.gtx.R;
import com.xxskb.gtx.util.QueryParser;

public class MainActivity extends AppCompatActivity {

    private SearchView searchView;
    String[] query = new String[] {"", "", ""};

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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setSubmitButtonEnabled(true);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnSuggestionListener(suggestionListener);

        return true;
    }

    private void handleIntent(Intent intent){
        Log.d("suggestion", intent.getAction());
        if(!Intent.ACTION_SEARCH.equals(intent.getAction())){
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("from", query[0]);
        bundle.putString("to", query[1]);
        Intent newIntent = new Intent(this, TrainActivity.class);
        newIntent.putExtras(bundle);
        startActivity(newIntent);
    }

    private SearchView.OnSuggestionListener suggestionListener = new SearchView.OnSuggestionListener(){
        @Override
        public boolean onSuggestionSelect(int position) {
            return true;
        }

        @Override
        public boolean onSuggestionClick(int position) {
//            Log.d("suggestion", String.valueOf(position));
            Cursor item = (Cursor) searchView.getSuggestionsAdapter().getItem(position);
            String station = item.getString(item.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_2));
            if(query[0].equals("")) {
                query[0] = station;
                searchView.setQuery(station, false);
            }else if (query[1].equals("")) {
                query[1] = station;
                searchView.setQuery(query[0] + " " + station, false);
            }

            return true;
        }
    };
}
