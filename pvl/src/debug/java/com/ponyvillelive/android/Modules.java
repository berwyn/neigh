package com.ponyvillelive.android;

import java.lang.Object;

final class Modules {
    static Object[] list(PVL app) {
        return new Object[] {
                new PvlModule(app),
                new DebugPvlModule()
        };
    }

    private Modules() {

    }
}