package com.qb.springboot.webhomework.service;

import com.qb.springboot.webhomework.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qb
 * @since 2022-11-10
 */
public interface IUserService extends IService<User> {

    User register(User user);

    User login(String phone, String password);


    boolean updateAttent(Integer myId, Integer whoId, Integer value);

    boolean updateLike(Integer userId, Integer articleId, Integer value);

    boolean updateCollect(Integer userId, Integer articleId, Integer value);

    List<User> getUserAttent(Integer userId);

    List<User> getUserFollow(Integer userId);
}
