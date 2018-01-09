package io.renren.modules.sys.service;

import com.qiniu.util.StringUtils;
import io.renren.common.annotation.DataFilter;
import io.renren.common.base.CrudService;
import io.renren.modules.sys.dao.SysConfigDao;
import io.renren.modules.sys.dao.SysDeptDao;
import io.renren.modules.sys.entity.SysConfigEntity;
import io.renren.modules.sys.entity.SysDeptEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 部门管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-20 15:23:47
 */
@Service
public class SysDeptService extends CrudService<SysDeptDao, SysDeptEntity> {

    @DataFilter(tableAlias = "d", user = false)
    public List<SysDeptEntity> queryList(Map<String, Object> map) {
        return dao.queryList(map);
    }

    /**
     * 查询子部门ID列表
     *
     * @param parentId 上级部门ID
     */
    public List<String> queryDetpIdList(String parentId) {
        return dao.queryDetpIdList(parentId);
    }

    /**
     * 获取子部门ID(包含本部门ID)，用于数据过滤
     */
    public String getSubDeptIdList(String deptId) {
        //部门及子部门ID列表
        List<String> deptIdList = new ArrayList<>();

        //获取子部门ID
        List<String> subIdList = queryDetpIdList(deptId);
        getDeptTreeList(subIdList, deptIdList);

        //添加本部门
        deptIdList.add(deptId);

        String deptFilter = StringUtils.join(deptIdList, ",");
        return deptFilter;
    }

    /**
     * 递归
     */
    private void getDeptTreeList(List<String> subIdList, List<String> deptIdList) {
        for (String deptId : subIdList) {
            List<String> list = queryDetpIdList(deptId);
            if (list.size() > 0) {
                getDeptTreeList(list, deptIdList);
            }

            deptIdList.add(deptId);
        }
    }

}
