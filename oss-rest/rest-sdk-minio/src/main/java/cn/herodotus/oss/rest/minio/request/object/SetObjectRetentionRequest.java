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

package cn.herodotus.oss.rest.minio.request.object;

import cn.herodotus.oss.dialect.minio.converter.retention.DomainToRetentionConverter;
import cn.herodotus.oss.dialect.minio.domain.RetentionDomain;
import cn.herodotus.oss.rest.minio.definition.ObjectVersionRequest;
import io.minio.SetObjectRetentionArgs;
import io.minio.messages.Retention;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>Description: SetObjectRetentionRequest </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/4/18 16:03
 */
@Schema(name = "设置对象保留请求参数实体", title = "设置对象保留请求参数实体")
public class SetObjectRetentionRequest extends ObjectVersionRequest<SetObjectRetentionArgs.Builder, SetObjectRetentionArgs> {

    private final Converter<RetentionDomain, Retention> toRetention = new DomainToRetentionConverter();

    @Schema(name = "保留配置", requiredMode = Schema.RequiredMode.REQUIRED, description = "既然是设置操作那么设置值就不能为空")
    private RetentionDomain retention;

    @Schema(name = "使用Governance模式")
    private Boolean bypassGovernanceMode;

    public RetentionDomain getRetention() {
        return retention;
    }

    public void setRetention(RetentionDomain retention) {
        this.retention = retention;
    }

    public Boolean getBypassGovernanceMode() {
        return bypassGovernanceMode;
    }

    public void setBypassGovernanceMode(Boolean bypassGovernanceMode) {
        this.bypassGovernanceMode = bypassGovernanceMode;
    }

    @Override
    public void prepare(SetObjectRetentionArgs.Builder builder) {
        builder.config(toRetention.convert(getRetention()));

        if (ObjectUtils.isNotEmpty(getBypassGovernanceMode())) {
            builder.bypassGovernanceMode(getBypassGovernanceMode());
        }
        super.prepare(builder);
    }

    @Override
    public SetObjectRetentionArgs.Builder getBuilder() {
        return SetObjectRetentionArgs.builder();
    }
}
