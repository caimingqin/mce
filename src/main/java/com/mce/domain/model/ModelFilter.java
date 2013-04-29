package com.mce.domain.model;

import java.util.List;

public abstract interface ModelFilter<T>
{
  public abstract List<T> filter(List<T> paramList);
}

