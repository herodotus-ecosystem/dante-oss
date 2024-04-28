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
import io.minio.DeleteBucketLifecycleArgs;
import io.minio.GetBucketLifecycleArgs;
import io.minio.SetBucketLifecycleArgs;
import io.minio.messages.LifecycleConfiguration;
import io.minio.messages.LifecycleRule;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <p>Description: Bucket 生命周期配置服务 </p>
 * <p>
 * 生命周期管理可适用于以下典型场景：
 * · 周期性上传的日志文件，可能只需要保留一个星期或一个月。到期后要删除它们。
 * · 某些文档在一段时间内经常访问，但是超过一定时间后便可能不再访问了。这些文档需要在一定时间后转化为低频访问存储，归档存储或者删除
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 15:39
 */
@Service
public class MinioBucketLifecycleService extends BaseMinioAsyncService {

    public MinioBucketLifecycleService(MinioAsyncClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 设置 Bucket 生命周期配置
     *
     * @param bucketName     bucketName
     * @param lifecycleRules {@link LifecycleRule}
     */
    public Mono<Void> setBucketLifecycle(String bucketName, List<LifecycleRule> lifecycleRules) {
        return setBucketLifecycle(bucketName, new LifecycleConfiguration(lifecycleRules));
    }

    /**
     * 置 Bucket 生命周期配置
     *
     * @param bucketName     bucketName
     * @param region         region
     * @param lifecycleRules {@link LifecycleRule}
     */
    public Mono<Void> setBucketLifecycle(String bucketName, String region, List<LifecycleRule> lifecycleRules) {
        return setBucketLifecycle(bucketName, region, new LifecycleConfiguration(lifecycleRules));
    }

    /**
     * 设置 Bucket 生命周期
     *
     * @param bucketName             bucketName
     * @param lifecycleConfiguration {@link LifecycleConfiguration}
     */
    public Mono<Void> setBucketLifecycle(String bucketName, LifecycleConfiguration lifecycleConfiguration) {
        return setBucketLifecycle(SetBucketLifecycleArgs.builder().bucket(bucketName).config(lifecycleConfiguration).build());
    }

    /**
     * 设置 Bucket 生命周期
     *
     * @param bucketName             bucketName
     * @param region                 region
     * @param lifecycleConfiguration @link LifecycleConfiguration}
     */
    public Mono<Void> setBucketLifecycle(String bucketName, String region, LifecycleConfiguration lifecycleConfiguration) {
        return setBucketLifecycle(SetBucketLifecycleArgs.builder().bucket(bucketName).region(region).config(lifecycleConfiguration).build());
    }

    /**
     * 设置 Bucket 生命周期
     *
     * @param setBucketLifecycleArgs {@link SetBucketLifecycleArgs}
     */
    public Mono<Void> setBucketLifecycle(SetBucketLifecycleArgs setBucketLifecycleArgs) {
        return fromFuture("setBucketLifecycle", (client) -> client.setBucketLifecycle(setBucketLifecycleArgs));
    }

    /**
     * 获取 Bucket 生命周期配置
     *
     * @param bucketName bucketName
     * @return {@link LifecycleConfiguration}
     */
    public Mono<LifecycleConfiguration> getBucketLifecycle(String bucketName) {
        return getBucketLifecycle(GetBucketLifecycleArgs.builder().bucket(bucketName).build());
    }

    /**
     * 获取 Bucket 生命周期配置
     *
     * @param bucketName bucketName
     * @param region     region
     * @return {@link LifecycleConfiguration}
     */
    public Mono<LifecycleConfiguration> getBucketLifecycle(String bucketName, String region) {
        return getBucketLifecycle(GetBucketLifecycleArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 获取 Bucket 生命周期配置
     *
     * @param getBucketLifecycleArgs {@link GetBucketLifecycleArgs}
     */
    public Mono<LifecycleConfiguration> getBucketLifecycle(GetBucketLifecycleArgs getBucketLifecycleArgs) {
        return fromFuture("getBucketLifecycle", (client) -> client.getBucketLifecycle(getBucketLifecycleArgs));
    }

    /**
     * 删除 Bucket 生命周期配置
     *
     * @param bucketName bucketName
     */
    public Mono<Void> deleteBucketLifecycle(String bucketName) {
        return deleteBucketLifecycle(DeleteBucketLifecycleArgs.builder().bucket(bucketName).build());
    }

    /**
     * 删除 Bucket 生命周期配置
     *
     * @param bucketName bucketName
     * @param region     region
     */
    public Mono<Void> deleteBucketLifecycle(String bucketName, String region) {
        return deleteBucketLifecycle(DeleteBucketLifecycleArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 删除 Bucket 生命周期配置
     *
     * @param deleteBucketLifecycleArgs {@link DeleteBucketLifecycleArgs}
     */
    public Mono<Void> deleteBucketLifecycle(DeleteBucketLifecycleArgs deleteBucketLifecycleArgs) {
        return fromFuture("deleteBucketLifecycle", (client) -> client.deleteBucketLifecycle(deleteBucketLifecycleArgs));
    }
}
