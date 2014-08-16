package com.ponyvillelive.android.module;

import com.ponyvillelive.android.net.API;
import com.ponyvillelive.android.ui.StationFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by berwyn on 12 / 7 /14.
 */
@Module(
        injects = {
                StationFragment.class
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
