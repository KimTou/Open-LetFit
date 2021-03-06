package com.letfit.service;

import com.letfit.pojo.dto.ResultInfo;
import com.letfit.pojo.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author cjt
 * @date 2021/4/10 15:40
 */
@Service
public interface UserService {

    /**
     * 通过openId查找用户
     * @param openId 微信小程序用户唯一凭证
     * @return
     */
    User findUserByOpenId(String openId);

    /**
     * 新增用户
     * @param openId 用户openId
     * @param userName 用户名
     * @param userAvatar 用户头像
     * @return
     */
    User insertUser(String openId, String userName,String userAvatar);

    /**
     * 更新用户积分
     * @param point
     * @param userId
     */
    ResultInfo<?> updateUserPoint(Integer userId, Integer point);


}
