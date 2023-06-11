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

import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.rest.core.annotation.AccessLimited;
import cn.herodotus.engine.rest.core.controller.Controller;
import cn.herodotus.oss.minio.scenario.entity.BucketSettingEntity;
import cn.herodotus.oss.minio.scenario.entity.ObjectSettingEntity;
import cn.herodotus.oss.minio.scenario.service.BucketSettingService;
import cn.herodotus.oss.minio.scenario.service.ObjectSettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: 对象存储桶设置接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/5 22:31
 */
@RestController
@RequestMapping("/oss/minio/object/setting")
@Tags({
        @Tag(name = "对象存储管理接口"),
        @Tag(name = "Minio 对象存储管理接口"),
        @Tag(name = "Minio 对象设置接口")
})
public class ObjectSettingController implements Controller {

    private final ObjectSettingService settingService;

    public ObjectSettingController(ObjectSettingService settingService) {
        this.settingService = settingService;
    }

    @AccessLimited
    @Operation(summary = "获取对象设置信息", description = "获取对象桶设置信息",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "存储桶设置信息", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ObjectSettingEntity.class))),
                    @ApiResponse(responseCode = "200", description = "查询成功，查到数据"),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败")
            })
    @Parameters({
            @Parameter(name = "bucketName", required = true, description = "存储桶名称"),
            @Parameter(name = "objectName", required = true, description = "对象名称"),
            @Parameter(name = "region", description = "区域"),
    })
    @GetMapping
    public Result<ObjectSettingEntity> get(@RequestParam(value = "bucketName") String bucketName, @RequestParam(value = "objectName") String objectName, @RequestParam(value = "region", required = false) String region) {
        ObjectSettingEntity entity = settingService.get(bucketName, region, objectName);
        return result(entity);
    }
}
