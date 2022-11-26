package com.qb.springboot.webhomework.mapper;

import com.qb.springboot.webhomework.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

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
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM attent WHERE userid = #{whoId} and followerid = #{myId}")
    Object getAttentByTwoUserId(@Param("myId") Integer myId,@Param("whoId")  Integer whoId);

    @Insert("INSERT INTO attent (userid, followerid) VALUES (#{whoId}, #{myId})")
    Boolean addAttentByTwoUserId(@Param("myId") Integer myId,@Param("whoId")  Integer whoId);

    @Delete("DELETE FROM attent WHERE userid = #{whoId} and followerid = #{myId};")
    Boolean deleteAttentByTwoUserId(@Param("myId") Integer myId,@Param("whoId")  Integer whoId);



    @Select("SELECT * FROM `like` WHERE userid = #{userId} and articleid = #{articleId}")
    Object getLikeOne(@Param("userId") Integer userId,@Param("articleId")  Integer articleId);

    @Insert("INSERT INTO `like` (userid, articleid) VALUES (#{userId}, #{articleId})")
    Boolean addLikeOne(@Param("userId") Integer userId,@Param("articleId")  Integer articleId);

    @Delete("DELETE FROM `like` WHERE userid = #{userId} and articleid = #{articleId};")
    Boolean deleteLikeOne(@Param("userId") Integer userId,@Param("articleId")  Integer articleId);


    @Select("SELECT * FROM collect WHERE userid = #{userId} and articleid = #{articleId}")
    Object getCollect(@Param("userId") Integer userId,@Param("articleId")  Integer articleId);

    @Insert("INSERT INTO collect (userid, articleid) VALUES (#{userId}, #{articleId})")
    Boolean addCollectOne(@Param("userId") Integer userId,@Param("articleId")  Integer articleId);

    @Delete("DELETE FROM collect WHERE userid = #{userId} and articleid = #{articleId};")
    Boolean deleteCollectOne(@Param("userId") Integer userId,@Param("articleId")  Integer articleId);


    @Select("SELECT * FROM `attent` LEFT JOIN `user` on attent.userid = `user`.userid where followerid = #{userId} ")
    List<User> getUserAttent(Integer userId);
    @Select("SELECT * FROM `attent` LEFT JOIN `user` on attent.followerid = `user`.userid where `attent`.userid = #{userId} ")
    List<User> getUserFollow(Integer userId);
}
