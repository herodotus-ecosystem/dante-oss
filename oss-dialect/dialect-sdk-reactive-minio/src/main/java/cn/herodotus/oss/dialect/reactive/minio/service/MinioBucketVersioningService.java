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
import io.minio.GetBucketVersioningArgs;
import io.minio.SetBucketVersioningArgs;
import io.minio.messages.VersioningConfiguration;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>Description: Bucket 版本控制 </p>
 * <p>
 * 若开启了多版本控制，上传对象时，OBS自动为每个对象创建唯一的版本号。上传同名的对象将以不同的版本号同时保存在OBS中。
 * <p>
 * 若未开启多版本控制，向同一个文件夹中上传同名的对象时，新上传的对象将覆盖原有的对象。
 * <p>
 * 某些功能（例如版本控制、对象锁定和存储桶复制）需要使用擦除编码分布式部署 MinIO。开启了版本控制后，允许在同一密钥下保留同一对象的多个版本。
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 16:01
 */
@Service
public class MinioBucketVersioningService extends BaseMinioAsyncService {

    public MinioBucketVersioningService(MinioAsyncClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 开启 Bucket 版本控制
     *
     * @param bucketName bucketName
     */
    public Mono<Void> enabledBucketVersioning(String bucketName) {
        return setBucketVersioning(bucketName, VersioningConfiguration.Status.ENABLED);
    }

    /**
     * 开启 Bucket 版本控制
     *
     * @param bucketName bucketName
     * @param region     region
     */
    public Mono<Void> enabledBucketVersioning(String bucketName, String region) {
        return setBucketVersioning(bucketName, region, VersioningConfiguration.Status.ENABLED);
    }

    /**
     * 暂停 Bucket 版本控制
     *
     * @param bucketName bucketName
     */
    public Mono<Void> suspendedBucketVersioning(String bucketName) {
        return setBucketVersioning(bucketName, VersioningConfiguration.Status.SUSPENDED);
    }

    /**
     * 暂停 Bucket 版本控制
     *
     * @param bucketName bucketName
     * @param region     region
     */
    public Mono<Void> suspendedBucketVersioning(String bucketName, String region) {
        return setBucketVersioning(bucketName, region, VersioningConfiguration.Status.SUSPENDED);
    }

    /**
     * 关闭 Bucket 版本控制
     *
     * @param bucketName bucketName
     */
    public Mono<Void> offBucketVersioning(String bucketName) {
        return setBucketVersioning(bucketName, VersioningConfiguration.Status.OFF);
    }

    /**
     * 关闭 Bucket 版本控制
     *
     * @param bucketName bucketName
     * @param region     region
     */
    public Mono<Void> offBucketVersioning(String bucketName, String region) {
        return setBucketVersioning(bucketName, region, VersioningConfiguration.Status.OFF);
    }


    /**
     * 设置 Bucket 版本控制
     *
     * @param bucketName bucketName
     * @param status     {@link  VersioningConfiguration.Status}
     */
    public Mono<Void> setBucketVersioning(String bucketName, VersioningConfiguration.Status status) {
        return setBucketVersioning(bucketName, status, null);
    }

    /**
     * 设置 Bucket 版本控制
     *
     * @param bucketName bucketName
     * @param status     {@link  VersioningConfiguration.Status}
     * @param mfaDelete  mfaDelete
     */
    public Mono<Void> setBucketVersioning(String bucketName, VersioningConfiguration.Status status, Boolean mfaDelete) {
        return setBucketVersioning(bucketName, new VersioningConfiguration(status, mfaDelete));
    }

    /**
     * 设置 Bucket 版本控制
     *
     * @param bucketName              bucketName
     * @param versioningConfiguration {@link VersioningConfiguration}
     */
    public Mono<Void> setBucketVersioning(String bucketName, VersioningConfiguration versioningConfiguration) {
        return setBucketVersioning(SetBucketVersioningArgs.builder().bucket(bucketName).config(versioningConfiguration).build());
    }

    /**
     * 设置 Bucket 版本控制
     *
     * @param bucketName bucketName
     * @param region     region
     * @param status     {@link  VersioningConfiguration.Status}
     */
    public Mono<Void> setBucketVersioning(String bucketName, String region, VersioningConfiguration.Status status) {
        return setBucketVersioning(bucketName, region, status, null);
    }

    /**
     * 设置 Bucket 版本控制
     *
     * @param bucketName bucketName
     * @param region     region
     * @param status     {@link  VersioningConfiguration.Status}
     * @param mfaDelete  mfaDelete
     */
    public Mono<Void> setBucketVersioning(String bucketName, String region, VersioningConfiguration.Status status, Boolean mfaDelete) {
        return setBucketVersioning(bucketName, region, new VersioningConfiguration(status, mfaDelete));
    }

    /**
     * 设置 Bucket 版本控制
     *
     * @param bucketName              bucketName
     * @param region                  region
     * @param versioningConfiguration {@link VersioningConfiguration}
     */
    public Mono<Void> setBucketVersioning(String bucketName, String region, VersioningConfiguration versioningConfiguration) {
        return setBucketVersioning(SetBucketVersioningArgs.builder().bucket(bucketName).region(region).config(versioningConfiguration).build());
    }

    /**
     * 设置 Bucket 版本控制
     *
     * @param setBucketVersioningArgs {@link SetBucketVersioningArgs}
     */
    public Mono<Void> setBucketVersioning(SetBucketVersioningArgs setBucketVersioningArgs) {
        return fromFuture("setBucketVersioning", (client) -> client.setBucketVersioning(setBucketVersioningArgs));
    }

    /**
     * 获取 Bucket 版本配置
     *
     * @param bucketName bucketName
     * @return {@link VersioningConfiguration}
     */
    public Mono<VersioningConfiguration> getBucketVersioning(String bucketName) {
        return getBucketVersioning(GetBucketVersioningArgs.builder().bucket(bucketName).build());
    }

    /**
     * 获取 Bucket 版本配置
     *
     * @param bucketName bucketName
     * @param region     region
     * @return {@link VersioningConfiguration}
     */
    public Mono<VersioningConfiguration> getBucketVersioning(String bucketName, String region) {
        return getBucketVersioning(GetBucketVersioningArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 获取 Bucket 版本配置
     *
     * @param getBucketVersioningArgs {@link GetBucketVersioningArgs}
     * @return {@link VersioningConfiguration}
     */
    public Mono<VersioningConfiguration> getBucketVersioning(GetBucketVersioningArgs getBucketVersioningArgs) {
        return fromFuture("getBucketVersioning", (client) -> client.getBucketVersioning(getBucketVersioningArgs));
    }
}
