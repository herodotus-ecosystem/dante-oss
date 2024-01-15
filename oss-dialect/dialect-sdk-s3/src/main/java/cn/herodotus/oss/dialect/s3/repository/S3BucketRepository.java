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

package cn.herodotus.oss.dialect.s3.repository;

import cn.herodotus.engine.assistant.definition.support.AbstractObjectPool;
import cn.herodotus.oss.dialect.core.exception.OssServerException;
import cn.herodotus.oss.dialect.core.utils.ConverterUtils;
import cn.herodotus.oss.dialect.s3.converter.arguments.ArgumentsToCreateBucketRequestConverter;
import cn.herodotus.oss.dialect.s3.converter.arguments.ArgumentsToDeleteBucketRequestConverter;
import cn.herodotus.oss.dialect.s3.converter.domain.BucketToDomainConverter;
import cn.herodotus.oss.dialect.s3.definition.service.BaseS3Service;
import cn.herodotus.oss.specification.arguments.bucket.CreateBucketArguments;
import cn.herodotus.oss.specification.arguments.bucket.DeleteBucketArguments;
import cn.herodotus.oss.specification.core.repository.OssBucketRepository;
import cn.herodotus.oss.specification.domain.bucket.BucketDomain;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.DeleteBucketRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Description: Amazon S3 Java OSS API 存储桶操作实现 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/24 19:10
 */
@Service
public class S3BucketRepository extends BaseS3Service implements OssBucketRepository {

    private static final Logger log = LoggerFactory.getLogger(S3BucketRepository.class);

    public S3BucketRepository(AbstractObjectPool<AmazonS3> ossClientObjectPool) {
        super(ossClientObjectPool);
    }

    @Override
    public boolean doesBucketExist(String bucketName) {
        String function = "doesBucketExistV2";

        AmazonS3 client = getClient();
        try {
            return client.doesBucketExistV2(bucketName);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public List<BucketDomain> listBuckets() {
        String function = "listBuckets";

        AmazonS3 client = getClient();
        try {
            return ConverterUtils.toDomains(client.listBuckets(), new BucketToDomainConverter());
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public BucketDomain createBucket(String bucketName) {
        String function = "createBucket";

        AmazonS3 client = getClient();
        try {
            return ConverterUtils.toDomain(client.createBucket(bucketName), new BucketToDomainConverter());
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public BucketDomain createBucket(CreateBucketArguments arguments) {
        String function = "createBucket";

        AmazonS3 client = getClient();
        try {
            Converter<CreateBucketArguments, CreateBucketRequest> toRequest = new ArgumentsToCreateBucketRequestConverter();
            return ConverterUtils.toDomain(client.createBucket(toRequest.convert(arguments)), new BucketToDomainConverter());
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public void deleteBucket(String bucketName) {
        String function = "deleteBucket";

        AmazonS3 client = getClient();
        try {
            client.deleteBucket(bucketName);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public void deleteBucket(DeleteBucketArguments arguments) {
        String function = "deleteBucket";

        AmazonS3 client = getClient();

        try {
            Converter<DeleteBucketArguments, DeleteBucketRequest> toRequest = new ArgumentsToDeleteBucketRequestConverter();
            client.deleteBucket(toRequest.convert(arguments));
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }
}
