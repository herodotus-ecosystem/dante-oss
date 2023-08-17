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

package cn.herodotus.oss.dialect.s3.converter.domain;

import cn.herodotus.oss.definition.domain.base.OwnerDomain;
import cn.herodotus.oss.definition.domain.bucket.BucketDomain;
import com.amazonaws.services.s3.model.Bucket;
import org.springframework.core.convert.converter.Converter;

import java.util.Optional;

/**
 * <p>Description: S3 Bucket 转 BucketDomain 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/15 21:28
 */
public class BucketToDomainConverter implements Converter<Bucket, BucketDomain> {
    @Override
    public BucketDomain convert(Bucket source) {

        Optional<Bucket> optional = Optional.ofNullable(source);
        return optional.map(bucket -> {

            BucketDomain bucketDomain = new BucketDomain();

            Optional.ofNullable(bucket.getOwner()).ifPresent(o -> {
                OwnerDomain ownerAttributeDomain = new OwnerDomain();
                ownerAttributeDomain.setId(bucket.getOwner().getId());
                ownerAttributeDomain.setDisplayName(bucket.getOwner().getDisplayName());
                bucketDomain.setOwner(ownerAttributeDomain);
            });

            bucketDomain.setBucketName(bucket.getName());
            bucketDomain.setCreationDate(bucket.getCreationDate());

            return bucketDomain;
        }).orElse(null);
    }
}
