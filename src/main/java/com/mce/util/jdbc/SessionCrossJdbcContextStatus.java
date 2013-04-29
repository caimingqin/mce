package com.mce.util.jdbc;

public class SessionCrossJdbcContextStatus extends JdbcContextStatus
{
  private SessionCrossJdbcContextEventListener listener;

  public SessionCrossJdbcContextStatus()
  {
    this.listener = new SessionCrossJdbcContextEventListener();
  }

  protected JdbcContextEventListener getJdbcContextEventListener()
  {
    return this.listener;
  }

  private class SessionCrossJdbcContextEventListener
    implements JdbcContextEventListener
  {
    private SessionCrossJdbcContextEventListener()
    {
    }

    public void onEvent(String type, JdbcContext jc)
    {
      if ("onClear".equalsIgnoreCase(type))
        jc.clear();
    }
  }
}

