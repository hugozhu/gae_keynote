package com.jute.google.util;

/**
 * User: hugozhu
 * Date: Aug 20, 2009
 * Time: 11:09:58 PM
 */
public class GChartEncodingUtil {
    final static char[] simpleEncodingChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    final static char[] extendEncodingChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-.".toCharArray();

    public static String simpleEncode (Comparable[] values, Comparable max) {
        StringBuilder sb = new StringBuilder("s:");
        for(Comparable d: values) {
            if (d == null) {
                sb.append('_');
                continue;
            }
            float scale = 1;
            if (max instanceof Integer) {
                scale = (Integer) d / (Integer) max; 
            }
            else if (max instanceof Float) {
                scale = (Float) d/ (Float) max;
            }
            int x  = Math.round((simpleEncodingChars.length-1) * scale);
            sb.append(simpleEncodingChars[x]);
        }
        return sb.toString();
    }

    public static String textEncode(Object[] values) {
        StringBuilder sb = new StringBuilder("t:");
        for (Object s:values) {
            sb.append(s);
            sb.append(",");
        }
        sb.setLength(sb.length()-1);
        return sb.toString();
    }


    public static String extendedEncode(Object[] values) {
        StringBuilder sb = new StringBuilder("e:");
        for (Object s:values) {
            sb.append(s);
            sb.append(",");
        }
        sb.setLength(sb.length()-1);
        return sb.toString();
    }
}
