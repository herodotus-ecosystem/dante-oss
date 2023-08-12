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

package cn.herodotus.oss.dialect.s3.adapter;

import cn.herodotus.oss.definition.adapter.OssObjectAdapter;
import cn.herodotus.oss.definition.arguments.bucket.DeleteObjectArguments;
import cn.herodotus.oss.definition.arguments.object.ListObjectsArguments;
import cn.herodotus.oss.definition.arguments.object.ListObjectsV2Arguments;
import cn.herodotus.oss.definition.domain.object.ObjectListingDomain;
import cn.herodotus.oss.definition.domain.object.ObjectListingV2Domain;
import cn.herodotus.oss.dialect.core.client.AbstractOssClientObjectPool;
import cn.herodotus.oss.dialect.core.exception.OssServerException;
import cn.herodotus.oss.dialect.s3.converter.arguments.ArgumentsToDeleteObjectRequestConverter;
import cn.herodotus.oss.dialect.s3.converter.arguments.ArgumentsToListObjectsRequestConverter;
import cn.herodotus.oss.dialect.s3.converter.arguments.ArgumentsToListObjectsV2RequestConverter;
import cn.herodotus.oss.dialect.s3.converter.domain.ListObjectsV2ResultToDomainConverter;
import cn.herodotus.oss.dialect.s3.converter.domain.ObjectListingToDomainConverter;
import cn.herodotus.oss.dialect.s3.definition.service.BaseS3Service;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

/**
 * <p>Description: Amazon S3 兼容模式对象操作处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/9 16:47
 */
@Service
public class S3ObjectAdapter extends BaseS3Service implements OssObjectAdapter {

    private static final Logger log = LoggerFactory.getLogger(S3ObjectAdapter.class);

    public S3ObjectAdapter(AbstractOssClientObjectPool<AmazonS3> ossClientObjectPool) {
        super(ossClientObjectPool);
    }

    @Override
    public ObjectListingDomain listObjects(ListObjectsArguments arguments) {
        String function = "listObjects";

        Converter<ListObjectsArguments, ListObjectsRequest> toArgs = new ArgumentsToListObjectsRequestConverter();
        Converter<ObjectListing, ObjectListingDomain> toDomain = new ObjectListingToDomainConverter();

        AmazonS3 client = getClient();
        try {
            ObjectListing objectListing = client.listObjects(toArgs.convert(arguments));
            return toDomain.convert(objectListing);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public ObjectListingV2Domain listObjectsV2(ListObjectsV2Arguments arguments) {
        String function = "listObjectsV2";

        Converter<ListObjectsV2Arguments, ListObjectsV2Request> toArgs = new ArgumentsToListObjectsV2RequestConverter();
        Converter<ListObjectsV2Result, ObjectListingV2Domain> toDomain = new ListObjectsV2ResultToDomainConverter();

        AmazonS3 client = getClient();
        try {
            ListObjectsV2Result listObjectsV2Result = client.listObjectsV2(toArgs.convert(arguments));
            return toDomain.convert(listObjectsV2Result);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public void deleteObject(DeleteObjectArguments arguments) {
        String function = "deleteObject";

        AmazonS3 client = getClient();
        try {
            Converter<DeleteObjectArguments, DeleteObjectRequest> toArgs = new ArgumentsToDeleteObjectRequestConverter();
            client.deleteObject(toArgs.convert(arguments));
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }
}
