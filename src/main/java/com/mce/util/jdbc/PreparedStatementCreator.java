package com.mce.util.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract interface PreparedStatementCreator
{
  public abstract PreparedStatement createPreparedStatement(Connection paramConnection)
    throws SQLException;
}

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.PreparedStatementCreator
 * JD-Core Version:    0.6.2
 */