/*    */ package com.mce.core.notification;
/*    */ 
/*    */ public final class NotifierConfig
/*    */ {
/*    */   public static final String DEFAULT = "dnotifier";
/*    */   public static final String SIMPLE = "snotifier";
/*    */   private String type;
/*    */ 
/*    */   NotifierConfig()
/*    */   {
/*    */   }
/*    */ 
/*    */   public NotifierConfig(String type)
/*    */   {
/* 23 */     this.type = type;
/*    */   }
/*    */ 
/*    */   public Notifier build() {
/* 27 */     if ("dnotifier".equalsIgnoreCase(this.type)) {
/* 28 */       return new DefaultNotifier();
/*    */     }
/*    */ 
/* 31 */     return new SimpleNotifier();
/*    */   }
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.core.notification.NotifierConfig
 * JD-Core Version:    0.6.2
 */