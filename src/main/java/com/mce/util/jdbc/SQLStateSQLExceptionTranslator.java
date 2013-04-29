/*     */ package com.mce.util.jdbc;
/*     */ 
/*     */ import com.mce.util.jdbc.support.BadSqlGrammarException;
/*     */ import com.mce.util.jdbc.support.DataIntegrityViolationException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.springframework.util.Assert;
/*     */ 
/*     */ public class SQLStateSQLExceptionTranslator
/*     */ {
/*  16 */   protected final Log logger = LogFactory.getLog(getClass());
/*     */   private SQLExceptionTranslator fallbackTranslator;
/*  19 */   private static final Set<String> BAD_SQL_GRAMMAR_CODES = new HashSet(8);
/*     */ 
/*  21 */   private static final Set<String> DATA_INTEGRITY_VIOLATION_CODES = new HashSet(8);
/*     */ 
/*  23 */   private static final Set<String> DATA_ACCESS_RESOURCE_FAILURE_CODES = new HashSet(8);
/*     */ 
/*  25 */   private static final Set<String> TRANSIENT_DATA_ACCESS_RESOURCE_CODES = new HashSet(8);
/*     */ 
/*  27 */   private static final Set<String> CONCURRENCY_FAILURE_CODES = new HashSet(4);
/*     */ 
/*     */   protected DataAccessException doTranslate(String task, String sql, SQLException ex)
/*     */   {
/*  62 */     String sqlState = getSqlState(ex);
/*  63 */     if ((sqlState != null) && (sqlState.length() >= 2)) {
/*  64 */       String classCode = sqlState.substring(0, 2);
/*  65 */       if (this.logger.isDebugEnabled()) {
/*  66 */         this.logger.debug("Extracted SQL state class '" + classCode + "' from value '" + sqlState + "'");
/*     */       }
/*  68 */       if (BAD_SQL_GRAMMAR_CODES.contains(classCode)) {
/*  69 */         return new BadSqlGrammarException(task, sql, ex);
/*     */       }
/*  71 */       if (DATA_INTEGRITY_VIOLATION_CODES.contains(classCode)) {
/*  72 */         return new DataIntegrityViolationException(buildMessage(task, sql, ex), ex);
/*     */       }
/*  74 */       if (DATA_ACCESS_RESOURCE_FAILURE_CODES.contains(classCode)) {
/*  75 */         return new DataAccessException(buildMessage(task, sql, ex), ex);
/*     */       }
/*  77 */       if (TRANSIENT_DATA_ACCESS_RESOURCE_CODES.contains(classCode)) {
/*  78 */         return new DataAccessException(buildMessage(task, sql, ex), ex);
/*     */       }
/*  80 */       if (CONCURRENCY_FAILURE_CODES.contains(classCode)) {
/*  81 */         return new DataAccessException(buildMessage(task, sql, ex), ex);
/*     */       }
/*     */     }
/*  84 */     return null;
/*     */   }
/*     */ 
/*     */   private String getSqlState(SQLException ex)
/*     */   {
/*  96 */     String sqlState = ex.getSQLState();
/*  97 */     if (sqlState == null) {
/*  98 */       SQLException nestedEx = ex.getNextException();
/*  99 */       if (nestedEx != null) {
/* 100 */         sqlState = nestedEx.getSQLState();
/*     */       }
/*     */     }
/* 103 */     return sqlState;
/*     */   }
/*     */ 
/*     */   public void setFallbackTranslator(SQLExceptionTranslator fallback)
/*     */   {
/* 112 */     this.fallbackTranslator = fallback;
/*     */   }
/*     */ 
/*     */   public SQLExceptionTranslator getFallbackTranslator()
/*     */   {
/* 119 */     return this.fallbackTranslator;
/*     */   }
/*     */ 
/*     */   public DataAccessException translate(String task, String sql, SQLException ex)
/*     */   {
/* 128 */     Assert.notNull(ex, "Cannot translate a null SQLException");
/* 129 */     if (task == null) {
/* 130 */       task = "";
/*     */     }
/* 132 */     if (sql == null) {
/* 133 */       sql = "";
/*     */     }
/* 135 */     DataAccessException dex = doTranslate(task, sql, ex);
/* 136 */     if (dex != null) {
/* 137 */       return dex;
/*     */     }
/* 139 */     SQLExceptionTranslator fallback = getFallbackTranslator();
/* 140 */     if (fallback != null) {
/* 141 */       return fallback.translate(task, sql, ex);
/*     */     }
/* 143 */     return new DataAccessException(buildMessage(task, sql, ex));
/*     */   }
/*     */ 
/*     */   protected String buildMessage(String task, String sql, SQLException ex) {
/* 147 */     return task + "; SQL [" + sql + "]; " + ex.getMessage();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  31 */     BAD_SQL_GRAMMAR_CODES.add("07");
/*  32 */     BAD_SQL_GRAMMAR_CODES.add("21");
/*  33 */     BAD_SQL_GRAMMAR_CODES.add("2A");
/*  34 */     BAD_SQL_GRAMMAR_CODES.add("37");
/*  35 */     BAD_SQL_GRAMMAR_CODES.add("42");
/*  36 */     BAD_SQL_GRAMMAR_CODES.add("65");
/*  37 */     BAD_SQL_GRAMMAR_CODES.add("S0");
/*     */ 
/*  39 */     DATA_INTEGRITY_VIOLATION_CODES.add("01");
/*  40 */     DATA_INTEGRITY_VIOLATION_CODES.add("02");
/*  41 */     DATA_INTEGRITY_VIOLATION_CODES.add("22");
/*  42 */     DATA_INTEGRITY_VIOLATION_CODES.add("23");
/*  43 */     DATA_INTEGRITY_VIOLATION_CODES.add("27");
/*  44 */     DATA_INTEGRITY_VIOLATION_CODES.add("44");
/*     */ 
/*  46 */     DATA_ACCESS_RESOURCE_FAILURE_CODES.add("08");
/*  47 */     DATA_ACCESS_RESOURCE_FAILURE_CODES.add("53");
/*  48 */     DATA_ACCESS_RESOURCE_FAILURE_CODES.add("54");
/*  49 */     DATA_ACCESS_RESOURCE_FAILURE_CODES.add("57");
/*  50 */     DATA_ACCESS_RESOURCE_FAILURE_CODES.add("58");
/*     */ 
/*  52 */     TRANSIENT_DATA_ACCESS_RESOURCE_CODES.add("JW");
/*  53 */     TRANSIENT_DATA_ACCESS_RESOURCE_CODES.add("JZ");
/*  54 */     TRANSIENT_DATA_ACCESS_RESOURCE_CODES.add("S1");
/*     */ 
/*  56 */     CONCURRENCY_FAILURE_CODES.add("40");
/*  57 */     CONCURRENCY_FAILURE_CODES.add("61");
/*     */   }
/*     */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.SQLStateSQLExceptionTranslator
 * JD-Core Version:    0.6.2
 */