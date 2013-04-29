/*    */ package com.mce.domain.model.impl;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class DescriptionLine
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String name;
/*    */   private String value;
/*    */ 
/*    */   DescriptionLine()
/*    */   {
/*    */   }
/*    */ 
/*    */   public DescriptionLine(String key, String value)
/*    */   {
/* 19 */     this.name = key;
/* 20 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public String getValue() {
/* 24 */     return this.value;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 28 */     return this.name;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 33 */     if (this == o) return true;
/* 34 */     if ((o == null) || (getClass() != o.getClass())) return false;
/* 35 */     DescriptionLine other = (DescriptionLine)o;
/* 36 */     return getName() == other.getName();
/*    */   }
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 41 */     return getName().hashCode();
/*    */   }
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.domain.model.impl.DescriptionLine
 * JD-Core Version:    0.6.2
 */