package com.ponyvillelive.android;

import android.app.Application;

import com.ponyvillelive.android.module.NetworkModule;

import dagger.ObjectGraph;

/**
 * Created by berwyn on 12 / 7 /14.
 */
public class PVL extends Application {

    private ObjectGraph og;

    @Override
    public void onCreate() {
        super.onCreate();
        og = ObjectGraph.create(new NetworkModule());
    }

    public ObjectGraph getGraph() {
        return og;
    }
}
