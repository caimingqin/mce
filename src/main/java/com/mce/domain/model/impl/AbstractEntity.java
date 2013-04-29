/*    */ package com.mce.domain.model.impl;
/*    */ 
/*    */ import com.mce.domain.IdUtils;
/*    */ import com.mce.domain.model.Entity;
/*    */ import com.mce.domain.model.EntityStatus;
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ import org.apache.commons.lang.Validate;
/*    */ 
/*    */ public abstract class AbstractEntity
/*    */   implements Serializable, Entity
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 18 */   private String sessionId = IdUtils.getUUID();
/* 19 */   private Date created = new Date();
/* 20 */   private Descriptor descriptor = new Descriptor();
/* 21 */   private EntityStatus status = EntityStatus.NORMAL;
/*    */ 
/*    */   protected AbstractEntity()
/*    */   {
/*    */   }
/*    */ 
/*    */   public AbstractEntity(String sessionId, Date created, Descriptor des, EntityStatus status)
/*    */   {
/* 30 */     Validate.notEmpty(new Object[] { sessionId, created, des, status });
/* 31 */     this.sessionId = sessionId;
/* 32 */     this.created = created;
/* 33 */     this.descriptor = des;
/* 34 */     this.status = status;
/*    */   }
/*    */ 
/*    */   public AbstractEntity(Date created, Descriptor des) {
/* 38 */     this(IdUtils.getUUID(), created, des, EntityStatus.NORMAL);
/*    */   }
/*    */ 
/*    */   public AbstractEntity(String ds) {
/* 42 */     this(new Date(), new Descriptor(ds));
/*    */   }
/*    */ 
/*    */   public String getSessionId() {
/* 46 */     return this.sessionId;
/*    */   }
/*    */ 
/*    */   public EntityStatus getStatus() {
/* 50 */     return this.status;
/*    */   }
/*    */ 
/*    */   public Date getCreated() {
/* 54 */     return this.created;
/*    */   }
/*    */ 
/*    */   protected void addDescription(DescriptionLine descriptionLine) {
/* 58 */     this.descriptor.add(descriptionLine);
/*    */   }
/*    */ 
/*    */   public Descriptor getDescriptor() {
/* 62 */     return this.descriptor;
/*    */   }
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.domain.model.impl.AbstractEntity
 * JD-Core Version:    0.6.2
 */