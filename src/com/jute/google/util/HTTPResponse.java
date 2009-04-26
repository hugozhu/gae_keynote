package com.jute.google.util;

import com.google.appengine.api.urlfetch.HTTPHeader;

import java.util.List;

/**
 * User: hugozhu
 * Date: Apr 25, 2009
 * Time: 9:55:00 PM
 */
public class HTTPResponse {
    private int responseCode;
    private java.util.List<com.google.appengine.api.urlfetch.HTTPHeader> headers;
    private byte[] content;

    private int connectTime = 0;
    private int readTime = 0;

    public HTTPResponse(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public List<HTTPHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(List<HTTPHeader> headers) {
        this.headers = headers;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(int connectTime) {
        this.connectTime = connectTime;
    }

    public int getReadTime() {
        return readTime;
    }

    public void setReadTime(int readTime) {
        this.readTime = readTime;
    }
}
