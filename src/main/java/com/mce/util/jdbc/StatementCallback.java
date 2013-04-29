package com.mce.util.jdbc;

import java.sql.SQLException;
import java.sql.Statement;

public abstract interface StatementCallback
{
  public abstract void doInStatement(Statement paramStatement)
    throws SQLException;
}

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.StatementCallback
 * JD-Core Version:    0.6.2
 */