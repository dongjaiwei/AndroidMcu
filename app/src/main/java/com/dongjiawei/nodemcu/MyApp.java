package com.dongjiawei.nodemcu;

import android.app.Application;

import com.hw.ycshareelement.YcShareElement;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        YcShareElement.enableContentTransition(this);
    }
}
