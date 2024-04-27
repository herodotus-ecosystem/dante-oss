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
import io.minio.DeleteBucketReplicationArgs;
import io.minio.GetBucketReplicationArgs;
import io.minio.SetBucketReplicationArgs;
import io.minio.messages.ReplicationConfiguration;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>Description: Minio Bucket Replication </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 15:55
 */
@Service
public class MinioBucketReplicationService extends BaseMinioAsyncService {

    public MinioBucketReplicationService(MinioAsyncClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 设置 Bucket 策略
     *
     * @param setBucketReplicationArgs {@link SetBucketReplicationArgs}
     */
    public Mono<Void> setBucketReplication(SetBucketReplicationArgs setBucketReplicationArgs) {
        return fromFuture("setBucketReplication", (client) -> client.setBucketReplication(setBucketReplicationArgs));
    }

    /**
     * 获取 Bucket 通知配置
     *
     * @param getBucketReplicationArgs {@link GetBucketReplicationArgs}
     */
    public Mono<ReplicationConfiguration> getBucketReplication(GetBucketReplicationArgs getBucketReplicationArgs) {
        return fromFuture("getBucketReplication", (client) -> client.getBucketReplication(getBucketReplicationArgs));
    }

    public Mono<Void> deleteBucketReplication(String bucketName) {
        return deleteBucketReplication(DeleteBucketReplicationArgs.builder().bucket(bucketName).build());
    }

    public Mono<Void> deleteBucketReplication(DeleteBucketReplicationArgs deleteBucketReplicationArgs) {
        return fromFuture("deleteBucketReplication", (client) -> client.deleteBucketReplication(deleteBucketReplicationArgs));
    }
}
