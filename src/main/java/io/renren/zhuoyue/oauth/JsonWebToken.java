package io.renren.zhuoyue.oauth;

import io.renren.common.utils.R;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.sys.shiro.ShiroUtils;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class JsonWebToken {
    protected Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private Audience audience;

    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value="oauth/token")
    @ApiOperation(value="获取TOKEN", notes="API访问时需要TOKEN")
    public R getAccessToken(@RequestBody SysUserEntity sysUserEntity) {
        try {
            // 使用密码或salt作为ClientId

            // 验证码校验

            //验证用户名密码
            SysUserEntity user = sysUserService.queryByUserName(sysUserEntity.getUsername());
            if (user == null) {
                return R.error(30003, "用户名或密码无效");
            } else {
                String md5Password = ShiroUtils.sha256(sysUserEntity.getPassword(), user.getSalt());

                if (md5Password.compareTo(user.getPassword()) != 0) {
                    return R.error(30003, "用户名或密码无效");
                }
            }

            // 拼装 AccessToken
            logger.info("audience " + audience.getName() + " " + audience.getExpiresSecond() + " " + audience.getBase64Secret());
            String accessToken = JwtHelper.createJWT(sysUserEntity.getUsername(), user.getUserId(), user.getPassword(), audience.getName(), audience.getExpiresSecond() * 1000, audience.getBase64Secret());

            // 返回accessToken
            AccessToken accessTokenEntity = new AccessToken();
            accessTokenEntity.setAccessToken(accessToken);
            accessTokenEntity.setExpiresIn(audience.getExpiresSecond());
            accessTokenEntity.setTokenType("oauth");

            // 放到Header返回

            return R.ok().put("data", accessTokenEntity);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info("异常信息：" + ex.getLocalizedMessage());
            return R.error();
        }
    }
}
