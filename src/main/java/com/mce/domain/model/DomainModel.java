package com.mce.domain.model;

import java.util.Date;

public abstract interface DomainModel
{
  public abstract String getSessionId();

  public abstract Date getCreated();
}

