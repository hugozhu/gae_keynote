package com.jute.google.framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * User: hugozhu
 * Date: Jul 22, 2009
 * Time: 12:48:46 AM
 */
public interface Action {
    String execute(Map context, HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
