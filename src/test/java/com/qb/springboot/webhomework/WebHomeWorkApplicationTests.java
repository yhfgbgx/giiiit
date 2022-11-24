package com.qb.springboot.webhomework;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qb.springboot.webhomework.entity.Article;
import com.qb.springboot.webhomework.entity.Comment;
import com.qb.springboot.webhomework.mapper.ArticleMapper;
import com.qb.springboot.webhomework.mapper.CommentMapper;
import com.qb.springboot.webhomework.service.IArticleService;
import org.apache.catalina.security.SecurityUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class WebHomeWorkApplicationTests {

	@Resource
	private ArticleMapper articleMapper;
	@Resource
	private CommentMapper commentMapper;

	@Test
	void contextLoads() {
		String s = SecureUtil.md5("123");
		System.out.println(s);
		System.out.println(s.length());

	}

}
