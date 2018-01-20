package io.renren.common.base;

import com.github.pagehelper.PageHelper;
import io.renren.common.utils.Query;
import org.apache.commons.lang.StringUtils;
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

    public void update(T t) {
        t.preUpdate();
        dao.update(t);
    }

    public void update(Map<String, Object> map) {
        dao.update(map);
    }

    public void delete(String id) {
        dao.delete(id);
    }

    public void delete(Map<String, Object> map) {
        dao.delete(map);
    }

    public void deleteBatch(String[] id) {
        dao.deleteBatch(id);
    }

    public T queryObject(String id) {
        return dao.queryObject(id);
    }

    public List<T> queryList(Map<String, Object> map) {
        if (map instanceof Query) {
            PageHelper.startPage(map);
        }
        return dao.queryList(map);
    }

    public List<T> queryList(String id) {
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