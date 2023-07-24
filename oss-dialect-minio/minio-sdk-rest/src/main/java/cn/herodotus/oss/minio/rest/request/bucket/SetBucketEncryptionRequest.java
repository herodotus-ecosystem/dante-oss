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

package cn.herodotus.oss.minio.rest.request.bucket;

import cn.herodotus.oss.dialect.minio.enums.SseConfigurationEnums;
import cn.herodotus.oss.minio.rest.definition.BucketRequest;
import io.minio.SetBucketEncryptionArgs;
import io.minio.messages.SseConfiguration;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.ObjectUtils;

/**
 * <p>Description: 设置存储桶加密方式请求参数实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/6 22:03
 */
@Schema(name = "设置存储桶加密方式请求参数实体", title = "设置存储桶加密方式请求参数实体")
public class SetBucketEncryptionRequest extends BucketRequest<SetBucketEncryptionArgs.Builder, SetBucketEncryptionArgs> {

    @Schema(name = "服务端加密算法", description = "1：为AWS_KMS；2：为AES256", requiredMode = Schema.RequiredMode.REQUIRED)
//    @EnumeratedValue(names = {"AWS_KMS", "AES256"}, message = "设置存储桶加密方式，必须为有效加密方式， 不能为 0 (Disabled)")
    private SseConfigurationEnums sseConfiguration;

    @Schema(name = "KMS加密MasterKeyId", description = "可选参数，主要用于AWS_KMS加密算法")
    private String kmsMasterKeyId;

    public SseConfigurationEnums getSseConfiguration() {
        return sseConfiguration;
    }

    public void setSseConfiguration(SseConfigurationEnums sseConfiguration) {
        this.sseConfiguration = sseConfiguration;
    }

    public String getKmsMasterKeyId() {
        return kmsMasterKeyId;
    }

    public void setKmsMasterKeyId(String kmsMasterKeyId) {
        this.kmsMasterKeyId = kmsMasterKeyId;
    }

    @Override
    public void prepare(SetBucketEncryptionArgs.Builder builder) {
        SseConfigurationEnums enums = getSseConfiguration();
        if (ObjectUtils.isNotEmpty(enums)) {
            if (enums == SseConfigurationEnums.AES256) {
                builder.config(SseConfiguration.newConfigWithSseS3Rule());
            } else {
                builder.config(SseConfiguration.newConfigWithSseKmsRule(getKmsMasterKeyId()));
            }
        }
        super.prepare(builder);
    }

    @Override
    public SetBucketEncryptionArgs.Builder getBuilder() {
        return SetBucketEncryptionArgs.builder();
    }
}
