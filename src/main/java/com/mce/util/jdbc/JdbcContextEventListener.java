package com.mce.util.jdbc;

abstract interface JdbcContextEventListener
{
  public static final String ON_OPEN = "onOpen";
  public static final String ON_CLOSED = "onClosed";
  public static final String ON_CLEAR = "onClear";

  public abstract void onEvent(String paramString, JdbcContext paramJdbcContext);
}

