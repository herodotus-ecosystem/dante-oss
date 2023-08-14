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

package cn.herodotus.oss.dialect.minio.converter.domain;

import cn.herodotus.oss.definition.attribute.OwnerAttribute;
import cn.herodotus.oss.definition.domain.multipart.MultipartUploadDomain;
import cn.herodotus.oss.dialect.minio.converter.attribute.InitiatorToAttributeConverter;
import cn.herodotus.oss.dialect.minio.converter.attribute.OwnerToAttributeConverter;
import io.minio.messages.Initiator;
import io.minio.messages.Owner;
import io.minio.messages.Upload;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: Upload 转 MultipartUploadDomain 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/13 23:05
 */
public class UploadToDomainConverter implements Converter<List<Upload>, List<MultipartUploadDomain>> {

    private final Converter<Owner, OwnerAttribute> owner = new OwnerToAttributeConverter();
    private final Converter<Initiator, OwnerAttribute> initiator = new InitiatorToAttributeConverter();

    @Override
    public List<MultipartUploadDomain> convert(List<Upload> source) {
        if (CollectionUtils.isNotEmpty(source)) {
            return source.stream().map(this::convert).toList();
        }
        return new ArrayList<>();
    }

    private MultipartUploadDomain convert(Upload source) {
        MultipartUploadDomain domain = new MultipartUploadDomain();
        domain.setKey(source.objectName());
        domain.setUploadId(source.uploadId());
        domain.setOwner(owner.convert(source.owner()));
        domain.setInitiator(initiator.convert(source.initiator()));
        domain.setStorageClass(source.storageClass());
        domain.setInitiated(Date.from(source.initiated().toInstant()));
        return domain;
    }
}
