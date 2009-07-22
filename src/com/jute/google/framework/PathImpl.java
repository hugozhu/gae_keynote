package com.jute.google.framework;

import java.io.Serializable;
import java.lang.annotation.Annotation;


public class PathImpl implements Path, Serializable {

  private final String id;

  public PathImpl(String id) {
    this.id = id;
  }

  public String id() {
    return this.id;
  }

  public int hashCode() {
    // This is specified in java.lang.Annotation.
    return (127 * "id".hashCode()) ^ id.hashCode();
  }

  public boolean equals(Object o) {
    if (!(o instanceof Path)) {
      return false;
    }

    Path other = (Path) o;
    return id.equals(other.id());
  }

  public String toString() {
    return "@" + Path.class.getName() + "(id=" + id + ")";
  }

  public Class<? extends Annotation> annotationType() {
    return Path.class;
  }

  private static final long serialVersionUID = 0;
}
