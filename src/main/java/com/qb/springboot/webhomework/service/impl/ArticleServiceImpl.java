package com.qb.springboot.webhomework.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qb.springboot.webhomework.entity.Article;
import com.qb.springboot.webhomework.entity.User;
import com.qb.springboot.webhomework.mapper.ArticleMapper;
import com.qb.springboot.webhomework.mapper.CommentMapper;
import com.qb.springboot.webhomework.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qb.springboot.webhomework.service.ICommentService;
import com.qb.springboot.webhomework.service.IUserService;
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
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private IUserService userService;
    @Resource
    private ICommentService commentService;

    @Override
    public IPage<Article> findPageByType(Integer pageNub, Integer pageSize, String type) {

        Page<Article> articlePage = new Page<Article>(pageNub, pageSize);

        //List<Article> articleList = articleMapper.findPageByType((pageNub - 1) * pageSize, pageSize, type);
        //articlePage.setRecords(articleList);
        return  articleMapper.findPageByType(articlePage, type);

    }

    @Override
    public List<Article> findPageByTop() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("likeNub");

        List<Article> list = this.list(wrapper);
        System.out.println(list);

        return list;
    }

    @Override
    public IPage<Article> findPageByAttention(Integer pageNub, Integer pageSize, Integer userid) {
        Page<Article> articlePage = new Page<>(pageNub, pageSize);



        return articleMapper.findPageByAttention(articlePage, userid);
    }

    @Override
    public Article selectDetail(Integer articleId) {
        Article article = articleMapper.selectDetail(articleId);

        article.setComments(commentService.getCommentListByArticleId(articleId));

        return article;
    }

    @Override
    public Boolean addArticle(Article article) {
        Boolean flg = articleMapper.addArticle(article);
        if (!flg) return false;
        User one = userService.getById(article.getUser().getUserid());
        one.setArticleNub(one.getArticleNub() + 1);
        userService.updateById(one);
        return true;
    }

    @Override
    public List<Article> searchArticle(String val) {

        return articleMapper.search(val);
    }

    @Override
    public IPage<Article> findPageByCreate(Integer pageNub, Integer pageSize, Integer userid) {
        Page<Article> articlePage = new Page<>(pageNub, pageSize);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", userid);
        wrapper.orderByDesc("releasetime");


        return page(articlePage, wrapper);
    }

    @Override
    public IPage<Article> findPageByCollect(Integer pageNub, Integer pageSize, Integer userid) {
        Page<Article> articlePage = new Page<>();

        return articleMapper.findPageByCollect(articlePage, userid);
    }

    @Override
    public IPage<Article> findPageByLike(Integer pageNub, Integer pageSize, Integer userid) {
        Page<Article> articlePage = new Page<>();

        return articleMapper.findPageByLike(articlePage, userid);
    }

    @Override
    public Boolean deleteArticle(Integer deleteId) {
        articleMapper.deleteCollect(deleteId);
        articleMapper.deleteLike(deleteId);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("articleid", deleteId);

        Article article = articleMapper.selectDetail(deleteId);
        User one = userService.getById(article.getUser().getUserid());
        one.setArticleNub(one.getArticleNub() - 1);
        userService.updateById(one);
        return this.remove(wrapper);
    }


}
