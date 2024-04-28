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

import cn.herodotus.oss.core.minio.definition.pool.MinioAsyncClientObjectPool;
import cn.herodotus.oss.dialect.reactive.minio.definition.service.BaseMinioAsyncService;
import io.minio.DeleteObjectLockConfigurationArgs;
import io.minio.GetObjectLockConfigurationArgs;
import io.minio.SetObjectLockConfigurationArgs;
import io.minio.messages.ObjectLockConfiguration;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>Description: Minio 对象锁定配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 16:04
 */
@Service
public class MinioObjectLockConfigurationService extends BaseMinioAsyncService {

    public MinioObjectLockConfigurationService(MinioAsyncClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 获取对象锁定配置
     *
     * @param bucketName 存储桶名称
     * @return {@link ObjectLockConfiguration}
     */
    public Mono<ObjectLockConfiguration> getObjectLockConfiguration(String bucketName) {
        return getObjectLockConfiguration(bucketName, null);
    }

    /**
     * 获取对象锁定配置
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @return {@link ObjectLockConfiguration}
     */
    public Mono<ObjectLockConfiguration> getObjectLockConfiguration(String bucketName, String region) {
        return getObjectLockConfiguration(GetObjectLockConfigurationArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 获取对象锁定配置
     *
     * @param getObjectLockConfigurationArgs {@link GetObjectLockConfigurationArgs}
     * @return {@link ObjectLockConfiguration}
     */
    public Mono<ObjectLockConfiguration> getObjectLockConfiguration(GetObjectLockConfigurationArgs getObjectLockConfigurationArgs) {
        return fromFuture("getObjectLockConfiguration", (client) -> client.getObjectLockConfiguration(getObjectLockConfigurationArgs));
    }

    /**
     * 设置对象锁定
     *
     * @param bucketName 存储桶名称
     * @param config     对象锁定配置 {@link ObjectLockConfiguration}
     */
    public Mono<Void> setObjectLockConfiguration(String bucketName, ObjectLockConfiguration config) {
        return setObjectLockConfiguration(bucketName, null, config);
    }

    /**
     * 设置对象锁定
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param config     对象锁定配置 {@link ObjectLockConfiguration}
     */
    public Mono<Void> setObjectLockConfiguration(String bucketName, String region, ObjectLockConfiguration config) {
        return setObjectLockConfiguration(SetObjectLockConfigurationArgs.builder().bucket(bucketName).region(region).config(config).build());
    }

    /**
     * 设置对象锁定
     *
     * @param setObjectLockConfigurationArgs {@link SetObjectLockConfigurationArgs}
     */
    public Mono<Void> setObjectLockConfiguration(SetObjectLockConfigurationArgs setObjectLockConfigurationArgs) {
        return fromFuture("setObjectLockConfiguration", (client) -> client.setObjectLockConfiguration(setObjectLockConfigurationArgs));
    }

    /**
     * 删除对象锁定配置
     *
     * @param bucketName 存储桶名称
     */
    public Mono<Void> deleteObjectLockConfiguration(String bucketName) {
        return deleteObjectLockConfiguration(bucketName, null);
    }

    /**
     * 删除对象锁定配置
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     */
    public Mono<Void> deleteObjectLockConfiguration(String bucketName, String region) {
        return deleteObjectLockConfiguration(DeleteObjectLockConfigurationArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 删除对象锁定
     *
     * @param deleteObjectLockConfigurationArgs {@link DeleteObjectLockConfigurationArgs}
     */
    public Mono<Void> deleteObjectLockConfiguration(DeleteObjectLockConfigurationArgs deleteObjectLockConfigurationArgs) {
        return fromFuture("deleteObjectLockConfiguration", (client) -> client.deleteObjectLockConfiguration(deleteObjectLockConfigurationArgs));

    }
}
