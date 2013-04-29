package com.mce.util.jdbc;

public abstract interface JdbcSession
{
  public abstract void query(String paramString, RowCallbackHandler paramRowCallbackHandler);

  public abstract void query(String paramString, Object[] paramArrayOfObject, RowCallbackHandler paramRowCallbackHandler);

  public abstract int executeUpdate(String paramString, Object[] paramArrayOfObject);
}

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.JdbcSession
 * JD-Core Version:    0.6.2
 */