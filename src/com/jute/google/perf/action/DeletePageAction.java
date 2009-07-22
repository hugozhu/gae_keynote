package com.jute.google.perf.action;

import com.jute.google.framework.PMF;
import com.jute.google.framework.AbstractAction;
import com.jute.google.framework.Path;
import com.jute.google.perf.model.Page;
import com.jute.google.perf.dao.PersistenceManagerContextHolder;
import com.jute.google.perf.dao.PageDao;
import com.google.inject.Singleton;
import com.google.inject.Inject;

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
@Singleton
@Path(id="/delete_page")
public class DeletePageAction  extends AbstractAction {
    @Inject
    PageDao pageDao;
    
    public String execute(Map context, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String url = req.getParameter("url");
        if (url!=null) {
            pageDao.deletePage(url);
            resp.sendRedirect("index");
            return null;
        }
        resp.sendError(403);
        return null;
    }
}
