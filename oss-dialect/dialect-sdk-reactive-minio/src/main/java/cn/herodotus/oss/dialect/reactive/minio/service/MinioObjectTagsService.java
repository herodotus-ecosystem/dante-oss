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
import io.minio.DeleteObjectTagsArgs;
import io.minio.GetObjectTagsArgs;
import io.minio.SetObjectTagsArgs;
import io.minio.messages.Tags;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>Description: Minio 对象标签服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 21:10
 */
@Service
public class MinioObjectTagsService extends BaseMinioAsyncService {

    public MinioObjectTagsService(MinioAsyncClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 获取对象的标签
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @return {@link Tags}
     */
    public Mono<Tags> getObjectTags(String bucketName, String objectName) {
        return getObjectTags(bucketName, null, objectName);
    }

    /**
     * 获取对象的标签
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param region     region
     * @return {@link Tags}
     */
    public Mono<Tags> getObjectTags(String bucketName, String region, String objectName) {
        return getObjectTags(bucketName, region, objectName, null);
    }

    /**
     * 获取对象的标签
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param region     region
     * @param versionId  versionId
     * @return {@link Tags}
     */
    public Mono<Tags> getObjectTags(String bucketName, String region, String objectName, String versionId) {
        return getObjectTags(GetObjectTagsArgs.builder().bucket(bucketName).object(objectName).region(region).versionId(versionId).build());
    }

    /**
     * 获取对象的标签。
     *
     * @param getObjectTagsArgs {@link GetObjectTagsArgs}
     * @return {@link Tags}
     */
    public Mono<Tags> getObjectTags(GetObjectTagsArgs getObjectTagsArgs) {
        return fromFuture("getObjectTags", (client) -> client.getObjectTags(getObjectTagsArgs));
    }

    /**
     * 为对象设置标签
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param tags       标签 {@link Tags}
     */
    public Mono<Void> setObjectTags(String bucketName, String objectName, Tags tags) {
        return setObjectTags(bucketName, null, objectName, tags);
    }

    /**
     * 为对象设置标签
     *
     * @param bucketName 存储桶名称
     * @param region     存储桶区域
     * @param objectName 对象名称
     * @param tags       标签 {@link Tags}
     */
    public Mono<Void> setObjectTags(String bucketName, String region, String objectName, Tags tags) {
        return setObjectTags(bucketName, region, objectName, tags, null);
    }

    /**
     * 为对象设置标签
     *
     * @param bucketName 存储桶名称
     * @param region     存储桶区域
     * @param objectName 对象名称
     * @param tags       标签 {@link Tags}
     * @param versionId  版本ID
     */
    public Mono<Void> setObjectTags(String bucketName, String region, String objectName, Tags tags, String versionId) {
        return setObjectTags(SetObjectTagsArgs.builder().bucket(bucketName).object(objectName).region(region).versionId(versionId).tags(tags).build());
    }

    /**
     * 为对象设置标签
     *
     * @param setObjectTagsArgs {@link SetObjectTagsArgs}
     */
    public Mono<Void> setObjectTags(SetObjectTagsArgs setObjectTagsArgs) {
        return fromFuture("setObjectTags", (client) -> client.setObjectTags(setObjectTagsArgs));
    }

    /**
     * 清空对象设置标签
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     */
    public Mono<Void> deleteObjectTags(String bucketName, String objectName) {
        return deleteObjectTags(bucketName, null, objectName);
    }

    /**
     * 清空对象设置标签
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     */
    public Mono<Void> deleteObjectTags(String bucketName, String region, String objectName) {
        return deleteObjectTags(bucketName, region, objectName, null);
    }


    /**
     * 清空对象设置标签
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param versionId  版本ID
     */
    public Mono<Void> deleteObjectTags(String bucketName, String region, String objectName, String versionId) {
        return deleteObjectTags(DeleteObjectTagsArgs.builder().bucket(bucketName).object(objectName).region(region).versionId(versionId).build());
    }

    /**
     * 清空对象设置标签
     *
     * @param deleteObjectTagsArgs {@link DeleteObjectTagsArgs}
     */
    public Mono<Void> deleteObjectTags(DeleteObjectTagsArgs deleteObjectTagsArgs) {
        return fromFuture("deleteObjectTags", (client) -> client.deleteObjectTags(deleteObjectTagsArgs));
    }
}
