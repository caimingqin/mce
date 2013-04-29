/*     */ package com.mce.core.notification;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CopyOnWriteArraySet;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import jsr166y.LinkedTransferQueue;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ class NotifyRunner
/*     */   implements Runnable
/*     */ {
/*  18 */   private LinkedTransferQueue<Notification> pool = new LinkedTransferQueue();
/*     */ 
/*  20 */   private boolean start = true;
/*  21 */   private Set<NotificationListener> listeners = new CopyOnWriteArraySet();
/*  22 */   private Log logger = LogFactory.getLog(getClass().getName());
/*  23 */   private AtomicBoolean started = new AtomicBoolean(false);
/*     */   private Executor executor;
/*     */ 
/*     */   NotifyRunner()
/*     */   {
/*     */   }
/*     */ 
/*     */   NotifyRunner(NotificationListener nl, Executor executor)
/*     */   {
/*  30 */     this(new NotificationListener[] { nl }, executor);
/*     */   }
/*     */ 
/*     */   NotifyRunner(NotificationListener[] nl, Executor executor) {
/*  34 */     Validate.notEmpty(nl);
/*  35 */     Validate.notNull(executor);
/*  36 */     this.executor = executor;
/*  37 */     for (NotificationListener n : nl)
/*  38 */       this.listeners.add(n);
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/*  44 */     while (this.start) {
/*  45 */       Notification ntf = null;
/*     */       try {
/*  47 */         ntf = (Notification)this.pool.take();
/*     */       } catch (InterruptedException e) {
/*  49 */         e.printStackTrace();
/*     */       }
/*  51 */       if (ntf != null) {
/*  52 */         proceess(ntf);
/*     */       }
/*     */     }
/*  55 */     if (this.pool.size() > 0)
/*  56 */       for (Notification n : this.pool)
/*  57 */         proceess(n);
/*     */   }
/*     */ 
/*     */   private void proceess(Notification n)
/*     */   {
/*  63 */     for (NotificationListener nl : this.listeners)
/*  64 */       handeNotify(nl, n, this.logger);
/*     */   }
/*     */ 
/*     */   private void pushNotification(Notification n)
/*     */   {
/*     */     try {
/*  70 */       this.pool.transfer(n);
/*     */     } catch (InterruptedException e) {
/*  72 */       this.logger.error(e.getMessage());
/*     */     }
/*     */ 
/*  75 */     if (Notification.isStopNotify(n))
/*  76 */       stop();
/*     */   }
/*     */ 
/*     */   private void stop()
/*     */   {
/*  81 */     this.start = false;
/*     */   }
/*     */ 
/*     */   boolean isStop() {
/*  85 */     return (!this.start) && (this.pool.isEmpty());
/*     */   }
/*     */ 
/*     */   static void handeNotify(NotificationListener nl, Notification n, Log logger) {
/*     */     try {
/*  90 */       nl.handle(n);
/*     */     } catch (RuntimeException re) {
/*  92 */       logger.error(re); } catch (Exception e) {
/*  93 */       logger.error(e); } catch (Throwable t) {
/*  94 */       logger.error(t);
/*     */     }
/*     */   }
/*     */ 
/*     */   static void broadcast(List<NotifyRunner> nrs, Notification nf) {
/*  99 */     Validate.notNull(nrs);
/* 100 */     Validate.notNull(nf);
/* 101 */     for (NotifyRunner nr : nrs)
/* 102 */       nr.pushNotification(nf);
/*     */   }
/*     */ 
/*     */   void start()
/*     */   {
/* 108 */     if (this.started.compareAndSet(false, true))
/*     */     {
/* 110 */       this.executor.execute(this);
/*     */ 
/* 112 */       Notification nf = new Notification("observerMessage", NotificationStatus.PREPARE);
/* 113 */       pushNotification(nf);
/*     */ 
/* 115 */       Notification snf = new Notification("observerMessage", NotificationStatus.START);
/* 116 */       pushNotification(snf);
/*     */     }
/*     */     else {
/* 119 */       throw new IllegalStateException("The NotifyRunner is Running now");
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.core.notification.NotifyRunner
 * JD-Core Version:    0.6.2
 */