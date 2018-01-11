package io.renren.common.base;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Service基类
 */
@Transactional
public abstract class CrudService<D extends CrudDao<T>, T extends BaseEntity<T>> extends BaseService {

    /**
     * 持久层对象
     */
    @Autowired
    protected D dao;

    public void insert(T t) {
        t.preInsert();
        dao.insert(t);
    }

    public void insert(Map<String, Object> map) {
        dao.insert(map);
    }

    public void insertBatch(List<T> list) {
        dao.insertBatch(list);
    }

    public int update(T t) {
        t.preUpdate();
        return dao.update(t);
    }

    public int update(Map<String, Object> map) {
        return dao.update(map);
    }

    public int delete(Object id) {
        return dao.delete(id);
    }

    public int delete(Map<String, Object> map) {
        return dao.delete(map);
    }

    public int deleteBatch(Object[] id) {
        return dao.deleteBatch(id);
    }

    public T queryObject(Object id) {
        return dao.queryObject(id);
    }

    public List<T> queryList(Map<String, Object> map) {
        return dao.queryList(map);
    }

    public List<T> queryList(Object id) {
        return dao.queryList(id);
    }

    public int queryTotal(Map<String, Object> map) {
        return dao.queryTotal(map);
    }

    public int queryTotal() {
        return dao.queryTotal();
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param entity
     */
    public void save(T entity) {
        if (StringUtils.isEmpty(entity.getUid())) {
            entity.preInsert();
            dao.insert(entity);
        } else {
            entity.preUpdate();
            dao.update(entity);
        }
    }

}