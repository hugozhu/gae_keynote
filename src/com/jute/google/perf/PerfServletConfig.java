package com.jute.google.perf;

import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * User: hugozhu
 * Date: Jul 21, 2009
 * Time: 11:29:52 PM
 */
public class PerfServletConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServletModule(), new BusinessModule());
    }
}
