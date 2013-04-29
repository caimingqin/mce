/*    */ package com.mce.util.jdbc.support;
/*    */ 
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class ArgTypePreparedStatementSetter
/*    */   implements PreparedStatementSetter
/*    */ {
/*    */   private final Object[] args;
/*    */   private final int[] argTypes;
/*    */ 
/*    */   public ArgTypePreparedStatementSetter(Object[] args, int[] argTypes)
/*    */   {
/* 22 */     if (((args != null) && (argTypes == null)) || ((args == null) && (argTypes != null)) || ((args != null) && (args.length != argTypes.length)))
/*    */     {
/* 24 */       throw new InvalidDataAccessApiUsageException("args and argTypes parameters must match");
/*    */     }
/* 26 */     this.args = args;
/* 27 */     this.argTypes = argTypes;
/*    */   }
/*    */ 
/*    */   public void setValues(PreparedStatement ps) throws SQLException
/*    */   {
/* 32 */     int parameterPosition = 1;
/* 33 */     if (this.args != null)
/* 34 */       for (int i = 0; i < this.args.length; i++) {
/* 35 */         Object arg = this.args[i];
/*    */         Iterator it;
/* 36 */         if (((arg instanceof Collection)) && (this.argTypes[i] != 2003)) {
/* 37 */           Collection entries = (Collection)arg;
/* 38 */           for (it = entries.iterator(); it.hasNext(); ) {
/* 39 */             Object entry = it.next();
/* 40 */             if ((entry instanceof Object[])) {
/* 41 */               Object[] valueArray = (Object[])entry;
/* 42 */               for (int k = 0; k < valueArray.length; k++) {
/* 43 */                 Object argValue = valueArray[k];
/* 44 */                 doSetValue(ps, parameterPosition, this.argTypes[i], argValue);
/* 45 */                 parameterPosition++;
/*    */               }
/*    */             }
/*    */             else {
/* 49 */               doSetValue(ps, parameterPosition, this.argTypes[i], entry);
/* 50 */               parameterPosition++;
/*    */             }
/*    */           }
/*    */         }
/*    */         else {
/* 55 */           doSetValue(ps, parameterPosition, this.argTypes[i], arg);
/* 56 */           parameterPosition++;
/*    */         }
/*    */       }
/*    */   }
/*    */ 
/*    */   protected void doSetValue(PreparedStatement ps, int parameterPosition, int argType, Object argValue)
/*    */     throws SQLException
/*    */   {
/* 64 */     StatementCreatorUtils.setParameterValue(ps, parameterPosition, argType, argValue);
/*    */   }
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.support.ArgTypePreparedStatementSetter
 * JD-Core Version:    0.6.2
 */