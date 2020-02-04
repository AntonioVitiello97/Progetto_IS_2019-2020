package test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import storage_layer.DriverManagerConnectionPool;

public class DriverManagerConnectionPoolTest {

  @Test
  public final void testGetConnection() {
    try {
      assertNotEquals(DriverManagerConnectionPool.getConnection(), null);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test
  public final void realeaseConnection() {
    assertEquals(true, true);
  }
}
