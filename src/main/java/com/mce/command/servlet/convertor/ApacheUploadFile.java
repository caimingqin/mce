/*    */ package com.mce.command.servlet.convertor;
/*    */ 
/*    */ import com.mce.command.UploadFile;
/*    */ import org.apache.commons.fileupload.FileItem;
/*    */ 
/*    */ public class ApacheUploadFile
/*    */   implements UploadFile
/*    */ {
/*    */   private boolean deleted;
/*    */   private FileItem fItem;
/*    */ 
/*    */   public ApacheUploadFile(FileItem fi)
/*    */   {
/* 12 */     this.fItem = fi;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 17 */     return this.fItem.getName();
/*    */   }
/*    */ 
/*    */   public byte[] getUploadByte()
/*    */   {
/* 22 */     return this.fItem.get();
/*    */   }
/*    */ 
/*    */   public void delete()
/*    */   {
/* 27 */     if (this.deleted)
/* 28 */       this.fItem.delete();
/*    */   }
/*    */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.command.servlet.convertor.ApacheUploadFile
 * JD-Core Version:    0.6.2
 */