package com.xxskb.gtx.util;

import android.text.TextUtils;

/**
 * Created by renyufei on 15-10-20.
 */
public class QueryParser {
    private static final int QUERY_TYPE_UNKNOWN = 0;
    private static final int QUERY_TYPE_TRAIN = 1;
    private static final int QUERY_TYPE_STATION = 2;
    private static final int QUERY_TYPE_LINE = 3;

    public static int getQueryType(String query){
        String[] fields = query.split(" ");
        return fields.length;
    }

    public static String changeQuery(String station, String query){
        String[] fields = query.split(" ");
        String the_query=query;
        int type = QueryParser.getQueryType(query);
        if(type==1){
            the_query = station;
        }else {
            fields[1]=station;
            the_query = TextUtils.join(" ", fields);
        }

        return the_query;
    }
}
