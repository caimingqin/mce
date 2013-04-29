/*    */ package com.mce.domain.model.impl;
/*    */ 
/*    */ import com.mce.util.jackson.JacksonUtils;
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import org.codehaus.jackson.type.TypeReference;
/*    */ 
/*    */ public class Descriptor
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final String DESCRIPTION_STR_KEY = "description_str";
/* 19 */   private List<DescriptionLine> lines = new ArrayList();
/*    */ 
/*    */   public Descriptor() {
/*    */   }
/*    */ 
/*    */   public Descriptor(String des) {
/* 25 */     if ((des != null) && (des.length() > 0))
/*    */     {
/* 27 */       List lines = JacksonUtils.convertToList(des, new TypeReference()
/*    */       {
/*    */       });
/* 28 */       this.lines.addAll(lines);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void add(DescriptionLine descriptionLine) {
/* 33 */     for (DescriptionLine dl : this.lines) {
/* 34 */       if (dl.getName().equalsIgnoreCase(descriptionLine.getName())) {
/* 35 */         this.lines.remove(dl);
/* 36 */         break;
/*    */       }
/*    */     }
/* 39 */     this.lines.add(descriptionLine);
/*    */   }
/*    */ 
/*    */   public List<DescriptionLine> getAll()
/*    */   {
/* 44 */     return Collections.unmodifiableList(this.lines);
/*    */   }
/*    */ 
/*    */   public String getDescriptionLine(String key) {
/* 48 */     for (DescriptionLine dl : this.lines) {
/* 49 */       if (dl.getName().equalsIgnoreCase(key)) {
/* 50 */         return dl.getValue();
/*    */       }
/*    */     }
/* 53 */     return null;
/*    */   }
/*    */ 
/*    */   public String toJSONString() {
/* 57 */     return JacksonUtils.writeValueString(this.lines);
/*    */   }
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.domain.model.impl.Descriptor
 * JD-Core Version:    0.6.2
 */