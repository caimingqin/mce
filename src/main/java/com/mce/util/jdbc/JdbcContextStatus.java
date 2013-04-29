/*    */ package com.mce.util.jdbc;
/*    */ 
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ 
/*    */ public abstract class JdbcContextStatus
/*    */ {
/*    */   private JdbcContext jdbcContext;
/* 11 */   private Log logger = LogFactory.getLog(getClass().getName());
/*    */ 
/*    */   protected Log getLogger() {
/* 14 */     return this.logger;
/*    */   }
/*    */ 
/*    */   public void start()
/*    */   {
/* 20 */     this.logger.debug("Openning Connection >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
/* 21 */     getJdbcContextEventListener().onEvent("onOpen", this.jdbcContext);
/* 22 */     this.jdbcContext.setMode(JdbcContext.ProcessMode.REALASE);
/*    */   }
/*    */ 
/*    */   public void stop() {
/* 26 */     this.jdbcContext.setMode(JdbcContext.ProcessMode.LOCK);
/* 27 */     getJdbcContextEventListener().onEvent("onClear", this.jdbcContext);
/*    */   }
/*    */ 
/*    */   protected void setJdbcContext(JdbcContext jc) {
/* 31 */     this.jdbcContext = jc;
/* 32 */     this.jdbcContext.addContextEventListener(getJdbcContextEventListener());
/*    */   }
/*    */ 
/*    */   protected abstract JdbcContextEventListener getJdbcContextEventListener();
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.JdbcContextStatus
 * JD-Core Version:    0.6.2
 */