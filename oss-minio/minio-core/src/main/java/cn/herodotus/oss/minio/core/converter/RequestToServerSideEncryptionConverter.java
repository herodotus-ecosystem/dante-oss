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

package cn.herodotus.oss.minio.core.converter;

import cn.herodotus.oss.minio.core.domain.ServerSideEncryptionDomain;
import cn.herodotus.oss.minio.core.enums.ServerSideEncryptionEnums;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.minio.ServerSideEncryption;
import io.minio.ServerSideEncryptionCustomerKey;
import io.minio.ServerSideEncryptionKms;
import io.minio.ServerSideEncryptionS3;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>Description: Minio Request 转 ServerSideEncryption 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/9 12:45
 */
public class RequestToServerSideEncryptionConverter implements Converter<ServerSideEncryptionDomain, ServerSideEncryption> {

    private static final Logger log = LoggerFactory.getLogger(RequestToServerSideEncryptionConverter.class);
    private final Converter<String, ServerSideEncryptionCustomerKey> toCustomerKey = new RequestToServerSideEncryptionCustomerKeyConverter();

    @Override
    public ServerSideEncryption convert(ServerSideEncryptionDomain source) {

        if (ObjectUtils.isNotEmpty(source.getType())) {
            ServerSideEncryptionEnums type = ServerSideEncryptionEnums.get(source.getType());
            switch (type) {
                case CUSTOM -> {
                    if (StringUtils.isNotBlank(source.getCustomerKey())) {
                        return toCustomerKey.convert(source.getCustomerKey());
                    } else {
                        return null;
                    }
                }
                case AWS_KMS -> {
                    if (StringUtils.isNotBlank(source.getKeyId())) {
                        try {
                            return new ServerSideEncryptionKms(source.getKeyId(), source.getContext());
                        } catch (JsonProcessingException e) {
                            log.error("[Herodotus] |- Minio catch JsonProcessingException in RequestToServerSideEncryptionConverter.", e);
                            return null;
                        }
                    }
                }
                default -> {
                    return new ServerSideEncryptionS3();
                }
            }
        }

        return null;
    }
}
