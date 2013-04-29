package com.mce.domain.event.handler;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.TYPE})
@Inherited
@Documented
public @interface AutoEventHandler
{
  public abstract String name();

  public abstract String[] interceptors();

  public abstract boolean isGrouping();
}

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.domain.event.handler.AutoEventHandler
 * JD-Core Version:    0.6.2
 */