package com.qb.springboot.webhomework.controller.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class ArticleDto {

    private Integer userId;

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
     * 正文
     */
    @ApiModelProperty("正文")
    private String txt;

    /**
     * 类型
     */
    @ApiModelProperty("文章类型")
    private String type;
}
