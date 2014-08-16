package so.codeweaver.neigh;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import so.codeweaver.neigh.ui.UiModule;

/**
 * Created by berwyn on 16/08/14.
 */
@Module(
        includes = {
                UiModule.class
        },
        complete = false,
        library = true
)
public final class NeighModule {
    private NeighApp app;

    public NeighModule(NeighApp app) {
        this.app = app;
    }

    @Provides @Singleton
    Application provideApplication() {
        return app;
    }
}
