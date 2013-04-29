/*    */ package com.mce.command;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class EMessage
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private int errorCode;
/*    */   private String message;
/*    */ 
/*    */   EMessage()
/*    */   {
/*    */   }
/*    */ 
/*    */   public EMessage(int code, String message)
/*    */   {
/* 15 */     this.errorCode = code;
/* 16 */     this.message = message;
/*    */   }
/*    */ 
/*    */   public int getErrorCode()
/*    */   {
/* 23 */     return this.errorCode;
/*    */   }
/*    */   public String getMessage() {
/* 26 */     return this.message;
/*    */   }
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.command.EMessage
 * JD-Core Version:    0.6.2
 */