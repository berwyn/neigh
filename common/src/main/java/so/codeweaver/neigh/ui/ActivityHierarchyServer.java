package so.codeweaver.neigh.ui;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by berwyn on 16/08/14.
 */
public interface ActivityHierarchyServer extends Application.ActivityLifecycleCallbacks {

    ActivityHierarchyServer NONE = new ActivityHierarchyServer() {
        public void onActivityCreated(Activity activity, Bundle bundle) {}

        public void onActivityStarted(Activity activity) {}

        public void onActivityResumed(Activity activity) {}
        
        public void onActivityPaused(Activity activity) {}
        
        public void onActivityStopped(Activity activity) {}
        
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {}
        
        public void onActivityDestroyed(Activity activity) {}
    };

}
