package so.codeweaver.neigh.ui;

import android.app.Activity;
import android.view.ViewGroup;

import so.codeweaver.neigh.NeighApp;

import static butterknife.ButterKnife.findById;

/**
 * An interface to abstract away the root container used to provide
 * application-level common UI
 */
public interface AppContainer {
    /**
     * The root {@link android.view.ViewGroup} that an activity
     * should place its contents inside
     * @param activity The activity to inject
     * @param app The application the activity is running inside
     * @return The root {@link android.view.ViewGroup}
     */
    public ViewGroup get(Activity activity, NeighApp app);

    AppContainer DEFAULT = new AppContainer() {
        public ViewGroup get(Activity activity, NeighApp app) {
            return findById(activity, android.R.id.content);
        }
    };
}
