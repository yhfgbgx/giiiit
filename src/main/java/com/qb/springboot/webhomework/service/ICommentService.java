package com.qb.springboot.webhomework.service;

import com.qb.springboot.webhomework.entity.Comment;
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
public interface ICommentService extends IService<Comment> {

    List<Comment> getCommentListByArticleId(Integer articleId);

    Comment addOneComment(Comment comment);

    Boolean deleteComment(Integer deleteId);
}
