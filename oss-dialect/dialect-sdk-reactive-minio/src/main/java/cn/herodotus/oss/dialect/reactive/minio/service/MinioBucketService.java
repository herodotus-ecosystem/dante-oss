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

import cn.herodotus.oss.core.domain.bucket.BucketDomain;
import cn.herodotus.oss.core.minio.converter.domain.BucketToDomainConverter;
import cn.herodotus.oss.core.minio.definition.pool.MinioAsyncClientObjectPool;
import cn.herodotus.oss.dialect.core.utils.ConverterUtils;
import cn.herodotus.oss.dialect.reactive.minio.definition.service.BaseMinioAsyncService;
import io.minio.BucketExistsArgs;
import io.minio.ListBucketsArgs;
import io.minio.MakeBucketArgs;
import io.minio.RemoveBucketArgs;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <p>Description: Minio Bucket 存储通基础操作服务 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 13:26
 */
@Service
public class MinioBucketService extends BaseMinioAsyncService {

    public MinioBucketService(MinioAsyncClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 查询所有存储桶
     *
     * @param listBucketsArgs {@link ListBucketsArgs}
     * @return Bucket 列表
     */
    public Mono<List<BucketDomain>> listBuckets(ListBucketsArgs listBucketsArgs) {
        return fromFuture("listBuckets", (client) -> ObjectUtils.isNotEmpty(listBucketsArgs) ? client.listBuckets(listBucketsArgs) : client.listBuckets())
                .map(items -> ConverterUtils.toDomains(items, new BucketToDomainConverter()));
    }

    /**
     * 存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return 是否存在，true 存在，false 不存在
     */
    public Mono<Boolean> bucketExists(String bucketName) {
        return bucketExists(bucketName, null);
    }

    /**
     * 存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @return 是否存在，true 存在，false 不存在
     */
    public Mono<Boolean> bucketExists(String bucketName, String region) {
        return bucketExists(BucketExistsArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 存储桶是否存在
     *
     * @param bucketExistsArgs {@link BucketExistsArgs}
     * @return true 存在，false 不存在
     */
    public Mono<Boolean> bucketExists(BucketExistsArgs bucketExistsArgs) {
        return fromFuture("bucketExists", (client) -> client.bucketExists(bucketExistsArgs));
    }

    /**
     * 创建存储桶
     *
     * @param bucketName 存储桶名称
     */
    public Mono<Void> makeBucket(String bucketName) {
        return makeBucket(bucketName, null);
    }

    /**
     * 创建存储桶
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     */
    public Mono<Void> makeBucket(String bucketName, String region) {
        return makeBucket(MakeBucketArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 创建存储桶
     * <p>
     * 该方法仅仅是 Minio 原始方法的封装，不包含校验等操作。
     *
     * @param makeBucketArgs {@link MakeBucketArgs}
     */
    public Mono<Void> makeBucket(MakeBucketArgs makeBucketArgs) {
        return fromFuture("makeBucket", (client) -> client.makeBucket(makeBucketArgs));
    }

    /**
     * 删除一个空的存储桶 如果存储桶存在对象不为空时，删除会报错。
     *
     * @param bucketName 存储桶名称
     */
    public Mono<Void> removeBucket(String bucketName) {
        return removeBucket(bucketName, null);
    }

    /**
     * 删除一个空的存储桶 如果存储桶存在对象不为空时，删除会报错。
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     */
    public Mono<Void> removeBucket(String bucketName, String region) {
        return removeBucket(RemoveBucketArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 删除一个空的存储桶 如果存储桶存在对象不为空时，删除会报错。
     *
     * @param removeBucketArgs {@link RemoveBucketArgs}
     */
    public Mono<Void> removeBucket(RemoveBucketArgs removeBucketArgs) {
        return fromFuture("removeBucket", (client) -> client.removeBucket(removeBucketArgs));
    }
}
