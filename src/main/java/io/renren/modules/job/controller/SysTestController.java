package io.renren.modules.job.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.renren.common.base.BaseController;

import io.renren.modules.job.entity.SysTestEntity;
import io.renren.modules.job.service.SysTestService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;

/**
 * 测试表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-02-15 16:32:03
 */
@RestController
@RequestMapping("systest")
public class SysTestController extends BaseController {
	@Autowired
	private SysTestService sysTestService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("systest:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        PageHelper.startPage(query.getPage(), query.getLimit());

		List<SysTestEntity> sysTestList = sysTestService.queryList(query);
        PageInfo page = new PageInfo(sysTestList);
        int total = (int) page.getTotal();

        R r = R.ok();
        r.put("count", total);
        r.put("data", sysTestList);

		return r;
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{uid}")
	@RequiresPermissions("systest:info")
	public R info(@PathVariable("uid") String uid){
		SysTestEntity sysTest = sysTestService.queryObject(uid);
		
		return R.ok().put("sysTest", sysTest);
	}
	
	/**
	 * 新增或修改
	 */
    @RequestMapping("/save")
    @RequiresPermissions(value = {"systest:insert", "systest:update"}, logical = Logical.OR)
    public R save(@RequestBody SysTestEntity sysTest){
		sysTestService.save(sysTest);

        return R.ok();
    }

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("systest:delete")
	public R delete(@RequestBody String[] uids){
		sysTestService.deleteBatch(uids);
		
		return R.ok();
	}
	
}
