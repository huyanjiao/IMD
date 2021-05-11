package com.example.getwebpagesourcecode;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class PageLoader extends AsyncTaskLoader<String>{
    private String urlstring;

    PageLoader(Context context, String urlstring) {
        super(context);
        this.urlstring = urlstring;
    }
    public PageLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getPageInfo(urlstring);
    }
}