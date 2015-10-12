package com.xxskb.gtx.provider;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by renyufei on 15-10-12.
 */
public class SearchSuggestionProvider extends SearchRecentSuggestionsProvider {
    public static final String AUTHORITY = "com.xxskb.gtx.provider.SearchSuggestionProvider";
    public  final static int MODE = DATABASE_MODE_QUERIES;

    public SearchSuggestionProvider(){
        setupSuggestions(AUTHORITY, MODE);
    }
}
