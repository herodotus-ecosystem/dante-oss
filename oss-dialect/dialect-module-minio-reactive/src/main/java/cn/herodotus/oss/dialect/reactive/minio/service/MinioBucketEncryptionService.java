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
import io.minio.DeleteBucketEncryptionArgs;
import io.minio.GetBucketEncryptionArgs;
import io.minio.SetBucketEncryptionArgs;
import io.minio.messages.SseConfiguration;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>Description: Bucket 加密服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 15:31
 */
@Service
public class MinioBucketEncryptionService extends BaseMinioAsyncService {

    public MinioBucketEncryptionService(MinioAsyncClientObjectPool minioAsyncClientObjectPool) {
        super(minioAsyncClientObjectPool);
    }

    /**
     * 获取 Bucket 加密配置
     *
     * @param bucketName 存储桶名称
     * @return 自定义 SseConfiguration 枚举 {@link SseConfiguration}
     */
    public Mono<SseConfiguration> getBucketEncryption(String bucketName) {
        return getBucketEncryption(bucketName, null);
    }

    /**
     * 获取 Bucket 加密配置
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @return 自定义 SseConfiguration 枚举 {@link SseConfiguration}
     */
    public Mono<SseConfiguration> getBucketEncryption(String bucketName, String region) {
        return getBucketEncryption(GetBucketEncryptionArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 获取 Bucket 加密配置
     *
     * @param getBucketEncryptionArgs {@link GetBucketEncryptionArgs}
     */
    public Mono<SseConfiguration> getBucketEncryption(GetBucketEncryptionArgs getBucketEncryptionArgs) {
        return fromFuture("getBucketEncryption", (client) -> client.getBucketEncryption(getBucketEncryptionArgs));
    }

    /**
     * 设置 Bucket 加密
     *
     * @param bucketName 存储桶名称
     * @param config     加密配置 {@link SseConfiguration}
     */
    public Mono<Void> setBucketEncryption(String bucketName, SseConfiguration config) {
        return setBucketEncryption(bucketName, null, config);
    }

    /**
     * 设置 Bucket 加密
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param config     加密配置 {@link SseConfiguration}
     */
    public Mono<Void> setBucketEncryption(String bucketName, String region, SseConfiguration config) {
        return setBucketEncryption(SetBucketEncryptionArgs.builder().bucket(bucketName).region(region).config(config).build());
    }


    /**
     * 设置 Bucket 加密
     *
     * @param setBucketEncryptionArgs {@link SetBucketEncryptionArgs}
     */
    public Mono<Void> setBucketEncryption(SetBucketEncryptionArgs setBucketEncryptionArgs) {
        return fromFuture("setBucketEncryption", (client) -> client.setBucketEncryption(setBucketEncryptionArgs));
    }

    /**
     * 删除 Bucket 加密配置
     *
     * @param bucketName 存储桶名称
     */
    public Mono<Void> deleteBucketEncryption(String bucketName) {
        return deleteBucketEncryption(bucketName, null);
    }

    /**
     * 删除 Bucket 加密配置
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     */
    public Mono<Void> deleteBucketEncryption(String bucketName, String region) {
        return deleteBucketEncryption(DeleteBucketEncryptionArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 删除 Bucket 加密配置
     *
     * @param deleteBucketEncryptionArgs {@link DeleteBucketEncryptionArgs}
     */
    public Mono<Void> deleteBucketEncryption(DeleteBucketEncryptionArgs deleteBucketEncryptionArgs) {
        return fromFuture("deleteBucketEncryption", (client) -> client.deleteBucketEncryption(deleteBucketEncryptionArgs));
    }


}
