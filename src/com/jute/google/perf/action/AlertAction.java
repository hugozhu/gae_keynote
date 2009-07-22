package com.jute.google.perf.action;

import com.jute.google.framework.PMF;
import com.jute.google.framework.AbstractAction;
import com.jute.google.framework.Path;
import com.jute.google.util.Mailer;
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
 * Date: Jul 1, 2009
 * Time: 11:21:40 PM
 */
@Singleton
@Path(id="/alert")
public class AlertAction extends AbstractAction {
    public String execute(Map context, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String to = req.getParameter("to");
        String subject = null;
        String body =  null;        
        if (to==null || to.indexOf("@")<0) {
            to = "hugozhu@gmail.com";
        }
        PersistenceManager pm = PersistenceManagerContextHolder.get();
        Query query = pm.newQuery(Page.class);
        query.setFilter("status == statusParam");
        query.declareParameters("String statusParam");
        List<Page> pages = (List<Page>) query.execute("alert");
        resp.setContentType("text/plain; charset=UTF-8");
        for(Page page: pages) {
            query = pm.newQuery(DataPoint.class);
            query.setFilter("pageId == pageIdParam");
            query.setOrdering("date desc");
            query.declareParameters("Long pageIdParam");
            query.setRange(0,5);
            List<DataPoint> points = (List<DataPoint>) query.execute(page.getId());
            int error = 0;
            for(DataPoint point: points) {
                if (point.getCode()!=200) {
                    error++;
                }
            }
            resp.getWriter().println(error+"\t"+page.getUrl());
            if (error >= 4) {
                subject = "[Alert] "+page.getUrl()+" - "+error+" errors in last 5 minutes";
                body = "Please check http://jute.appspot.com/perf/top_data_points?id="+page.getId() +" \n"+page.getStatus();
                Mailer.send(subject, body, to);                
            }
        }
        return null;
    }    
}
