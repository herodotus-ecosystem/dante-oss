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

package cn.herodotus.oss.minio.rest.request.domain;

import cn.herodotus.oss.minio.rest.definition.ObjectConditionalReadRequest;
import io.minio.ComposeSource;
import io.minio.CopySource;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

/**
 * <p>Description: 组合对象源请求参数 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/31 14:37
 */
@Schema(name = "组合对象源请求参数")
public class ComposeSourceRequest extends ObjectConditionalReadRequest<ComposeSource.Builder, ComposeSource> {

    private Long objectSize = null;
    private Map<String, String> headers = null;

    public ComposeSourceRequest(ObjectConditionalReadRequest<CopySource.Builder, CopySource> request) {
        this.setExtraHeaders(request.getExtraHeaders());
        this.setExtraQueryParams(request.getExtraQueryParams());
        this.setBucketName(request.getBucketName());
        this.setRegion(request.getRegion());
        this.setObjectName(request.getObjectName());
        this.setVersionId(request.getVersionId());
        this.setServerSideEncryptionCustomerKey(request.getServerSideEncryptionCustomerKey());
        this.setOffset(request.getOffset());
        this.setLength(request.getLength());
        this.setMatchETag(request.getMatchETag());
        this.setNotMatchETag(request.getNotMatchETag());
        this.setModifiedSince(request.getModifiedSince());
        this.setUnmodifiedSince(request.getUnmodifiedSince());
    }

    public Long getObjectSize() {
        return objectSize;
    }

    public void setObjectSize(Long objectSize) {
        this.objectSize = objectSize;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public ComposeSource.Builder getBuilder() {
        return ComposeSource.builder();
    }
}
