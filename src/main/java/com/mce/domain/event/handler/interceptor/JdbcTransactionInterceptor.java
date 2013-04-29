/*    */ package com.mce.domain.event.handler.interceptor;
/*    */ 
/*    */ import com.mce.domain.event.DomainEvent;
/*    */ import com.mce.domain.event.DomainEventContext;
/*    */ import com.mce.domain.event.DomainEventInterceptor;
/*    */ import com.mce.util.jdbc.JdbcContextFactory;
/*    */ import com.mce.util.jdbc.JdbcContextStatus;
/*    */ import com.mce.util.jdbc.TransactionJdbcContextStatus;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ 
/*    */ public class JdbcTransactionInterceptor
/*    */   implements DomainEventInterceptor
/*    */ {
/*    */   private JdbcContextFactory jcFactory;
/* 17 */   private Log logger = LogFactory.getLog(getClass().getName());
/*    */ 
/*    */   JdbcTransactionInterceptor() {
/*    */   }
/*    */   public JdbcTransactionInterceptor(JdbcContextFactory dcf) {
/* 22 */     setJdbcContextFactory(dcf);
/*    */   }
/*    */ 
/*    */   public void setJdbcContextFactory(JdbcContextFactory jcf) {
/* 26 */     Validate.notNull(jcf, "JdbcContextFactory is required");
/* 27 */     this.jcFactory = jcf;
/*    */   }
/*    */ 
/*    */   public void beforeHandle(DomainEventContext dec, DomainEvent de)
/*    */   {
/* 32 */     TransactionJdbcContextStatus tjcs = new TransactionJdbcContextStatus();
/* 33 */     this.jcFactory.getCurrentJdbcContext(tjcs);
/* 34 */     tjcs.start();
/* 35 */     dec.addProperty(JdbcContextStatus.class.getName(), tjcs);
/*    */   }
/*    */ 
/*    */   public void afterHandle(DomainEventContext dec, DomainEvent de)
/*    */   {
/* 40 */     TransactionJdbcContextStatus tjcs = (TransactionJdbcContextStatus)dec.get(JdbcContextStatus.class.getName());
/* 41 */     this.logger.debug("Start Stop TransactionStatus now");
/* 42 */     tjcs.stop();
/* 43 */     this.logger.debug("End TransactionStatus now");
/*    */   }
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.domain.event.handler.interceptor.JdbcTransactionInterceptor
 * JD-Core Version:    0.6.2
 */