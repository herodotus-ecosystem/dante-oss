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

package cn.herodotus.oss.dialect.s3.converter.domain;

import cn.herodotus.oss.specification.domain.base.OwnerDomain;
import cn.herodotus.oss.specification.domain.multipart.ListPartsDomain;
import cn.herodotus.oss.specification.domain.multipart.PartSummaryDomain;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.PartListing;
import com.amazonaws.services.s3.model.PartSummary;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

/**
 * <p>Description: PartListing 转 ListPartsDomain 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/14 20:44
 */
public class PartListingToDomainConverter implements Converter<PartListing, ListPartsDomain> {

    private final Converter<Owner, OwnerDomain> toAttribute = new OwnerToDomainConverter();
    private final Converter<List<PartSummary>, List<PartSummaryDomain>> toDomain = new PartSummaryToDomainConverter();

    @Override
    public ListPartsDomain convert(PartListing source) {

        ListPartsDomain domain = new ListPartsDomain();
        domain.setOwner(toAttribute.convert(source.getOwner()));
        domain.setInitiator(toAttribute.convert(source.getInitiator()));
        domain.setStorageClass(source.getStorageClass());
        domain.setMaxParts(source.getMaxParts());
        domain.setPartNumberMarker(source.getPartNumberMarker());
        domain.setNextPartNumberMarker(source.getNextPartNumberMarker());
        domain.setTruncated(source.isTruncated());
        domain.setParts(toDomain.convert(source.getParts()));
        domain.setUploadId(source.getUploadId());
        domain.setBucketName(source.getBucketName());
        domain.setObjectName(source.getKey());

        return domain;
    }
}
