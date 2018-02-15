package io.renren.common.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.renren.common.utils.Constant;
import io.renren.common.utils.IdGen;
import io.renren.common.utils.UserUtils;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.zhuoyue.common.IdWorker;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

public abstract class DataEntity<T> extends BaseEntity<T> {
    protected String remarks;    // 备注
    protected SysUserEntity createBy;    // 创建者
    protected Date createDate;    // 创建日期
    protected SysUserEntity updateBy;    // 更新者
    protected Date updateDate;    // 更新日期
    protected String delFlag;    // 删除标记（0：正常；1：删除；2：审核）

    public DataEntity() {
        super();
        this.delFlag = DEL_FLAG_NORMAL;
    }

    public DataEntity(String id) {
        super(id);
    }

    /**
     * 插入之前执行方法，需要手动调用
     */
    @Override
    public void preInsert() {
        // 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
        if (!this.isNewRecord) {
            IdWorker idWorker = new IdWorker(Constant.WORKER_ID);
            String uuid = String.valueOf(idWorker.nextId()); // IdGen.uuid();
            setUid(uuid);
        }
        SysUserEntity user = UserUtils.getUser();
        if (StringUtils.isNotBlank(user.getUserId())) {
            this.updateBy = user;
            this.createBy = user;
        }
        this.updateDate = new Date();
        this.createDate = this.updateDate;
    }

    /**
     * 更新之前执行方法，需要手动调用
     */
    @Override
    public void preUpdate() {
        SysUserEntity user = UserUtils.getUser();
        if (StringUtils.isNotBlank(user.getUserId())) {
            this.updateBy = user;
        }
        this.updateDate = new Date();
    }

    @Length(min = 0, max = 255)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @JsonIgnore
    public SysUserEntity getCreateBy() {
        return createBy;
    }

    public void setCreateBy(SysUserEntity createBy) {
        this.createBy = createBy;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @JsonIgnore
    public SysUserEntity getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(SysUserEntity updateBy) {
        this.updateBy = updateBy;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @JsonIgnore
    @Length(min = 1, max = 1)
    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
