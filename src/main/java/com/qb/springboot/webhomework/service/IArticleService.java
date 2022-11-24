package com.qb.springboot.webhomework.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qb.springboot.webhomework.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qb
 * @since 2022-11-10
 */
public interface IArticleService extends IService<Article> {

    IPage<Article> findPageByType(Integer pageNub, Integer pageSize, String condition);

    List<Article> findPageByTop();


    IPage<Article> findPageByAttention(Integer pageNub, Integer pageSize, Integer userid);

    Article selectDetail(Integer articleId);

    Boolean addArticle(Article article);

    List<Article> searchArticle(String val);

    IPage<Article> findPageByCreate(Integer pageNub, Integer pageSize, Integer userid);

    IPage<Article> findPageByCollect(Integer pageNub, Integer pageSize, Integer userid);

    IPage<Article> findPageByLike(Integer pageNub, Integer pageSize, Integer userid);

    Boolean deleteArticle(Integer deleteId);
}
