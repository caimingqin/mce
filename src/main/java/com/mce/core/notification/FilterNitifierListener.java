/*    */ package com.mce.core.notification;
/*    */ 
/*    */ import org.apache.commons.lang.Validate;
/*    */ 
/*    */ class FilterNitifierListener
/*    */   implements NotificationListener
/*    */ {
/*    */   private NotifyFilter nFilter;
/*    */   private NotificationListener nListener;
/*    */ 
/*    */   FilterNitifierListener(NotificationListener nl, NotifyFilter nf)
/*    */   {
/* 19 */     Validate.noNullElements(new Object[] { nl, nf });
/* 20 */     this.nFilter = nf;
/* 21 */     this.nListener = nl;
/*    */   }
/*    */ 
/*    */   public void handle(Notification nf)
/*    */   {
/* 26 */     if (this.nFilter.filter(nf))
/* 27 */       this.nListener.handle(nf);
/*    */   }
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.core.notification.FilterNitifierListener
 * JD-Core Version:    0.6.2
 */