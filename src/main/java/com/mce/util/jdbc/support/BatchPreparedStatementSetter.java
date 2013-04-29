package com.mce.util.jdbc.support;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract interface BatchPreparedStatementSetter
{
  public abstract void setValues(PreparedStatement paramPreparedStatement, int paramInt)
    throws SQLException;

  public abstract int getBatchSize();
}

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.support.BatchPreparedStatementSetter
 * JD-Core Version:    0.6.2
 */