/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante OSS 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/herodotus/dante-oss>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/herodotus/dante-oss>
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.oss.minio.scenario.controller;

import cn.herodotus.engine.rest.core.annotation.Idempotent;
import cn.herodotus.oss.minio.core.domain.ObjectWriteDomain;
import cn.herodotus.oss.minio.scenario.request.ObjectDownloadRequest;
import cn.herodotus.oss.minio.scenario.service.MinioObjectStreamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>Description: Minio 对象下载接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/9 16:44
 */
@RestController
@RequestMapping("/oss/minio/object/stream")
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "Minio 对象存储管理接口"),
        @Tag(name = "Minio 对象下载接口")
})
public class MinioObjectStreamController {

    private static final Logger log = LoggerFactory.getLogger(MinioObjectStreamController.class);

    private final MinioObjectStreamService minioObjectStreamService;

    public MinioObjectStreamController(MinioObjectStreamService minioObjectStreamService) {
        this.minioObjectStreamService = minioObjectStreamService;
    }

    @Idempotent
    @Operation(summary = "下载", description = "下载Object对应的文件",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "所有对象", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "500", description = "操作失败"),
                    @ApiResponse(responseCode = "503", description = "Minio Server无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "request", required = true, description = "ObjectDownloadRequest请求参数实体", schema = @Schema(implementation = ObjectDownloadRequest.class))
    })
    @PostMapping("/download")
    public void download(@Validated @RequestBody ObjectDownloadRequest request, HttpServletResponse response) {
        try {
            minioObjectStreamService.download(request.getBucketName(), request.getObjectName(), response);
        } catch (IOException e) {
            log.error("[Herodotus] |- Download file from minio catch error", e);
        }
    }

    @Idempotent
    @Operation(summary = "文件上传", description = "普通的文件上传操作接口",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "所有对象", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ObjectWriteDomain.class))),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "500", description = "操作失败"),
                    @ApiResponse(responseCode = "503", description = "Minio Server无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "bucketName", required = true, description = "存储桶名称"),
            @Parameter(name = "file", required = true, description = "文件", schema = @Schema(implementation = MultipartFile.class))
    })
    @PostMapping("/upload")
    public ObjectWriteDomain upload(@RequestParam(value = "bucketName") String bucketName, @RequestPart(value = "file") MultipartFile file, HttpServletRequest request) {
        return minioObjectStreamService.upload(bucketName, file);
    }
}
