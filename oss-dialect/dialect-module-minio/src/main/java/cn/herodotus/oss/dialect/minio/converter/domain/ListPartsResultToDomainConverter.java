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

import cn.herodotus.oss.core.arguments.multipart.ListPartsArguments;
import cn.herodotus.oss.core.domain.base.OwnerDomain;
import cn.herodotus.oss.core.domain.multipart.ListPartsDomain;
import cn.herodotus.oss.core.domain.multipart.PartSummaryDomain;
import cn.herodotus.oss.core.minio.converter.domain.OwnerToDomainConverter;
import io.minio.messages.Initiator;
import io.minio.messages.ListPartsResult;
import io.minio.messages.Owner;
import io.minio.messages.Part;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

/**
 * <p>Description: ListPartsResult 转 PartSummaryDomain </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/13 23:42
 */
public class ListPartsResultToDomainConverter implements Converter<ListPartsResult, ListPartsDomain> {

    private final Converter<Owner, OwnerDomain> owner = new OwnerToDomainConverter();
    private final Converter<Initiator, OwnerDomain> initiator = new InitiatorToDomainConverter();
    private final Converter<List<Part>, List<PartSummaryDomain>> parts = new PartToDomainConverter();

    private final ListPartsArguments arguments;

    public ListPartsResultToDomainConverter(ListPartsArguments arguments) {
        this.arguments = arguments;
    }

    @Override
    public ListPartsDomain convert(ListPartsResult source) {

        ListPartsDomain domain = new ListPartsDomain();
        domain.setOwner(owner.convert(source.owner()));
        domain.setInitiator(initiator.convert(source.initiator()));
        domain.setStorageClass(source.storageClass());
        domain.setMaxParts(source.maxParts());
        domain.setPartNumberMarker(source.partNumberMarker());
        domain.setNextPartNumberMarker(source.nextPartNumberMarker());
        domain.setTruncated(source.isTruncated());
        domain.setParts(parts.convert(source.partList()));
        domain.setUploadId(arguments.getUploadId());
        domain.setBucketName(source.bucketName());
        domain.setObjectName(source.objectName());

        return domain;
    }
}