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

package cn.herodotus.oss.rest.minio.definition;

import cn.herodotus.oss.core.minio.definition.request.ObjectRequest;
import cn.herodotus.oss.dialect.minio.converter.retention.DomainToRetentionConverter;
import cn.herodotus.oss.dialect.minio.converter.sse.RequestToServerSideEncryptionConverter;
import cn.herodotus.oss.core.minio.domain.RetentionDomain;
import cn.herodotus.oss.dialect.minio.domain.ServerSideEncryptionDomain;
import io.minio.ObjectWriteArgs;
import io.minio.ServerSideEncryption;
import io.minio.messages.Retention;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;

/**
 * <p>Description: ObjectWriteRequest </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/2 21:58
 */
public abstract class ObjectWriteRequest<B extends ObjectWriteArgs.Builder<B, A>, A extends ObjectWriteArgs> extends ObjectRequest<B, A> {

    private final Converter<RetentionDomain, Retention> toRetention = new DomainToRetentionConverter();
    private final Converter<ServerSideEncryptionDomain, ServerSideEncryption> toServerSideEncryption = new RequestToServerSideEncryptionConverter();

    @Schema(name = "自定义 Header 信息")
    private Map<String, String> headers;

    @Schema(name = "用户信息")
    private Map<String, String> userMetadata;

    @Schema(name = "服务端加密")
    private ServerSideEncryptionDomain serverSideEncryption;

    @Schema(name = "标签")
    private Map<String, String> tags;

    @Schema(name = "保留配置")
    private RetentionDomain retention;

    @Schema(name = "合法持有")
    private Boolean legalHold;

    public Converter<RetentionDomain, Retention> getToRetention() {
        return toRetention;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getUserMetadata() {
        return userMetadata;
    }

    public void setUserMetadata(Map<String, String> userMetadata) {
        this.userMetadata = userMetadata;
    }

    public ServerSideEncryptionDomain getServerSideEncryption() {
        return serverSideEncryption;
    }

    public void setServerSideEncryption(ServerSideEncryptionDomain serverSideEncryption) {
        this.serverSideEncryption = serverSideEncryption;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public RetentionDomain getRetention() {
        return retention;
    }

    public void setRetention(RetentionDomain retention) {
        this.retention = retention;
    }

    public Boolean getLegalHold() {
        return legalHold;
    }

    public void setLegalHold(Boolean legalHold) {
        this.legalHold = legalHold;
    }

    @Override
    public void prepare(B builder) {
        if (MapUtils.isNotEmpty(getHeaders())) {
            builder.headers(getHeaders());
        }

        if (MapUtils.isNotEmpty(getUserMetadata())) {
            builder.headers(getUserMetadata());
        }

        if (MapUtils.isNotEmpty(getTags())) {
            builder.headers(getTags());
        }

        builder.tags(getTags());

        if (ObjectUtils.isNotEmpty(getServerSideEncryption())) {
            ServerSideEncryption encryption = toServerSideEncryption.convert(getServerSideEncryption());
            if (ObjectUtils.isNotEmpty(encryption)) {
                builder.sse(encryption);
            }
        }

        if (ObjectUtils.isNotEmpty(getRetention())) {
            builder.retention(toRetention.convert(getRetention()));
        }

        if (ObjectUtils.isNotEmpty(getLegalHold())) {
            builder.legalHold(getLegalHold());
        }

        super.prepare(builder);
    }
}
