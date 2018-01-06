package io.renren.common.utils;

import io.renren.common.xss.SQLFilter;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommonQuery extends LinkedHashMap<String, Object> {

    public CommonQuery(Map<String, Object> params) {
        this.putAll(params);

        this.put("dbName", Global.getConfig("jdbc.type"));
    }
}
