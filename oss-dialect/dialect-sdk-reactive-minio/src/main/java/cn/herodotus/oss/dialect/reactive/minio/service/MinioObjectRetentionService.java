/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus Cloud.
 *
 * Herodotus Cloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus Cloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.vip>.
 */

package cn.herodotus.oss.dialect.reactive.minio.service;

import cn.herodotus.oss.core.minio.converter.retention.RetentionToDomainConverter;
import cn.herodotus.oss.core.minio.definition.pool.MinioAsyncClientObjectPool;
import cn.herodotus.oss.core.minio.domain.RetentionDomain;
import cn.herodotus.oss.dialect.reactive.minio.definition.service.BaseMinioAsyncService;
import io.minio.GetObjectRetentionArgs;
import io.minio.SetObjectRetentionArgs;
import io.minio.messages.Retention;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>Description: Minio 对象保留配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 21:08
 */
@Service
public class MinioObjectRetentionService extends BaseMinioAsyncService {

    private final Converter<Retention, RetentionDomain> toDo;

    public MinioObjectRetentionService(MinioAsyncClientObjectPool objectPool) {
        super(objectPool);
        this.toDo = new RetentionToDomainConverter();
    }

    /**
     * 获取对象的保留配置
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @return 自定义保留域对象
     */
    public Mono<RetentionDomain> getObjectRetention(String bucketName, String objectName) {
        return getObjectRetention(bucketName, null, objectName);
    }

    /**
     * 获取对象的保留配置
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @return 自定义保留域对象
     */
    public Mono<RetentionDomain> getObjectRetention(String bucketName, String region, String objectName) {
        return getObjectRetention(bucketName, region, objectName, null);
    }

    /**
     * 获取对象的保留配置
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param versionId  版本ID
     * @return 自定义保留域对象
     */
    public Mono<RetentionDomain> getObjectRetention(String bucketName, String region, String objectName, String versionId) {
        return getObjectRetention(GetObjectRetentionArgs.builder().bucket(bucketName).region(region).object(objectName).versionId(versionId).build());
    }

    /**
     * 获取对象的保留配置
     *
     * @param getObjectRetentionArgs {@link GetObjectRetentionArgs}
     * @return {@link RetentionDomain}
     */
    public Mono<RetentionDomain> getObjectRetention(GetObjectRetentionArgs getObjectRetentionArgs) {
        return fromFuture("getObjectRetention", (client) -> client.getObjectRetention(getObjectRetentionArgs))
                .flatMap(retention -> Mono.justOrEmpty(toDo.convert(retention)));
    }

    /**
     * 添加对象的保留配置，存储桶需要设置为对象锁定模式，并且没有开启版本控制，否则会报错收蠕虫保护。
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param config     保留配置 {@link Retention}
     */
    public Mono<Void> setObjectRetention(String bucketName, String objectName, Retention config) {
        return setObjectRetention(bucketName, objectName, config, false);
    }

    /**
     * 添加对象的保留配置，存储桶需要设置为对象锁定模式，并且没有开启版本控制，否则会报错收蠕虫保护。
     *
     * @param bucketName           存储桶名称
     * @param objectName           对象名称
     * @param config               保留配置 {@link Retention}
     * @param bypassGovernanceMode 使用 Governance 模式
     */
    public Mono<Void> setObjectRetention(String bucketName, String objectName, Retention config, boolean bypassGovernanceMode) {
        return setObjectRetention(bucketName, null, objectName, config, bypassGovernanceMode);
    }

    /**
     * 添加对象的保留配置，存储桶需要设置为对象锁定模式，并且没有开启版本控制，否则会报错收蠕虫保护。
     *
     * @param bucketName           存储桶名称
     * @param region               区域
     * @param objectName           对象名称
     * @param config               保留配置 {@link Retention}
     * @param bypassGovernanceMode 使用 Governance 模式
     */
    public Mono<Void> setObjectRetention(String bucketName, String region, String objectName, Retention config, boolean bypassGovernanceMode) {
        return setObjectRetention(bucketName, region, objectName, config, bypassGovernanceMode, null);
    }

    /**
     * 添加对象的保留配置，存储桶需要设置为对象锁定模式，并且没有开启版本控制，否则会报错收蠕虫保护。
     *
     * @param bucketName           存储桶名称
     * @param region               区域
     * @param objectName           对象名称
     * @param config               保留配置 {@link Retention}
     * @param bypassGovernanceMode 使用 Governance 模式
     * @param versionId            版本ID
     */
    public Mono<Void> setObjectRetention(String bucketName, String region, String objectName, Retention config, boolean bypassGovernanceMode, String versionId) {
        return setObjectRetention(SetObjectRetentionArgs.builder()
                .bucket(bucketName)
                .region(region)
                .object(objectName)
                .config(config)
                .bypassGovernanceMode(bypassGovernanceMode)
                .versionId(versionId)
                .build());
    }

    /**
     * 添加对象的保留配置，存储桶需要设置为对象锁定模式，并且没有开启版本控制，否则会报错收蠕虫保护。
     *
     * @param setObjectRetentionArgs {@link SetObjectRetentionArgs}
     */
    public Mono<Void> setObjectRetention(SetObjectRetentionArgs setObjectRetentionArgs) {
        return fromFuture("setObjectRetention", (client) -> client.setObjectRetention(setObjectRetentionArgs));
    }
}
