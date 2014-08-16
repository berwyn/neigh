package so.codeweaver.neigh;

import java.lang.Object;

final class Modules {
    static Object[] list(NeighApp app) {
        return new Object[] {
                new NeighModule(app),
                new DebugNeighModule()
        };
    }

    private Modules() {

    }
}