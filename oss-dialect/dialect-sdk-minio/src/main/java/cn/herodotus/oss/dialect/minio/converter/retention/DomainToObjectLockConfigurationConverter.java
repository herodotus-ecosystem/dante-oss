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

package cn.herodotus.oss.dialect.minio.converter.retention;

import cn.herodotus.oss.dialect.minio.domain.ObjectLockConfigurationDomain;
import cn.herodotus.oss.dialect.minio.enums.RetentionModeEnums;
import cn.herodotus.oss.dialect.minio.enums.RetentionUnitEnums;
import io.minio.messages.*;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>Description: Minio Request 转 ObjectLockConfiguration 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/6 23:06
 */
public class DomainToObjectLockConfigurationConverter implements Converter<ObjectLockConfigurationDomain, ObjectLockConfiguration> {

    private final Converter<RetentionModeEnums, RetentionMode> toRetentionMode = new EnumToRetentionModeConverter();

    @Override
    public ObjectLockConfiguration convert(ObjectLockConfigurationDomain source) {

        if (isRetentionModeValid(source) && isRetentionDurationModeValid(source)) {
            RetentionMode mode = toRetentionMode.convert(source.getMode());
            RetentionDuration duration = getRetentionDuration(source.getUnit(), source.getValidity());
            return new ObjectLockConfiguration(mode, duration);
        }

        return null;
    }

    private boolean isRetentionModeValid(ObjectLockConfigurationDomain source) {
        RetentionModeEnums enums = source.getMode();
        return ObjectUtils.isNotEmpty(enums);
    }

    private boolean isRetentionDurationModeValid(ObjectLockConfigurationDomain source) {
        RetentionUnitEnums enums = source.getUnit();
        Integer duration = source.getValidity();
        return ObjectUtils.isNotEmpty(enums) && ObjectUtils.isNotEmpty(duration) && duration != 0;
    }

    private RetentionDuration getRetentionDuration(RetentionUnitEnums enums, Integer duration) {
        if (enums == RetentionUnitEnums.DAYS) {
            return new RetentionDurationDays(duration);
        } else {
            return new RetentionDurationYears(duration);
        }
    }
}
