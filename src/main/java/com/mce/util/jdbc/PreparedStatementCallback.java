package com.mce.util.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract interface PreparedStatementCallback
{
  public abstract void doInPreparedStatement(PreparedStatement paramPreparedStatement)
    throws SQLException, DataAccessException;
}

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.PreparedStatementCallback
 * JD-Core Version:    0.6.2
 */