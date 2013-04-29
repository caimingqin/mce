package com.mce.command;

import com.mce.core.inject.ClassVisitor;
import java.util.Map;

public abstract interface CommandClassVisitor extends ClassVisitor
{
  public abstract Map<String, Class<? extends Command>> getCommandMap();
}

/* Location:           E:\Zip2\mresouce\wmcore-0.6.9.jar
 * Qualified Name:     com.mce.command.CommandClassVisitor
 * JD-Core Version:    0.6.2
 */