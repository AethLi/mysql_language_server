package cn.aethli.mls.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.aethli.mls.datasource.DatabaseService;
import cn.aethli.mls.datasource.DataSourceManager;

@Service
public class MetadataService {

    @Resource
    private DataSourceManager dataSourceManager;
    @Resource
    private DatabaseService databaseService;
}
