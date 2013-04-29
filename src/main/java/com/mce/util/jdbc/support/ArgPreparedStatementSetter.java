/*    */ package com.mce.util.jdbc.support;
/*    */ 
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class ArgPreparedStatementSetter
/*    */   implements PreparedStatementSetter
/*    */ {
/*    */   private final Object[] args;
/*    */ 
/*    */   public ArgPreparedStatementSetter(Object[] args)
/*    */   {
/* 16 */     this.args = args;
/*    */   }
/*    */ 
/*    */   public void setValues(PreparedStatement ps)
/*    */     throws SQLException
/*    */   {
/* 22 */     if (this.args != null)
/* 23 */       for (int i = 0; i < this.args.length; i++) {
/* 24 */         Object arg = this.args[i];
/* 25 */         doSetValue(ps, i + 1, arg);
/*    */       }
/*    */   }
/*    */ 
/*    */   protected void doSetValue(PreparedStatement ps, int parameterPosition, Object argValue)
/*    */     throws SQLException
/*    */   {
/* 39 */     StatementCreatorUtils.setParameterValue(ps, parameterPosition, -2147483648, argValue);
/*    */   }
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.support.ArgPreparedStatementSetter
 * JD-Core Version:    0.6.2
 */