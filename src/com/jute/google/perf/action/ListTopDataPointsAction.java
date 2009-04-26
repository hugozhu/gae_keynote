package com.jute.google.perf.action;

import com.jute.google.framework.Action;
import com.jute.google.framework.PMF;
import com.jute.google.perf.model.Page;
import com.jute.google.perf.model.DataPoint;

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
public class ListTopDataPointsAction extends Action {
    public String execute(Map context, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        long pageId = Long.parseLong(req.getParameter("id"));
        context.put("page",pm.getObjectById(Page.class,pageId));
        Query query = pm.newQuery(DataPoint.class);
        query.setFilter("pageId == pageIdParam");
        query.setOrdering("id desc");
        query.declareParameters("Long pageIdParam");
        query.setRange(0,120);
        List<DataPoint> points = (List<DataPoint>) query.execute(pageId);
        context.put("points",points);
        return "list_top_data_points";
    }
}