package io.renren.modules.job.controller;

import java.util.List;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.renren.common.base.BaseController;

import io.renren.modules.job.entity.ScheduleJobEntity;
import io.renren.modules.job.service.ScheduleJobService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import com.github.pagehelper.PageInfo;

/**
 * 定时任务
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-01-20 14:47:22
 */
@RestController
@RequestMapping("schedulejob")
public class ScheduleJobController extends BaseController {
    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("schedulejob:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<ScheduleJobEntity> scheduleJobList = scheduleJobService.queryList(query);
        PageInfo page = new PageInfo(scheduleJobList);
        int total = (int) page.getTotal();

        PageUtils pageUtils = new PageUtils(scheduleJobList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtils);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{uid}")
    @RequiresPermissions("schedulejob:info")
    public R info(@PathVariable("uid") String uid) {
        ScheduleJobEntity scheduleJob = scheduleJobService.queryObject(uid);

        return R.ok().put("scheduleJob", scheduleJob);
    }

    /**
     * 保存
     */
    @RequestMapping("/insert")
    @RequiresPermissions("schedulejob:insert")
    public R insert(@RequestBody ScheduleJobEntity scheduleJob) {
        ValidatorUtils.validateEntity(scheduleJob);
        if (!CronExpression.isValidExpression(scheduleJob.getCronExpression())) {
            return R.error("请输入格式正确的Cron表达式");
        }
        scheduleJobService.save(scheduleJob);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("schedulejob:update")
    public R update(@RequestBody ScheduleJobEntity scheduleJob) {
        ValidatorUtils.validateEntity(scheduleJob);
        if (!CronExpression.isValidExpression(scheduleJob.getCronExpression())) {
            return R.error("请输入格式正确的Cron表达式");
        }
        scheduleJobService.save(scheduleJob);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("schedulejob:delete")
    public R delete(@RequestBody String[] uids) {
        scheduleJobService.deleteBatch(uids);

        return R.ok();
    }

    @RequestMapping("/pause")
    @RequiresPermissions("schedulejob:info")
    public R pause(@RequestBody String[] uids) {
        boolean ret = scheduleJobService.pause(uids);
        if (ret) {
            return R.ok();
        } else {
            return R.error("暂停出现异常");
        }
    }

    @RequestMapping("/resume")
    @RequiresPermissions("schedulejob:info")
    public R resume(@RequestBody String[] uids) {
        boolean ret = scheduleJobService.resume(uids);
        if (ret) {
            return R.ok();
        } else {
            return R.error("恢复出现异常");
        }
    }

    @RequestMapping("/run")
    @RequiresPermissions("schedulejob:info")
    public R run(@RequestBody String[] uids) {
        boolean ret = scheduleJobService.run(uids);
        if (ret) {
            return R.ok();
        } else {
            return R.error("运行出现异常");
        }
    }

}
