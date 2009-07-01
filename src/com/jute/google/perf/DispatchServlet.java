package com.jute.google.perf;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.users.UserService;
import com.jute.google.perf.action.*;
import com.jute.google.framework.Action;
import com.jute.google.framework.PMF;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.jdo.PersistenceManager;
import java.util.logging.Logger;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;

/**
 * User: hugozhu
 * Date: Apr 25, 2009
 * Time: 1:38:55 PM
 */
public class DispatchServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(DispatchServlet.class.getName());

    private static final Map<String, Action> actions = new HashMap<String, Action>();

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
         //Todo: load via IoC Container and Java annotation
        actions.put("/add", new AddDataPointAction());
        actions.put("/index", new ListPagesAction());
        actions.put("/top_data_points", new ListTopDataPointsAction());
        actions.put("/clear_data_points", new ClearOldDataPointsAction());
        actions.put("/alert", new AlertAction());
        actions.put("/delete_page", new DeletePageAction());

    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        doPost(req,resp);
    }
         
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws IOException, ServletException {
        Action action = null;

        String pathInfo = req.getPathInfo();
        log.info("path info:"+pathInfo);
        if (pathInfo!=null && pathInfo.length()>1) {
            String[] pathParts = pathInfo.substring(1).split("/");
            if (pathParts.length>0 && actions.containsKey("/"+pathParts[0])) {
                action = actions.get("/"+pathParts[0]);
            }
        }
        else {
            action = actions.get("/index");
        }

        if (action == null) {
            resp.sendError(404,"No action found to handle this request");
            return;
        }

        Map<String, Object> context = new HashMap<String, Object>();
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user!=null) {
            context.put("user",user);
        }

        String view = null;
        try {
            view = action.execute(context,req,resp);
            if (view !=null ) {
                renderView(view,req,resp,context);
            }
        } catch (Exception e) {
            throw new ServletException("Failed to execute exception",e);
        }
        finally{
            PersistenceManager pm = PMF.get().getPersistenceManager();
            if (!pm.isClosed()) {
                pm.close();
            }
        }
    }

    protected void setData(HttpServletRequest req, String name, Object value) {
        req.setAttribute(name,value);
    }

    protected void renderView(String path,HttpServletRequest req, HttpServletResponse resp, Map<String, Object> context) throws IOException, ServletException {
        if (context!=null && !context.isEmpty()) {
            for (Map.Entry<String, Object> entry: context.entrySet()) {
                req.setAttribute(entry.getKey(),entry.getValue());
            }
        }
        RequestDispatcher disp = this.getServletContext().getRequestDispatcher("/WEB-INF/templates/"+path+".jsp");
        if (disp!=null)
            disp.forward(req,resp);
    }

}
