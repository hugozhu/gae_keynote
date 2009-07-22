package com.jute.google.perf.dao;

import com.jute.google.framework.PMF;

import javax.jdo.PersistenceManager;

/**
 * User: hugozhu
 * Date: Jul 22, 2009
 * Time: 3:19:19 AM
 */
public class PersistenceManagerContextHolder {
    private final static ThreadLocal<PersistenceManager> holder = new ThreadLocal<PersistenceManager>();
    public static PersistenceManager get() {
        PersistenceManager pm = holder.get();
        if (pm==null) {
            pm = PMF.get().getPersistenceManager();
            holder.set(pm);
        }
        return pm;
    }

    public static void remove() {
        PersistenceManager pm = holder.get();
        if (pm!=null && !pm.isClosed()) {
            pm.close();
        }
        holder.remove();
    }
}
