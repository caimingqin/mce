package com.mce.command;

public abstract interface UploadFile
{
  public abstract String getName();

  public abstract byte[] getUploadByte();

  public abstract void delete();
}

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.command.UploadFile
 * JD-Core Version:    0.6.2
 */