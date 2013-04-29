/*    */ package com.mce.util.jdbc;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ import javax.sql.DataSource;
/*    */ import org.springframework.util.Assert;
/*    */ 
/*    */ public final class JdbcContextFactory
/*    */ {
/* 12 */   private ThreadLocal<JdbcContext> contextParams = new ThreadLocal();
/* 13 */   private DataSource ds = null;
/*    */ 
/* 42 */   private ResourceRealaseCallback rrCallback = new ResourceRealaseCallback();
/*    */ 
/*    */   JdbcContextFactory()
/*    */   {
/*    */   }
/*    */ 
/*    */   public JdbcContextFactory(DataSource ds)
/*    */   {
/* 18 */     Assert.notNull(ds, "DataSource is null");
/* 19 */     this.ds = ds;
/*    */   }
/*    */ 
/*    */   private void realaseJdbcContext(JdbcContext jc) {
/* 23 */     if (this.contextParams.get() != null)
/* 24 */       this.contextParams.remove();
/*    */   }
/*    */ 
/*    */   private Connection getConnection()
/*    */   {
/*    */     try {
/* 30 */       return this.ds.getConnection(); } catch (SQLException e) {
/*    */     }
/* 32 */     throw new IllegalArgumentException("Can not get Connection from DataSource");
/*    */   }
/*    */ 
/*    */   public JdbcContext getCurrentJdbcContext()
/*    */   {
/* 45 */     return getCurrentJdbcContext(null);
/*    */   }
/*    */ 
/*    */   public JdbcContext getCurrentJdbcContext(JdbcContextStatus jcs) {
/* 49 */     JdbcContext jcc = (JdbcContext)this.contextParams.get();
/* 50 */     if (jcc == null) {
/* 51 */       Connection con = getConnection();
/* 52 */       jcc = new JdbcContext(con, this.rrCallback);
/* 53 */       if (jcs == null) {
/* 54 */         AutoModeJdbcContextStatus amjcs = new AutoModeJdbcContextStatus();
/* 55 */         amjcs.setJdbcContext(jcc);
/* 56 */         amjcs.start();
/*    */       }
/*    */       else {
/* 59 */         jcs.setJdbcContext(jcc);
/*    */       }
/* 61 */       this.contextParams.set(jcc);
/*    */     }
/* 63 */     return jcc;
/*    */   }
/*    */ 
/*    */   class ResourceRealaseCallback
/*    */   {
/*    */     ResourceRealaseCallback()
/*    */     {
/*    */     }
/*    */ 
/*    */     void call(JdbcContext jc)
/*    */     {
/* 38 */       JdbcContextFactory.this.realaseJdbcContext(jc);
/*    */     }
/*    */   }
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.JdbcContextFactory
 * JD-Core Version:    0.6.2
 */