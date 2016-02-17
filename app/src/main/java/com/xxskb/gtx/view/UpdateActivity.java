package com.xxskb.gtx.view;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position){
            case 0:
                updateStation();
                break;
            case 1:
                updateTrain();
                break;
            case 2:
                updateTrain();
                break;
            case 3:
                updatePrice();
                break;
        }
    }

    private List<String> getData() {
        List<String> list = new ArrayList<String>();
        String[] update_text = getResources().getStringArray(R.array.update_text);
        list.addAll(Arrays.asList(update_text));

        return list;
    }

    private void updateStation(){

    }

    private void updateTrain(){

    }

    private void updateSchedule(){

    }

    private void updatePrice(){

    }

}
