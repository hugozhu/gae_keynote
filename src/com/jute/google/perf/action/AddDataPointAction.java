package com.jute.google.perf.action;

import com.jute.google.framework.Path;
import com.jute.google.framework.PMF;
import com.jute.google.framework.AbstractAction;
import com.jute.google.perf.model.DataPoint;
import com.jute.google.perf.model.Page;
import com.jute.google.util.HTTPResponse;
import com.jute.google.util.URLFetcher;
import com.google.inject.Singleton;
import com.google.inject.Inject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.cache.Cache;
import java.util.Map;
import java.util.List;
import java.util.Properties;
import java.net.URL;

import com.jute.google.perf.dao.PersistenceManagerContextHolder;

/**
 * User: hugozhu
 * Date: Apr 25, 2009
 * Time: 3:56:15 PM
 */
@Singleton
@Path(id="/add")
public class AddDataPointAction extends AbstractAction {
    @Inject
    Cache cache;

    public String execute(Map context, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String url = req.getParameter("url");
        if (url!=null) {
            PersistenceManager pm = PersistenceManagerContextHolder.get();

            Query query = pm.newQuery(Page.class, "url == nameParam");
            query.declareParameters("String nameParam");
            Page page;
            List<Page> pages = (List<Page>) query.execute(url);
            if (pages != null && !pages.isEmpty()) {
                page = pages.get(0);                
            }
            else {
                page = new Page(url);
                pm.makePersistent(page);
            }
            
            HTTPResponse response = URLFetcher.get(new URL(url));
            DataPoint point = new DataPoint(page.getId(), response.getConnectTime(), response.getReadTime(), response.getResponseCode());

            if (response.getContent()!=null) {
                point.setLength( String.valueOf(response.getContent().length));
            }
            pm.makePersistent(point);
            
            if (cache!=null) {
                try {
                    cache.put(page.getUrl(),point);
                }
                catch (Exception e) {
                    page.rememberLastDataPoint(point);
                    pm.makePersistent(page);
                }
            }
            else {
                page.rememberLastDataPoint(point);
                pm.makePersistent(page);
            }

            return null;
        }
        resp.sendError(403);
        return null;
    }    
}
