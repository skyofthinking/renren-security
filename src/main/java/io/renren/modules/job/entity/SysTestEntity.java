package io.renren.modules.job.entity;

import java.util.Date;
import io.renren.common.base.DataEntity;



/**
 * 测试表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-02-15 16:32:03
 */
public class SysTestEntity extends DataEntity<SysTestEntity> {
	private static final long serialVersionUID = 1L;
	
			//参数名
	private String keyword;
			//参数值
	private String value;
			//状态   0：隐藏   1：显示
	private Integer status;
	
			public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}
			public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
			public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}
	}
