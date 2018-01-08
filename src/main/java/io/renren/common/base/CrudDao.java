package io.renren.common.base;

import java.util.List;
import java.util.Map;

/**
 * DAO支持类实现
 */
public interface CrudDao<T> extends BaseDao {

    public void insert(T t);

    public void insert(Map<String, Object> map);

    public void insertBatch(List<T> list);

    public int update(T t);

    public int update(Map<String, Object> map);

    public int delete(Object id);

    public int delete(Map<String, Object> map);

    public int deleteBatch(Object[] id);

    public T queryObject(Object id);

    public List<T> queryList(Map<String, Object> map);

    public List<T> queryList(Object id);

    public int queryTotal(Map<String, Object> map);

    public int queryTotal();

}