package com.mce.util.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionJdbcContextStatus extends JdbcContextStatus
{
  private TransactionJdbcContextEventListener tjceListener = new TransactionJdbcContextEventListener();
  private TranCommintResult tcResult = TranCommintResult.None;

  public TranCommintResult getTransactionResult()
  {
    return this.tcResult;
  }

  public void end(Connection con)
  {
    try
    {
      getLogger().debug("Commit Transaction ");
      con.commit();
      this.tcResult = TranCommintResult.SUCCESS;
    } catch (SQLException e) {
      try {
        con.rollback();
      } catch (SQLException e1) {
        getLogger().error("Rollback Connection Error", e1);
      }
      finally {
        getLogger().warn("Commit Error", e);
        this.tcResult = TranCommintResult.FAILED;
      }
    }
    finally {
      getLogger().info("Set Connection Auto true");
      setConnectionStatus(con, true);
    }
  }

  public void setConnectionStatus(Connection con, boolean status) {
    try {
      getLogger().info("Handle Begin Connection Event setAutoCommit false");
      con.setAutoCommit(status);
    } catch (SQLException e) {
      getLogger().error("Set AutoCommit error", e);
    }
  }

  protected JdbcContextEventListener getJdbcContextEventListener()
  {
    return this.tjceListener;
  }

  private class TransactionJdbcContextEventListener
    implements JdbcContextEventListener
  {
    private TransactionJdbcContextEventListener()
    {
    }

    public void onEvent(String type, JdbcContext jc)
    {
      if ("onOpen".equalsIgnoreCase(type)) {
        Connection con = jc.getConnection();
        TransactionJdbcContextStatus.this.setConnectionStatus(con, false);
      }
      if ("onClear".equalsIgnoreCase(type)) {
        Connection con = jc.getConnection();
        TransactionJdbcContextStatus.this.end(con);
        jc.clear();
      }
    }
  }

  public static enum TranCommintResult
  {
    None, SUCCESS, FAILED;
  }
}

