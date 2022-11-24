package com.qb.springboot.webhomework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.sql.Blob;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.*;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * <p>
 *
 * </p>
 *
 * @author qb
 * @since 2022-11-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户实体")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @ApiModelProperty("id")
    @TableId(value = "userid", type = IdType.AUTO)
    private Integer userid;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phone;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String headportait;

    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private String gender;

    /**
     * 年龄
     */
    @ApiModelProperty("年龄")
    private Integer age;

    /**
     * 职业
     */
    @ApiModelProperty("职业")
    private String profession;

    /**
     * 所在地
     */
    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("关注量")
    @TableField("attent_nub")
    private Integer attentNub;

    @ApiModelProperty("粉丝量")
    @TableField("follow_nub")
    private Integer followNub;

    @ApiModelProperty("作品量")
    @TableField("article_nub")
    private Integer articleNub;

    public User(Integer userId) {
        this.userid = userId;
    }
/*
      //发表的文章
      private List<Article> myArticleList;

      //收藏的文章
      private List<Article> likeArticleList;

      //关注的人
      private List<User> likeUserList;

      //粉丝列表
      private List<User> fanList;*/


}
