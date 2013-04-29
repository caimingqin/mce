/*    */ package com.mce.util.jdbc.support;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class BadSqlGrammarException extends InvalidDataAccessResourceUsageException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String sql;
/*    */ 
/*    */   public BadSqlGrammarException(String task, String sql, SQLException ex)
/*    */   {
/* 22 */     super(task + "; bad SQL grammar [" + sql + "]", ex);
/* 23 */     this.sql = sql;
/*    */   }
/*    */ 
/*    */   public SQLException getSQLException()
/*    */   {
/* 31 */     return (SQLException)getCause();
/*    */   }
/*    */ 
/*    */   public String getSql()
/*    */   {
/* 38 */     return this.sql;
/*    */   }
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.support.BadSqlGrammarException
 * JD-Core Version:    0.6.2
 */