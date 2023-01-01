package com.qb.springboot.webhomework.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qb.springboot.webhomework.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
public interface ArticleMapper extends BaseMapper<Article> {

    Page<Article> findPageByType(@Param("page") Page<Article> page, @Param("type") String type);

    IPage<Article> findPageByAttention(@Param("page") Page<Article> articlePage, @Param("userid") Integer userid);

    Article selectDetail(Integer articleId);
    @Insert("INSERT INTO article (head, summary, cover, txt, `type`, userid) values (#{head}, #{summary}, #{cover}, #{txt}, #{type}, #{user.userid})")
    Boolean addArticle(Article article);

    List<Article> search(String v);

    IPage<Article> findPageByCollect(@Param("page") Page<Article> articlePage, @Param("userid") Integer userid);

    IPage<Article> findPageByLike(Page<Article> articlePage, Integer userid);

    @Delete("delete from `collect` where articleid = #{deleteId}")
    void deleteCollect(Integer deleteId);
    @Delete("delete from `like` where articleid = #{deleteId}")
    void deleteLike(Integer deleteId);
}
