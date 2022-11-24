package com.qb.springboot.webhomework.controller;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.qb.springboot.webhomework.common.Constants;
import com.qb.springboot.webhomework.common.Result;
import com.qb.springboot.webhomework.controller.Dto.UserDto;
import com.qb.springboot.webhomework.entity.Article;
import com.qb.springboot.webhomework.entity.User;
import com.qb.springboot.webhomework.service.IArticleService;
import com.qb.springboot.webhomework.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qb
 * @since 2022-11-10
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/user")
/*@ApiResponses(value = {
        @ApiResponse(code = 0, message = "用户信息", response = Result.class)
})*/
public class UserController {

    @Resource
    IUserService userService;

    @Resource
    IArticleService articleService;

    @GetMapping
    public String index() {
        return "你好，成功连接";
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(@RequestBody UserDto userDto) {

        if (StringUtils.isBlank(userDto.getPhone()) || StringUtils.isBlank(userDto.getPassword())) {
            return Result.error(Constants.CODE_250, "参数错误");
        }
        User loginUser = userService.login(userDto.getPhone(), SecureUtil.md5(userDto.getPassword()));
        return Result.success(loginUser);

    }

    @Operation(summary = "注册")
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        if (StringUtils.isBlank(user.getPhone()) || StringUtils.isBlank(user.getPassword())) {
            return Result.error(Constants.CODE_250, "参数错误");
        }
        if (!"男".equals(user.getGender()) && !"女".equals(user.getGender()) ) {
            user.setGender("保密");
        }
        User register = userService.register(user);

        return Result.success(register);
    }

    @ApiOperation("关注/取消关注")
    @GetMapping("/attent/{myId}/{whoId}/{value}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "myId",value = "本人id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "whoId",value = "要关注的人的id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "value",value = "关注为1，取关为-1", defaultValue = "1", required = true, paramType = "path")
    })
    public Result updateAttent(@PathVariable Integer myId,
                            @PathVariable Integer whoId,
                            @PathVariable Integer value) {
        boolean flag = userService.updateAttent(myId, whoId, value);
        if (!flag) Result.error(Constants.CODE_250, "系统错误");

        return Result.success();
    }

    @ApiOperation("喜欢/取消喜欢(点赞)")
    @GetMapping("/like/{userId}/{articleId}/{value}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "本人id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "articleId",value = "要点赞的文章id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "value",value = "点赞为1，取消点赞为-1", defaultValue = "1", required = true, paramType = "path")
    })
    public Result updateLike(@PathVariable Integer userId,
                            @PathVariable Integer articleId,
                            @PathVariable Integer value) {
        boolean flag = userService.updateLike(userId, articleId, value);
        if (!flag) Result.error(Constants.CODE_250, "系统错误");

        return Result.success();
    }


    @ApiOperation("收藏文章/取消收藏")
    @GetMapping("/collect/{userId}/{articleId}/{value}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "本人id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "articleId",value = "要收藏的文章id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "value",value = "收藏为1，取消收藏为-1", defaultValue = "1", required = true, paramType = "path")
    })
    public Result updateCollect(@PathVariable Integer userId,
                             @PathVariable Integer articleId,
                             @PathVariable Integer value) {
        boolean flag = userService.updateCollect(userId, articleId, value);
        if (!flag) Result.error(Constants.CODE_250, "系统错误");

        return Result.success();
    }

    @ApiOperation("修改用户信息")
    @PutMapping
    public Result updateUser(@RequestBody User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", user.getUserid());
        boolean flg = userService.update(user, wrapper);
        if (!flg) return Result.error(Constants.CODE_250, "系统错误");

        return Result.success();
    }

    @ApiOperation("查询用户信息")
    @GetMapping("/{userId}")
    public Result getUser(@PathVariable Integer userId) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", userId);
        User user = userService.getById(userId);
        if (user == null) return Result.error(Constants.CODE_250, "系统错误");

        return Result.success(user);
    }

    @ApiOperation("查询用户关注列表")
    @GetMapping("/attent/{userId}")
    public Result getUserAttent(@PathVariable Integer userId) {

        List<User> userList =  userService.getUserAttent(userId);

        return Result.success(userList);
    }

    @ApiOperation("查询用户粉丝列表")
    @GetMapping("/follow/{userId}")
    public Result getUserFollow(@PathVariable Integer userId) {

        List<User> userList =  userService.getUserFollow(userId);

        return Result.success(userList);
    }

    @ApiOperation("查询用户创作的文章列表")
    @GetMapping("/article/{userId}")
    public Result getUserArticle(@PathVariable Integer userId) {

        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", userId);
        wrapper.orderByDesc("releasetime");
        List<Article> list = articleService.list(wrapper);

        return Result.success(list);
    }

}

