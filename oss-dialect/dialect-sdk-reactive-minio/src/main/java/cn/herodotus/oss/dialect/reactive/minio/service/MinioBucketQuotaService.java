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

import cn.herodotus.oss.core.minio.definition.pool.MinioAdminClientObjectPool;
import cn.herodotus.oss.dialect.reactive.minio.definition.service.BaseMinioAdminService;
import io.minio.admin.QuotaUnit;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;

/**
 * <p>Description: Minio User 管理服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/25 10:53
 */
@Service
public class MinioBucketQuotaService extends BaseMinioAdminService {

    public MinioBucketQuotaService(MinioAdminClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 设置存储桶配额
     *
     * @param bucketName 存储桶名称
     * @param size       配额大小
     * @param unit       配额单位
     */
    public Mono<Void> setBucketQuota(@Nonnull String bucketName, long size, @Nonnull QuotaUnit unit) {
        return empty("setBucketQuota", client -> client.setBucketQuota(bucketName, size, unit));
    }

    /**
     * 清除存储桶配额
     *
     * @param bucketName 存储桶名称
     */
    public Mono<Void> clearBucketQuota(@Nonnull String bucketName) {
        return setBucketQuota(bucketName, 0, QuotaUnit.KB);
    }

    /**
     * 获取存储桶配额大小
     *
     * @param bucketName 存储桶名称
     * @return 配额大小
     */
    public Mono<Long> getBucketQuota(String bucketName) {
        return just("getBucketQuota", client -> client.getBucketQuota(bucketName));
    }

}
