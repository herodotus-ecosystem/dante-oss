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
import cn.herodotus.engine.rest.core.annotation.AccessLimited;
import cn.herodotus.oss.dialect.minio.service.MinioAdminService;
import io.minio.admin.messages.DataUsageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: Minio 统计信息接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/28 14:58
 */
@RestController
@RequestMapping("/oss/minio/admin")
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "Minio 对象存储管理接口"),
        @Tag(name = "Minio 统计信息接口")
})
public class MinioAdminController {

    private final MinioAdminService minioAdminService;

    public MinioAdminController(MinioAdminService minioAdminService) {
        this.minioAdminService = minioAdminService;
    }

    @AccessLimited
    @Operation(summary = "获取 Minio 统计信息", description = "获取 Minio 统计信息",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "统计信息", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataUsageInfo.class))),
                    @ApiResponse(responseCode = "200", description = "查询成功，查到数据"),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败"),
                    @ApiResponse(responseCode = "503", description = "Minio Server无法访问或未启动")
            })
    @GetMapping("/usage")
    public Result<DataUsageInfo> dataUsageInfo() {
        DataUsageInfo info = minioAdminService.getDataUsageInfo();
        if (ObjectUtils.isNotEmpty(info)) {
            return Result.success("查询成功", info);
        } else {
            return Result.empty();
        }
    }
}
