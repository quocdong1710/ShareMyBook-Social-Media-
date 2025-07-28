package com.profile_service.repository.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.profile_service.configuration.AuthenticationRequestInterceptor;
import com.profile_service.dto.response.APIResponse;
import com.profile_service.dto.response.FileResponse;

@FeignClient(
        name = "file-service",
        url = "http://localhost:8084",
        configuration = {AuthenticationRequestInterceptor.class})
public interface FileClient {
    @PostMapping(value = "/file/media/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    APIResponse<FileResponse> uploadMedia(@RequestPart("file") MultipartFile file);
}
