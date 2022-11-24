package com.qb.springboot.webhomework.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qb.springboot.webhomework.entity.FilePojo;
import com.qb.springboot.webhomework.mapper.FileMapper;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Api(tags = "文件接口")
@RestController
@RequestMapping("/file")
public class FileController {


    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Value("${server.ip}")
    private String ip;

    @Value("${server.port}")
    private String port;

    @Resource
    private FileMapper fileMapper;


    @GetMapping
    public  String index(){
        return "hyfgbgx";
    }

    @PostMapping("/upload")
    public  String uploda(@RequestParam MultipartFile file) throws IOException {
        //获取上传文件的文件名
        String originalFilename = file.getOriginalFilename();
        //获取上传文件的拓展文件名
        String type = FileUtil.extName(originalFilename);
        //获取上传文件的大小
        long size = file.getSize();

        //new一个存文件的目录，如果目录不存在就创建它
        File uploadFile = new File(fileUploadPath);
        if (!uploadFile.exists()) {
            uploadFile.mkdirs();
        }

        //随机生成一串uuid
        String uuid = IdUtil.fastSimpleUUID();
        //创建保存到磁盘的文件
        File one = new File(fileUploadPath + uuid + "." + type);
        //创建上传文件的唯一标识符
        String md5 = SecureUtil.md5(file.getInputStream());
        //如果数据库中存在该 md5， 说明该文件已经存在于磁盘中，否则是新文件
        QueryWrapper<FilePojo> wrapper = new QueryWrapper<>();
        wrapper.eq("md5", md5);
        List<FilePojo> filePojoList = fileMapper.selectList(wrapper);
        //文件下载路径
        String url;
        if (filePojoList.size() == 0) {
            //将文件存入磁盘
            file.transferTo(one);
            //新建下载路径
            //url = "http://47.109.58.84:9090/file/" + uuid + "." + type;
            //url = "http://localhost:9090/file/" + uuid + "." + type;
            url = "http://" + ip + ":" + port + "/file/" + uuid + "." + type;
        }else {
            //下载路径为数据库中已存在文件的下载路径
            url = filePojoList.get(0).getUrl();
        }


        //将上传文件记录更新到数据库
        FilePojo saveFile = new FilePojo();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size / 1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileMapper.insert(saveFile);

        //返回文件下载路径
        return url;
    }

    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException{
        File file = new File(fileUploadPath + fileUUID);

        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");

        os.write(FileUtil.readBytes(file));
        os.flush();
        os.close();

    }





}
