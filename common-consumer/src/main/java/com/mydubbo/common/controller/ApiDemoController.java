package com.mydubbo.common.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.mydubbo.common.entity.WordDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.mydubbo.common.service.ApiDemoService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

/**
 * @author xiyou
 */
@RestController
public class ApiDemoController {


    @Reference(version = "${demo.service.version}")
    private ApiDemoService apiDemoService;


    @GetMapping("/say/{name}")
    public String sayHello(@PathVariable("name") String name) {
        return apiDemoService.sayHello(name);
    }


    @GetMapping("/test/dubbo")
    public String sayHello() {
        WordDTO wordDTO = new WordDTO();
        wordDTO.setId((long) 23333);
        wordDTO.setTimestamp((long) 1223343434);
        wordDTO.setUuid("uuid");
        wordDTO.setWord("word");
        return apiDemoService.testError(wordDTO);
    }

    /**
     * 为商品上传图片
     * 上传文件-方式1：MultipartHttpServletRequest 接收前端参数
     *
     * @return
     */
    @RequestMapping(value = "upload/v1", method = RequestMethod.POST)
    public String uploadV1(MultipartHttpServletRequest request) {
        MultipartFile multipartFile = request.getFile("myFile");
        //实际的业务信息,可以自定义
        String param1 = request.getParameter("param1");
        String param2 = request.getParameter("param2");
        String param3 = request.getParameter("param3");
        System.out.println(param1 + param2 + param3);
        String result = "";
        try {
            result = upload(multipartFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 上传文件 - nio的方式
     * https://mp.weixin.qq.com/s/_dYzXpLlyWXSX5HAqolJAg
     *
     * @param file
     * @throws Exception
     */
    public String upload(MultipartFile file) throws Exception {
        SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMdd");
        String fileName = file.getOriginalFilename();
        String suffix = StringUtils.substring(fileName, StringUtils.indexOf(fileName, "."));
        Long size = file.getSize();

        //附件输入流
        InputStream is = file.getInputStream();


        String rootPath = "E:\\";
        Path path = Paths.get(rootPath);
        File file1 = new File(rootPath);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        //创建新的文件
        String newFileName = System.nanoTime() + suffix;
        String newFile = rootPath + File.separator + newFileName;
        path = Paths.get(newFile);

//        //方式一
//        Files.copy(is,path, StandardCopyOption.REPLACE_EXISTING); //如果存在则覆盖
        //方式二
        Files.write(path, file.getBytes());


        return newFile;
    }
}
