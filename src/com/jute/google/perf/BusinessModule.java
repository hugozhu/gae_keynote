package com.jute.google.perf;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import com.jute.google.perf.dao.AbstractDao;
import com.jute.google.perf.dao.DaoInterceptor;

/**
 * User: hugozhu
 * Date: Jul 21, 2009
 * Time: 11:58:04 PM
 */
public class BusinessModule extends AbstractModule {
    protected void configure() {
//        this.bindInterceptor(Matchers.subclassesOf(AbstractDao.class), Matchers.any() ,new DaoInterceptor());
    }
}
