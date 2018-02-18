package io.renren.modules.sys.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.renren.common.base.BaseController;
import io.renren.modules.sys.entity.SysLogEntity;
import io.renren.modules.sys.service.SysLogService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * 系统日志
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-08 10:40:56
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController extends BaseController {
    @Autowired
    private SysLogService sysLogService;

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("sys:log:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<SysLogEntity> sysLogList = sysLogService.queryList(query);
        PageInfo page = new PageInfo(sysLogList);
        int total = (int) page.getTotal();

        R r = R.ok();
        r.put("count", total);
        r.put("data", sysLogList);

        return r;
    }

}
