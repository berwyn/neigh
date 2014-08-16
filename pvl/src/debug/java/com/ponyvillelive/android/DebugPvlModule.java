package com.ponyvillelive.android;

import dagger.Module;
import so.codeweaver.neigh.DebugNeighModule;

/**
 * Created by berwyn on 16/08/14.
 */
@Module(
        addsTo = DebugNeighModule.class,
        overrides = true,
        complete = false,
        library = true
)
public class DebugPvlModule {
}
