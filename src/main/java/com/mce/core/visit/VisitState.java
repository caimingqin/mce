/*    */ package com.mce.core.visit;
/*    */ 
/*    */ public class VisitState
/*    */ {
/*    */   private Object target;
/* 10 */   private VisitStatus status = VisitStatus.RUN;
/*    */ 
/*    */   public Object getTarget()
/*    */   {
/* 15 */     return this.target;
/*    */   }
/*    */ 
/*    */   public void setTarget(Object target) {
/* 19 */     this.target = target;
/*    */   }
/*    */ 
/*    */   public VisitStatus getStatus() {
/* 23 */     return this.status;
/*    */   }
/*    */ 
/*    */   public void stop() {
/* 27 */     this.status = VisitStatus.STOP;
/*    */   }
/*    */ 
/*    */   public static enum VisitStatus
/*    */   {
/*  6 */     STOP, RUN;
/*    */   }
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.core.visit.VisitState
 * JD-Core Version:    0.6.2
 */