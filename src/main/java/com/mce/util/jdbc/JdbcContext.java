/*     */ package com.mce.util.jdbc;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLWarning;
/*     */ import java.sql.Statement;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.springframework.util.Assert;
/*     */ 
/*     */ public final class JdbcContext
/*     */ {
/*  19 */   private Log logger = LogFactory.getLog(getClass().getName());
/*     */   private Connection con;
/*     */   private JdbcContextEventListener jcEventListener;
/*  22 */   private ProcessMode pMode = ProcessMode.LOCK;
/*     */   private JdbcContextFactory.ResourceRealaseCallback rrCallback;
/* 104 */   private int fetchSize = 0;
/* 105 */   private int maxRows = 0;
/* 106 */   private int queryTimeout = 0;
/*     */ 
/*     */   public JdbcContext(Connection con, JdbcContextFactory.ResourceRealaseCallback rrCallback)
/*     */   {
/*  30 */     this.con = con;
/*  31 */     this.rrCallback = rrCallback;
/*     */   }
/*     */ 
/*     */   public void executeSQL(PreparedStatementCreator psc, PreparedStatementCallback action)
/*     */   {
/*  37 */     validateModeState();
/*     */ 
/*  39 */     Assert.notNull(psc, "PreparedStatementCreator must not be null");
/*  40 */     Assert.notNull(action, "Callback object must not be null");
/*  41 */     if (this.logger.isDebugEnabled()) {
/*  42 */       String sql = JdbcUtils.getSql(psc);
/*  43 */       this.logger.debug("Executing prepared SQL statement" + (sql != null ? " [" + sql + "]" : ""));
/*     */     }
/*     */ 
/*  46 */     PreparedStatement ps = null;
/*     */     try {
/*  48 */       ps = psc.createPreparedStatement(this.con);
/*  49 */       applyStatementSettings(ps);
/*  50 */       PreparedStatement psToUse = ps;
/*  51 */       action.doInPreparedStatement(psToUse);
/*  52 */       handleWarnings(ps);
/*     */     }
/*     */     catch (SQLException ex) {
/*  55 */       String sql = JdbcUtils.getSql(psc);
/*  56 */       psc = null;
/*  57 */       JdbcUtils.closeStatement(ps);
/*  58 */       ps = null;
/*  59 */       throw new SQLStateSQLExceptionTranslator().translate("PreparedStatementCallback", sql, ex);
/*     */     }
/*     */     finally {
/*  62 */       JdbcUtils.closeStatement(ps);
/*  63 */       this.jcEventListener.onEvent("onClosed", this);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void validateModeState() {
/*  68 */     if (ProcessMode.LOCK.equals(this.pMode))
/*  69 */       throw new IllegalArgumentException("The Lock mode");
/*     */   }
/*     */ 
/*     */   public void execute(StatementCallback action)
/*     */   {
/*  75 */     Assert.notNull(action, "Callback object must not be null");
/*  76 */     validateModeState();
/*  77 */     Statement stmt = null;
/*     */     try {
/*  79 */       stmt = this.con.createStatement();
/*  80 */       applyStatementSettings(stmt);
/*  81 */       Statement stmtToUse = stmt;
/*  82 */       action.doInStatement(stmtToUse);
/*  83 */       handleWarnings(stmt);
/*     */     }
/*     */     catch (SQLException ex) {
/*  86 */       throw new SQLStateSQLExceptionTranslator().translate("StatementCallback", JdbcUtils.getSql(action), ex);
/*     */     }
/*     */     finally {
/*  89 */       JdbcUtils.closeStatement(stmt);
/*  90 */       stmt = null;
/*  91 */       this.jcEventListener.onEvent("onClosed", this);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void handleWarnings(Statement stmt) throws SQLException {
/*  96 */     SQLWarning warningToLog = stmt.getWarnings();
/*  97 */     while (warningToLog != null) {
/*  98 */       this.logger.debug("SQLWarning ignored: SQL state '" + warningToLog.getSQLState() + "', error code '" + warningToLog.getErrorCode() + "', message [" + warningToLog.getMessage() + "]");
/*     */ 
/* 100 */       warningToLog = warningToLog.getNextWarning();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void applyStatementSettings(Statement stmt)
/*     */     throws SQLException
/*     */   {
/* 108 */     if (this.fetchSize > 0) {
/* 109 */       stmt.setFetchSize(this.fetchSize);
/*     */     }
/* 111 */     if (this.maxRows > 0) {
/* 112 */       stmt.setMaxRows(this.maxRows);
/*     */     }
/* 114 */     if (this.queryTimeout > 0)
/* 115 */       stmt.setQueryTimeout(this.queryTimeout);
/*     */   }
/*     */ 
/*     */   void clear()
/*     */   {
/* 123 */     this.logger.debug("Close Connection now");
/* 124 */     closeConnection();
/* 125 */     this.rrCallback.call(this);
/*     */   }
/*     */ 
/*     */   private void closeConnection() {
/*     */     try {
/* 130 */       this.con.close();
/*     */     }
/*     */     catch (SQLException ex) {
/* 133 */       this.logger.debug("Could not close JDBC Connection", ex);
/*     */     }
/*     */     catch (Throwable ex) {
/* 136 */       this.logger.debug("Unexpected exception on closing JDBC Connection", ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   Connection getConnection() {
/* 141 */     return this.con;
/*     */   }
/*     */ 
/*     */   void setMode(ProcessMode mode)
/*     */   {
/* 146 */     this.pMode = mode;
/*     */   }
/*     */ 
/*     */   void addContextEventListener(JdbcContextEventListener JdbcContextEventListener)
/*     */   {
/* 152 */     this.jcEventListener = JdbcContextEventListener;
/*     */   }
/*     */ 
/*     */   public static enum ProcessMode
/*     */   {
/*  26 */     LOCK, REALASE;
/*     */   }
/*     */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.JdbcContext
 * JD-Core Version:    0.6.2
 */