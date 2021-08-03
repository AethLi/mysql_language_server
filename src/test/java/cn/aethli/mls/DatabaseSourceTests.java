package cn.aethli.mls;

import cn.aethli.mls.datasource.DatabaseService;
import cn.aethli.mls.datasource.DataSourceManager;
import cn.aethli.mls.model.DatabaseConfig;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.SQLException;

@SpringBootTest
@Slf4j
public class DatabaseSourceTests {
    @Resource
    private ObjectMapper defaultMapper;
    @Resource
    private DataSourceManager dataSourceManager;
    @Resource
    private DatabaseService dataService;

    private DatabaseConfig getDatabaseConfig() throws IOException {
        return defaultMapper.readValue(
                DatabaseSourceTests.class.getClassLoader().getResourceAsStream("databaseConfig1.json"),
                DatabaseConfig.class);
    }

    @Test
    public void selectTest() throws IOException, SQLException {

        log.info(defaultMapper.writeValueAsString(dataService.executeSelect(getDatabaseConfig(), "mysql",
                "SELECT SCHEMA_NAME name,SCHEMA_NAME schemaName FROM information_schema.SCHEMATA;")));
    }
}
