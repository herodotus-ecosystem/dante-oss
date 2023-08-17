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

import cn.herodotus.oss.definition.arguments.load.*;
import cn.herodotus.oss.definition.core.repository.OssObjectLoadRepository;
import cn.herodotus.oss.definition.domain.base.ObjectWriteDomain;
import cn.herodotus.oss.definition.domain.load.GetObjectDomain;
import cn.herodotus.oss.definition.domain.load.ObjectMetadataDomain;
import cn.herodotus.oss.definition.domain.load.PutObjectDomain;
import cn.herodotus.oss.definition.domain.load.UploadObjectDomain;
import cn.herodotus.oss.dialect.minio.converter.arguments.*;
import cn.herodotus.oss.dialect.minio.converter.domain.GetObjectResponseToDomainConverter;
import cn.herodotus.oss.dialect.minio.converter.domain.ObjectWriteResponseToPutObjectDomainConverter;
import cn.herodotus.oss.dialect.minio.converter.domain.ObjectWriteResponseToUploadObjectDomainConverter;
import cn.herodotus.oss.dialect.minio.service.MinioObjectLoadService;
import cn.herodotus.oss.dialect.minio.service.MinioObjectService;
import io.minio.*;
import org.dromara.hutool.core.net.url.URLUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.net.URL;

/**
 * <p>Description: Minio Java OSS API 上传、下载操作实现 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/15 17:28
 */
@Service
public class MinioObjectLoadRepository implements OssObjectLoadRepository {

    private final MinioObjectService minioObjectService;
    private final MinioObjectLoadService minioObjectLoadService;

    public MinioObjectLoadRepository(MinioObjectService minioObjectService, MinioObjectLoadService minioObjectLoadService) {
        this.minioObjectService = minioObjectService;
        this.minioObjectLoadService = minioObjectLoadService;
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
    public URL generatePreSignedUrl(GeneratePreSignedUrlArguments arguments) {

        Converter<GeneratePreSignedUrlArguments, GetPresignedObjectUrlArgs> toRequest = new ArgumentsToGetPreSignedObjectUrlConverter();

        String url = minioObjectLoadService.getPreSignedObjectUrl(toRequest.convert(arguments));
        return URLUtil.url(url);
    }

    @Override
    public ObjectMetadataDomain download(DownloadObjectArguments arguments) {
        Converter<DownloadObjectArguments, DownloadObjectArgs> toRequest = new ArgumentsToDownloadObjectArgsConverter();
        minioObjectLoadService.downloadObject(toRequest.convert(arguments));
        return new ObjectMetadataDomain();
    }

    @Override
    public ObjectWriteDomain upload(UploadObjectArguments arguments) {

        Converter<UploadObjectArguments, UploadObjectArgs> toRequest = new ArgumentsToUploadObjectArgsConverter();
        Converter<ObjectWriteResponse, UploadObjectDomain> toDomain = new ObjectWriteResponseToUploadObjectDomainConverter();

        ObjectWriteResponse response = minioObjectLoadService.uploadObject(toRequest.convert(arguments));
        return toDomain.convert(response);
    }
}
