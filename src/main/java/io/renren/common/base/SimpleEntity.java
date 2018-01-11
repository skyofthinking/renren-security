package io.renren.common.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.renren.common.utils.IdGen;
import io.renren.common.utils.UserUtils;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

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
