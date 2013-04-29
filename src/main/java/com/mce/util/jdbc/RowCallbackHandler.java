package com.mce.util.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract interface RowCallbackHandler
{
  public abstract void processRow(ResultSet paramResultSet)
    throws SQLException;
}

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.RowCallbackHandler
 * JD-Core Version:    0.6.2
 */