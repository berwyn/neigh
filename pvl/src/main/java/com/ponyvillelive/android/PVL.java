package com.ponyvillelive.android;

import dagger.ObjectGraph;
import so.codeweaver.neigh.NeighApp;

/**
 * Created by berwyn on 12 / 7 /14.
 */
public class PVL extends NeighApp {
    public void buildAndInjectObjectGraph() {
        objectGraph = ObjectGraph.create(Modules.list(this));
        objectGraph.inject(this);
    }
}
