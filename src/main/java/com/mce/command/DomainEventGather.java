/*    */ package com.mce.command;
/*    */ 
/*    */ import com.mce.domain.event.DomainEvent;
/*    */ 
/*    */ public final class DomainEventGather
/*    */ {
/*    */   private DomainEvent domainEvent;
/*    */ 
/*    */   public DomainEvent getDomainEvent()
/*    */   {
/*  9 */     return this.domainEvent;
/*    */   }
/*    */   public void setDomainEvent(DomainEvent domainEvent) {
/* 12 */     this.domainEvent = domainEvent;
/*    */   }
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.command.DomainEventGather
 * JD-Core Version:    0.6.2
 */