package io.renren.modules.sys.entity;

import io.renren.common.base.DataEntity;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 系统配置信息
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月4日 下午6:43:36
 */
public class SysConfigEntity extends DataEntity<SysConfigEntity> {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "参数名不能为空")
    private String keyword;
    @NotBlank(message = "参数值不能为空")
    private String value;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
