package com.jute.google.perf.dao;

import com.jute.google.framework.PMF;
import com.google.inject.Inject;

import javax.jdo.PersistenceManager;

/**
 * User: hugozhu
 * Date: Jul 22, 2009
 * Time: 2:35:59 AM
 */
public abstract class AbstractDao {

    public PersistenceManager getPersistenceManager() {
        return PersistenceManagerContextHolder.get();
    }
}
