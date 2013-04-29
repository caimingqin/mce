/*     */ package com.mce.util.jdbc.support;
/*     */ 
/*     */ import java.io.StringWriter;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Clob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Calendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ public class StatementCreatorUtils
/*     */ {
/*  21 */   private static final Log logger = LogFactory.getLog(StatementCreatorUtils.class);
/*     */ 
/*  23 */   private static Map<Class<?>, Integer> javaTypeToSqlTypeMap = new HashMap(32);
/*     */ 
/*     */   public static int javaTypeToSqlParameterType(Class<?> javaType)
/*     */   {
/*  58 */     Integer sqlType = (Integer)javaTypeToSqlTypeMap.get(javaType);
/*  59 */     if (sqlType != null) {
/*  60 */       return sqlType.intValue();
/*     */     }
/*  62 */     if (Number.class.isAssignableFrom(javaType)) {
/*  63 */       return 2;
/*     */     }
/*  65 */     if (isStringValue(javaType)) {
/*  66 */       return 12;
/*     */     }
/*  68 */     if ((isDateValue(javaType)) || (Calendar.class.isAssignableFrom(javaType))) {
/*  69 */       return 93;
/*     */     }
/*  71 */     return -2147483648;
/*     */   }
/*     */ 
/*     */   public static void setParameterValue(PreparedStatement ps, int paramIndex, int sqlType, Object inValue)
/*     */     throws SQLException
/*     */   {
/*  78 */     setParameterValueInternal(ps, paramIndex, sqlType, null, null, inValue);
/*     */   }
/*     */ 
/*     */   public static void setParameterValue(PreparedStatement ps, int paramIndex, int sqlType, String typeName, Object inValue)
/*     */     throws SQLException
/*     */   {
/*  85 */     setParameterValueInternal(ps, paramIndex, sqlType, typeName, null, inValue);
/*     */   }
/*     */ 
/*     */   private static void setParameterValueInternal(PreparedStatement ps, int paramIndex, int sqlType, String typeName, Integer scale, Object inValue)
/*     */     throws SQLException
/*     */   {
/*  92 */     String typeNameToUse = typeName;
/*  93 */     int sqlTypeToUse = sqlType;
/*  94 */     Object inValueToUse = inValue;
/*     */ 
/* 112 */     if (logger.isTraceEnabled()) {
/* 113 */       logger.trace("Setting SQL statement parameter value: column index " + paramIndex + ", parameter value [" + inValueToUse + "], value class [" + (inValueToUse != null ? inValueToUse.getClass().getName() : "null") + "], SQL type " + (sqlTypeToUse == -2147483648 ? "unknown" : Integer.toString(sqlTypeToUse)));
/*     */     }
/*     */ 
/* 118 */     if (inValueToUse == null) {
/* 119 */       setNull(ps, paramIndex, sqlTypeToUse, typeNameToUse);
/*     */     }
/*     */     else
/* 122 */       setValue(ps, paramIndex, sqlTypeToUse, typeNameToUse, scale, inValueToUse);
/*     */   }
/*     */ 
/*     */   private static void setNull(PreparedStatement ps, int paramIndex, int sqlType, String typeName)
/*     */     throws SQLException
/*     */   {
/* 133 */     if (sqlType == -2147483648) {
/* 134 */       boolean useSetObject = false;
/* 135 */       sqlType = 0;
/*     */       try {
/* 137 */         DatabaseMetaData dbmd = ps.getConnection().getMetaData();
/* 138 */         String databaseProductName = dbmd.getDatabaseProductName();
/* 139 */         String jdbcDriverName = dbmd.getDriverName();
/* 140 */         if ((databaseProductName.startsWith("Informix")) || (jdbcDriverName.startsWith("Microsoft SQL Server")))
/*     */         {
/* 142 */           useSetObject = true;
/*     */         }
/* 144 */         else if ((databaseProductName.startsWith("DB2")) || (jdbcDriverName.startsWith("jConnect")) || (jdbcDriverName.startsWith("SQLServer")) || (jdbcDriverName.startsWith("Apache Derby")))
/*     */         {
/* 148 */           sqlType = 12;
/*     */         }
/*     */       }
/*     */       catch (Throwable ex) {
/* 152 */         logger.debug("Could not check database or driver name", ex);
/*     */       }
/* 154 */       if (useSetObject) {
/* 155 */         ps.setObject(paramIndex, null);
/*     */       }
/*     */       else {
/* 158 */         ps.setNull(paramIndex, sqlType);
/*     */       }
/*     */     }
/* 161 */     else if (typeName != null) {
/* 162 */       ps.setNull(paramIndex, sqlType, typeName);
/*     */     }
/*     */     else {
/* 165 */       ps.setNull(paramIndex, sqlType);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void setValue(PreparedStatement ps, int paramIndex, int sqlType, String typeName, Integer scale, Object inValue)
/*     */     throws SQLException
/*     */   {
/* 172 */     if ((inValue instanceof SqlTypeValue)) {
/* 173 */       ((SqlTypeValue)inValue).setTypeValue(ps, paramIndex, sqlType, typeName);
/*     */     }
/* 175 */     else if ((sqlType == 12) || (sqlType == -1) || ((sqlType == 2005) && (isStringValue(inValue.getClass()))))
/*     */     {
/* 177 */       ps.setString(paramIndex, inValue.toString());
/*     */     }
/* 179 */     else if ((sqlType == 3) || (sqlType == 2)) {
/* 180 */       if ((inValue instanceof BigDecimal)) {
/* 181 */         ps.setBigDecimal(paramIndex, (BigDecimal)inValue);
/*     */       }
/* 183 */       else if (scale != null) {
/* 184 */         ps.setObject(paramIndex, inValue, sqlType, scale.intValue());
/*     */       }
/*     */       else {
/* 187 */         ps.setObject(paramIndex, inValue, sqlType);
/*     */       }
/*     */     }
/* 190 */     else if (sqlType == 91) {
/* 191 */       if ((inValue instanceof java.util.Date)) {
/* 192 */         if ((inValue instanceof java.sql.Date)) {
/* 193 */           ps.setDate(paramIndex, (java.sql.Date)inValue);
/*     */         }
/*     */         else {
/* 196 */           ps.setDate(paramIndex, new java.sql.Date(((java.util.Date)inValue).getTime()));
/*     */         }
/*     */       }
/* 199 */       else if ((inValue instanceof Calendar)) {
/* 200 */         Calendar cal = (Calendar)inValue;
/* 201 */         ps.setDate(paramIndex, new java.sql.Date(cal.getTime().getTime()), cal);
/*     */       }
/*     */       else {
/* 204 */         ps.setObject(paramIndex, inValue, 91);
/*     */       }
/*     */     }
/* 207 */     else if (sqlType == 92) {
/* 208 */       if ((inValue instanceof java.util.Date)) {
/* 209 */         if ((inValue instanceof Time)) {
/* 210 */           ps.setTime(paramIndex, (Time)inValue);
/*     */         }
/*     */         else {
/* 213 */           ps.setTime(paramIndex, new Time(((java.util.Date)inValue).getTime()));
/*     */         }
/*     */       }
/* 216 */       else if ((inValue instanceof Calendar)) {
/* 217 */         Calendar cal = (Calendar)inValue;
/* 218 */         ps.setTime(paramIndex, new Time(cal.getTime().getTime()), cal);
/*     */       }
/*     */       else {
/* 221 */         ps.setObject(paramIndex, inValue, 92);
/*     */       }
/*     */     }
/* 224 */     else if (sqlType == 93) {
/* 225 */       if ((inValue instanceof java.util.Date)) {
/* 226 */         if ((inValue instanceof Timestamp)) {
/* 227 */           ps.setTimestamp(paramIndex, (Timestamp)inValue);
/*     */         }
/*     */         else {
/* 230 */           ps.setTimestamp(paramIndex, new Timestamp(((java.util.Date)inValue).getTime()));
/*     */         }
/*     */       }
/* 233 */       else if ((inValue instanceof Calendar)) {
/* 234 */         Calendar cal = (Calendar)inValue;
/* 235 */         ps.setTimestamp(paramIndex, new Timestamp(cal.getTime().getTime()), cal);
/*     */       }
/*     */       else {
/* 238 */         ps.setObject(paramIndex, inValue, 93);
/*     */       }
/*     */     }
/* 241 */     else if (sqlType == -2147483648) {
/* 242 */       if (isStringValue(inValue.getClass())) {
/* 243 */         ps.setString(paramIndex, inValue.toString());
/*     */       }
/* 245 */       else if (isDateValue(inValue.getClass())) {
/* 246 */         ps.setTimestamp(paramIndex, new Timestamp(((java.util.Date)inValue).getTime()));
/*     */       }
/* 248 */       else if ((inValue instanceof Calendar)) {
/* 249 */         Calendar cal = (Calendar)inValue;
/* 250 */         ps.setTimestamp(paramIndex, new Timestamp(cal.getTime().getTime()), cal);
/*     */       }
/*     */       else
/*     */       {
/* 254 */         ps.setObject(paramIndex, inValue);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 259 */       ps.setObject(paramIndex, inValue, sqlType);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static boolean isStringValue(Class<?> inValueType)
/*     */   {
/* 268 */     return (CharSequence.class.isAssignableFrom(inValueType)) || (StringWriter.class.isAssignableFrom(inValueType));
/*     */   }
/*     */ 
/*     */   private static boolean isDateValue(Class<?> inValueType)
/*     */   {
/* 273 */     return (java.util.Date.class.isAssignableFrom(inValueType)) && (!java.sql.Date.class.isAssignableFrom(inValueType)) && (!Time.class.isAssignableFrom(inValueType)) && (!Timestamp.class.isAssignableFrom(inValueType));
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  30 */     javaTypeToSqlTypeMap.put(Byte.TYPE, Integer.valueOf(-6));
/*  31 */     javaTypeToSqlTypeMap.put(Byte.class, Integer.valueOf(-6));
/*  32 */     javaTypeToSqlTypeMap.put(Short.TYPE, Integer.valueOf(5));
/*  33 */     javaTypeToSqlTypeMap.put(Short.class, Integer.valueOf(5));
/*  34 */     javaTypeToSqlTypeMap.put(Integer.TYPE, Integer.valueOf(4));
/*  35 */     javaTypeToSqlTypeMap.put(Integer.class, Integer.valueOf(4));
/*  36 */     javaTypeToSqlTypeMap.put(Long.TYPE, Integer.valueOf(-5));
/*  37 */     javaTypeToSqlTypeMap.put(Long.class, Integer.valueOf(-5));
/*  38 */     javaTypeToSqlTypeMap.put(BigInteger.class, Integer.valueOf(-5));
/*  39 */     javaTypeToSqlTypeMap.put(Float.TYPE, Integer.valueOf(6));
/*  40 */     javaTypeToSqlTypeMap.put(Float.class, Integer.valueOf(6));
/*  41 */     javaTypeToSqlTypeMap.put(Double.TYPE, Integer.valueOf(8));
/*  42 */     javaTypeToSqlTypeMap.put(Double.class, Integer.valueOf(8));
/*  43 */     javaTypeToSqlTypeMap.put(BigDecimal.class, Integer.valueOf(3));
/*  44 */     javaTypeToSqlTypeMap.put(java.sql.Date.class, Integer.valueOf(91));
/*  45 */     javaTypeToSqlTypeMap.put(Time.class, Integer.valueOf(92));
/*  46 */     javaTypeToSqlTypeMap.put(Timestamp.class, Integer.valueOf(93));
/*  47 */     javaTypeToSqlTypeMap.put(Blob.class, Integer.valueOf(2004));
/*  48 */     javaTypeToSqlTypeMap.put(Clob.class, Integer.valueOf(2005));
/*     */   }
/*     */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.util.jdbc.support.StatementCreatorUtils
 * JD-Core Version:    0.6.2
 */