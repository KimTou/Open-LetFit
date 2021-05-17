package com.openletfit.service.impl;

import com.openletfit.aop.annotation.ReadOnly;
import com.openletfit.mapper.UserMapper;
import com.openletfit.pojo.entity.User;
import com.openletfit.service.UserService;
import com.openletfit.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author cjt
 * @date 2021/4/10 15:40
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 通过openId查找用户
     * @param openId 微信小程序用户唯一凭证
     * @return
     */
    @ReadOnly
    @Override
    public User findUserByOpenId(String openId) {
        return userMapper.findUserByOpenId(openId);
    }

    /**
     * 新增用户
     * @param openId 用户openId
     * @param userName 用户名
     * @param userAvatar 用户头像
     * @return
     */
    @Override
    public User insertUser(String openId, String userName, String userAvatar) {
        Integer userId = userMapper.insertUser(openId,userName,userAvatar);
        User user = new User();
        user.setUserId(userId);
        user.setOpenId(openId);
        user.setUserName(userName);
        user.setUserAvatar(userAvatar);
        user.setTotalLike(0);
        return user;
    }



}
