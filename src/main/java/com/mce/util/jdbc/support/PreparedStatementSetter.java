package com.mce.util.jdbc.support;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract interface PreparedStatementSetter
{
  public abstract void setValues(PreparedStatement paramPreparedStatement)
    throws SQLException;
}

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.support.PreparedStatementSetter
 * JD-Core Version:    0.6.2
 */