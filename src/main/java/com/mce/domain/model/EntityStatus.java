/*    */ package com.mce.domain.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public final class EntityStatus
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private int code;
/*    */   private String name;
/* 16 */   public static final EntityStatus NORMAL = new EntityStatus(1, "NORMAL");
/* 17 */   public static final EntityStatus DELETED = new EntityStatus(-1, "DELETED");
/* 18 */   public static final EntityStatus LOCK = new EntityStatus(0, "LOCK");
/*    */ 
/* 20 */   private static final List<EntityStatus> all = new ArrayList();
/*    */ 
/*    */   private EntityStatus(int code, String name)
/*    */   {
/* 30 */     this.code = code;
/* 31 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public int getCode() {
/* 35 */     return this.code;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 39 */     return this.name;
/*    */   }
/*    */ 
/*    */   public static EntityStatus get(int code)
/*    */   {
/* 44 */     for (EntityStatus es : all) {
/* 45 */       if (es.getCode() == code) {
/* 46 */         return es;
/*    */       }
/*    */     }
/* 49 */     return null;
/*    */   }
/*    */ 
/*    */   public static boolean isNormal(EntityStatus entityStatus) {
/* 53 */     return NORMAL.equals(entityStatus);
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 23 */     all.add(NORMAL);
/* 24 */     all.add(DELETED);
/* 25 */     all.add(LOCK);
/*    */   }
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.domain.model.EntityStatus
 * JD-Core Version:    0.6.2
 */