package com.openletfit.common;

import com.openletfit.util.DynamicDbUtil;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author cjt
 * @date 2021/5/14 11:44
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 返回当前线程正在使用代表数据库的枚举对象
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDbUtil.get();
    }

}
