package com.jute.google.perf;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.appengine.api.memcache.stdimpl.GCacheFactory;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.CacheFactory;
import java.util.Map;
import java.util.HashMap;

/**
 * User: hugozhu
 * Date: Jul 21, 2009
 * Time: 11:58:04 PM
 */
public class BusinessModule extends AbstractModule {
    protected void configure() {
//        this.bindInterceptor(Matchers.subclassesOf(AbstractDao.class), Matchers.any() ,new DaoInterceptor());
    }

    @Provides
    Cache provideCache() {
        Map props = new HashMap();
        props.put(GCacheFactory.EXPIRATION_DELTA, 3600);

        try {
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            return cacheFactory.createCache(props);
        } catch (CacheException e) {
            // ...
        }
        return null;
    }
}
