package com.qb.springboot.webhomework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
@ApiModel("文章实体")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 帖子id
     */
    @TableId(value = "articleid", type = IdType.AUTO)
    @ApiModelProperty("文章id")
    private Integer articleid;

    /**
     * 标题
     */
    @ApiModelProperty("文章标题")
    private String head;

    /**
     * 简介
     */
    @ApiModelProperty("文章简介")
    private String summary;

    /**
     * 封面
     */
    @ApiModelProperty("文章封面")
    private String cover;

    /**
     * 发布时间
     */
    @ApiModelProperty("发布时间")
    private LocalDateTime releasetime;

    /**
     * 正文
     */
    @ApiModelProperty("正文")
    private String txt;

    /**
     * 类型
     */
    @ApiModelProperty("文章类型")
    private String type;

    @TableField("likeNub")
    @ApiModelProperty("文章点赞数")
    private Integer likeNub;

    /**
     * 用户
     */
    @TableField(exist = false)
    private User user;



    @TableField(exist = false)
    private List<Comment> comments;

    public Article(String cover, String head, String summary, String txt, String type) {

        this.cover = cover;
        this.head = head;
        this.summary = summary;
        this.txt = txt;
        this.type = type;
    }
}
