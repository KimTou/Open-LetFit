package com.openletfit.mapper;

import com.openletfit.pojo.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author cjt
 * @date 2021/4/10 15:56
 */
@Repository
public interface UserMapper {

    /**
     * 通过openId查找用户
     * @param openId
     * @return
     */
    User findUserByOpenId(String openId);

    /**
     * 通过用户id查找用户
     * @param userId
     * @return
     */
    User findUserByUserId(Integer userId);

    /**
     * 新增用户
     * @param openId 用户openId
     * @param userName 用户名
     * @param userAvatar 用户头像
     * @return
     */
    Integer insertUser(String openId, String userName, String userAvatar);


}
