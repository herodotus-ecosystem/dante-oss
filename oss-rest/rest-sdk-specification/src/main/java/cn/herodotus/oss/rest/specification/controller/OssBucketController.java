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
import cn.herodotus.engine.rest.core.annotation.AccessLimited;
import cn.herodotus.engine.rest.core.annotation.Idempotent;
import cn.herodotus.engine.rest.core.controller.Controller;
import cn.herodotus.oss.specification.arguments.bucket.CreateBucketArguments;
import cn.herodotus.oss.specification.arguments.bucket.DeleteBucketArguments;
import cn.herodotus.oss.specification.core.repository.OssBucketRepository;
import cn.herodotus.oss.specification.domain.bucket.BucketDomain;
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

import java.util.List;

/**
 * <p>Description: Dante OSS 统一存储桶管理接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/25 22:10
 */
@RestController
@RequestMapping("/oss/bucket")
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "OSS统一管理接口"),
        @Tag(name = "OSS统一存储桶管理接口")
})
public class OssBucketController implements Controller {

    private final OssBucketRepository ossBucketHandler;

    public OssBucketController(OssBucketRepository ossBucketHandler) {
        this.ossBucketHandler = ossBucketHandler;
    }

    @AccessLimited
    @Operation(summary = "查询存储桶是否存在", description = "根据BucketName查询存储桶是否存在",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "是否Bucket存在", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "200", description = "查询成功"),
                    @ApiResponse(responseCode = "500", description = "查询失败"),
                    @ApiResponse(responseCode = "503", description = "OSS Server无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "bucketName", required = true, description = "存储桶名称")
    })
    @GetMapping("/exists")
    public Result<Boolean> doesBucketExist(String bucketName) {
        boolean isExists = ossBucketHandler.doesBucketExist(bucketName);
        return result(isExists);
    }

    @AccessLimited
    @Operation(summary = "获取全部存储桶(Bucket)", description = "获取全部存储桶(Bucket)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "所有Buckets", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
                    @ApiResponse(responseCode = "200", description = "查询成功，查到数据"),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败"),
                    @ApiResponse(responseCode = "503", description = "Minio Server无法访问或未启动")
            })
    @GetMapping("/list")
    public Result<List<BucketDomain>> listBuckets() {
        List<BucketDomain> domains = ossBucketHandler.listBuckets();
        return result(domains);
    }

    @Idempotent
    @Operation(summary = "创建存储桶", description = "创建存储桶接口，该接口仅是创建，不包含是否已存在检查",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "Minio API 无返回值，所以返回200即表示成功，不成功会抛错", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "500", description = "操作失败，具体查看错误信息内容"),
                    @ApiResponse(responseCode = "503", description = "Minio Server无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "arguments", required = true, description = "CreateBucketArguments请求参数实体", schema = @Schema(implementation = CreateBucketArguments.class))
    })
    @PostMapping
    public Result<Boolean> createBucket(@Validated @RequestBody CreateBucketArguments arguments) {
        // Minio 的 MakeBucket 没有返回值
        ossBucketHandler.createBucket(arguments);
        return result(true);
    }

    @Idempotent
    @Operation(summary = "删除存储桶", description = "根据存储桶名称删除数据，可指定 Region",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "Minio API 无返回值，所以返回200即表示成功，不成功会抛错", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "500", description = "操作失败，具体查看错误信息内容"),
                    @ApiResponse(responseCode = "503", description = "Minio Server 无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "arguments", required = true, description = "DeleteBucketArguments请求参数实体", schema = @Schema(implementation = DeleteBucketArguments.class))
    })
    @DeleteMapping
    public Result<Boolean> deleteBucket(@Validated @RequestBody DeleteBucketArguments arguments) {
        ossBucketHandler.deleteBucket(arguments);
        return result(true);
    }
}
