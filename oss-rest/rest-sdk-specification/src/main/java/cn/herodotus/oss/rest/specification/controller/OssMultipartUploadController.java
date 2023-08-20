/*
 * Copyright (c) 2020-2030 码匠君<herodotus@aliyun.com>
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

package cn.herodotus.oss.rest.specification.controller;

import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.rest.core.annotation.Idempotent;
import cn.herodotus.engine.rest.core.controller.Controller;
import cn.herodotus.oss.rest.specification.arguments.CompleteMultipartUploadArguments;
import cn.herodotus.oss.rest.specification.arguments.CreateMultipartUploadArguments;
import cn.herodotus.oss.solution.business.CreateMultipartUploadBusiness;
import cn.herodotus.oss.solution.constants.OssSolutionConstants;
import cn.herodotus.oss.solution.proxy.OssPresignedUrlProxy;
import cn.herodotus.oss.solution.service.OssMultipartUploadService;
import cn.herodotus.oss.specification.domain.base.ObjectWriteDomain;
import cn.herodotus.oss.specification.domain.multipart.CompleteMultipartUploadDomain;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>Description: OSS统一大文件分片接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/19 18:56
 */
@RestController
@RequestMapping(OssSolutionConstants.OSS_MULTIPART_UPLOAD_REQUEST_MAPPING)
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "OSS统一管理接口"),
        @Tag(name = "OSS统一大文件分片接口")
})
public class OssMultipartUploadController implements Controller {

    private final OssMultipartUploadService ossMultipartUploadService;
    private final OssPresignedUrlProxy ossPresignedUrlProxy;

    public OssMultipartUploadController(OssMultipartUploadService ossMultipartUploadService, OssPresignedUrlProxy ossPresignedUrlProxy) {
        this.ossMultipartUploadService = ossMultipartUploadService;
        this.ossPresignedUrlProxy = ossPresignedUrlProxy;
    }


    @Idempotent
    @Operation(summary = "创建分片上传信息", description = "创建分片上传信息",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "uploadId 和 预下载地址", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateMultipartUploadBusiness.class))),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "204", description = "无结果"),
                    @ApiResponse(responseCode = "500", description = "操作失败")
            })
    @Parameters({
            @Parameter(name = "arguments", required = true, description = "CreateMultipartUploadArguments参数实体", schema = @Schema(implementation = CreateMultipartUploadArguments.class))
    })
    @PostMapping("/create")
    public Result<CreateMultipartUploadBusiness> createMultipartUpload(@Validated @RequestBody CreateMultipartUploadArguments arguments) {
        CreateMultipartUploadBusiness result = ossMultipartUploadService.createMultipartUpload(arguments.getBucketName(), arguments.getObjectName(), arguments.getPartNumber());
        return result(result);
    }

    @Idempotent
    @Operation(summary = "完成分片上传", description = "完成分片上传，Minio将上传完成的分片信息进行合并",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "操作结果", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ObjectWriteDomain.class))),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "204", description = "无结果"),
                    @ApiResponse(responseCode = "500", description = "操作失败")
            })
    @Parameters({
            @Parameter(name = "arguments", required = true, description = "CompleteMultipartUploadArguments参数实体", schema = @Schema(implementation = CompleteMultipartUploadArguments.class))
    })
    @PostMapping("/complete")
    public Result<CompleteMultipartUploadDomain> completeMultipartUpload(@Validated @RequestBody CompleteMultipartUploadArguments arguments) {
        CompleteMultipartUploadDomain entity = ossMultipartUploadService.completeMultipartUpload(arguments.getBucketName(), arguments.getObjectName(), arguments.getUploadId());
        return result(entity);
    }

    @Operation(summary = "预下载代理地址", description = "预下载代理地址，避免前端直接访问OSS，同时导致微服务寻址错误",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "操作结果", content = @Content(mediaType = "application/json")),
            })
    @PutMapping(value = OssSolutionConstants.OSS_PRESIGNED_OBJECT_PROXY_REQUEST_MAPPING)
    public ResponseEntity<String> presignedUrlProxy(HttpServletRequest request) {
        return ossPresignedUrlProxy.delegate(request);
    }
}
