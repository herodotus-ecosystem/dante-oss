/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante OSS licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante OSS 是 Dante Cloud 对象存储组件库 采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante OSS 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.oss.rest.minio.controller;

import cn.herodotus.engine.assistant.definition.domain.Result;
import cn.herodotus.engine.rest.core.annotation.Idempotent;
import cn.herodotus.engine.rest.core.controller.Controller;
import cn.herodotus.oss.dialect.minio.service.MinioBucketTagsService;
import cn.herodotus.oss.rest.minio.request.bucket.DeleteBucketTagsRequest;
import cn.herodotus.oss.rest.minio.request.bucket.SetBucketTagsRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>Description: Minio 存储桶标签管理接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/6 22:56
 */
@RestController
@RequestMapping("/oss/minio/bucket/tags")
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "Minio 对象存储管理接口"),
        @Tag(name = "Minio 存储桶标签管理接口")
})
public class MinioBucketTagsController implements Controller {

    private final MinioBucketTagsService minioBucketTagsService;

    public MinioBucketTagsController(MinioBucketTagsService minioBucketTagsService) {
        this.minioBucketTagsService = minioBucketTagsService;
    }

    @Idempotent
    @Operation(summary = "设置存储桶标签", description = "设置存储桶标签",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "Minio API 无返回值，所以返回200即表示成功，不成功会抛错", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "500", description = "操作失败，具体查看错误信息内容"),
                    @ApiResponse(responseCode = "503", description = "Minio Server 无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "request", required = true, description = "SetBucketTagsRequest请求参数实体", schema = @Schema(implementation = SetBucketTagsRequest.class))
    })
    @PutMapping
    public Result<Boolean> set(@Validated @RequestBody SetBucketTagsRequest request) {
        minioBucketTagsService.setBucketTags(request.build());
        return result(true);
    }

    @Idempotent
    @Operation(summary = "清空存储桶标签", description = "利用Tags的增减就可以实现Tags的删除，所以这个删除应该理解成清空",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "Minio API 无返回值，所以返回200即表示成功，不成功会抛错", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "500", description = "操作失败，具体查看错误信息内容"),
                    @ApiResponse(responseCode = "503", description = "Minio Server 无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "request", required = true, description = "DeleteBucketPolicyRequest请求参数实体", schema = @Schema(implementation = DeleteBucketTagsRequest.class))
    })
    @DeleteMapping
    public Result<Boolean> delete(@Validated @RequestBody DeleteBucketTagsRequest request) {
        minioBucketTagsService.deleteBucketTags(request.build());
        return result(true);
    }
}
