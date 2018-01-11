package io.renren.modules.gen.entity;

import io.renren.common.base.DataEntity;

public class SysGeneratorEntity extends DataEntity<SysGeneratorEntity> {
    private static final long serialVersionUID = 1L;

    // 表名
    private String tableName;
    // 引擎 MySQL
    private String engine;
    // 表注释
    private String tableComment;
    // 创建时间
    private String createTime;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
