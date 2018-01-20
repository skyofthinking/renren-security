package io.renren.modules.sys.service;

import io.renren.common.base.CrudService;
import io.renren.modules.sys.entity.SysConfigEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统配置信息
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月4日 下午6:49:01
 */

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;

import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysConfigDao;
import io.renren.common.exception.RRException;
import io.renren.modules.sys.redis.SysConfigRedis;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysConfigService extends CrudService<SysConfigDao, SysConfigEntity> {

    @Autowired
    private SysConfigRedis sysConfigRedis;

    @Transactional
    public void save(SysConfigEntity config) {
        super.save(config);
        sysConfigRedis.saveOrUpdate(config);
    }

    @Transactional
    public void updateValueByKey(String key, String value) {
        dao.updateValueByKey(key, value);
        sysConfigRedis.delete(key);
    }

    @Transactional
    public void deleteBatch(String[] ids) {
        for (String id : ids) {
            SysConfigEntity config = queryObject(id);
            sysConfigRedis.delete(config.getKeyword());
        }

        super.deleteBatch(ids);
    }

    /**
     * 根据key，获取配置的value值
     *
     * @param key key
     */
    public String getValue(String key) {
        SysConfigEntity config = sysConfigRedis.get(key);
        if (config == null) {
            config = dao.queryByKey(key);
            sysConfigRedis.saveOrUpdate(config);
        }

        return config == null ? null : config.getValue();
    }

    /**
     * 根据key，获取value的Object对象
     *
     * @param key   ey
     * @param clazz Object对象
     */
    public <T> T getConfigObject(String key, Class<T> clazz) {
        String value = getValue(key);
        if (StringUtils.isNotBlank(value)) {
            return new Gson().fromJson(value, clazz);
        }

        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RRException("获取参数失败");
        }
    }
}