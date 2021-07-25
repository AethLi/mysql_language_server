package cn.aethli.mls.datasource;

import javax.sql.DataSource;

import cn.aethli.mls.exception.DataRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Logger;

/**
 * simple connection pool
 *
 * @author selcaNyan
 */
@Slf4j
public class ExpansionAbleConnectionPool implements DataSource {

  private static final int DEFAULT_TIMEOUT = 30;
  private static final ConcurrentLinkedDeque<Connection> POOL = new ConcurrentLinkedDeque<>();
  private static boolean initFlag = false;
  private static String version;
  private static String url;
  private static String user;
  private static String password;
  private PrintWriter logWriter;
  private volatile int timeout = DEFAULT_TIMEOUT;

  /** hidden construct method */
  private ExpansionAbleConnectionPool() {
  }

  /**
   * init database connection pool
   *
   * @param driver   database driver full name
   * @param url      database url within a schema name
   * @param user     database user
   * @param password password
   */
  public static void init(String driver, String url, String user, String password, int poolSize) throws SQLException {

    log.info("Connection pool start init!");

    ExpansionAbleConnectionPool.url = url;
    ExpansionAbleConnectionPool.user = user;
    ExpansionAbleConnectionPool.password = password;

    if (initFlag) {
      synchronized (POOL) {
        for (Iterator<Connection> iterator = POOL.iterator(); iterator.hasNext();) {
          Connection connection = iterator.next();
          iterator.remove();
          try {
            connection.close();
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
      }
    }
    // init driver
    try {
      Class.forName(driver);
    } catch (ClassNotFoundException e) {
      log.error(e.getMessage());
      log.info("The error above may not cause any exception.");
    }
    for (int i = 0; i < poolSize; i++) {
      Connection conn = DriverManager.getConnection(url, user, password);
      POOL.add(conn);
    }
    initFlag = true;
    version = UUID.randomUUID().toString();
  }

  public static void clear() {
    version = UUID.randomUUID().toString();
    synchronized (POOL) {
      for (Iterator<Connection> iterator = POOL.iterator(); iterator.hasNext();) {
        Connection connection = iterator.next();
        iterator.remove();
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      initFlag = false;
    }
  }

  /**
   * get connection from pool
   *
   * @return a database connection
   */
  public Connection getConnection() throws SQLException {
    if (!POOL.isEmpty()) {
      String v = version;
      final Connection connection = POOL.removeFirst();
      return (Connection) Proxy.newProxyInstance(ExpansionAbleConnectionPool.class.getClassLoader(),
          connection.getClass().getInterfaces(), (proxy, method, args) -> {
            if (!"close".equals(method.getName())) {
              return method.invoke(connection, args);
            } else {
              addConnection(connection, v);
              return null;
            }
          });
    } else { // when there's no idle connection got a non-proxy connection
      return DriverManager.getConnection(url, user, password);
    }
  }

  private void addConnection(Connection connection, String v) throws SQLException {
    if (version.equals(v)) {
      POOL.add(connection);
    } else {
      connection.close();
    }
  }

  @Override
  public Connection getConnection(String username, String password) {
    throw new DataRuntimeException("Unsupported: getConnection(String username, String password), use getConnection()");
  }

  @Override
  public <T> T unwrap(Class<T> iface) {
    throw new DataRuntimeException("Unsupported: unwrap");
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) {
    throw new DataRuntimeException("Unsupported: isWrapperFor");
  }

  @Override
  public PrintWriter getLogWriter() {
    return logWriter;
  }

  @Override
  public void setLogWriter(PrintWriter out) {
    this.logWriter = out;
  }

  @Override
  public int getLoginTimeout() {
    return timeout;
  }

  @Override
  public void setLoginTimeout(int seconds) {
    this.timeout = seconds;
  }

  @Override
  public Logger getParentLogger() {
    return null;
  }
}
