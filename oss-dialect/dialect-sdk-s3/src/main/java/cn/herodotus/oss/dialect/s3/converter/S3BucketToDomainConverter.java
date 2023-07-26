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

package cn.herodotus.oss.dialect.s3.converter;

import cn.herodotus.oss.dialect.s3.domain.S3BucketDomain;
import cn.herodotus.oss.dialect.s3.domain.S3OwnerDomain;
import com.amazonaws.services.s3.model.Bucket;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/15 21:28
 */
public class S3BucketToDomainConverter implements Converter<Bucket, S3BucketDomain> {
    @Override
    public S3BucketDomain convert(Bucket source) {
        if (ObjectUtils.isNotEmpty(source)) {

            S3OwnerDomain domain = new S3OwnerDomain();
            domain.setDisplayName(source.getOwner().getDisplayName());
            domain.setId(source.getOwner().getId());

            S3BucketDomain bucketDomain = new S3BucketDomain();
            bucketDomain.setName(source.getName());
            bucketDomain.setOwner(domain);
            bucketDomain.setCreationDate(source.getCreationDate());

            return bucketDomain;
        }
        return null;
    }
}
