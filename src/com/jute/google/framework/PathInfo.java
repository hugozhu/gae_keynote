package com.jute.google.framework;
/**
 * User: hugozhu
 * Date: Jul 22, 2009
 * Time: 1:49:22 AM
 */
public class PathInfo {

  private PathInfo() {}

  /**
   * Creates a {@link com.google.inject.name.Named} annotation with {@code name} as the value.
   */
  public static Path path(String name) {
    return new PathImpl(name);
  }
}