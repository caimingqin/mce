/*    */ package com.mce.core.visit;
/*    */ 
/*    */ public abstract class AbstractVisitor<T>
/*    */   implements Visitor<T>
/*    */ {
/*  7 */   private VisitState vs = new VisitState();
/*    */ 
/*  9 */   public boolean visit(T obs) { visit(obs, this.vs);
/* 10 */     if (VisitState.VisitStatus.STOP.equals(this.vs.getStatus())) {
/* 11 */       return true;
/*    */     }
/* 13 */     return false; }
/*    */ 
/*    */   public VisitState getVisitState()
/*    */   {
/* 17 */     return this.vs;
/*    */   }
/*    */ 
/*    */   public abstract void visit(T paramT, VisitState paramVisitState);
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.core.visit.AbstractVisitor
 * JD-Core Version:    0.6.2
 */