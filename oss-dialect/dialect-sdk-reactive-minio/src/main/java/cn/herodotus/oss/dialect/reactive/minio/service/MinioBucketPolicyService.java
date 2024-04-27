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
import cn.herodotus.oss.core.minio.enums.PolicyEnums;
import cn.herodotus.oss.dialect.reactive.minio.definition.service.BaseMinioAsyncService;
import com.google.common.base.Enums;
import io.minio.DeleteBucketPolicyArgs;
import io.minio.GetBucketPolicyArgs;
import io.minio.SetBucketPolicyArgs;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>Description: Bucket 访问策略 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 15:44
 */
@Service
public class MinioBucketPolicyService extends BaseMinioAsyncService {

    public MinioBucketPolicyService(MinioAsyncClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 获取 Bucket 访问策略配置
     *
     * @param bucketName 存储桶名称
     * @return 自定义策略枚举 {@link PolicyEnums}
     */
    public Mono<PolicyEnums> getBucketPolicy(String bucketName) {
        return getBucketPolicy(bucketName, null);
    }

    /**
     * 获取 Bucket 访问策略配置
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @return 自定义策略枚举 {@link PolicyEnums}
     */
    public Mono<PolicyEnums> getBucketPolicy(String bucketName, String region) {
        return getBucketPolicy(GetBucketPolicyArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 获取 Bucket 访问策略配置
     *
     * @param getBucketPolicyArgs {@link GetBucketPolicyArgs}
     */
    public Mono<PolicyEnums> getBucketPolicy(GetBucketPolicyArgs getBucketPolicyArgs) {
        return fromFuture("getBucketPolicy", (client) -> client.getBucketPolicy(getBucketPolicyArgs))
                .flatMap(policy -> Mono.just(Enums.getIfPresent(PolicyEnums.class, policy).or(PolicyEnums.PRIVATE)));
    }

    /**
     * 设置 Bucket 访问策略
     *
     * @param bucketName 存储桶名称
     * @param config     策略配置
     */
    public Mono<Void> setBucketPolicy(String bucketName, String config) {
        return setBucketPolicy(bucketName, null, config);
    }

    /**
     * 设置 Bucket 访问策略
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param config     策略配置
     */
    public Mono<Void> setBucketPolicy(String bucketName, String region, String config) {
        return setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucketName).region(region).config(config).build());
    }

    /**
     * 设置 Bucket 访问策略
     *
     * @param setBucketPolicyArgs {@link SetBucketPolicyArgs}
     */
    public Mono<Void> setBucketPolicy(SetBucketPolicyArgs setBucketPolicyArgs) {
        return fromFuture("setBucketPolicy", (client) -> client.setBucketPolicy(setBucketPolicyArgs));
    }

    /**
     * 删除 Bucket 访问策略
     *
     * @param bucketName 存储桶名称
     */
    public Mono<Void> deleteBucketPolicy(String bucketName) {
        return deleteBucketPolicy(bucketName, null);
    }

    /**
     * 删除 Bucket 访问策略
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     */
    public Mono<Void> deleteBucketPolicy(String bucketName, String region) {
        return deleteBucketPolicy(DeleteBucketPolicyArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 删除 Bucket 访问策略
     *
     * @param deleteBucketPolicyArgs {@link DeleteBucketPolicyArgs}
     */
    public Mono<Void> deleteBucketPolicy(DeleteBucketPolicyArgs deleteBucketPolicyArgs) {
        return fromFuture("deleteBucketPolicy", (client) -> client.deleteBucketPolicy(deleteBucketPolicyArgs));
    }
}
