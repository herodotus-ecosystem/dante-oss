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

package cn.herodotus.oss.rest.reactive.minio.service;

import cn.herodotus.oss.core.minio.bo.BucketSettingBusiness;
import cn.herodotus.oss.core.minio.converter.retention.ObjectLockConfigurationToDomainConverter;
import cn.herodotus.oss.core.minio.converter.retention.VersioningConfigurationToDomainConverter;
import cn.herodotus.oss.core.minio.converter.sse.SseConfigurationToEnumConverter;
import cn.herodotus.oss.core.minio.domain.ObjectLockConfigurationDomain;
import cn.herodotus.oss.core.minio.domain.VersioningConfigurationDomain;
import cn.herodotus.oss.core.minio.enums.PolicyEnums;
import cn.herodotus.oss.core.minio.enums.SseConfigurationEnums;
import cn.herodotus.oss.dialect.reactive.minio.service.*;
import io.minio.messages.ObjectLockConfiguration;
import io.minio.messages.SseConfiguration;
import io.minio.messages.Tags;
import io.minio.messages.VersioningConfiguration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>Description: Bucket 管理页面数据获取 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/5 18:17
 */
@Service
public class MinioBucketSettingService {

    private final Converter<SseConfiguration, SseConfigurationEnums> toSseConfigurationEnums;
    private final Converter<ObjectLockConfiguration, ObjectLockConfigurationDomain> toObjectLockDomain;
    private final Converter<VersioningConfiguration, VersioningConfigurationDomain> toVersioningDomain;

    private final MinioBucketEncryptionService minioBucketEncryptionService;
    private final MinioBucketPolicyService minioBucketPolicyService;
    private final MinioBucketTagsService minioBucketTagsService;
    private final MinioBucketVersioningService minioBucketVersioningService;
    private final MinioBucketQuotaService minioBucketQuotaService;
    private final MinioObjectLockConfigurationService minioObjectLockConfigurationService;

    public MinioBucketSettingService(MinioBucketEncryptionService minioBucketEncryptionService, MinioBucketPolicyService minioBucketPolicyService, MinioBucketTagsService minioBucketTagsService, MinioBucketVersioningService minioBucketVersioningService, MinioBucketQuotaService minioBucketQuotaService, MinioObjectLockConfigurationService minioObjectLockConfigurationService) {
        this.minioBucketEncryptionService = minioBucketEncryptionService;
        this.minioBucketPolicyService = minioBucketPolicyService;
        this.minioBucketTagsService = minioBucketTagsService;
        this.minioBucketVersioningService = minioBucketVersioningService;
        this.minioBucketQuotaService = minioBucketQuotaService;
        this.minioObjectLockConfigurationService = minioObjectLockConfigurationService;
        this.toSseConfigurationEnums = new SseConfigurationToEnumConverter();
        this.toObjectLockDomain = new ObjectLockConfigurationToDomainConverter();
        this.toVersioningDomain = new VersioningConfigurationToDomainConverter();
    }

    public Mono<BucketSettingBusiness> get(String bucketName) {
        return get(bucketName, null);
    }

    public Mono<BucketSettingBusiness> get(String bucketName, String region) {

        Mono<SseConfiguration> sseConfiguration = minioBucketEncryptionService.getBucketEncryption(bucketName, region);
        Mono<Tags> tags = minioBucketTagsService.getBucketTags(bucketName, region);
        Mono<PolicyEnums> policy = minioBucketPolicyService.getBucketPolicy(bucketName, region);
        Mono<ObjectLockConfiguration> objectLockConfiguration = minioObjectLockConfigurationService.getObjectLockConfiguration(bucketName, region);
        Mono<VersioningConfiguration> versioningConfiguration = minioBucketVersioningService.getBucketVersioning(bucketName, region);
        Mono<Long> quota = minioBucketQuotaService.getBucketQuota(bucketName);

        return Mono.zip(sseConfiguration, tags, policy, objectLockConfiguration, versioningConfiguration, quota)
                .map(tuple -> {
                    BucketSettingBusiness entity = new BucketSettingBusiness();
                    entity.setSseConfiguration(toSseConfigurationEnums.convert(tuple.getT1()));
                    entity.setTags(tuple.getT2().get());
                    entity.setPolicy(tuple.getT3());
                    entity.setObjectLock(toObjectLockDomain.convert(tuple.getT4()));
                    entity.setVersioning(toVersioningDomain.convert(tuple.getT5()));
                    entity.setQuota(tuple.getT6());
                    return entity;
                });
    }
}
