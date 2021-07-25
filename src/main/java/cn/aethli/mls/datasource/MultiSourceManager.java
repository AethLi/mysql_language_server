package cn.aethli.mls.datasource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MultiSourceManager {

    private static Map<String, ExpansionAbleConnectionPool> POOLS = new ConcurrentHashMap<>();

}
