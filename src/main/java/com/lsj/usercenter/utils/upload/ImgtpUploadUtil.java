package com.lsj.usercenter.utils.upload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsj.usercenter.model.dto.ImgTPResult;
import com.lsj.usercenter.model.execption.BusinessExecption;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

import static com.lsj.usercenter.model.common.ErrCode.ERR_UPLODA_AVATAR;


@Component
public class ImgtpUploadUtil {


    private String token;

    private ImgtpPicProperties picProperties;
    private ObjectMapper objectMapper;
    private OkHttpClient client;

    @Autowired
    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.client = okHttpClient;
    }

    @Autowired
    public void setPicProperties(ImgtpPicProperties picProperties) {
        this.picProperties = picProperties;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    synchronized String getToken() {

        if (!StringUtils.isBlank(this.token)) {
            return this.token;
        }

        HttpUrl url = Objects.requireNonNull(HttpUrl.parse("https://imgtp.com/api/token"))
                .newBuilder()
                .addQueryParameter("email", picProperties.getEmail())
                .addQueryParameter("password", picProperties.getPassword())
                .build();
        String value;
        try {
            value = objectMapper.writeValueAsString(picProperties);
        } catch (JsonProcessingException e) {
            throw new BusinessExecption(ERR_UPLODA_AVATAR, e.getMessage());
        }
        Request request = new Request.Builder()
                .post(RequestBody.create(value,
                        MediaType.get("application/json; charset=utf-8")))
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            ImgTPResult tokenResult = objectMapper.readValue(Objects.requireNonNull(response.body()).bytes(), ImgTPResult.class);
            if (response.isSuccessful()) {
                this.token = tokenResult.getData().getToken();
                return this.token;
            }
            throw new BusinessExecption(ERR_UPLODA_AVATAR, "头像上传失败" + tokenResult.getMsg());
        } catch (IOException e) {
            throw new BusinessExecption(ERR_UPLODA_AVATAR, "头像上传失败" + e.getMessage());
        }

    }


    public String uploadfile(byte[] content, String filename) {

        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", filename,
                        RequestBody.create(content, MediaType.parse("application/octet-stream")))
                .build();

        Request request = new Request.Builder()
                .addHeader("token", getToken())
                .post(multipartBody)
                .url("https://imgtp.com/api/upload")
                .build();

        try (Response response = client.newCall(request).execute()) {
            ImgTPResult imgtpResponse = objectMapper.readValue(Objects.requireNonNull(response.body()).bytes(), ImgTPResult.class);
            if (response.isSuccessful()) {
                return imgtpResponse.getData().getUrl();
            }
            throw new BusinessExecption(ERR_UPLODA_AVATAR, "头像上传失败" + imgtpResponse.getMsg());
        } catch (IOException e) {
            throw new BusinessExecption(ERR_UPLODA_AVATAR, "头像上传失败" + e.getMessage());
        }
    }


}
