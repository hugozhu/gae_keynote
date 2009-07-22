package com.jute.google.perf.action;

import com.jute.google.framework.AbstractAction;
import com.jute.google.framework.Path;
import com.jute.google.perf.dao.PageDao;
import com.jute.google.perf.dao.impl.PageDaoImpl;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * User: hugozhu
 * Date: Apr 25, 2009
 * Time: 11:57:57 PM
 */

@Singleton
@Path(id="/clear_data_points")
public class ListPagesAction extends AbstractAction {
    @Inject
    PageDao pageDao;

    public String execute(Map context, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        context.put("pages",pageDao.getAllPages());
        return "list_pages";
    }
}
