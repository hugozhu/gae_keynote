package com.jute.google.framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.logging.Logger;

/**
 * User: hugozhu
 * Date: Apr 25, 2009
 * Time: 3:56:31 PM
 */
public abstract class Action {
    protected static final Logger log = Logger.getLogger(Action.class.getName());    
    public abstract String execute(Map context, HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
