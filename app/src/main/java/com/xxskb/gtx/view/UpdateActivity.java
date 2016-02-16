package com.xxskb.gtx.view;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.xxskb.gtx.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getData()));
    }

    private List<String> getData() {
        List<String> list = new ArrayList<String>();
        String[] update_text = getResources().getStringArray(R.array.update_text);
        list.addAll(Arrays.asList(update_text));

        return list;
    }

}
