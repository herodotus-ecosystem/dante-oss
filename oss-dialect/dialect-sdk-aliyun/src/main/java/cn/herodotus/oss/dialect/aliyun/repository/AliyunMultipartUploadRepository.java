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

package cn.herodotus.oss.dialect.aliyun.repository;

import cn.herodotus.stirrup.core.definition.support.AbstractObjectPool;
import cn.herodotus.oss.dialect.aliyun.converter.arguments.*;
import cn.herodotus.oss.dialect.aliyun.converter.domain.*;
import cn.herodotus.oss.dialect.aliyun.definition.service.BaseAliyunService;
import cn.herodotus.oss.dialect.core.exception.OssExecutionException;
import cn.herodotus.oss.dialect.core.exception.OssServerException;
import cn.herodotus.oss.core.arguments.multipart.*;
import cn.herodotus.oss.core.definition.repository.OssMultipartUploadRepository;
import cn.herodotus.oss.core.domain.multipart.*;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>Description: Aliyun 兼容模式分片上传操作处理适配器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/13 21:13
 */
public class AliyunMultipartUploadRepository extends BaseAliyunService implements OssMultipartUploadRepository {

    private static final Logger log = LoggerFactory.getLogger(AliyunMultipartUploadRepository.class);

    public AliyunMultipartUploadRepository(AbstractObjectPool<OSS> ossClientObjectPool) {
        super(ossClientObjectPool);
    }

    @Override
    public InitiateMultipartUploadDomain initiateMultipartUpload(InitiateMultipartUploadArguments arguments) {
        String function = "initiateMultipartUpload";

        Converter<InitiateMultipartUploadArguments, InitiateMultipartUploadRequest> toRequest = new ArgumentsToInitiateMultipartUploadRequestConverter();
        Converter<InitiateMultipartUploadResult, InitiateMultipartUploadDomain> toDomain = new InitiateMultipartUploadResultToDomainConverter();

        OSS client = getClient();

        try {
            InitiateMultipartUploadResult result = client.initiateMultipartUpload(toRequest.convert(arguments));
            return toDomain.convert(result);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssExecutionException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public UploadPartDomain uploadPart(UploadPartArguments arguments) {
        String function = "uploadPart";

        Converter<UploadPartArguments, UploadPartRequest> toRequest = new ArgumentsToUploadPartRequestConverter();
        Converter<UploadPartResult, UploadPartDomain> toDomain = new UploadPartResultToDomainConverter();

        OSS client = getClient();

        try {
            UploadPartResult result = client.uploadPart(toRequest.convert(arguments));
            return toDomain.convert(result);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssExecutionException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public UploadPartCopyDomain uploadPartCopy(UploadPartCopyArguments arguments) {
        String function = "uploadPartCopy";

        Converter<UploadPartCopyArguments, UploadPartCopyRequest> toRequest = new ArgumentsToUploadPartCopyRequestConverter();
        Converter<UploadPartCopyResult, UploadPartCopyDomain> toDomain = new UploadPartCopyResultToDomainConverter();

        OSS client = getClient();

        try {
            UploadPartCopyResult result = client.uploadPartCopy(toRequest.convert(arguments));
            return toDomain.convert(result);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssExecutionException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public CompleteMultipartUploadDomain completeMultipartUpload(CompleteMultipartUploadArguments arguments) {
        String function = "completeMultipartUpload";

        Converter<CompleteMultipartUploadArguments, CompleteMultipartUploadRequest> toRequest = new ArgumentsToCompleteMultipartUploadRequestConverter();
        Converter<CompleteMultipartUploadResult, CompleteMultipartUploadDomain> toDomain = new CompleteMultipartUploadResultToDomainConverter();

        OSS client = getClient();

        try {
            CompleteMultipartUploadResult result = client.completeMultipartUpload(toRequest.convert(arguments));
            return toDomain.convert(result);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssExecutionException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public AbortMultipartUploadDomain abortMultipartUpload(AbortMultipartUploadArguments arguments) {
        String function = "abortMultipartUpload";

        Converter<AbortMultipartUploadArguments, AbortMultipartUploadRequest> toRequest = new ArgumentsToAbortMultipartUploadRequestConverter();

        OSS client = getClient();

        try {
            client.abortMultipartUpload(toRequest.convert(arguments));
            AbortMultipartUploadDomain domain = new AbortMultipartUploadDomain();
            domain.setUploadId(arguments.getUploadId());
            domain.setBucketName(arguments.getBucketName());
            domain.setObjectName(arguments.getObjectName());
            return domain;
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssExecutionException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public ListPartsDomain listParts(ListPartsArguments arguments) {
        String function = "listParts";

        Converter<ListPartsArguments, ListPartsRequest> toRequest = new ArgumentsToListPartsRequestConverter();
        Converter<PartListing, ListPartsDomain> toDomain = new PartListingToDomainConverter();

        OSS client = getClient();

        try {
            PartListing listing = client.listParts(toRequest.convert(arguments));
            return toDomain.convert(listing);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssExecutionException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public ListMultipartUploadsDomain listMultipartUploads(ListMultipartUploadsArguments arguments) {
        String function = "listMultipartUploads";

        Converter<ListMultipartUploadsArguments, ListMultipartUploadsRequest> toRequest = new ArgumentsToListMultipartUploadsRequest();
        Converter<MultipartUploadListing, ListMultipartUploadsDomain> toDomain = new MultipartUploadListingToDomainConverter();

        OSS client = getClient();

        try {
            MultipartUploadListing listing = client.listMultipartUploads(toRequest.convert(arguments));
            return toDomain.convert(listing);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssExecutionException(e.getMessage());
        } finally {
            close(client);
        }
    }
}
