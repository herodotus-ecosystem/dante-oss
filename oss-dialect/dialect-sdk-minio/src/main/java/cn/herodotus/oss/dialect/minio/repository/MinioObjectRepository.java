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

package cn.herodotus.oss.dialect.minio.repository;

import cn.herodotus.oss.dialect.minio.converter.arguments.*;
import cn.herodotus.oss.dialect.minio.converter.domain.*;
import cn.herodotus.oss.dialect.minio.service.MinioObjectService;
import cn.herodotus.oss.dialect.minio.utils.ConverterUtils;
import cn.herodotus.oss.specification.arguments.object.*;
import cn.herodotus.oss.specification.core.repository.OssObjectRepository;
import cn.herodotus.oss.specification.domain.base.ObjectWriteDomain;
import cn.herodotus.oss.specification.domain.object.*;
import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Description: Minio Java OSS API 对象操作实现 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/9 16:50
 */
@Service
public class MinioObjectRepository implements OssObjectRepository {

    private static final Logger log = LoggerFactory.getLogger(MinioObjectRepository.class);

    private final MinioObjectService minioObjectService;

    public MinioObjectRepository(MinioObjectService minioObjectService) {
        this.minioObjectService = minioObjectService;
    }

    @Override
    public ListObjectsDomain listObjects(ListObjectsArguments arguments) {
        Converter<ListObjectsArguments, ListObjectsArgs> toArgs = new ArgumentsToListObjectsArgsConverter();
        Iterable<Result<Item>> iterable = minioObjectService.listObjects(toArgs.convert(arguments));
        Converter<Iterable<Result<Item>>, ListObjectsDomain> toDomain = new IterableResultItemToDomainConverter(arguments);
        return toDomain.convert(iterable);
    }

    @Override
    public ListObjectsV2Domain listObjectsV2(ListObjectsV2Arguments arguments) {
        Converter<ListObjectsV2Arguments, ListObjectsArgs> toArgs = new ArgumentsToListObjectsV2ArgsConverter();
        Iterable<Result<Item>> iterable = minioObjectService.listObjects(toArgs.convert(arguments));
        Converter<Iterable<Result<Item>>, ListObjectsV2Domain> toDomain = new IterableResultItemV2ToDomainConverter(arguments);
        return toDomain.convert(iterable);
    }

    @Override
    public void deleteObject(DeleteObjectArguments arguments) {
        Converter<DeleteObjectArguments, RemoveObjectArgs> toArgs = new ArgumentsToRemoveObjectArgsConverter();
        minioObjectService.removeObject(toArgs.convert(arguments));
    }

    @Override
    public List<DeleteObjectDomain> deleteObjects(DeleteObjectsArguments arguments) {
        Converter<DeleteObjectsArguments, RemoveObjectsArgs> toArgs = new ArgumentsToRemoveObjectsArgsConverter();
        Iterable<Result<DeleteError>> deletesErrors = minioObjectService.removeObjects(toArgs.convert(arguments));
        return ConverterUtils.toDomains(deletesErrors, new ResultDeleteErrorToDomainConverter());
    }

    @Override
    public ObjectMetadataDomain getObjectMetadata(GetObjectMetadataArguments arguments) {

        Converter<GetObjectMetadataArguments, StatObjectArgs> toRequest = new ArgumentsToStatObjectArgsConverter();
        Converter<StatObjectResponse, ObjectMetadataDomain> toDomain = new StatObjectResponseToDomainConverter();

        StatObjectResponse response = minioObjectService.statObject(toRequest.convert(arguments));
        return toDomain.convert(response);
    }

    @Override
    public GetObjectDomain getObject(GetObjectArguments arguments) {

        Converter<GetObjectArguments, GetObjectArgs> toRequest = new ArgumentsToGetObjectArgsConverter();
        Converter<GetObjectResponse, GetObjectDomain> toDomain = new GetObjectResponseToDomainConverter();

        GetObjectResponse response = minioObjectService.getObject(toRequest.convert(arguments));
        return toDomain.convert(response);
    }

    @Override
    public PutObjectDomain putObject(PutObjectArguments arguments) {

        Converter<PutObjectArguments, PutObjectArgs> toRequest = new ArgumentsToPutObjectArgsConverter();
        Converter<ObjectWriteResponse, PutObjectDomain> toDomain = new ObjectWriteResponseToPutObjectDomainConverter();

        ObjectWriteResponse response = minioObjectService.putObject(toRequest.convert(arguments));
        return toDomain.convert(response);
    }

    @Override
    public String generatePresignedUrl(GeneratePresignedUrlArguments arguments) {

        Converter<GeneratePresignedUrlArguments, GetPresignedObjectUrlArgs> toRequest = new ArgumentsToGetPreSignedObjectUrlConverter();
        return minioObjectService.getPreSignedObjectUrl(toRequest.convert(arguments));
    }

    @Override
    public ObjectMetadataDomain download(DownloadObjectArguments arguments) {
        Converter<DownloadObjectArguments, DownloadObjectArgs> toRequest = new ArgumentsToDownloadObjectArgsConverter();
        minioObjectService.downloadObject(toRequest.convert(arguments));
        return new ObjectMetadataDomain();
    }

    @Override
    public ObjectWriteDomain upload(UploadObjectArguments arguments) {

        Converter<UploadObjectArguments, UploadObjectArgs> toRequest = new ArgumentsToUploadObjectArgsConverter();
        Converter<ObjectWriteResponse, UploadObjectDomain> toDomain = new ObjectWriteResponseToUploadObjectDomainConverter();

        ObjectWriteResponse response = minioObjectService.uploadObject(toRequest.convert(arguments));
        return toDomain.convert(response);
    }
}
