package com.jute.google.perf.action;

import com.jute.google.framework.PMF;
import com.jute.google.framework.AbstractAction;
import com.jute.google.framework.Path;
import com.jute.google.perf.model.Page;
import com.jute.google.perf.model.DataPoint;
import com.jute.google.perf.dao.PersistenceManagerContextHolder;
import com.google.inject.Singleton;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.Map;
import java.util.List;

/**
 * User: hugozhu
 * Date: Apr 26, 2009
 * Time: 12:38:37 AM
 */

@Singleton
@Path(id="/top_data_points")
public class ListTopDataPointsAction extends AbstractAction {
    public String execute(Map context, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        PersistenceManager pm = PersistenceManagerContextHolder.get();
        long pageId = Long.parseLong(req.getParameter("id"));
        context.put("page",pm.getObjectById(Page.class,pageId));
        Query query = pm.newQuery(DataPoint.class);
        query.setFilter("pageId == pageIdParam");
        query.setOrdering("date desc");
        query.declareParameters("Long pageIdParam");
        query.setRange(0,200);
        List<DataPoint> points = (List<DataPoint>) query.execute(pageId);
        context.put("points",points);
        return "list_top_data_points";
    }
}
