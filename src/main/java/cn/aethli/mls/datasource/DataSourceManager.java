package cn.aethli.mls.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import cn.aethli.mls.model.DatabaseConfig;

@Component
public class DataSourceManager {

    /**
     * 
     */
    private final Map<String, HikariDataSource> POOLS = new ConcurrentHashMap<>();

    /**
     * 
     * @param key
     * @return
     */
    private HikariDataSource getDatasource(@NotNull String key) {
        return POOLS.getOrDefault(key, null);
    }

    /**
     * 
     * @param hikariConfig
     * @return
     */
    private HikariDataSource getDatasource(@NotNull HikariConfig hikariConfig) {
        String key = String.format("%s-%s-%s", hikariConfig.getJdbcUrl(), hikariConfig.getUsername(),
                hikariConfig.getPassword());
        HikariDataSource hikariDataSource = getDatasource(key);
        if (hikariDataSource == null) {
            hikariDataSource = new HikariDataSource(hikariConfig);
            POOLS.put(key, hikariDataSource);
        }

        return hikariDataSource;
    }

    /**
     * 
     * @param config
     * @param schemaName
     * @return
     * @throws SQLException
     */
    public Connection getConnection(DatabaseConfig config, String schemaName) throws SQLException {
        String url = String.format("jdbc:mysql://%s:%s/%s", config.getHost(), config.getPort(), schemaName);
        HikariConfig hikariConfig = new HikariConfig() {
            {
                setJdbcUrl(url);
                setUsername(config.getUser());
                setPassword(config.getPassword());
            }
        };
        return getDatasource(hikariConfig).getConnection();
    }

    /**
     * 
     */
    public void closeAll() {
        POOLS.values().forEach(d -> d.close());
    }
}
