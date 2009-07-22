package com.jute.google.perf;

import com.jute.google.DebugServlet;
import com.jute.google.perf.action.*;
import com.jute.google.framework.Action;
import com.jute.google.framework.PathInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * User: hugozhu
 * Date: Jul 22, 2009
 * Time: 12:37:16 AM
 */
public class ServletModule extends com.google.inject.servlet.ServletModule {
    @Override
    protected void configureServlets() {
        serve("/perf/*").with(DispatchServlet.class);
        serve("/debug").with(DebugServlet.class);

        addAction("/add", AddDataPointAction.class);
        addAction("/index", ListPagesAction.class);
        addAction("/top_data_points", ListTopDataPointsAction.class);
        addAction("/clear_data_points", ClearOldDataPointsAction.class);
        addAction("/alert", AlertAction.class);
        addAction("/delete_page", DeletePageAction.class);
        addAction("/update_page", UpdatePageAction.class);
    }
    
    protected void addAction(String path, Class<? extends Action> c) {
        bind(Action.class).annotatedWith(PathInfo.path(path)).to(c);
    }
}
