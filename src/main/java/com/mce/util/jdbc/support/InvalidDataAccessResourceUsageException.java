/*    */ package com.mce.util.jdbc.support;
/*    */ 
/*    */ public class InvalidDataAccessResourceUsageException extends NonTransientDataAccessException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public InvalidDataAccessResourceUsageException(String msg)
/*    */   {
/* 11 */     super(msg);
/*    */   }
/*    */ 
/*    */   public InvalidDataAccessResourceUsageException(String msg, Throwable cause)
/*    */   {
/* 20 */     super(msg, cause);
/*    */   }
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.support.InvalidDataAccessResourceUsageException
 * JD-Core Version:    0.6.2
 */