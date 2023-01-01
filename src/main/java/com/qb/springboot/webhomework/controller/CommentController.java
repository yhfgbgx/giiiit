package com.qb.springboot.webhomework.controller;


import cn.hutool.json.JSON;
import com.qb.springboot.webhomework.common.Constants;
import com.qb.springboot.webhomework.common.Result;
import com.qb.springboot.webhomework.controller.Dto.CommentDto;
import com.qb.springboot.webhomework.controller.Dto.DeleteIdDto;
import com.qb.springboot.webhomework.entity.Comment;
import com.qb.springboot.webhomework.entity.User;
import com.qb.springboot.webhomework.exception.ServiceException;
import com.qb.springboot.webhomework.service.ICommentService;
import com.qb.springboot.webhomework.service.impl.CommentServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import io.swagger.v3.oas.annotations.Operation;
import javafx.util.Pair;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qb
 * @since 2022-11-10
 */
@Api(tags = "回复接口")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private ICommentService commentService;

    @Operation(summary = "新增回复")
    @PostMapping
    public Result addComment(@RequestBody CommentDto dto) {
        Comment comment = new Comment();
        comment.setArticleid(dto.getArticleId());
        comment.setUser(new User(dto.getUserId()));
        comment.setComtext(dto.getComtext());
        Comment one = commentService.addOneComment(comment);

        return Result.success(one);
    }

    @ApiOperation("删除回复")
    @DeleteMapping
    public Result deleteComment(@RequestBody DeleteIdDto dto){
        Boolean flg = commentService.deleteComment(dto.getDeleteId());
        if (!flg) throw new ServiceException(Constants.CODE_250, "系统错误");

        return Result.success();
    }

}

