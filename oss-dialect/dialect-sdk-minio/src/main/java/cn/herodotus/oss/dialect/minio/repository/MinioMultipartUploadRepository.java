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

package cn.herodotus.oss.dialect.minio.repository;

import cn.herodotus.oss.definition.arguments.multipart.*;
import cn.herodotus.oss.definition.attribute.PartAttribute;
import cn.herodotus.oss.definition.core.repository.OssMultipartUploadRepository;
import cn.herodotus.oss.definition.domain.multipart.*;
import cn.herodotus.oss.dialect.minio.converter.attribute.AttributeToPartConverter;
import cn.herodotus.oss.dialect.minio.converter.domain.*;
import cn.herodotus.oss.dialect.minio.service.MinioMultipartUploadService;
import io.minio.*;
import io.minio.messages.InitiateMultipartUploadResult;
import io.minio.messages.ListMultipartUploadsResult;
import io.minio.messages.ListPartsResult;
import io.minio.messages.Part;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Description: Minio Java OSS API 分片上传操作实现 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/13 21:11
 */
@Service
public class MinioMultipartUploadRepository implements OssMultipartUploadRepository {

    private final MinioMultipartUploadService minioMultipartUploadService;

    public MinioMultipartUploadRepository(MinioMultipartUploadService minioMultipartUploadService) {
        this.minioMultipartUploadService = minioMultipartUploadService;
    }

    @Override
    public InitiateMultipartUploadDomain initiateMultipartUpload(InitiateMultipartUploadArguments arguments) {

        Converter<InitiateMultipartUploadResult, InitiateMultipartUploadDomain> toDomain = new InitiateMultipartUploadResultToDomainConverter();

        CreateMultipartUploadResponse response = minioMultipartUploadService.createMultipartUpload(
                arguments.getBucketName(),
                arguments.getRegion(),
                arguments.getObjectName(),
                arguments.getExtraHeaders(),
                arguments.getExtraQueryParams());

        return toDomain.convert(response.result());
    }

    @Override
    public UploadPartDomain uploadPart(UploadPartArguments arguments) {

        Converter<UploadPartResponse, UploadPartDomain> toDomain = new UploadPartResponseToDomainConverter();

        UploadPartResponse response = minioMultipartUploadService.uploadPart(
                arguments.getBucketName(),
                arguments.getRegion(),
                arguments.getObjectName(),
                arguments.getInputStream(),
                arguments.getPartSize(),
                arguments.getUploadId(),
                arguments.getPartNumber(),
                arguments.getExtraHeaders(),
                arguments.getExtraQueryParams());

        return toDomain.convert(response);
    }

    @Override
    public UploadPartCopyDomain uploadPartCopy(UploadPartCopyArguments arguments) {

        Converter<UploadPartCopyResponse, UploadPartCopyDomain> toDomain = new UploadPartCopyResponseToDomainConverter();

        UploadPartCopyResponse response = minioMultipartUploadService.uploadPartCopy(
                arguments.getBucketName(),
                arguments.getRegion(),
                arguments.getObjectName(),
                arguments.getUploadId(),
                arguments.getPartNumber(),
                arguments.getExtraHeaders(),
                arguments.getExtraQueryParams());

        return toDomain.convert(response);
    }

    @Override
    public CompleteMultipartUploadDomain completeMultipartUpload(CompleteMultipartUploadArguments arguments) {

        Converter<List<PartAttribute>, Part[]> toPart = new AttributeToPartConverter();
        Converter<ObjectWriteResponse, CompleteMultipartUploadDomain> toDomain = new ObjectWriteResponseToDomainConverter();

        ObjectWriteResponse response = minioMultipartUploadService.completeMultipartUpload(
                arguments.getBucketName(),
                arguments.getRegion(),
                arguments.getObjectName(),
                arguments.getUploadId(),
                toPart.convert(arguments.getParts()),
                arguments.getExtraHeaders(),
                arguments.getExtraQueryParams());

        return toDomain.convert(response);
    }

    @Override
    public AbortMultipartUploadDomain abortMultipartUpload(AbortMultipartUploadArguments arguments) {

        Converter<AbortMultipartUploadResponse, AbortMultipartUploadDomain> toDomain = new AbortMultipartUploadResponseToDomainConverter();

        AbortMultipartUploadResponse response = minioMultipartUploadService.abortMultipartUpload(
                arguments.getBucketName(),
                arguments.getRegion(),
                arguments.getObjectName(),
                arguments.getUploadId(),
                arguments.getExtraHeaders(),
                arguments.getExtraQueryParams());

        return toDomain.convert(response);
    }

    @Override
    public ListPartsDomain listParts(ListPartsArguments arguments) {

        Converter<ListPartsResult, ListPartsDomain> toDomain = new ListPartsResultToDomainConverter(arguments);

        ListPartsResponse response = minioMultipartUploadService.listParts(
                arguments.getBucketName(),
                arguments.getRegion(),
                arguments.getObjectName(),
                arguments.getMaxParts(),
                arguments.getPartNumberMarker(),
                arguments.getUploadId(),
                arguments.getExtraHeaders(),
                arguments.getExtraQueryParams());

        return toDomain.convert(response.result());
    }

    @Override
    public ListMultipartUploadsDomain listMultipartUploads(ListMultipartUploadsArguments arguments) {

        Converter<ListMultipartUploadsResult, ListMultipartUploadsDomain> toDomain = new ListMultipartUploadsResultToDomainConverter(arguments);

        ListMultipartUploadsResponse response = minioMultipartUploadService.listMultipartUploads(
                arguments.getBucketName(),
                arguments.getRegion(),
                arguments.getDelimiter(),
                arguments.getEncodingType(),
                arguments.getKeyMarker(),
                arguments.getMaxUploads(),
                arguments.getPrefix(),
                arguments.getUploadIdMarker(),
                arguments.getExtraHeaders(),
                arguments.getExtraQueryParams());

        return toDomain.convert(response.result());
    }
}
