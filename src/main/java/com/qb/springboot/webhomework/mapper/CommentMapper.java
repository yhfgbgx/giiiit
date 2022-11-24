package com.qb.springboot.webhomework.mapper;

import com.qb.springboot.webhomework.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.assertj.core.api.IntArrayAssert;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qb
 * @since 2022-11-10
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {



    List<Comment> getCommentListByArticleId(@Param("articleId") Integer articleId);

    Boolean addOneComment(Comment comment);

    Comment getCommentByCommentId(Integer id);

}
