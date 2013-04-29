package com.mce.util.jdbc.support;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract interface SqlTypeValue
{
  public static final int TYPE_UNKNOWN = -2147483648;

  public abstract void setTypeValue(PreparedStatement paramPreparedStatement, int paramInt1, int paramInt2, String paramString)
    throws SQLException;
}

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.support.SqlTypeValue
 * JD-Core Version:    0.6.2
 */