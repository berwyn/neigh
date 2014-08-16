package com.ponyvillelive.android;

import android.app.Application;

import com.ponyvillelive.android.net.NetworkModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import so.codeweaver.neigh.NeighModule;

/**
 * Created by berwyn on 16/08/14.
 */
@Module(
        addsTo = NeighModule.class,
        includes = {
                NetworkModule.class
        },
        complete = false,
        library = true,
        overrides = true
)
public class PvlModule {
    private PVL app;

    public PvlModule(PVL app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return app;
    }
}
