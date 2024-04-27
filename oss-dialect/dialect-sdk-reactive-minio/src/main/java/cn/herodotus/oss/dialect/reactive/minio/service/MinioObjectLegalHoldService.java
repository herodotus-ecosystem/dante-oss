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
import io.minio.DisableObjectLegalHoldArgs;
import io.minio.EnableObjectLegalHoldArgs;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>Description: Object 合法持有 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/11 9:46
 */
@Service
public class MinioObjectLegalHoldService extends BaseMinioAsyncService {

    public MinioObjectLegalHoldService(MinioAsyncClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 启用对对象的合法保留
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     */
    public Mono<Void> enableObjectLegalHold(String bucketName, String objectName) {
        return enableObjectLegalHold(bucketName, null, objectName);
    }

    /**
     * 启用对对象的合法保留
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     */
    public Mono<Void> enableObjectLegalHold(String bucketName, String region, String objectName) {
        return enableObjectLegalHold(bucketName, region, objectName, null);
    }

    /**
     * 启用对对象的合法保留
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param versionId  版本ID
     */
    public Mono<Void> enableObjectLegalHold(String bucketName, String region, String objectName, String versionId) {
        return enableObjectLegalHold(EnableObjectLegalHoldArgs.builder().bucket(bucketName).region(region).object(objectName).versionId(versionId).build());
    }

    /**
     * 启用对对象的合法保留
     *
     * @param enableObjectLegalHoldArgs {@link EnableObjectLegalHoldArgs}
     */
    public Mono<Void> enableObjectLegalHold(EnableObjectLegalHoldArgs enableObjectLegalHoldArgs) {
        return fromFuture("enableObjectLegalHold", (client) -> client.enableObjectLegalHold(enableObjectLegalHoldArgs));
    }

    /**
     * 禁用对对象的合法保留。
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     */
    public Mono<Void> disableObjectLegalHold(String bucketName, String objectName) {
        return disableObjectLegalHold(bucketName, null, objectName);
    }

    /**
     * 禁用对对象的合法保留。
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     */
    public Mono<Void> disableObjectLegalHold(String bucketName, String region, String objectName) {
        return disableObjectLegalHold(bucketName, region, objectName, null);
    }

    /**
     * 禁用对对象的合法保留。
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param versionId  版本ID
     */
    public Mono<Void> disableObjectLegalHold(String bucketName, String region, String objectName, String versionId) {
        return disableObjectLegalHold(DisableObjectLegalHoldArgs.builder().bucket(bucketName).region(region).object(objectName).versionId(versionId).build());
    }

    /**
     * 禁用对对象的合法保留。
     *
     * @param disableObjectLegalHoldArgs {@link DisableObjectLegalHoldArgs}
     */
    public Mono<Void> disableObjectLegalHold(DisableObjectLegalHoldArgs disableObjectLegalHoldArgs) {
        return fromFuture("disableObjectLegalHold", (client) -> client.disableObjectLegalHold(disableObjectLegalHoldArgs));
    }
}
