package com.jute.google.perf.dao.impl;

import com.jute.google.perf.model.Page;
import com.jute.google.perf.dao.PageDao;
import com.jute.google.perf.dao.AbstractDao;
import com.jute.google.perf.dao.PersistenceManagerContextHolder;
import com.jute.google.framework.PMF;
import com.google.inject.Singleton;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.List;

/**
 * User: hugozhu
 * Date: Jul 21, 2009
 * Time: 11:11:24 PM
 */
@Singleton
public class PageDaoImpl extends AbstractDao implements PageDao {
    
    public List<Page> getAllPages() {
        Query query = getPersistenceManager().newQuery(Page.class);
        query.setOrdering("url asc");
        return (List<Page>) query.execute();
    }

    public void deletePage(String url) {
        PersistenceManager pm = getPersistenceManager();
        Query query = pm.newQuery(Page.class, "url == nameParam");
        query.declareParameters("String nameParam");
        Page page;
        List<Page> pages = (List<Page>) query.execute(url);
        if (pages != null && !pages.isEmpty()) {
            page = pages.get(0);
            pm.deletePersistent(page);
        }
    }

}
