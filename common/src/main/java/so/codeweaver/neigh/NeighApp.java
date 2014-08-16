package so.codeweaver.neigh;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;

import dagger.ObjectGraph;
import so.codeweaver.neigh.common.BuildConfig;
import so.codeweaver.neigh.ui.ActivityHierarchyServer;
import timber.log.Timber;

import static timber.log.Timber.DebugTree;

/**
 * Created by berwyn on 16/08/14.
 */
public class NeighApp extends Application {
    protected ObjectGraph objectGraph;

    @Inject
    ActivityHierarchyServer activityHierarchyServer;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        } else {
            // TODO: Published logging
        }

        buildAndInjectObjectGraph();
        registerActivityLifecycleCallbacks(activityHierarchyServer);
    }

    public void buildAndInjectObjectGraph() {
        objectGraph = ObjectGraph.create(Modules.list(this));
        objectGraph.inject(this);
    }

    public void inject(Object obj) {
        objectGraph.inject(obj);
    }

    public static NeighApp get(Context context) {
        return (NeighApp) context.getApplicationContext();
    }
}
