package com.letfit.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.letfit.pojo.entity.User;
import com.letfit.pojo.dto.ResultInfo;
import com.letfit.service.UserService;
import com.letfit.common.Const;
import com.letfit.util.HttpUtil;
import com.letfit.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author cjt
 * @date 2021/4/8 19:38
 */
@Api(tags = "用户请求接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 微信授权登录
     * @param code
     * @param nickName
     * @param avatar
     * @return
     */
    @ApiOperation(value = "用户进行微信登录", notes = "返回用户信息，包括openid")
    @GetMapping("/wxLogin")
    public ResultInfo<User> wxLogin(@RequestParam("code") String code,
                                    @RequestParam("nickName") String nickName,
                                    @RequestParam("avatar") String avatar){
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        StringBuffer stringBuffer = new StringBuffer (url).append ("?appid=").append (Const.APP_ID)
                .append ("&secret=").append (Const.APP_SECRET).append ("&js_code=").append (code)
                .append ("&grant_type=").append ("authorization_code");
        String requestUrl = stringBuffer.toString();
        String result = HttpUtil.sendGet(requestUrl);
        JSONObject object = JSON.parseObject(result);
        String openId = object.getString("openid");
        String sessionKey = object.getString("session_key");

        // 将session_key保存在服务器
        if(redisUtil.get(openId) == null && (openId != null && sessionKey != null)){
            redisUtil.set(openId,sessionKey,6000);
        }
        User user = userService.findUserByOpenId(openId);
        // 用户第一次登录小程序
        if(user == null){
            user = userService.insertUser(openId,nickName,avatar);
        }

        logger.info("nickName:{}",nickName);
        logger.info("code:{}",code);
        logger.info("openId:{}",openId);
        logger.info("session_key:{}",sessionKey);
        logger.info("requestUrl:{}",requestUrl);
        logger.info("result:{}",result);
        logger.info("user:{}",user);

        return ResultInfo.success(200,"登录成功",user);
    }


}
