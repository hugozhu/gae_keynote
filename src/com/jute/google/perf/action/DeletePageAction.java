package com.jute.google.perf.action;

import com.jute.google.framework.Action;
import com.jute.google.framework.PMF;
import com.jute.google.perf.model.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.Map;
import java.util.List;

/**
 * User: hugozhu
 * Date: Jul 1, 2009
 * Time: 11:33:22 PM
 */
public class DeletePageAction  extends Action {

    public String execute(Map context, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String url = req.getParameter("url");
        if (url!=null) {
            PersistenceManager pm = PMF.get().getPersistenceManager();

            Query query = pm.newQuery(Page.class, "url == nameParam");
            query.declareParameters("String nameParam");
            Page page;
            List<Page> pages = (List<Page>) query.execute(url);
            if (pages != null && !pages.isEmpty()) {
                page = pages.get(0);
                pm.deletePersistent(page);
            }
            return null;
        }
        resp.sendError(403);
        return null;
    }
}
