package io.renren.common.base;

import io.renren.common.utils.IdGen;

public abstract class SimpleEntity<T> extends BaseEntity<T> {

    public SimpleEntity() {
        super();
    }

    public SimpleEntity(String id) {
        super(id);
    }

    /**
     * 插入之前执行方法，需要手动调用
     */
    @Override
    public void preInsert() {
        // 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
        if (!this.isNewRecord) {
            String uuid = IdGen.uuid();
            setUid(uuid);
        }
    }

    /**
     * 更新之前执行方法，需要手动调用
     */
    @Override
    public void preUpdate() {
    }

}
