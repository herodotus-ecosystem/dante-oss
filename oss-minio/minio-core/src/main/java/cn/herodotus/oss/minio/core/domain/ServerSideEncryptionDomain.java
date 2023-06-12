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

package cn.herodotus.oss.minio.core.domain;

import cn.herodotus.engine.assistant.core.definition.domain.Entity;
import cn.herodotus.oss.minio.core.enums.ServerSideEncryptionEnums;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

/**
 * <p>Description: 服务端加密域对象 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/9 12:39
 */
public class ServerSideEncryptionDomain implements Entity {

    @Schema(name = "服务端加密方式类型", description = "1:SSE_KMS, 2:SSE_S3, 3: 自定义")
    private ServerSideEncryptionEnums type;

    @Schema(name = "自定义服务端加密方式加密Key", description = "Minio 默认仅支持 256 位 AES")
    private String customerKey;

    @Schema(name = "KMS加密MasterKeyId", description = "可选参数，主要用于AWS_KMS加密算法")
    private String keyId;

    @Schema(name = "KMS加密context", description = "可选参数，主要用于AWS_KMS加密算法")
    private Map<String, String> context;

    public ServerSideEncryptionEnums getType() {
        return type;
    }

    public void setType(ServerSideEncryptionEnums type) {
        this.type = type;
    }

    public String getCustomerKey() {
        return customerKey;
    }

    public void setCustomerKey(String customerKey) {
        this.customerKey = customerKey;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public Map<String, String> getContext() {
        return context;
    }

    public void setContext(Map<String, String> context) {
        this.context = context;
    }
}
