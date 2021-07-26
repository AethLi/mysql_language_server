package cn.aethli.mls.datasource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jetbrains.annotations.NotNull;

public class MultiSourceManager {

    private static Map<String, MlsDatasource> POOLS = new ConcurrentHashMap<>();

    private static MlsDatasource getMlsDatasource(@NotNull String key) {
        if (POOLS.containsKey(key)) {
            return POOLS.get(key);
        } else {
            return null;
        }
    }
}
