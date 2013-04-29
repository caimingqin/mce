/*    */ package com.mce.command;
/*    */ 
/*    */ import com.mce.core.UserSession;
/*    */ import com.mce.domain.event.DomainEvent;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public abstract class AbstractEventCommand extends AbstractCommand
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private DomainEvent dEvent;
/*    */ 
/*    */   public final Object execute()
/*    */   {
/* 24 */     DomainEventGather deg = new DomainEventGather();
/* 25 */     Object obs = execute(deg);
/* 26 */     DomainEvent de = deg.getDomainEvent();
/* 27 */     if (de == null) {
/* 28 */       throw new CommandHandleException(500, "DomainEvent is null");
/*    */     }
/* 30 */     this.dEvent = de;
/* 31 */     addContextParameter(this.dEvent);
/* 32 */     return obs;
/*    */   }
/*    */ 
/*    */   public abstract Object execute(DomainEventGather paramDomainEventGather);
/*    */ 
/*    */   protected void preDestory()
/*    */   {
/* 40 */     this.dEvent = null;
/*    */   }
/*    */ 
/*    */   private final void addContextParameter(DomainEvent de)
/*    */   {
/* 45 */     Map contextParams = UserSession.instance().getAll();
/* 46 */     if ((contextParams != null) && (contextParams.size() > 0))
/*    */     {
/* 48 */       Iterator ki = contextParams.keySet().iterator();
/* 49 */       while (ki.hasNext()) {
/* 50 */         String key = (String)ki.next();
/* 51 */         Object param = contextParams.get(key);
/* 52 */         if (param != null)
/* 53 */           de.addContextProperty(key, param);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public DomainEvent getDomainEvent()
/*    */   {
/* 60 */     return this.dEvent;
/*    */   }
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.command.AbstractEventCommand
 * JD-Core Version:    0.6.2
 */