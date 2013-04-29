/*    */ package com.mce.util.jdbc;
/*    */ 
/*    */ public class DataAccessException extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public DataAccessException(String msg)
/*    */   {
/* 10 */     super(msg);
/*    */   }
/*    */ 
/*    */   public DataAccessException(String msg, Throwable cause)
/*    */   {
/* 20 */     super(msg, cause);
/*    */   }
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.DataAccessException
 * JD-Core Version:    0.6.2
 */