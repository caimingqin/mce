package com.mce.domain.model;

public abstract interface Entity
{
  public abstract String getSessionId();

  public abstract EntityStatus getStatus();
}

