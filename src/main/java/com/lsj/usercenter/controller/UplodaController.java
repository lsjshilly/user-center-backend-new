package com.lsj.usercenter.controller;


import com.github.echisan.wbp4j.exception.UploadFailedException;
import com.lsj.usercenter.model.dto.Result;
import com.lsj.usercenter.utils.upload.ImgtpUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UplodaController {

    private ImgtpUploadUtil imgtpUploadUtil;

    @Autowired

    public void setImgtpUploadUtil(ImgtpUploadUtil imgtpUploadUtil) {
        this.imgtpUploadUtil = imgtpUploadUtil;
    }

    @PostMapping("/upload")
    public Result uploadImage(@RequestPart("avatar") MultipartFile image) throws IOException, UploadFailedException {
        String uploadfile = imgtpUploadUtil.uploadfile(image.getBytes(), image.getOriginalFilename());
        return Result.success(uploadfile);
    }
}
