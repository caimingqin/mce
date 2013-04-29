/*    */ package com.mce.util.jdbc;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.DatabaseMetaData;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Statement;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ 
/*    */ public class JdbcUtils
/*    */ {
/* 15 */   private static Log logger = LogFactory.getLog(JdbcUtils.class.getName());
/*    */ 
/*    */   public static boolean supportsBatchUpdates(Connection con)
/*    */   {
/*    */     try {
/* 20 */       DatabaseMetaData dbmd = con.getMetaData();
/* 21 */       if (dbmd != null) {
/* 22 */         if (dbmd.supportsBatchUpdates()) {
/* 23 */           logger.debug("JDBC driver supports batch updates");
/* 24 */           return true;
/*    */         }
/*    */ 
/* 27 */         logger.debug("JDBC driver does not support batch updates");
/*    */       }
/*    */     }
/*    */     catch (SQLException ex)
/*    */     {
/* 32 */       logger.debug("JDBC driver 'supportsBatchUpdates' method threw exception", ex);
/*    */     }
/*    */     catch (AbstractMethodError err) {
/* 35 */       logger.debug("JDBC driver does not support JDBC 2.0 'supportsBatchUpdates' method", err);
/*    */     }
/* 37 */     return false;
/*    */   }
/*    */ 
/*    */   public static String getSql(Object sqlProvider)
/*    */   {
/* 42 */     if ((sqlProvider instanceof SqlProvider)) {
/* 43 */       return ((SqlProvider)sqlProvider).getSql();
/*    */     }
/*    */ 
/* 46 */     return null;
/*    */   }
/*    */ 
/*    */   public static void processResultHandler(ResultSet rs, RowCallbackHandler rch) throws SQLException
/*    */   {
/* 51 */     while (rs.next())
/* 52 */       rch.processRow(rs);
/*    */   }
/*    */ 
/*    */   public static void closeStatement(Statement stmt)
/*    */   {
/* 57 */     if (stmt != null)
/*    */       try {
/* 59 */         stmt.close();
/*    */       }
/*    */       catch (SQLException ex) {
/* 62 */         logger.trace("Could not close JDBC Statement", ex);
/*    */       }
/*    */       catch (Throwable ex)
/*    */       {
/* 66 */         logger.trace("Unexpected exception on closing JDBC Statement", ex);
/*    */       }
/*    */   }
/*    */ 
/*    */   public static void closeResultSet(ResultSet rs)
/*    */   {
/* 72 */     if (rs != null)
/*    */       try {
/* 74 */         rs.close();
/*    */       }
/*    */       catch (SQLException ex) {
/* 77 */         logger.trace("Could not close JDBC ResultSet", ex);
/*    */       }
/*    */       catch (Throwable ex)
/*    */       {
/* 81 */         logger.trace("Unexpected exception on closing JDBC ResultSet", ex);
/*    */       }
/*    */   }
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.JdbcUtils
 * JD-Core Version:    0.6.2
 */