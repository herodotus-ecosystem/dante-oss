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

package cn.herodotus.oss.rest.integration.controller;

import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.rest.core.annotation.AccessLimited;
import cn.herodotus.engine.rest.core.annotation.Idempotent;
import cn.herodotus.engine.rest.core.controller.Controller;
import cn.herodotus.oss.definition.adapter.OssObjectAdapter;
import cn.herodotus.oss.definition.arguments.object.DeleteObjectArguments;
import cn.herodotus.oss.definition.arguments.object.DeleteObjectsArguments;
import cn.herodotus.oss.definition.arguments.object.ListObjectsArguments;
import cn.herodotus.oss.definition.arguments.object.ListObjectsV2Arguments;
import cn.herodotus.oss.definition.domain.object.DeleteObjectDomain;
import cn.herodotus.oss.definition.domain.object.ObjectListingDomain;
import cn.herodotus.oss.definition.domain.object.ObjectListingV2Domain;
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
 * <p>Description: OSS统一对象管理接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/10 14:15
 */
@RestController
@RequestMapping("/oss/object")
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "OSS统一管理接口"),
        @Tag(name = "OSS统一对象管理接口")
})
public class OssObjectController implements Controller {

    private final OssObjectAdapter ossObjectAdapter;

    public OssObjectController(OssObjectAdapter ossObjectAdapter) {
        this.ossObjectAdapter = ossObjectAdapter;
    }

    @AccessLimited
    @Operation(summary = "获取对象列表", description = "获取对象列表",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "所有对象", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ObjectListingDomain.class))),
                    @ApiResponse(responseCode = "200", description = "查询成功，查到数据"),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败"),
                    @ApiResponse(responseCode = "503", description = "Minio Server无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "arguments", required = true, description = "ListObjectsArguments参数实体", schema = @Schema(implementation = ListObjectsArguments.class))
    })
    @GetMapping("/list")
    public Result<ObjectListingDomain> list(@Validated ListObjectsArguments arguments) {
        ObjectListingDomain domain = ossObjectAdapter.listObjects(arguments);
        return result(domain);
    }

    @AccessLimited
    @Operation(summary = "获取对象列表V2", description = "获取对象列表V2",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "所有对象", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ObjectListingDomain.class))),
                    @ApiResponse(responseCode = "200", description = "查询成功，查到数据"),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败"),
                    @ApiResponse(responseCode = "503", description = "Minio Server无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "arguments", required = true, description = "ListObjectsV2Arguments参数实体", schema = @Schema(implementation = ListObjectsV2Arguments.class))
    })
    @GetMapping("/v2/list")
    public Result<ObjectListingV2Domain> list(@Validated ListObjectsV2Arguments arguments) {
        ObjectListingV2Domain domain = ossObjectAdapter.listObjectsV2(arguments);
        return result(domain);
    }

    @Idempotent
    @Operation(summary = "删除一个对象", description = "根据传入的参数对指定对象进行删除",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "Minio API无返回值，所以返回200即表示成功，不成功会抛错", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "200", description = "操作成功"),
                    @ApiResponse(responseCode = "500", description = "操作失败，具体查看错误信息内容"),
                    @ApiResponse(responseCode = "503", description = "Minio Server无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "arguments", required = true, description = "DeleteObjectArguments参数实体", schema = @Schema(implementation = DeleteObjectArguments.class))
    })
    @DeleteMapping
    public Result<Boolean> deleteObject(@Validated @RequestBody DeleteObjectArguments arguments) {
        ossObjectAdapter.deleteObject(arguments);
        return result(true);
    }

    @Idempotent
    @Operation(summary = "删除多个对象", description = "根据传入的参数对指定多个对象进行删除",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "返回删除操作出错对象的具体信息", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
                    @ApiResponse(responseCode = "200", description = "查询成功，查到数据"),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败"),
                    @ApiResponse(responseCode = "503", description = "Minio Server无法访问或未启动")
            })
    @Parameters({
            @Parameter(name = "arguments", required = true, description = "删除对象请求参数实体", schema = @Schema(implementation = DeleteObjectsArguments.class))
    })
    @DeleteMapping("/multi")
    public Result<List<DeleteObjectDomain>> removeObjects(@Validated @RequestBody DeleteObjectsArguments arguments) {
        List<DeleteObjectDomain> domains = ossObjectAdapter.deleteObjects(arguments);
        return result(domains);
    }
}
