```
ALTER TABLE sys_config ADD create_by varchar(64) NULL COMMENT '创建者' ;
ALTER TABLE sys_config ADD create_date datetime NULL COMMENT '创建时间' ;
ALTER TABLE sys_config ADD update_by varchar(64) NULL COMMENT '更新者' ;
ALTER TABLE sys_config ADD update_date datetime NULL COMMENT '更新时间' ;
ALTER TABLE sys_config ADD remarks varchar(500) NULL COMMENT '备注' ;
ALTER TABLE sys_config ADD del_flag char(1) DEFAULT '0' NULL COMMENT '删除标记' ;

CREATE TABLE `sys_test` (
  `uid` varchar(64) NOT NULL,
  `keyword` varchar(50) DEFAULT NULL COMMENT '参数名',
  `value` varchar(2000) DEFAULT NULL COMMENT '参数值',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `key` (`keyword`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试表'
```


