package com.ponyvillelive.android.module;

import com.ponyvillelive.android.net.API;
import com.ponyvillelive.android.ui.StationAdapter;
import com.ponyvillelive.android.ui.TVBrowseFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by berwyn on 12 / 7 /14.
 */
@Module(
        injects = {
                StationAdapter.class,
                TVBrowseFragment.class
        }
)
public class NetworkModule {

    private final API api;

    public NetworkModule() {
        this.api = new API.Builder()
                    .build();
    }

    @Provides @Singleton
    public API provideAPI() {
        return api;
    }

}
