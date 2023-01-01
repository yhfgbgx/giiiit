package com.qb.springboot.webhomework.controller.Dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CommentDto {


    private Integer userId;


    private Integer articleId;
    /**
     * 评论内容
     */
    private String comtext;
}
