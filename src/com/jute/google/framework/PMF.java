package com.jute.google.framework;

/**
 * User: hugozhu
 * Date: Apr 25, 2009
 * Time: 12:24:01 PM
 */

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * @author Max Ross <maxr@google.com>
 */
public final class PMF {

  private static final PersistenceManagerFactory INSTANCE =
      JDOHelper.getPersistenceManagerFactory("transactions-optional");

  public static PersistenceManagerFactory get() {
    return INSTANCE;
  }

  private PMF() {}
}
