package com.qb.springboot.webhomework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@ApiModel("评论实体")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论id
     */
    @TableId(value = "commentid", type = IdType.AUTO)
    @ApiModelProperty("评论id")
    private Integer commentid;

    @TableField(exist = false)
    @ApiModelProperty("回复的文章id")
    private Integer articleid;

    /**
     * 评论时间
     */
    @ApiModelProperty("评论时间")
    private LocalDateTime comtime;

    /**
     * 评论内容
     */
    @ApiModelProperty("评论内容")
    private String comtext;


    @TableField(exist = false)
    private User user;

}
