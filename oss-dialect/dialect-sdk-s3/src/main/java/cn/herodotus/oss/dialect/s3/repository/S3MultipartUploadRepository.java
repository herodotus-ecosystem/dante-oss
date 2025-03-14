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

package cn.herodotus.oss.dialect.s3.repository;

import cn.herodotus.engine.assistant.definition.support.AbstractObjectPool;
import cn.herodotus.oss.dialect.core.exception.OssServerException;
import cn.herodotus.oss.dialect.s3.converter.arguments.*;
import cn.herodotus.oss.dialect.s3.converter.domain.*;
import cn.herodotus.oss.dialect.s3.definition.service.BaseS3Service;
import cn.herodotus.oss.specification.arguments.multipart.*;
import cn.herodotus.oss.specification.core.repository.OssMultipartUploadRepository;
import cn.herodotus.oss.specification.domain.multipart.*;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>Description: Amazon S3 Java OSS API 分片上传操作实现 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/13 21:12
 */
public class S3MultipartUploadRepository extends BaseS3Service implements OssMultipartUploadRepository {

    private static final Logger log = LoggerFactory.getLogger(S3MultipartUploadRepository.class);

    public S3MultipartUploadRepository(AbstractObjectPool<AmazonS3> ossClientObjectPool) {
        super(ossClientObjectPool);
    }

    @Override
    public InitiateMultipartUploadDomain initiateMultipartUpload(InitiateMultipartUploadArguments arguments) {

        String function = "initiateMultipartUpload";

        Converter<InitiateMultipartUploadArguments, InitiateMultipartUploadRequest> toRequest = new ArgumentsToInitiateMultipartUploadRequestConverter();
        Converter<InitiateMultipartUploadResult, InitiateMultipartUploadDomain> toDomain = new InitiateMultipartUploadResultToDomainConverter();

        AmazonS3 client = getClient();
        try {
            InitiateMultipartUploadResult result = client.initiateMultipartUpload(toRequest.convert(arguments));
            return toDomain.convert(result);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public UploadPartDomain uploadPart(UploadPartArguments arguments) {

        String function = "uploadPart";

        Converter<UploadPartArguments, UploadPartRequest> toRequest = new ArgumentsToUploadPartRequestConverter();
        Converter<UploadPartResult, UploadPartDomain> toDomain = new UploadPartResultToDomainConverter();

        AmazonS3 client = getClient();

        try {
            UploadPartResult result = client.uploadPart(toRequest.convert(arguments));
            return toDomain.convert(result);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public UploadPartCopyDomain uploadPartCopy(UploadPartCopyArguments arguments) {
        String function = "uploadPartCopy";

        Converter<UploadPartCopyArguments, CopyPartRequest> toRequest = new ArgumentsToCopyPartRequestConverter();
        Converter<CopyPartResult, UploadPartCopyDomain> toDomain = new CopyPartResultToDomainConverter();

        AmazonS3 client = getClient();
        try {
            CopyPartResult result = client.copyPart(toRequest.convert(arguments));
            return toDomain.convert(result);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public CompleteMultipartUploadDomain completeMultipartUpload(CompleteMultipartUploadArguments arguments) {
        String function = "completeMultipartUpload";

        Converter<CompleteMultipartUploadArguments, CompleteMultipartUploadRequest> toRequest = new ArgumentsToCompleteMultipartUploadRequestConverter();
        Converter<CompleteMultipartUploadResult, CompleteMultipartUploadDomain> toDomain = new CompleteMultipartUploadResultToDomainConverter();

        AmazonS3 client = getClient();

        try {
            CompleteMultipartUploadResult result = client.completeMultipartUpload(toRequest.convert(arguments));
            return toDomain.convert(result);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public AbortMultipartUploadDomain abortMultipartUpload(AbortMultipartUploadArguments arguments) {
        String function = "abortMultipartUpload";

        Converter<AbortMultipartUploadArguments, AbortMultipartUploadRequest> toRequest = new ArgumentsToAbortMultipartUploadRequestConverter();

        AmazonS3 client = getClient();

        try {
            client.abortMultipartUpload(toRequest.convert(arguments));
            AbortMultipartUploadDomain domain = new AbortMultipartUploadDomain();
            domain.setUploadId(arguments.getUploadId());
            domain.setBucketName(arguments.getBucketName());
            domain.setObjectName(arguments.getObjectName());
            return domain;
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public ListPartsDomain listParts(ListPartsArguments arguments) {

        String function = "listParts";

        Converter<ListPartsArguments, ListPartsRequest> toRequest = new ArgumentsToListPartsRequestConverter();
        Converter<PartListing, ListPartsDomain> toDomain = new PartListingToDomainConverter();

        AmazonS3 client = getClient();

        try {
            PartListing partListing = client.listParts(toRequest.convert(arguments));
            return toDomain.convert(partListing);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public ListMultipartUploadsDomain listMultipartUploads(ListMultipartUploadsArguments arguments) {
        String function = "listMultipartUploads";

        Converter<ListMultipartUploadsArguments, ListMultipartUploadsRequest> toRequest = new ArgumentsToListMultipartUploadsRequest();
        Converter<MultipartUploadListing, ListMultipartUploadsDomain> toDomain = new MultipartUploadListingToDomainConverter();

        AmazonS3 client = getClient();

        try {
            MultipartUploadListing multipartUploadListing = client.listMultipartUploads(toRequest.convert(arguments));
            return toDomain.convert(multipartUploadListing);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }
}
