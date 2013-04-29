package com.mce.core.notification;

import java.util.List;

public abstract interface Notifier
{
  public static final String OBSERVER_MESSAGE = "observerMessage";

  public abstract void shutdown(String paramString);

  public abstract NotifyBox start(String paramString, List<NotificationListener> paramList);

  public abstract boolean isRunning();
}

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.core.notification.Notifier
 * JD-Core Version:    0.6.2
 */