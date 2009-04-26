package com.jute.google.perf.action;

import com.jute.google.framework.Action;
import com.jute.google.framework.PMF;
import com.jute.google.perf.model.Page;
import com.jute.google.perf.model.DataPoint;
import com.jute.google.util.HTTPResponse;
import com.jute.google.util.URLFetcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.Map;
import java.util.List;

/**
 * User: hugozhu
 * Date: Apr 25, 2009
 * Time: 11:57:57 PM
 */
public class ListPagesAction extends Action {
    public String execute(Map context, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        PersistenceManager pm = PMF.get().getPersistenceManager();    
        Query query = pm.newQuery(Page.class);
        List<Page> pages = (List<Page>) query.execute();
        context.put("pages",pages);
        return "list_pages";
    }
}
