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

package cn.herodotus.oss.dialect.autoconfigure.condition;

import cn.herodotus.engine.assistant.core.context.PropertyResolver;
import cn.herodotus.oss.dialect.core.constants.OssConstants;
import cn.herodotus.oss.dialect.core.enums.Dialect;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * <p>Description: 使用 Minio 实现条件 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/25 16:25
 */
public class UseAliyunDialectCondition implements Condition {

    private static final Logger log = LoggerFactory.getLogger(UseAliyunDialectCondition.class);

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String property = PropertyResolver.getProperty(context, OssConstants.ITEM_OSS_DIALECT);
        boolean result = StringUtils.equalsIgnoreCase(property, Dialect.ALIYUN.name());
        log.debug("[Herodotus] |- Condition [Use Aliyun Dialect] value is [{}]", result);
        return result;
    }
}
