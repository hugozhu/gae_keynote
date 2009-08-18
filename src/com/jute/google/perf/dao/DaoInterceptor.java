package com.jute.google.perf.dao;

import org.aopalliance.intercept.MethodInvocation;
import org.aopalliance.intercept.MethodInterceptor;
import com.jute.google.framework.PMF;

/**
 * User: hugozhu
 * Date: Jul 22, 2009
 * Time: 2:41:35 AM
 */
public class DaoInterceptor implements MethodInterceptor {
  public Object invoke(MethodInvocation invocation) throws Throwable {
      if (invocation.getMethod().getName().indexOf("PersistenceManager")>-1) {
          return invocation.proceed();
      }

      try {
//          ((AbstractDao)invocation.getThis()).setPersistenceManager(PMF.get().getPersistenceManager());
          return invocation.proceed();
      } finally {

      }
  }
}
