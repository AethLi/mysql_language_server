package cn.aethli.mls.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.aethli.mls.model.DatabaseConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DatabaseService {
  @Resource
  private DataSourceManager dataSourceManager;

  private DatabaseService() {
  }

  public List<Map<String, String>> executeSelect(DatabaseConfig config, String schemaName, String sql)
      throws SQLException {
    log.info("execute select sql:{}", sql);
    Connection connection = dataSourceManager.getConnection(config, schemaName);
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    ResultSet resultSet = preparedStatement.executeQuery();
    List<Map<String, String>> result = new ArrayList<>();
    ResultSetMetaData md = resultSet.getMetaData();
    int columnCount = md.getColumnCount();
    while (resultSet.next()) {
      Map<String, String> rowData = new HashMap<>();
      for (int i = 1; i <= columnCount; i++) {
        rowData.put(md.getColumnName(i), resultSet.getString(i));
      }
      result.add(rowData);
    }
    close(resultSet, preparedStatement, connection);
    return result;
  }

  public static void close(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
    if (null != resultSet) {
      try {
        resultSet.close();
      } catch (Exception e) {
        log.error(e.getMessage());
        log.debug(e.getMessage(), e);
      }
    }
    if (null != preparedStatement) {
      try {
        preparedStatement.close();
      } catch (Exception e) {
        log.error(e.getMessage());
        log.debug(e.getMessage(), e);
      }
    }
    if (null != connection) {
      try {
        connection.close();
      } catch (Exception e) {
        log.error(e.getMessage());
        log.debug(e.getMessage(), e);
      }
    }
  }
}
