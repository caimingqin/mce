package com.mce.util.jdbc;

import java.sql.SQLException;

public abstract interface SQLExceptionTranslator
{
  public abstract DataAccessException translate(String paramString1, String paramString2, SQLException paramSQLException);
}

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.SQLExceptionTranslator
 * JD-Core Version:    0.6.2
 */