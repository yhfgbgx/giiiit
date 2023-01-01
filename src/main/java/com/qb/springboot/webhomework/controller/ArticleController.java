package com.qb.springboot.webhomework.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qb.springboot.webhomework.common.Constants;
import com.qb.springboot.webhomework.common.Result;
import com.qb.springboot.webhomework.controller.Dto.ArticleDto;
import com.qb.springboot.webhomework.controller.Dto.DeleteIdDto;
import com.qb.springboot.webhomework.entity.Article;
import com.qb.springboot.webhomework.entity.User;
import com.qb.springboot.webhomework.service.IArticleService;
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
 *  前端控制器
 * </p>
 *
 * @author qb
 * @since 2022-11-10
 */
@Api(tags = "文章接口")
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private IArticleService articleService;

    @GetMapping
    @ApiOperation("根据类型查询文章列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNub", value = "第几页", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "该页展示多少条数据", required = false, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "展示什么类型的数据（学习、生活、求职） 默认展示所有类型", required = false, paramType = "query"),
    })
    public IPage<Article> findPageByType(@RequestParam(defaultValue = "1") Integer pageNub,
                                        @RequestParam(defaultValue = "20") Integer pageSize,
                                        @RequestParam(defaultValue = "") String type) {

       return articleService.findPageByType(pageNub, pageSize, type);

    }

    @ApiOperation("发表文章")
    @PostMapping
    public Result addArticle(@RequestBody ArticleDto dto){
        Article article = new Article(dto.getCover(), dto.getHead(), dto.getSummary(), dto.getTxt(), dto.getType(), dto.getUserId());
        article.setUser(new User(dto.getUserId()));
        Boolean flg = articleService.addArticle(article);
        if (!flg) return Result.error(Constants.CODE_250, "系统错误");

        return Result.success();
    }




    @GetMapping("/top")
    @ApiOperation("查询点赞倒序文章列表")
    public Result findPageByTop() {

        List<Article> pageByTop = articleService.findPageByTop();
        return Result.success(pageByTop);

}

    @GetMapping("/attention")
    @ApiOperation("查询某用户关注的人发表的文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNub", value = "第几页", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页展示多少条数据", required = false, paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "用户id", required = true, paramType = "query")
    })
    public IPage<Article>  findPageByAttention(@RequestParam Integer userid,
                                                @RequestParam(defaultValue = "1") Integer pageNub,
                                                @RequestParam(defaultValue = "20") Integer pageSize) {
        return articleService.findPageByAttention(pageNub, pageSize, userid);
    }

    @GetMapping("/{articleId}")
    @ApiOperation("查询某条帖子的全部信息查询某条帖子的全部信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章id", required = true, paramType = "path")
    })
    public Result selectDetail(@PathVariable Integer articleId){
        Article article = articleService.selectDetail(articleId);
        return Result.success(article);
    }

    @GetMapping("/search")
    @ApiOperation("模糊查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "val", value = "搜索内容", required = true, paramType = "query")
    })
    public Result searchArticle(@RequestParam String val){
        List<Article> list =  articleService.searchArticle(val);


        return Result.success(list);
    }


    @GetMapping("/create")
    @ApiOperation("查询用户创作的文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNub", value = "第几页", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页展示多少条数据", required = false, paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "用户id", required = true, paramType = "query")
    })
    public IPage<Article>  findPageByCreate(@RequestParam Integer userid,
                                                @RequestParam(defaultValue = "1") Integer pageNub,
                                                @RequestParam(defaultValue = "20") Integer pageSize) {
        return articleService.findPageByCreate(pageNub, pageSize, userid);
    }


    @GetMapping("/collect")
    @ApiOperation("查询用户收藏的文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNub", value = "第几页", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页展示多少条数据", required = false, paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "用户id", required = true, paramType = "query")
    })
    public IPage<Article>  findPageByCollect(@RequestParam Integer userid,
                                             @RequestParam(defaultValue = "1") Integer pageNub,
                                             @RequestParam(defaultValue = "20") Integer pageSize) {
        return articleService.findPageByCollect(pageNub, pageSize, userid);
    }

    @GetMapping("/like")
    @ApiOperation("查询用户点赞的文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNub", value = "第几页", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页展示多少条数据", required = false, paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "用户id", required = true, paramType = "query")
    })
    public IPage<Article>  findPageByLike(@RequestParam Integer userid,
                                              @RequestParam(defaultValue = "1") Integer pageNub,
                                              @RequestParam(defaultValue = "20") Integer pageSize) {
        return articleService.findPageByLike(pageNub, pageSize, userid);
    }


    @DeleteMapping
    @ApiOperation("删除指定文章")
    public Result deleteArticle(@RequestBody DeleteIdDto dto) {
        Boolean flg =  articleService.deleteArticle(dto.getDeleteId());
        if (!flg) return Result.error(Constants.CODE_250, "系统错误");

        return Result.success();
    }

}

