/*    */ package com.mce.util.jdbc.support;
/*    */ 
/*    */ public class InvalidDataAccessApiUsageException extends NonTransientDataAccessException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public InvalidDataAccessApiUsageException(String msg)
/*    */   {
/* 15 */     super(msg);
/*    */   }
/*    */ 
/*    */   public InvalidDataAccessApiUsageException(String msg, Throwable cause)
/*    */   {
/* 24 */     super(msg, cause);
/*    */   }
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.support.InvalidDataAccessApiUsageException
 * JD-Core Version:    0.6.2
 */