package com.jute.google.perf;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.inject.Singleton;
import com.google.inject.Injector;
import com.google.inject.Inject;
import com.google.inject.Key;
import com.jute.google.perf.action.*;
import com.jute.google.perf.dao.PersistenceManagerContextHolder;
import com.jute.google.framework.*;

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
import java.util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * User: hugozhu
 * Date: Apr 25, 2009
 * Time: 1:38:55 PM
 */
@Singleton
public class DispatchServlet extends HttpServlet {
    @Inject
    private Injector injector;
    
    private static final Logger log = Logger.getLogger(DispatchServlet.class.getName());

    private static final Map<String, Action> actions = new ConcurrentHashMap<String, Action>();

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
                pathInfo = "/"+pathParts[0];
            }
        }
        else {
            pathInfo = "/index";
        }

        action = actions.get(pathInfo);
        if (action==null) {
            try {
                action = injector.getInstance(Key.get(Action.class, PathInfo.path(pathInfo)));
                if (action!=null) {
                    actions.put(pathInfo,action);
                }
            } catch (Exception e) {
            }
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
        PersistenceManager pm = null;
        try {
            PersistenceManagerContextHolder.get();
            view = action.execute(context,req,resp);
            if (view !=null ) {
                renderView(view,req,resp,context);
            }
        } catch (Exception e) {
            throw new ServletException("Failed to execute exception",e);
        }
        finally {
            PersistenceManagerContextHolder.remove();
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
        log.info("render template:"+path);
        RequestDispatcher disp = this.getServletContext().getRequestDispatcher("/WEB-INF/templates/"+path+".jsp");
        if (disp!=null)
            disp.include(req,resp);
    }

}
