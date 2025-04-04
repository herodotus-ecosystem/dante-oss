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

package cn.herodotus.oss.rest.minio.definition;

import cn.herodotus.oss.dialect.minio.converter.sse.RequestToServerSideEncryptionCustomerKeyConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.minio.ObjectReadArgs;
import io.minio.ServerSideEncryptionCustomerKey;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>Description: ObjectReadRequest </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/30 23:18
 */
public abstract class ObjectReadRequest<B extends ObjectReadArgs.Builder<B, A>, A extends ObjectReadArgs> extends ObjectVersionRequest<B, A> {

    private final Converter<String, ServerSideEncryptionCustomerKey> toCustomerKey = new RequestToServerSideEncryptionCustomerKeyConverter();

    @Schema(name = "服务端加密自定义Key", description = "Minio 默认仅支持 256 位 AES")
    private String customerKey;

    @JsonIgnore
    private ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey;

    public String getCustomerKey() {
        return customerKey;
    }

    public void setCustomerKey(String customerKey) {
        this.customerKey = customerKey;
    }

    public ServerSideEncryptionCustomerKey getServerSideEncryptionCustomerKey() {
        serverSideEncryptionCustomerKey = toCustomerKey.convert(getCustomerKey());
        return serverSideEncryptionCustomerKey;
    }

    public void setServerSideEncryptionCustomerKey(ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey) {
        this.serverSideEncryptionCustomerKey = serverSideEncryptionCustomerKey;
    }

    @Override
    public void prepare(B builder) {

        if (ObjectUtils.isNotEmpty(getServerSideEncryptionCustomerKey())) {
            builder.ssec(getServerSideEncryptionCustomerKey());
        }

        super.prepare(builder);
    }
}
