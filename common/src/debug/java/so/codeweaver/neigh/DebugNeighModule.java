package so.codeweaver.neigh;

import dagger.Module;
import so.codeweaver.neigh.ui.DebugUiModule;

/**
 * Created by berwyn on 16/08/14.
 */
@Module(
        addsTo = NeighModule.class,
        includes = {
                DebugUiModule.class
        },
        overrides = true
)
public class DebugNeighModule {
}
