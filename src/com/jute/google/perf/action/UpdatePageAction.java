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
 * Date: Jul 15, 2009
 * Time: 12:36:56 AM
 */
public class UpdatePageAction  extends Action {

    public String execute(Map context, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        long id = Integer.parseInt(req.getParameter("id"));
        if (id>0) {
            PersistenceManager pm = PMF.get().getPersistenceManager();

            Query query = pm.newQuery(Page.class, "id == idParam");
            query.declareParameters("long idParam");
            Page page;
            List<Page> pages = (List<Page>) query.execute(id);
            if (pages != null && !pages.isEmpty()) {
                page = pages.get(0);
                page.setStatus(req.getParameter("status"));
                pm.makePersistent(page);
            }
            pm.close();
        }
        resp.sendRedirect("./");
        return null;
    }
}

