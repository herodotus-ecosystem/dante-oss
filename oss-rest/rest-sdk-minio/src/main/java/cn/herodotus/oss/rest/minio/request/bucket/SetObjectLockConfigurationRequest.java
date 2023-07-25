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

package cn.herodotus.oss.rest.minio.request.bucket;

import cn.herodotus.oss.dialect.minio.converter.retention.DomainToObjectLockConfigurationConverter;
import cn.herodotus.oss.dialect.minio.domain.ObjectLockConfigurationDomain;
import cn.herodotus.oss.rest.minio.definition.BucketRequest;
import io.minio.SetObjectLockConfigurationArgs;
import io.minio.messages.ObjectLockConfiguration;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>Description: 设置存储桶对象锁定配置请求参数实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/6 23:01
 */
@Schema(name = "设置存储桶对象锁定配置请求参数实体", title = "设置存储桶对象锁定配置请求参数实体")
public class SetObjectLockConfigurationRequest extends BucketRequest<SetObjectLockConfigurationArgs.Builder, SetObjectLockConfigurationArgs> {

    private final Converter<ObjectLockConfigurationDomain, ObjectLockConfiguration> requestTo = new DomainToObjectLockConfigurationConverter();

    @Schema(name = "对象锁定配置", requiredMode = Schema.RequiredMode.REQUIRED, description = "既然是设置操作那么设置的值就不能为空")
    @NotNull(message = "对象锁定配置信息不能为空")
    private ObjectLockConfigurationDomain objectLock;

    public ObjectLockConfigurationDomain getObjectLock() {
        return objectLock;
    }

    public void setObjectLock(ObjectLockConfigurationDomain objectLock) {
        this.objectLock = objectLock;
    }

    @Override
    public void prepare(SetObjectLockConfigurationArgs.Builder builder) {
        // 既然是设置操作那么设置的值就不能为空
        builder.config(requestTo.convert(getObjectLock()));
        super.prepare(builder);
    }

    @Override
    public SetObjectLockConfigurationArgs.Builder getBuilder() {
        return SetObjectLockConfigurationArgs.builder();
    }
}
