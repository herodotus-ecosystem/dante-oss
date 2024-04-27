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
import io.minio.DeleteBucketNotificationArgs;
import io.minio.GetBucketNotificationArgs;
import io.minio.SetBucketNotificationArgs;
import io.minio.messages.NotificationConfiguration;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>Description: Bucket 通知配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 15:42
 */
@Service
public class MinioBucketNotificationService extends BaseMinioAsyncService {

    public MinioBucketNotificationService(MinioAsyncClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 设置 Bucket 通知
     *
     * @param bucketName                bucketName
     * @param notificationConfiguration {@link NotificationConfiguration}
     */
    public Mono<Void> setBucketNotification(String bucketName, NotificationConfiguration notificationConfiguration) {
        return setBucketNotification(SetBucketNotificationArgs.builder().bucket(bucketName).config(notificationConfiguration).build());
    }

    /**
     * 设置 Bucket 通知
     *
     * @param bucketName                bucketName
     * @param region                    region
     * @param notificationConfiguration {@link NotificationConfiguration}
     */
    public Mono<Void> setBucketNotification(String bucketName, String region, NotificationConfiguration notificationConfiguration) {
        return setBucketNotification(SetBucketNotificationArgs.builder().bucket(bucketName).region(region).config(notificationConfiguration).build());
    }


    /**
     * 设置 Bucket 通知
     *
     * @param setBucketNotificationArgs {@link SetBucketNotificationArgs}
     */
    public Mono<Void> setBucketNotification(SetBucketNotificationArgs setBucketNotificationArgs) {
        return fromFuture("setBucketNotification", (client) -> client.setBucketNotification(setBucketNotificationArgs));
    }

    /**
     * 获取 Bucket 通知配置
     *
     * @param bucketName bucketName
     * @return {@link  NotificationConfiguration}
     */
    public Mono<NotificationConfiguration> getBucketNotification(String bucketName) {
        return getBucketNotification(GetBucketNotificationArgs.builder().bucket(bucketName).build());
    }

    /**
     * 获取 Bucket 通知配置
     *
     * @param bucketName bucketName
     * @param region     region
     * @return {@link  NotificationConfiguration}
     */
    public Mono<NotificationConfiguration> getBucketNotification(String bucketName, String region) {
        return getBucketNotification(GetBucketNotificationArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 获取 Bucket 通知配置
     *
     * @param getBucketNotificationArgs {@link GetBucketNotificationArgs}
     * @return {@link  NotificationConfiguration}
     */
    public Mono<NotificationConfiguration> getBucketNotification(GetBucketNotificationArgs getBucketNotificationArgs) {
        return fromFuture("getBucketNotification", (client) -> client.getBucketNotification(getBucketNotificationArgs));
    }

    /**
     * 删除 Bucket 通知配置
     *
     * @param bucketName bucketName
     */
    public Mono<Void> deleteBucketNotification(String bucketName) {
        return deleteBucketNotification(DeleteBucketNotificationArgs.builder().bucket(bucketName).build());
    }

    /**
     * 删除 Bucket 通知配置
     *
     * @param bucketName bucketName
     * @param region     region
     */
    public Mono<Void> deleteBucketNotification(String bucketName, String region) {
        return deleteBucketNotification(DeleteBucketNotificationArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 删除 Bucket 通知配置
     *
     * @param deleteBucketNotificationArgs {@link DeleteBucketNotificationArgs}
     */
    public Mono<Void> deleteBucketNotification(DeleteBucketNotificationArgs deleteBucketNotificationArgs) {
        return fromFuture("deleteBucketNotification", (client) -> client.deleteBucketNotification(deleteBucketNotificationArgs));
    }
}
