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

package cn.herodotus.oss.minio.rest.request.object;

import cn.herodotus.oss.minio.rest.converter.RequestToComposeSourceConverter;
import cn.herodotus.oss.minio.rest.definition.ObjectWriteRequest;
import cn.herodotus.oss.minio.rest.request.domain.ComposeSourceRequest;
import io.minio.ComposeObjectArgs;
import io.minio.ComposeSource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>Description: 组合来自不同源对象请求参数 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/31 14:45
 */
public class ComposeObjectRequest extends ObjectWriteRequest<ComposeObjectArgs.Builder, ComposeObjectArgs> {

    private final Converter<ComposeSourceRequest, ComposeSource> requestTo = new RequestToComposeSourceConverter();
    private List<ComposeSourceRequest> sources;

    public ComposeObjectRequest(CopyObjectRequest request) {
        this.setExtraHeaders(request.getExtraHeaders());
        this.setExtraQueryParams(request.getExtraQueryParams());
        this.setBucketName(request.getBucketName());
        this.setRegion(request.getRegion());
        this.setObjectName(request.getObjectName());
        this.setHeaders(request.getHeaders());
        this.setUserMetadata(request.getUserMetadata());
        this.setServerSideEncryption(request.getServerSideEncryption());
        this.setTags(request.getTags());
        this.setRetention(request.getRetention());
        this.setLegalHold(request.getLegalHold());
        this.sources = new LinkedList<>();
        this.sources.add(new ComposeSourceRequest(request.getSource()));
    }

    public List<ComposeSourceRequest> getSources() {
        return sources;
    }

    public void setSources(List<ComposeSourceRequest> sources) {
        this.sources = sources;
    }

    @Override
    public void prepare(ComposeObjectArgs.Builder builder) {
        if (CollectionUtils.isNotEmpty(getSources())) {
            List<ComposeSource> composeSources = getSources().stream().map(requestTo::convert).toList();
            builder.sources(composeSources);
        }
        super.prepare(builder);
    }

    @Override
    public ComposeObjectArgs.Builder getBuilder() {
        return ComposeObjectArgs.builder();
    }
}
