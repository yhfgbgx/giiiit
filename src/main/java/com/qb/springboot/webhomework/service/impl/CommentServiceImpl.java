package com.qb.springboot.webhomework.service.impl;

import com.qb.springboot.webhomework.common.Constants;
import com.qb.springboot.webhomework.entity.Comment;
import com.qb.springboot.webhomework.exception.ServiceException;
import com.qb.springboot.webhomework.mapper.CommentMapper;
import com.qb.springboot.webhomework.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private CommentMapper commentMapper;



    @Override
    public List<Comment> getCommentListByArticleId(Integer articleId) {
        return commentMapper.getCommentListByArticleId(articleId);
    }

    @Override
    public Comment addOneComment(Comment comment) {
        Boolean flag = commentMapper.addOneComment(comment);
        if (!flag) throw new ServiceException(Constants.CODE_250, "系统错误");

        Comment one = commentMapper.getCommentByCommentId(comment.getCommentid());
        if (one == null) throw new ServiceException(Constants.CODE_250, "系统错误");

        return one;
    }

    @Override
    public Boolean deleteComment(Integer deleteId) {

        this.removeById(deleteId);

        return true;
    }


}
