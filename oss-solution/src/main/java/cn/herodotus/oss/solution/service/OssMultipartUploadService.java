/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante OSS Licensed under the Apache License, Version 2.0 (the "License");
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

package cn.herodotus.oss.solution.service;

import cn.herodotus.oss.solution.business.CreateMultipartUploadBusiness;
import cn.herodotus.oss.solution.properties.OssProxyProperties;
import cn.herodotus.oss.solution.proxy.OssProxyAddressConverter;
import cn.herodotus.oss.specification.arguments.object.GeneratePresignedUrlArguments;
import cn.herodotus.oss.specification.core.repository.OssMultipartUploadRepository;
import cn.herodotus.oss.specification.core.repository.OssObjectRepository;
import cn.herodotus.oss.specification.domain.base.ObjectWriteDomain;
import cn.herodotus.oss.specification.domain.multipart.CompleteMultipartUploadDomain;
import cn.herodotus.oss.specification.domain.multipart.ListPartsDomain;
import cn.herodotus.oss.specification.domain.multipart.PartSummaryDomain;
import cn.herodotus.oss.specification.enums.HttpMethod;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 对象存储分片上传 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/19 10:13
 */
@Service
public class OssMultipartUploadService {

    private final OssObjectRepository ossObjectRepository;
    private final OssMultipartUploadRepository ossMultipartUploadRepository;
    private final Converter<String, String> converter;

    public OssMultipartUploadService(OssObjectRepository ossObjectRepository, OssMultipartUploadRepository ossMultipartUploadRepository, OssProxyProperties ossProxyProperties) {
        this.ossObjectRepository = ossObjectRepository;
        this.ossMultipartUploadRepository = ossMultipartUploadRepository;
        this.converter = new OssProxyAddressConverter(ossProxyProperties);
    }

    /**
     * 第一步：创建分片上传请求, 返回 UploadId
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @return 大文件分片上传 UploadId
     */
    private String createUploadId(String bucketName, String objectName) {
        return ossMultipartUploadRepository.initiateMultipartUpload(bucketName, objectName);
    }

    /**
     * 第二步：创建文件预上传地址
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param uploadId   第一步中创建的 UploadId
     * @param partNumber 分片号
     * @return 预上传地址
     */
    private String createPresignedObjectUrl(String bucketName, String objectName, String uploadId, int partNumber) {
        Map<String, String> extraQueryParams = new HashMap<>();
        extraQueryParams.put("partNumber", String.valueOf(partNumber));
        extraQueryParams.put("uploadId", uploadId);

        GeneratePresignedUrlArguments arguments = new GeneratePresignedUrlArguments();
        arguments.setBucketName(bucketName);
        arguments.setObjectName(objectName);
        arguments.setMethod(HttpMethod.PUT);
        arguments.setExtraQueryParams(extraQueryParams);
        arguments.setExpiration(Duration.ofHours(1));
        return ossObjectRepository.generatePresignedUrl(arguments);
    }

    /**
     * 第三步：获取指定 uploadId 下所有的分片文件
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param uploadId   第一步中创建的 UploadId
     * @return uploadId 对应的所有分片
     */
    private List<PartSummaryDomain> listParts(String bucketName, String objectName, String uploadId) {
        ListPartsDomain domain = ossMultipartUploadRepository.listParts(bucketName, objectName, uploadId);
        return domain.getParts();
    }

    /**
     * 创建大文件分片上传
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param totalParts 分片总数
     * @return {@link CreateMultipartUploadBusiness}
     */
    public CreateMultipartUploadBusiness createMultipartUpload(String bucketName, String objectName, int totalParts) {
        String uploadId = createUploadId(bucketName, objectName);
        CreateMultipartUploadBusiness entity = new CreateMultipartUploadBusiness(uploadId);

        // 从 1 开始才能保证 Minio 正确上传。
        for (int i = 1; i <= totalParts; i++) {
            String uploadUrl = createPresignedObjectUrl(bucketName, objectName, uploadId, i);
            entity.append(converter.convert(uploadUrl));
        }
        return entity;
    }

    /**
     * 合并已经上传完成的分片
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param uploadId   第一步中创建的 UploadId
     * @return {@link ObjectWriteDomain}
     */
    public CompleteMultipartUploadDomain completeMultipartUpload(String bucketName, String objectName, String uploadId) {
        List<PartSummaryDomain> parts = listParts(bucketName, objectName, uploadId);
        if (CollectionUtils.isNotEmpty(parts)) {
            return ossMultipartUploadRepository.completeMultipartUpload(bucketName, objectName, uploadId, parts);
        }

        return null;
    }
}
