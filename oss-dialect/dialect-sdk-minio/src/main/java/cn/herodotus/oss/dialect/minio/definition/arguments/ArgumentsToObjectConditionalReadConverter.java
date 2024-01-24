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

package cn.herodotus.oss.dialect.minio.definition.arguments;

import cn.herodotus.engine.assistant.core.utils.type.DateTimeUtils;
import cn.herodotus.stirrup.core.definition.constants.SymbolConstants;
import cn.herodotus.oss.specification.arguments.base.ObjectConditionalReadArguments;
import io.minio.ObjectConditionalReadArgs;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>Description: 统一定义对象条件请求参数转换为 Minio 参数转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/15 21:40
 */
public abstract class ArgumentsToObjectConditionalReadConverter<S extends ObjectConditionalReadArguments, T extends ObjectConditionalReadArgs, B extends ObjectConditionalReadArgs.Builder<B, T>> extends ArgumentsToObjectReadConverter<S, T, B> {

    @Override
    public void prepare(S arguments, B builder) {

        if (ObjectUtils.isNotEmpty(arguments.getLength()) && arguments.getLength() >= 0) {
            builder.length(arguments.getLength());
        }

        if (ObjectUtils.isNotEmpty(arguments.getOffset()) && arguments.getOffset() >= 0) {
            builder.offset(arguments.getOffset());
        }

        if (CollectionUtils.isNotEmpty(arguments.getMatchETag())) {
            builder.matchETag(StringUtils.join(arguments.getMatchETag(), SymbolConstants.COMMA));
        }

        if (CollectionUtils.isNotEmpty(arguments.getNotMatchEtag())) {
            builder.notMatchETag(StringUtils.join(arguments.getNotMatchEtag(), SymbolConstants.COMMA));
        }

        if (ObjectUtils.isNotEmpty(arguments.getModifiedSince())) {
            builder.modifiedSince(DateTimeUtils.dateToZonedDateTime(arguments.getModifiedSince()));
        }

        if (ObjectUtils.isNotEmpty(arguments.getUnmodifiedSince())) {
            builder.unmodifiedSince(DateTimeUtils.dateToZonedDateTime(arguments.getUnmodifiedSince()));
        }

        super.prepare(arguments, builder);
    }
}
