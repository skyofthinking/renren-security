package io.renren.modules.oss.entity;

import io.renren.common.base.DataEntity;

import java.util.Date;

/**
 * 文件上传
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-25 12:13:26
 */
public class SysOssEntity extends DataEntity<SysOssEntity> {
    private static final long serialVersionUID = 1L;

    //URL地址
    private String url;
    //创建时间
    private Date createDate;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
