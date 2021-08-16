package com.hyc.report.report.service.impl;

import com.hyc.report.dynamiconfig.config.DynamicDataSource;
import com.hyc.report.dynamiconfig.context.DBContextHolder;
import com.hyc.report.dynamiconfig.entity.DataSource;
import com.hyc.report.report.mapper.DataSourceMapper;
import com.hyc.report.report.service.DBChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("DBChangeService")
public class DBChangeServiceImpl implements DBChangeService {

    @Autowired
    DataSourceMapper dataSourceMapper;
    @Autowired
    private DynamicDataSource dynamicDataSource;
    @Override
    public List<DataSource> get() {
        return dataSourceMapper.get();
    }

    @Override
    public boolean changeDb(String datasourceId) throws Exception {

        //默认切换到主数据源,进行整体资源的查找
        DBContextHolder.clearDataSource();

        List<DataSource> dataSourcesList = dataSourceMapper.get();

        for (DataSource dataSource : dataSourcesList) {
            if (dataSource.getDatabaseName().equals(datasourceId)) {
                System.out.println("需要使用的的数据源已经找到,datasourceName是：" + dataSource.getDatabaseName());
                //创建数据源连接&检查 若存在则不需重新创建
                dynamicDataSource.createDataSourceWithCheck(dataSource);
                //切换到该数据源
                DBContextHolder.setDataSource(dataSource.getDatabaseName());
                return true;
            }
        }
        return false;

    }

}
