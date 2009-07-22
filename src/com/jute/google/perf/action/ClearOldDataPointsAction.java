package com.jute.google.perf.action;

import com.jute.google.framework.PMF;
import com.jute.google.framework.AbstractAction;
import com.jute.google.framework.Path;
import com.jute.google.perf.model.DataPoint;
import com.jute.google.perf.dao.PersistenceManagerContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.Map;
import java.util.List;
import java.util.Date;

/**
 * User: hugozhu
 * Date: Jun 19, 2009
 * Time: 5:08:43 AM
 */
@Path(id="/clear_data_points")
public class ClearOldDataPointsAction  extends AbstractAction {
    public String execute(Map context, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        PersistenceManager pm = PersistenceManagerContextHolder.get();
        Query query = pm.newQuery(DataPoint.class);
        query.setFilter("date < dateMaximumParam");
        query.declareParameters("java.util.Date dateMaximumParam");
        query.setOrdering("date asc");
        query.setRange(0,200);
        Date threeMonthAgo = new Date();
        threeMonthAgo.setTime(threeMonthAgo.getTime()-3*24*3600*30*1000l);
        List<DataPoint> points = (List<DataPoint>) query.execute(threeMonthAgo);
        if (points.size()>0) {
            pm.deletePersistentAll(points);
        }
        resp.getWriter().println(points.size());
        return null;
    }
}
