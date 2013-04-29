/*    */ package com.mce.domain.event.handler;
/*    */ 
/*    */ import com.mce.domain.event.DomainEvent;
/*    */ import com.mce.util.ReflectionUtility;
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ public final class MethodAction extends AbstractDomainEventHandler
/*    */ {
/*    */   private Method method;
/*    */   private Object target;
/*    */   private String eventType;
/*    */ 
/*    */   public MethodAction(String listenerType, Method method, Object target)
/*    */   {
/* 19 */     super(listenerType);
/* 20 */     this.eventType = listenerType;
/* 21 */     this.method = method;
/* 22 */     this.target = target;
/*    */   }
/*    */ 
/*    */   public String getEventType() {
/* 26 */     return this.eventType;
/*    */   }
/* 28 */   public Method getMethod() { return this.method; } 
/* 29 */   public Object getTarget() { return this.target; }
/*    */ 
/*    */   protected void processEvent(DomainEvent ae)
/*    */   {
/* 33 */     ReflectionUtility.invokeMethod(this.method, this.target, new Object[] { ae });
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 38 */     StringBuffer sb = new StringBuffer(super.toString());
/* 39 */     sb.append("&");
/* 40 */     sb.append(this.target.getClass().getName());
/* 41 */     sb.append("@Method(" + getEventType() + ")");
/* 42 */     return sb.toString();
/*    */   }
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.domain.event.handler.MethodAction
 * JD-Core Version:    0.6.2
 */