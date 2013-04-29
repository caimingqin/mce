/*    */ package com.mce.util.jdbc;
/*    */ 
/*    */ public class AutoModeJdbcContextStatus extends JdbcContextStatus
/*    */ {
/*    */   private AutoListener al;
/*    */ 
/*    */   public AutoModeJdbcContextStatus()
/*    */   {
/*  6 */     this.al = new AutoListener();
/*    */   }
/*    */ 
/*    */   protected JdbcContextEventListener getJdbcContextEventListener()
/*    */   {
/* 21 */     return this.al;
/*    */   }
/*    */ 
/*    */   public class AutoListener
/*    */     implements JdbcContextEventListener
/*    */   {
/*    */     public AutoListener()
/*    */     {
/*    */     }
/*    */ 
/*    */     public void onEvent(String type, JdbcContext jc)
/*    */     {
/* 12 */       if ("onClosed".equalsIgnoreCase(type)) {
/* 13 */         AutoModeJdbcContextStatus.this.stop();
/* 14 */         jc.clear();
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.AutoModeJdbcContextStatus
 * JD-Core Version:    0.6.2
 */