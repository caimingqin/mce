/*     */ package com.mce.core.notification;
/*     */ 
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.Executors;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ public class DefaultNotifier
/*     */   implements Notifier
/*     */ {
/*  16 */   private NotificationStatus status = NotificationStatus.PREPARE;
/*  17 */   private String sessionId = null;
/*  18 */   private Log logger = LogFactory.getLog(getClass().getName());
/*     */ 
/*  21 */   private Executor executor = Executors.newCachedThreadPool();
/*     */ 
/*  25 */   private List<NotifyRunner> nrs = new LinkedList();
/*     */ 
/*     */   void addNotificationListener(NotificationListener nl)
/*     */   {
/*  35 */     Validate.notNull(nl, "not allowed null notificaitonListener to add[" + getClass().getName() + "]");
/*  36 */     NotifyFilter nf = DefaultNotifyFilter.get().getFilter(nl);
/*  37 */     NotifyRunner nr = new NotifyRunner(new FilterNitifierListener(nl, nf), this.executor);
/*  38 */     this.nrs.add(nr);
/*     */   }
/*     */ 
/*     */   private void broadcast(Notification nf)
/*     */   {
/*  43 */     NotifyRunner.broadcast(this.nrs, nf);
/*     */   }
/*     */ 
/*     */   private void addAllNotificationListeners(List<NotificationListener> nll) {
/*  47 */     for (NotificationListener nl : nll)
/*  48 */       addNotificationListener(nl);
/*     */   }
/*     */ 
/*     */   public void shutdown(String sId)
/*     */   {
/*  55 */     this.logger.warn("Stop now");
/*     */ 
/*  57 */     synchronized (this) {
/*  58 */       if (NotificationStatus.START.equals(this.status)) {
/*  59 */         if (!this.sessionId.equalsIgnoreCase(sId)) {
/*  60 */           throw new IllegalArgumentException("Can not stop NotificationObserver[" + sId + "]");
/*     */         }
/*  62 */         this.status = NotificationStatus.STOP;
/*  63 */         this.logger.warn("Start Stopping NotificationListeners ");
/*  64 */         Notification stopNtf = new Notification("observerMessage", NotificationStatus.STOP);
/*  65 */         broadcast(stopNtf);
/*  66 */         waitStop();
/*     */ 
/*  68 */         this.logger.warn("Stop notificationObserver successfully");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void waitStop()
/*     */   {
/*  75 */     int count = 0;
/*  76 */     int nrSize = this.nrs.size();
/*     */ 
/*  78 */     long timeOutValue = System.currentTimeMillis();
/*  79 */     timeOutValue += 60000L;
/*     */ 
/*  81 */     boolean breakStatus = false;
/*     */ 
/*  83 */     this.logger.info("Stop NotifyRunners");
/*     */     while (true) {
/*  85 */       for (NotifyRunner nr : this.nrs) {
/*  86 */         if (nr.isStop()) {
/*  87 */           count += 1;
/*     */         }
/*     */       }
/*     */ 
/*  91 */       long cTimeOutValue = System.currentTimeMillis();
/*  92 */       if (timeOutValue > cTimeOutValue) {
/*  93 */         if (nrSize > count) {
/*  94 */           count = 0;
/*     */           try {
/*  96 */             wait(5000L);
/*     */           } catch (InterruptedException e) {
/*  98 */             this.logger.trace(e);
/*     */           }
/*     */         }
/*     */         else {
/* 102 */           breakStatus = true;
/*     */         }
/*     */       }
/*     */       else {
/* 106 */         breakStatus = true;
/*     */       }
/*     */ 
/* 109 */       if (breakStatus)
/*     */         break;
/*     */     }
/*     */   }
/*     */ 
/*     */   public NotifyBox start(String sessionId, List<NotificationListener> nll)
/*     */   {
/* 118 */     synchronized (this) {
/* 119 */       if (NotificationStatus.PREPARE.equals(this.status)) {
/* 120 */         this.sessionId = sessionId;
/* 121 */         this.status = NotificationStatus.START;
/*     */ 
/* 123 */         addAllNotificationListeners(nll);
/* 124 */         startRunners();
/* 125 */         return new NotifyBox()
/*     */         {
/*     */           public boolean add(Notification n) {
/* 128 */             if (NotificationStatus.STOP.equals(DefaultNotifier.this.status)) {
/* 129 */               return false;
/*     */             }
/* 131 */             DefaultNotifier.this.broadcast(n);
/* 132 */             return true;
/*     */           }
/*     */ 
/*     */         };
/*     */       }
/*     */ 
/* 138 */       throw new IllegalStateException("Call start method once please");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void startRunners()
/*     */   {
/* 144 */     for (NotifyRunner nr : this.nrs)
/* 145 */       nr.start();
/*     */   }
/*     */ 
/*     */   public boolean isRunning()
/*     */   {
/* 151 */     return NotificationStatus.START.equals(this.status);
/*     */   }
/*     */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.core.notification.DefaultNotifier
 * JD-Core Version:    0.6.2
 */