package cn.aethli.mls.datasource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class MultiSourceManager {

    private Map<String, MlsDatasource> POOLS = new ConcurrentHashMap<>();

    private MlsDatasource getMlsDatasource(@NotNull String key) {
        if (POOLS.containsKey(key)) {
            return POOLS.get(key);
        } else {
            return null;
        }
    }
}
