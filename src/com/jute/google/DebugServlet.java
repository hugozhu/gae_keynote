package com.jute.google;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class DebugServlet extends HttpServlet {

	@SuppressWarnings("unchecked")
	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
    throws IOException {
		res.setContentType("text/html;charset=UTF-8");
		print(res,"<html><head><title></title><style> * {font-family:Arial}</style></head><body>");
		print(res,"<h1>Google AppEngine Enviorment Information</h1>");	
		
		print(res,"<ul>");
		print(res,"AuthType",req.getAuthType());
		print(res,"CharacterEncoding",req.getCharacterEncoding());
		print(res,"ContentType",req.getContentType());
		print(res,"ContextPath",req.getContextPath());
		print(res,"Method",req.getMethod());
		print(res,"UserPrincipal",req.getUserPrincipal());
		print(res,"RequestURL",req.getRequestURL());
		print(res,"PathInfo",req.getPathInfo());
		print(res,"PathTranslated",req.getPathTranslated());
		print(res,"QueryString",req.getQueryString());
		print(res,"RemoteUser",req.getRemoteUser());
		print(res,"RemoteAddr",req.getRemoteAddr());
		print(res,"RemotePort",req.getRemotePort());
		print(res,"LocalAddr",req.getLocalAddr());		
		print(res,"</ul>");
		
		print(res,"<ul>Request Headers");
		Enumeration head = req.getHeaderNames();
		while(head.hasMoreElements()) {
			String key = (String) head.nextElement();
			print(res,key,req.getHeader(key));
		}
		print(res,"</ul>");
		
		print(res,"<ul>Request Attributes");
		head = req.getAttributeNames();
		while(head.hasMoreElements()) {
			String key = (String) head.nextElement();
			print(res,key,req.getParameter(key));
		}
		print(res,"</ul>");

		print(res,"<ul>Request Parameters");
		head = req.getParameterNames();
		while(head.hasMoreElements()) {
			String key = (String) head.nextElement();
			print(res,key,req.getParameter(key));
		}
		print(res,"</ul>");
		
		Properties props = System.getProperties();
		print(res,"<ul>System Properties");
		print(res,props);
		print(res,"</ul");
		
		print(res,"</body></html>");		
	}
	private void print(HttpServletResponse res, String name, Object value)  throws IOException {
		res.getWriter().println(String.format("<li><strong>%s</strong>: %s</li>",name, String.valueOf(value)));
	}
	
	private void print(HttpServletResponse res, Object obj)  throws IOException {
		if (obj instanceof Map) {
			Map map = (Map) obj;
			for(Object key: map.keySet()) {
				print(res,String.valueOf(key),String.valueOf(map.get(key)));
			}
		}
		else {
			res.getWriter().println(obj.toString());
		}
	}
	
}
