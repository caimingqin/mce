/*    */ package com.mce.util.jdbc.support;
/*    */ 
/*    */ import com.mce.util.jdbc.DataAccessException;
/*    */ 
/*    */ public class NonTransientDataAccessException extends DataAccessException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public NonTransientDataAccessException(String msg)
/*    */   {
/* 12 */     super(msg);
/*    */   }
/*    */ 
/*    */   public NonTransientDataAccessException(String msg, Throwable cause)
/*    */   {
/* 22 */     super(msg, cause);
/*    */   }
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.support.NonTransientDataAccessException
 * JD-Core Version:    0.6.2
 */