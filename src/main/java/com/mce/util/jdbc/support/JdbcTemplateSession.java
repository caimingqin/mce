/*     */ package com.mce.util.jdbc.support;
/*     */ 
/*     */ import com.mce.util.jdbc.DataAccessException;
/*     */ import com.mce.util.jdbc.JdbcContext;
/*     */ import com.mce.util.jdbc.JdbcContextFactory;
/*     */ import com.mce.util.jdbc.JdbcSession;
/*     */ import com.mce.util.jdbc.JdbcUtils;
/*     */ import com.mce.util.jdbc.PreparedStatementCallback;
/*     */ import com.mce.util.jdbc.PreparedStatementCreator;
/*     */ import com.mce.util.jdbc.RowCallbackHandler;
/*     */ import com.mce.util.jdbc.SqlProvider;
/*     */ import com.mce.util.jdbc.StatementCallback;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.springframework.util.Assert;
/*     */ 
/*     */ public class JdbcTemplateSession
/*     */   implements JdbcSession
/*     */ {
/*  28 */   private static Log logger = LogFactory.getLog(JdbcTemplateSession.class.getName());
/*  29 */   private JdbcContextFactory jdcf = null;
/*     */ 
/*     */   public JdbcTemplateSession(JdbcContextFactory jsf) {
/*  32 */     this.jdcf = jsf;
/*     */   }
/*     */ 
/*     */   public void query(String sql, Object[] params, final RowCallbackHandler rch)
/*     */   {
/*  37 */     Assert.notNull(sql, "SQL must not be null");
/*  38 */     if (logger.isDebugEnabled()) {
/*  39 */       logger.debug("Executing SQL query [" + sql + "]");
/*     */     }
/*  41 */     logger.debug("Executing prepared SQL query");
/*  42 */     JdbcContext jcc = this.jdcf.getCurrentJdbcContext();
/*  43 */     final PreparedStatementSetter pss = newArgPreparedStatementSetter(params);
/*  44 */     PreparedStatementCreator psc = new SimplePreparedStatementCreator(sql);
/*  45 */     jcc.executeSQL(psc, new PreparedStatementCallback()
/*     */     {
/*     */       public void doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException
/*     */       {
/*  49 */         ResultSet rs = null;
/*     */         try {
/*  51 */           if (pss != null) {
/*  52 */             pss.setValues(ps);
/*     */           }
/*  54 */           rs = ps.executeQuery();
/*  55 */           JdbcUtils.processResultHandler(rs, rch);
/*     */         }
/*     */         finally {
/*  58 */           JdbcUtils.closeResultSet(rs);
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void query(final String sql, final RowCallbackHandler rch)
/*     */   {
/*  66 */     JdbcContext jcc = this.jdcf.getCurrentJdbcContext();
/*  67 */     jcc.execute(new StatementCallback() {
/*     */       public void doInStatement(Statement stmt) throws SQLException {
/*  69 */         ResultSet rs = null;
/*     */         try {
/*  71 */           rs = stmt.executeQuery(sql);
/*  72 */           JdbcUtils.processResultHandler(rs, rch);
/*     */         }
/*     */         finally {
/*  75 */           JdbcUtils.closeResultSet(rs);
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public int executeUpdate(String string, Object[] objects)
/*     */   {
/*  83 */     JdbcUpdater ju = new JdbcUpdater(string, objects);
/*  84 */     return ju.executeUpdate();
/*     */   }
/*     */ 
/*     */   public int[] batchUpdater(String string, BatchPreparedStatementSetter pss) {
/*  88 */     PJdbcUpdater pu = new PJdbcUpdater(string, pss);
/*  89 */     return pu.batchUpdate();
/*     */   }
/*     */ 
/*     */   protected PreparedStatementSetter newArgPreparedStatementSetter(Object[] args)
/*     */   {
/* 186 */     return new ArgPreparedStatementSetter(args);
/*     */   }
/*     */ 
/*     */   private static class SimplePreparedStatementCreator
/*     */     implements PreparedStatementCreator, SqlProvider
/*     */   {
/*     */     private final String sql;
/*     */ 
/*     */     public SimplePreparedStatementCreator(String sql)
/*     */     {
/* 174 */       Assert.notNull(sql, "SQL must not be null");
/* 175 */       this.sql = sql;
/*     */     }
/*     */     public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
/* 178 */       return con.prepareStatement(this.sql);
/*     */     }
/*     */     public String getSql() {
/* 181 */       return this.sql;
/*     */     }
/*     */   }
/*     */ 
/*     */   private class PJdbcUpdater
/*     */     implements PreparedStatementCallback
/*     */   {
/*     */     private String sql;
/*     */     private BatchPreparedStatementSetter pss;
/*     */     private int[] rows;
/*     */ 
/*     */     public PJdbcUpdater(String sql, BatchPreparedStatementSetter pss)
/*     */     {
/* 127 */       this.sql = sql;
/* 128 */       this.pss = pss;
/*     */     }
/*     */ 
/*     */     public int[] batchUpdate() {
/* 132 */       JdbcContext jcc = JdbcTemplateSession.this.jdcf.getCurrentJdbcContext();
/* 133 */       jcc.executeSQL(new JdbcTemplateSession.SimplePreparedStatementCreator(this.sql), this);
/* 134 */       return this.rows;
/*     */     }
/*     */ 
/*     */     public void doInPreparedStatement(PreparedStatement ps)
/*     */       throws SQLException, DataAccessException
/*     */     {
/* 141 */       if (JdbcTemplateSession.logger.isDebugEnabled()) {
/* 142 */         JdbcTemplateSession.logger.debug("Executing SQL batch update [" + this.sql + "]");
/*     */       }
/* 144 */       int batchSize = this.pss.getBatchSize();
/* 145 */       this.rows = new int[batchSize];
/* 146 */       if (JdbcUtils.supportsBatchUpdates(ps.getConnection())) {
/* 147 */         for (int i = 0; i < batchSize; i++) {
/* 148 */           this.pss.setValues(ps, i);
/* 149 */           ps.addBatch();
/*     */         }
/* 151 */         this.rows = ps.executeBatch();
/*     */       }
/*     */       else {
/* 154 */         List rowsAffected = new ArrayList();
/* 155 */         for (int i = 0; i < batchSize; i++) {
/* 156 */           this.pss.setValues(ps, i);
/* 157 */           rowsAffected.add(Integer.valueOf(ps.executeUpdate()));
/*     */         }
/* 159 */         int[] rowsAffectedArray = new int[rowsAffected.size()];
/* 160 */         for (int i = 0; i < rowsAffectedArray.length; i++) {
/* 161 */           rowsAffectedArray[i] = ((Integer)rowsAffected.get(i)).intValue();
/*     */         }
/* 163 */         this.rows = rowsAffectedArray;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private class JdbcUpdater
/*     */     implements JdbcUpdaterHandler, PreparedStatementCallback
/*     */   {
/*     */     private String sql;
/*     */     private Object[] args;
/*  95 */     private int resultCount = 0;
/*     */ 
/*  97 */     public JdbcUpdater(String sql, Object[] args) { this.sql = sql;
/*  98 */       this.args = args; }
/*     */ 
/*     */     public int executeUpdate()
/*     */     {
/* 102 */       JdbcContext jdc = JdbcTemplateSession.this.jdcf.getCurrentJdbcContext();
/* 103 */       PreparedStatementCreator psc = new JdbcTemplateSession.SimplePreparedStatementCreator(this.sql);
/* 104 */       jdc.executeSQL(psc, this);
/* 105 */       return this.resultCount;
/*     */     }
/*     */ 
/*     */     public void doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException
/*     */     {
/* 110 */       PreparedStatementSetter pss = JdbcTemplateSession.this.newArgPreparedStatementSetter(this.args);
/* 111 */       if (ps != null) {
/* 112 */         pss.setValues(ps);
/*     */       }
/* 114 */       this.resultCount = ps.executeUpdate();
/* 115 */       if (JdbcTemplateSession.logger.isDebugEnabled())
/* 116 */         JdbcTemplateSession.logger.debug("SQL update affected " + this.resultCount + " rows");
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.support.JdbcTemplateSession
 * JD-Core Version:    0.6.2
 */