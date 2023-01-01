package com.qb.springboot.webhomework.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qb.springboot.webhomework.common.Constants;
import com.qb.springboot.webhomework.entity.Article;
import com.qb.springboot.webhomework.entity.User;
import com.qb.springboot.webhomework.exception.ServiceException;
import com.qb.springboot.webhomework.mapper.ArticleMapper;
import com.qb.springboot.webhomework.mapper.UserMapper;
import com.qb.springboot.webhomework.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qb
 * @since 2022-11-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    UserMapper userMapper;

    @Resource
    ArticleMapper articleMapper;


    @Override
    public User login(String phone, String password) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        User one = this.getOne(wrapper);
        if (one == null) throw new ServiceException(Constants.CODE_250, "手机号未注册");
        if (!one.getPassword().equals(password)) throw new ServiceException(Constants.CODE_250,"密码错误");

        return one;
    }


    @Override
    public User register(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", user.getPhone());
        User one = this.getOne(wrapper);
        if (one != null) throw new ServiceException(Constants.CODE_250, "该手机号已注册");
        user.setPassword(SecureUtil.md5(user.getPassword()));
        boolean isSave = this.save(user);
        if (!isSave) throw new ServiceException(Constants.CODE_250, "系统错误");
        wrapper.eq("userid", user.getUserid());
        User ret = this.getOne(wrapper);

        return ret;
    }

    @Override
    //value == 1是关注
    //value == -1是取关
    public boolean updateAttent(Integer myId, Integer whoId, Integer value) {
        Object flag = userMapper.getAttentByTwoUserId(myId, whoId);

        if (value == 1 && flag != null) throw  new ServiceException(Constants.CODE_250, "已经关注该用户了");
        if (value == -1 && flag == null) throw  new ServiceException(Constants.CODE_250, "还未关注该用户");

        //更改user表的attent_nub
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", myId);
        User one = this.getOne(wrapper);
        one.setAttentNub(one.getAttentNub() + value);
        this.update(one, wrapper);
        //更改user表的follow_nub
        wrapper = new QueryWrapper<>();
        wrapper.eq("userid", whoId);
        one = this.getOne(wrapper);
        one.setFollowNub(one.getFollowNub() + value);
        this.update(one, wrapper);

        if (value == 1) userMapper.addAttentByTwoUserId(myId, whoId);
        if (value == -1) userMapper.deleteAttentByTwoUserId(myId, whoId);
        return true;
    }

    @Override
    public boolean updateLike(Integer userId, Integer articleId, Integer value) {
        Object flag = userMapper.getLikeOne(userId, articleId);
        if (value == 1 && flag != null) throw  new ServiceException(Constants.CODE_250, "已经点过赞了");
        if (value == -1 && flag == null) throw  new ServiceException(Constants.CODE_250, "还未点赞");

        if (value == 1) userMapper.addLikeOne(userId, articleId);
        if (value == -1) userMapper.deleteLikeOne(userId, articleId);

        Article article = articleMapper.selectById(articleId);

        article.setLikeNub(article.getLikeNub() + value);
        articleMapper.updateById(article);

        return true;
    }

    @Override
    public boolean updateCollect(Integer userId, Integer articleId, Integer value) {
        Object flag = userMapper.getCollect(userId, articleId);

        if (value == 1 && flag != null) throw  new ServiceException(Constants.CODE_250, "已经收藏该文章了");
        if (value == -1 && flag == null) throw  new ServiceException(Constants.CODE_250, "还未收藏该文章");

        if (value == 1) userMapper.addCollectOne(userId, articleId);
        if (value == -1) userMapper.deleteCollectOne(userId, articleId);

        return true;
    }

    @Override
    public List<User> getUserAttent(Integer userId) {
        return userMapper.getUserAttent(userId);
    }

    @Override
    public List<User> getUserFollow(Integer userId) {
        return userMapper.getUserFollow(userId);
    }


}
