package com.jute.google.perf.dao;

import com.jute.google.perf.model.Page;
import com.jute.google.perf.dao.impl.PageDaoImpl;
import com.google.inject.ImplementedBy;

import java.util.List;

/**
 * User: hugozhu
 * Date: Jul 21, 2009
 * Time: 11:56:26 PM
 */
@ImplementedBy(PageDaoImpl.class)
public interface PageDao {

    public List<Page> getAllPages();

    public void deletePage(String url);
    
}
