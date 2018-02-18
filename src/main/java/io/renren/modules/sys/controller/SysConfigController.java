package io.renren.modules.sys.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.renren.common.annotation.SysLog;
import io.renren.common.base.BaseController;
import io.renren.modules.sys.entity.SysConfigEntity;
import io.renren.modules.sys.service.SysConfigService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统配置信息
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月4日 下午6:55:53
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends BaseController {

    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 所有配置列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:config:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<SysConfigEntity> sysConfigList = sysConfigService.queryList(query);
        PageInfo page = new PageInfo(sysConfigList);
        int total = (int) page.getTotal();

        R r = R.ok();
        r.put("count", total);
        r.put("data", sysConfigList);

        return r;
    }

    /**
     * 配置信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:config:info")
    public R info(@PathVariable("id") String id) {
        SysConfigEntity sysConfigEntity = sysConfigService.queryObject(id);

        return R.ok().put("config", sysConfigEntity);
    }

    /**
     * 保存配置
     * 通过主键判断增加或修改
     */
    @SysLog("保存配置")
    @RequestMapping("/save")
    @RequiresPermissions(value = {"sys:config:insert", "sys:config:update"}, logical = Logical.OR)
    public R save(@RequestBody SysConfigEntity sysConfigEntity) {
        ValidatorUtils.validateEntity(sysConfigEntity);
        sysConfigService.save(sysConfigEntity);
        return R.ok();
    }

    /**
     * 删除配置
     */
    @SysLog("删除配置")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:config:delete")
    public R delete(@RequestBody String[] ids) {
        sysConfigService.deleteBatch(ids);

        return R.ok();
    }

}
