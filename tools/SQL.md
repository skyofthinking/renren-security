```
ALTER TABLE sys_config ADD create_by varchar(64) NULL COMMENT '创建者' ;
ALTER TABLE sys_config ADD create_date datetime NULL COMMENT '创建时间' ;
ALTER TABLE sys_config ADD update_by varchar(64) NULL COMMENT '更新者' ;
ALTER TABLE sys_config ADD update_date datetime NULL COMMENT '更新时间' ;
ALTER TABLE sys_config ADD remarks varchar(500) NULL COMMENT '备注' ;
ALTER TABLE sys_config ADD del_flag char(1) DEFAULT '0' NULL COMMENT '删除标记' ;
```
