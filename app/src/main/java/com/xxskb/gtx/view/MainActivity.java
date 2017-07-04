package com.xxskb.gtx.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.widget.TextView;

import com.xxskb.gtx.R;

public class MainActivity extends AppCompatActivity {

    private String from_code="";
    private String to_code="";

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
        //Log.d("suggestion", intent.getAction());
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            if(from_code!=""&&to_code!="") {
                Bundle bundle = new Bundle();
                bundle.putString("from", from_code);
                bundle.putString("to", to_code);
                Intent newIntent = new Intent(this, TrainActivity.class);
                newIntent.putExtras(bundle);
                startActivity(newIntent);
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setSubmitButtonEnabled(true);
        searchView.setMaxWidth(Integer.MAX_VALUE);

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
                startActivity(new Intent(this, UpdateActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleIntent(Intent intent){
        //Log.d("suggestion", intent.getAction());
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            if(from_code!=""&&to_code!="") {
                Bundle bundle = new Bundle();
                bundle.putString("from", from_code);
                bundle.putString("to", to_code);
                Intent newIntent = new Intent(this, TrainActivity.class);
                newIntent.putExtras(bundle);
                startActivity(newIntent);
            }
        }
    }

}
