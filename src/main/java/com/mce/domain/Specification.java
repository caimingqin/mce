package com.mce.domain;

public abstract interface Specification<T>
{
  public abstract boolean isSatisfiedBy(T paramT);
}

