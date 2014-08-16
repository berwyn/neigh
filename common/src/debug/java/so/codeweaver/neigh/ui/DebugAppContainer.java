package so.codeweaver.neigh.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jakewharton.madge.MadgeFrameLayout;
import com.jakewharton.scalpel.ScalpelFrameLayout;

import javax.inject.Inject;
import javax.inject.Singleton;

import so.codeweaver.neigh.NeighApp;
import so.codeweaver.neigh.common.R;

import static butterknife.ButterKnife.findById;

/**
 * An implementation of {@link so.codeweaver.neigh.ui.AppContainer} that provides a
 * sliding drawer from the right with debug metrics
 */
@Singleton
public class DebugAppContainer implements AppContainer {

    private NeighApp app;
    private Activity activity;
    private Context  drawerContext;

    private DrawerLayout drawerLayout;
    private ViewGroup    content;

    private MadgeFrameLayout madgeFrameLayout;
    private ScalpelFrameLayout scalpelFrameLayout;

    @Inject
    public DebugAppContainer() {
    }

    @Override
    public ViewGroup get(final Activity activity, NeighApp app) {
        this.app = app;
        this.activity = activity;
        this.drawerContext = activity;

        activity.setContentView(R.layout.activity_debug_frame);

        ViewGroup drawer = findById(activity, R.id.debug_drawer);
        LayoutInflater.from(drawerContext).inflate(R.layout.layout_debug_drawer, drawer);

        bindViews();

        return null;
    }

    private void bindViews() {

    }
}
