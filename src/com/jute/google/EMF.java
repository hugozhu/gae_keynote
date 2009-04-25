package com.jute.google;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * User: hugozhu
 * Date: Apr 25, 2009
 * Time: 12:24:33 PM
 */
public final class EMF {

  private static final EntityManagerFactory INSTANCE = Persistence.createEntityManagerFactory("jute");

  public static EntityManagerFactory get() {
    return INSTANCE;
  }

  private EMF() {}
}