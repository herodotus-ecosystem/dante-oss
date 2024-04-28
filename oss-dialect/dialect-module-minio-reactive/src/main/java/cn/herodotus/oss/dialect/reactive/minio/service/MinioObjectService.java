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

import cn.herodotus.oss.core.domain.object.DeleteObjectDomain;
import cn.herodotus.oss.core.domain.object.ListObjectsDomain;
import cn.herodotus.oss.core.minio.converter.domain.BucketToDomainConverter;
import cn.herodotus.oss.core.minio.converter.domain.IterableResultItemToDomainConverter;
import cn.herodotus.oss.core.minio.converter.domain.ResultDeleteErrorToDomainConverter;
import cn.herodotus.oss.core.minio.definition.pool.MinioAsyncClientObjectPool;
import cn.herodotus.oss.core.minio.utils.MinioConverterUtils;
import cn.herodotus.oss.dialect.core.utils.ConverterUtils;
import cn.herodotus.oss.dialect.reactive.minio.definition.service.BaseMinioAsyncService;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>Description: Minio 对象操作服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 20:06
 */
@Service
public class MinioObjectService extends BaseMinioAsyncService {

    public MinioObjectService(MinioAsyncClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV1
     *
     * @param bucketName 存储桶名称
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjects(String bucketName) {
        return listObjects(bucketName, null);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV1
     *
     * @param bucketName 存储桶名称
     * @param prefix     前缀
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjects(String bucketName, String prefix) {
        return listObjects(bucketName, null, prefix);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV1
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param prefix     前缀
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjects(String bucketName, String region, String prefix) {
        return listObjects(bucketName, region, prefix, null);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV1
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param delimiter  分隔符
     * @param prefix     前缀
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjects(String bucketName, String region, String prefix, String delimiter) {
        return listObjects(bucketName, region, prefix, delimiter, false);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV1
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param delimiter  分隔符
     * @param recursive  是否递归
     * @param prefix     前缀
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjects(String bucketName, String region, String prefix, String delimiter, boolean recursive) {
        return listObjects(bucketName, region, prefix, delimiter, recursive, null);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV1
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param delimiter  分隔符
     * @param recursive  是否递归
     * @param keyMarker  关键字
     * @param prefix     前缀
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjects(String bucketName, String region, String prefix, String delimiter, boolean recursive, String keyMarker) {
        return listObjects(bucketName, region, prefix, delimiter, recursive, true, keyMarker);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV1
     *
     * @param bucketName         存储桶名称
     * @param region             区域
     * @param delimiter          分隔符
     * @param recursive          是否递归
     * @param useUrlEncodingType 是否使用 UrlEncoding
     * @param keyMarker          关键字
     * @param prefix             前缀
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjects(String bucketName, String region, String prefix, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker) {
        return listObjects(bucketName, region, prefix, delimiter, recursive, useUrlEncodingType, keyMarker, 1000);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV1
     *
     * @param bucketName         存储桶名称
     * @param region             区域
     * @param delimiter          分隔符
     * @param recursive          是否递归
     * @param useUrlEncodingType 是否使用 UrlEncoding
     * @param keyMarker          关键字
     * @param maxKeys            最大关键字
     * @param prefix             前缀
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjects(String bucketName, String region, String prefix, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker, int maxKeys) {
        return listObjects(bucketName, region, prefix, delimiter, recursive, useUrlEncodingType, keyMarker, maxKeys, false);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV1
     *
     * @param bucketName         存储桶名称
     * @param region             区域
     * @param delimiter          分隔符
     * @param recursive          是否递归
     * @param useUrlEncodingType 是否使用 UrlEncoding
     * @param keyMarker          关键字
     * @param maxKeys            最大关键字
     * @param prefix             前缀
     * @param includeVersions    是否包含版本
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjects(String bucketName, String region, String prefix, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker, int maxKeys, boolean includeVersions) {
        return listObjects(bucketName, region, prefix, delimiter, recursive, useUrlEncodingType, keyMarker, maxKeys, includeVersions, null);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV1
     *
     * @param bucketName         存储桶名称
     * @param region             区域
     * @param delimiter          分隔符
     * @param recursive          是否递归
     * @param useUrlEncodingType 是否使用 UrlEncoding
     * @param keyMarker          关键字
     * @param maxKeys            最大关键字
     * @param prefix             前缀
     * @param includeVersions    是否包含版本
     * @param versionIdMarker    版本关键字
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjects(String bucketName, String region, String prefix, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker, int maxKeys, boolean includeVersions, String versionIdMarker) {
        return listObjects(ListObjectsArgs.builder()
                .bucket(bucketName)
                .region(region)
                .delimiter(delimiter)
                .recursive(recursive)
                .useUrlEncodingType(useUrlEncodingType)
                .keyMarker(keyMarker)
                .maxKeys(maxKeys)
                .prefix(prefix)
                .includeVersions(includeVersions)
                .versionIdMarker(versionIdMarker)
                .useApiVersion1(true)
                .build());
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName 存储桶名称
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjectsV2(String bucketName) {
        return listObjectsV2(bucketName, null);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjectsV2(String bucketName, String region) {
        return listObjectsV2(bucketName, region, null);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param delimiter  分隔符
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjectsV2(String bucketName, String region, String delimiter) {
        return listObjectsV2(bucketName, region, delimiter, false);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param delimiter  分隔符
     * @param recursive  是否递归
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjectsV2(String bucketName, String region, String delimiter, boolean recursive) {
        return listObjectsV2(bucketName, region, delimiter, recursive, null);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param delimiter  分隔符
     * @param recursive  是否递归
     * @param keyMarker  关键字
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjectsV2(String bucketName, String region, String delimiter, boolean recursive, String keyMarker) {
        return listObjectsV2(bucketName, region, delimiter, recursive, true, keyMarker);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName         存储桶名称
     * @param region             区域
     * @param delimiter          分隔符
     * @param recursive          是否递归
     * @param useUrlEncodingType 是否使用 UrlEncoding
     * @param keyMarker          关键字
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjectsV2(String bucketName, String region, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker) {
        return listObjectsV2(bucketName, region, delimiter, recursive, useUrlEncodingType, keyMarker, 1000);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName         存储桶名称
     * @param region             区域
     * @param delimiter          分隔符
     * @param recursive          是否递归
     * @param useUrlEncodingType 是否使用 UrlEncoding
     * @param keyMarker          关键字
     * @param maxKeys            最大关键字
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjectsV2(String bucketName, String region, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker, int maxKeys) {
        return listObjectsV2(bucketName, region, delimiter, recursive, useUrlEncodingType, keyMarker, maxKeys, null);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName         存储桶名称
     * @param region             区域
     * @param delimiter          分隔符
     * @param recursive          是否递归
     * @param useUrlEncodingType 是否使用 UrlEncoding
     * @param keyMarker          关键字
     * @param maxKeys            最大关键字
     * @param prefix             前缀
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjectsV2(String bucketName, String region, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker, int maxKeys, String prefix) {
        return listObjectsV2(bucketName, region, delimiter, recursive, useUrlEncodingType, keyMarker, maxKeys, prefix, false);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName         存储桶名称
     * @param region             区域
     * @param delimiter          分隔符
     * @param recursive          是否递归
     * @param useUrlEncodingType 是否使用 UrlEncoding
     * @param keyMarker          关键字
     * @param maxKeys            最大关键字
     * @param prefix             前缀
     * @param includeVersions    是否包含版本
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjectsV2(String bucketName, String region, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker, int maxKeys, String prefix, boolean includeVersions) {
        return listObjectsV2(bucketName, region, delimiter, recursive, useUrlEncodingType, keyMarker, maxKeys, prefix, includeVersions, null);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName         存储桶名称
     * @param region             区域
     * @param delimiter          分隔符
     * @param recursive          是否递归
     * @param useUrlEncodingType 是否使用 UrlEncoding
     * @param keyMarker          关键字
     * @param maxKeys            最大关键字
     * @param prefix             前缀
     * @param includeVersions    是否包含版本
     * @param versionIdMarker    版本关键字
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjectsV2(String bucketName, String region, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker, int maxKeys, String prefix, boolean includeVersions, String versionIdMarker) {
        return listObjectsV2(bucketName, region, delimiter, recursive, useUrlEncodingType, keyMarker, maxKeys, prefix, includeVersions, versionIdMarker, null);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName         存储桶名称
     * @param region             区域
     * @param delimiter          分隔符
     * @param recursive          是否递归
     * @param useUrlEncodingType 是否使用 UrlEncoding
     * @param keyMarker          关键字
     * @param maxKeys            最大关键字
     * @param prefix             前缀
     * @param includeVersions    是否包含版本
     * @param versionIdMarker    版本关键字
     * @param continuationToken  持续集成 Token
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjectsV2(String bucketName, String region, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker, int maxKeys, String prefix, boolean includeVersions, String versionIdMarker, String continuationToken) {
        return listObjectsV2(bucketName, region, delimiter, recursive, useUrlEncodingType, keyMarker, maxKeys, prefix, includeVersions, versionIdMarker, continuationToken, false);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName         存储桶名称
     * @param region             区域
     * @param delimiter          分隔符
     * @param recursive          是否递归
     * @param useUrlEncodingType 是否使用 UrlEncoding
     * @param keyMarker          关键字
     * @param maxKeys            最大关键字
     * @param prefix             前缀
     * @param includeVersions    是否包含版本
     * @param versionIdMarker    版本关键字
     * @param continuationToken  持续集成 Token
     * @param fetchOwner         获取 OwnerDomain
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjectsV2(String bucketName, String region, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker, int maxKeys, String prefix, boolean includeVersions, String versionIdMarker, String continuationToken, boolean fetchOwner) {
        return listObjectsV2(bucketName, region, delimiter, recursive, useUrlEncodingType, keyMarker, maxKeys, prefix, includeVersions, versionIdMarker, continuationToken, fetchOwner, false);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName          存储桶名称
     * @param region              区域
     * @param delimiter           分隔符
     * @param recursive           是否递归
     * @param useUrlEncodingType  是否使用 UrlEncoding
     * @param keyMarker           关键字
     * @param maxKeys             最大关键字
     * @param prefix              前缀
     * @param includeVersions     是否包含版本
     * @param versionIdMarker     版本关键字
     * @param continuationToken   持续集成 Token
     * @param fetchOwner          获取 OwnerDomain
     * @param includeUserMetadata 包含用户自定义信息
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjectsV2(String bucketName, String region, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker, int maxKeys, String prefix, boolean includeVersions, String versionIdMarker, String continuationToken, boolean fetchOwner, boolean includeUserMetadata) {
        return listObjects(ListObjectsArgs.builder()
                .bucket(bucketName)
                .region(region)
                .delimiter(delimiter)
                .recursive(recursive)
                .useUrlEncodingType(useUrlEncodingType)
                .keyMarker(keyMarker)
                .maxKeys(maxKeys)
                .prefix(prefix)
                .includeVersions(includeVersions)
                .versionIdMarker(versionIdMarker)
                .useApiVersion1(false)
                .continuationToken(continuationToken)
                .fetchOwner(fetchOwner)
                .includeUserMetadata(includeUserMetadata)
                .build());
    }

    /**
     * listObjects列出桶的对象信息
     *
     * @param listObjectsArgs {@link ListObjectsArgs}
     * @return Iterable<Result < Item>>
     */
    public Mono<ListObjectsDomain> listObjects(ListObjectsArgs listObjectsArgs) {
        Converter<Iterable<Result<Item>>, ListObjectsDomain> toDomain = new IterableResultItemToDomainConverter(listObjectsArgs.bucket(), listObjectsArgs.prefix());
        return just("listObjects", (client) -> client.listObjects(listObjectsArgs))
                .mapNotNull(toDomain::convert);
    }

    /**
     * 懒惰地删除多个对象。它需要迭代返回的 Iterable 以执行删除
     *
     * @param bucketName 存储桶名称
     * @param objects    待删除对象
     * @return 自定义删除错误列表。列表 Size 为 0，表明全部正常删除；不为 0，则返回具体错误对象以及相关信息
     */
    public Mono<List<DeleteObjectDomain>> removeObjects(String bucketName, Iterable<DeleteObject> objects) {
        return removeObjects(bucketName, null, objects);
    }

    /**
     * 懒惰地删除多个对象。它需要迭代返回的 Iterable 以执行删除
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objects    待删除对象
     * @return 自定义删除错误列表。列表 Size 为 0，表明全部正常删除；不为 0，则返回具体错误对象以及相关信息
     */
    public Mono<List<DeleteObjectDomain>> removeObjects(String bucketName, String region, Iterable<DeleteObject> objects) {
        return removeObjects(bucketName, region, objects, false);
    }

    /**
     * 懒惰地删除多个对象。它需要迭代返回的 Iterable 以执行删除
     *
     * @param bucketName           存储桶名称
     * @param region               区域
     * @param objects              待删除对象
     * @param bypassGovernanceMode 使用 Governance 模式
     * @return 自定义删除错误列表。列表 Size 为 0，表明全部正常删除；不为 0，则返回具体错误对象以及相关信息
     */
    public Mono<List<DeleteObjectDomain>> removeObjects(String bucketName, String region, Iterable<DeleteObject> objects, boolean bypassGovernanceMode) {
        return removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).region(region).objects(objects).bypassGovernanceMode(bypassGovernanceMode).build());
    }

    /**
     * 懒惰地删除多个对象。它需要迭代返回的 Iterable 以执行删除
     *
     * @param removeObjectsArgs {@link RemoveObjectsArgs}
     * @return 自定义删除错误列表。列表 Size 为 0，表明全部正常删除；不为 0，则返回具体错误对象以及相关信息
     */
    public Mono<List<DeleteObjectDomain>> removeObjects(RemoveObjectsArgs removeObjectsArgs) {
        return just("removeObject", (client) -> client.removeObjects(removeObjectsArgs))
                .map(items -> MinioConverterUtils.toDomains(items, new ResultDeleteErrorToDomainConverter()));
    }

    /**
     * 移除一个对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名
     */
    public Mono<Void> removeObject(String bucketName, String objectName) {
        return removeObject(bucketName, null, objectName);
    }

    /**
     * 移除一个对象
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名
     */
    public Mono<Void> removeObject(String bucketName, String region, String objectName) {
        return removeObject(bucketName, region, objectName, null);
    }

    /**
     * 移除一个对象
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名
     * @param versionId  版本ID
     */
    public Mono<Void> removeObject(String bucketName, String region, String objectName, String versionId) {
        return removeObject(RemoveObjectArgs.builder().bucket(bucketName).region(region).object(objectName).versionId(versionId).build());
    }

    /**
     * 移除一个对象
     *
     * @param removeObjectArgs {@link RemoveObjectArgs}
     */
    public Mono<Void> removeObject(RemoveObjectArgs removeObjectArgs) {
        return fromFuture("removeObject", (client) -> client.removeObject(removeObjectArgs));
    }

    /**
     * 获取对象的对象信息和元数据
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @return {@link GetObjectResponse}
     */
    public Mono<StatObjectResponse> statObject(String bucketName, String objectName) {
        return statObject(bucketName, null, objectName);
    }

    /**
     * 获取对象的对象信息和元数据
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @return {@link GetObjectResponse}
     */
    public Mono<StatObjectResponse> statObject(String bucketName, String region, String objectName) {
        return statObject(bucketName, region, objectName, null);
    }


    /**
     * 获取对象的对象信息和元数据
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param matchETag  匹配的 ETag
     * @return {@link GetObjectResponse}
     */
    public Mono<StatObjectResponse> statObject(String bucketName, String region, String objectName, String matchETag) {
        return statObject(bucketName, region, objectName, matchETag, null);
    }

    /**
     * 获取对象的对象信息和元数据
     *
     * @param bucketName   存储桶名称
     * @param region       区域
     * @param objectName   对象名称
     * @param matchETag    匹配的 ETag
     * @param notMatchETag 不匹配的 ETag
     * @return {@link GetObjectResponse}
     */
    public Mono<StatObjectResponse> statObject(String bucketName, String region, String objectName, String matchETag, String notMatchETag) {
        return statObject(bucketName, region, objectName, matchETag, notMatchETag, null);
    }

    /**
     * 获取对象的对象信息和元数据
     *
     * @param bucketName    存储桶名称
     * @param region        区域
     * @param objectName    对象名称
     * @param matchETag     匹配的 ETag
     * @param notMatchETag  不匹配的 ETag
     * @param modifiedSince 某个时间以后的
     * @return {@link GetObjectResponse}
     */
    public Mono<StatObjectResponse> statObject(String bucketName, String region, String objectName, String matchETag, String notMatchETag, ZonedDateTime modifiedSince) {
        return statObject(bucketName, region, objectName, matchETag, notMatchETag, modifiedSince, null);
    }

    /**
     * 获取对象的对象信息和元数据
     *
     * @param bucketName      存储桶名称
     * @param region          区域
     * @param objectName      对象名称
     * @param matchETag       匹配的 ETag
     * @param notMatchETag    不匹配的 ETag
     * @param modifiedSince   某个时间以后的
     * @param unmodifiedSince 某个时间以前的
     * @return {@link GetObjectResponse}
     */
    public Mono<StatObjectResponse> statObject(String bucketName, String region, String objectName, String matchETag, String notMatchETag, ZonedDateTime modifiedSince, ZonedDateTime unmodifiedSince) {
        return statObject(bucketName, region, objectName, null, null, matchETag, notMatchETag, modifiedSince, unmodifiedSince);
    }

    /**
     * 获取对象的对象信息和元数据
     *
     * @param bucketName      存储桶名称
     * @param region          区域
     * @param objectName      对象名称
     * @param offset          偏移
     * @param length          长度
     * @param matchETag       匹配的 ETag
     * @param notMatchETag    不匹配的 ETag
     * @param modifiedSince   某个时间以后的
     * @param unmodifiedSince 某个时间以前的
     * @return {@link GetObjectResponse}
     */
    public Mono<StatObjectResponse> statObject(String bucketName, String region, String objectName, Long offset, Long length, String matchETag, String notMatchETag, ZonedDateTime modifiedSince, ZonedDateTime unmodifiedSince) {
        return statObject(bucketName, region, objectName, offset, length, matchETag, notMatchETag, modifiedSince, unmodifiedSince, null);
    }

    /**
     * 获取对象的对象信息和元数据
     *
     * @param bucketName                      存储桶名称
     * @param region                          区域
     * @param objectName                      对象名称
     * @param offset                          偏移
     * @param length                          长度
     * @param matchETag                       匹配的 ETag
     * @param notMatchETag                    不匹配的 ETag
     * @param modifiedSince                   某个时间以后的
     * @param unmodifiedSince                 某个时间以前的
     * @param serverSideEncryptionCustomerKey 服务端加密自定义KEY，目前 Minio 仅支持 256位 AES
     * @return {@link GetObjectResponse}
     */
    public Mono<StatObjectResponse> statObject(String bucketName, String region, String objectName, Long offset, Long length, String matchETag, String notMatchETag, ZonedDateTime modifiedSince, ZonedDateTime unmodifiedSince, ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey) {
        return statObject(bucketName, region, objectName, offset, length, matchETag, notMatchETag, modifiedSince, unmodifiedSince, serverSideEncryptionCustomerKey, null);
    }

    /**
     * 获取对象的对象信息和元数据
     *
     * @param bucketName                      存储桶名称
     * @param region                          区域
     * @param objectName                      对象名称
     * @param offset                          偏移
     * @param length                          长度
     * @param matchETag                       匹配的 ETag
     * @param notMatchETag                    不匹配的 ETag
     * @param modifiedSince                   某个时间以后的
     * @param unmodifiedSince                 某个时间以前的
     * @param serverSideEncryptionCustomerKey 服务端加密自定义KEY，目前 Minio 仅支持 256位 AES.
     * @param versionId                       版本ID
     * @return {@link StatObjectResponse}
     */
    public Mono<StatObjectResponse> statObject(String bucketName, String region, String objectName, Long offset, Long length, String matchETag, String notMatchETag, ZonedDateTime modifiedSince, ZonedDateTime unmodifiedSince, ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey, String versionId) {
        return statObject(StatObjectArgs.builder()
                .bucket(bucketName)
                .region(region)
                .object(objectName)
                .offset(offset)
                .length(length)
                .matchETag(matchETag)
                .notMatchETag(notMatchETag)
                .modifiedSince(modifiedSince)
                .unmodifiedSince(unmodifiedSince)
                .ssec(serverSideEncryptionCustomerKey)
                .versionId(versionId)
                .build());
    }

    /**
     * 获取对象的对象信息和元数据
     *
     * @param statObjectArgs {@link StatObjectArgs}
     * @return {@link StatObjectResponse}
     */
    public Mono<StatObjectResponse> statObject(StatObjectArgs statObjectArgs) {
        return fromFuture("statObject", (client) -> client.statObject(statObjectArgs));
    }

    /**
     * 通过使用服务器端副本组合来自不同源对象的数据来创建对象，比如可以将文件分片上传，然后将他们合并为一个文件
     *
     * @param composeObjectArgs {@link ComposeObjectArgs}
     * @return {@link ObjectWriteResponse}
     */
    public Mono<ObjectWriteResponse> composeObject(ComposeObjectArgs composeObjectArgs) {
        return fromFuture("composeObject", (client) -> client.composeObject(composeObjectArgs));
    }

    /**
     * 通过服务器端从另一个对象复制数据来创建一个对象
     *
     * @param copyObjectArgs {@link CopyObjectArgs}
     * @return {@link ObjectWriteResponse}
     */
    public Mono<ObjectWriteResponse> copyObject(CopyObjectArgs copyObjectArgs) {
        return fromFuture("copyObject", (client) -> client.copyObject(copyObjectArgs));
    }

    /**
     * 恢复对象
     *
     * @param args {@link RestoreObjectArgs}
     */
    public Mono<Void> restoreObject(RestoreObjectArgs args) {
        return fromFuture("restoreObject", (client) -> client.restoreObject(args));
    }

    /**
     * 通过 SQL 表达式选择对象的内容
     *
     * @param selectObjectContentArgs {@link SelectObjectContentArgs}
     * @return {@link SelectResponseStream}
     */
    public Mono<SelectResponseStream> selectObjectContent(SelectObjectContentArgs selectObjectContentArgs) {
        return just("selectObjectContent", (client) -> client.selectObjectContent(selectObjectContentArgs));
    }

    /**
     * 使用此方法，获取对象的上传策略（包含签名、文件信息、路径等），然后使用这些信息采用POST 方法的表单数据上传数据。也就是可以生成一个临时上传的信息对象，第三方可以使用这些信息，就可以上传文件。
     * <p>
     * 一般可用于，前端请求一个上传策略，后端返回给前端，前端使用Post请求+访问策略去上传文件，这可以用于JS+SDK的混合方式集成
     *
     * @param postPolicy {@link PostPolicy}
     * @return {@link  Map}
     */
    public Mono<Map<String, String>> getPreSignedPostFormData(PostPolicy postPolicy) {
        return just("getPreSignedPostFormData", (client) -> client.getPresignedPostFormData(postPolicy));
    }

    /**
     * 获取一个指定了 HTTP 方法、到期时间和自定义请求参数的对象URL地址，也就是返回带签名的URL，这个地址可以提供给没有登录的第三方共享访问或者上传对象。
     * <p>
     * 默认有效期 7 天, GET 类型 URL
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @return url string
     */
    public Mono<String> getPreSignedObjectUrl(String bucketName, String objectName) {
        return getPreSignedObjectUrl(bucketName, null, objectName);
    }

    /**
     * 获取一个指定了 HTTP 方法、到期时间和自定义请求参数的对象URL地址，也就是返回带签名的URL，这个地址可以提供给没有登录的第三方共享访问或者上传对象。
     * <p>
     * 默认有效期 7 天, GET 类型 URL
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @return url string
     */
    public Mono<String> getPreSignedObjectUrl(String bucketName, String region, String objectName) {
        return getPreSignedObjectUrl(bucketName, region, objectName, Method.GET);
    }

    /**
     * 获取一个指定了 HTTP 方法、到期时间和自定义请求参数的对象URL地址，也就是返回带签名的URL，这个地址可以提供给没有登录的第三方共享访问或者上传对象。
     * <p>
     * 默认有效期 7 天
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param method     方法类型 {@link Method}
     * @return url string
     */
    public Mono<String> getPreSignedObjectUrl(String bucketName, String region, String objectName, Method method) {
        return getPreSignedObjectUrl(bucketName, region, objectName, method, 7, TimeUnit.DAYS);
    }

    /**
     * 获取一个指定了 HTTP 方法、到期时间和自定义请求参数的对象URL地址，也就是返回带签名的URL，这个地址可以提供给没有登录的第三方共享访问或者上传对象。
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param method     方法类型 {@link Method}
     * @param duration   过期时间
     * @param unit       过期时间单位
     * @return url string
     */
    public Mono<String> getPreSignedObjectUrl(String bucketName, String region, String objectName, Method method, int duration, TimeUnit unit) {
        return getPreSignedObjectUrl(bucketName, region, objectName, method, duration, unit, null);
    }

    /**
     * 获取一个指定了 HTTP 方法、到期时间和自定义请求参数的对象URL地址，也就是返回带签名的URL，这个地址可以提供给没有登录的第三方共享访问或者上传对象。
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param method     方法类型 {@link Method}
     * @param duration   过期时间
     * @param unit       过期时间单位
     * @param versionId  版本ID
     * @return url string
     */
    public Mono<String> getPreSignedObjectUrl(String bucketName, String region, String objectName, Method method, int duration, TimeUnit unit, String versionId) {
        return getPreSignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .region(region)
                .object(objectName)
                .method(method)
                .expiry(duration, unit)
                .versionId(versionId)
                .build());
    }

    /**
     * 获取一个指定了 HTTP 方法、到期时间和自定义请求参数的对象URL地址，也就是返回带签名的URL，这个地址可以提供给没有登录的第三方共享访问或者上传对象。
     *
     * @param args {@link GetPresignedObjectUrlArgs}
     * @return url string
     */
    public Mono<String> getPreSignedObjectUrl(GetPresignedObjectUrlArgs args) {
        return just("getPreSignedObjectUrl", (client) -> client.getPresignedObjectUrl(args));
    }

    /**
     * 将对象的数据下载到文件。主要用于在服务端下载(非流方式)
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param fileName   具体保存的文件名，包括路径
     */
    public Mono<Void> downloadObject(String bucketName, String objectName, String fileName) {
        return downloadObject(bucketName, objectName, fileName, false);
    }

    /**
     * 将对象的数据下载到文件。主要用于在服务端下载(非流方式)
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param fileName   具体保存的文件名，包括路径
     * @param overwrite  是否覆盖
     */
    public Mono<Void> downloadObject(String bucketName, String objectName, String fileName, boolean overwrite) {
        return downloadObject(bucketName, null, objectName, fileName, overwrite);
    }

    /**
     * 将对象的数据下载到文件。主要用于在服务端下载(非流方式)
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param fileName   具体保存的文件名，包括路径
     * @param overwrite  是否覆盖
     */
    public Mono<Void> downloadObject(String bucketName, String region, String objectName, String fileName, boolean overwrite) {
        return downloadObject(bucketName, region, objectName, fileName, overwrite, null);
    }

    /**
     * 将对象的数据下载到文件。主要用于在服务端下载(非流方式)
     *
     * @param bucketName                      存储桶名称
     * @param region                          区域
     * @param objectName                      对象名称
     * @param fileName                        具体保存的文件名，包括路径
     * @param overwrite                       是否覆盖
     * @param serverSideEncryptionCustomerKey 服务端加密自定义KEY，目前 Minio 仅支持 256位 AES.
     */
    public Mono<Void> downloadObject(String bucketName, String region, String objectName, String fileName, boolean overwrite, ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey) {
        return downloadObject(bucketName, region, objectName, fileName, overwrite, serverSideEncryptionCustomerKey, null);
    }

    /**
     * 将对象的数据下载到文件。主要用于在服务端下载(非流方式)
     *
     * @param bucketName                      存储桶名称
     * @param region                          区域
     * @param objectName                      对象名称
     * @param fileName                        具体保存的文件名，包括路径
     * @param overwrite                       是否覆盖
     * @param serverSideEncryptionCustomerKey 服务端加密自定义KEY，目前 Minio 仅支持 256位 AES.
     * @param versionId                       版本ID
     */
    public Mono<Void> downloadObject(String bucketName, String region, String objectName, String fileName, boolean overwrite, ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey, String versionId) {
        return downloadObject(DownloadObjectArgs.builder()
                .bucket(bucketName)
                .region(region)
                .object(objectName)
                .filename(fileName)
                .overwrite(overwrite)
                .ssec(serverSideEncryptionCustomerKey)
                .versionId(versionId)
                .build());
    }

    /**
     * 将对象的数据下载到文件。主要用于在服务端下载
     *
     * @param downloadObjectArgs {@link DownloadObjectArgs}
     */
    public Mono<Void> downloadObject(DownloadObjectArgs downloadObjectArgs) {
        return fromFuture("downloadObject", (client) -> client.downloadObject(downloadObjectArgs));
    }

    /**
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param fileName   具体文件，完整的路径
     * @return {@link ObjectWriteResponse}
     * @throws IOException 读取文件失败
     */
    public Mono<ObjectWriteResponse> uploadObject(String bucketName, String objectName, String fileName) throws IOException {
        return uploadObject(bucketName, null, objectName, fileName);
    }

    /**
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param fileName   具体文件，完整的路径
     * @return {@link ObjectWriteResponse}
     * @throws IOException 读取文件失败
     */
    public Mono<ObjectWriteResponse> uploadObject(String bucketName, String region, String objectName, String fileName) throws IOException {
        return uploadObject(bucketName, region, objectName, fileName, null);
    }

    /**
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param bucketName  存储桶名称
     * @param region      区域
     * @param objectName  对象名称
     * @param fileName    具体文件，完整的路径
     * @param contentType 内容类型
     * @return {@link ObjectWriteResponse}
     * @throws IOException 读取文件失败
     */
    public Mono<ObjectWriteResponse> uploadObject(String bucketName, String region, String objectName, String fileName, String contentType) throws IOException {
        return uploadObject(bucketName, region, objectName, fileName, contentType, false);
    }

    /**
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param bucketName  存储桶名称
     * @param region      区域
     * @param objectName  对象名称
     * @param fileName    具体文件，完整的路径
     * @param contentType 内容类型
     * @param legalHold   是否保持
     * @return {@link ObjectWriteResponse}
     * @throws IOException 读取文件失败
     */
    public Mono<ObjectWriteResponse> uploadObject(String bucketName, String region, String objectName, String fileName, String contentType, boolean legalHold) throws IOException {
        return uploadObject(bucketName, region, objectName, fileName, contentType, legalHold, null);
    }

    /**
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param bucketName  存储桶名称
     * @param region      区域
     * @param objectName  对象名称
     * @param fileName    具体文件，完整的路径
     * @param contentType 内容类型
     * @param legalHold   是否保持
     * @param retention   保存设置
     * @return {@link ObjectWriteResponse}
     * @throws IOException 读取文件失败
     */
    public Mono<ObjectWriteResponse> uploadObject(String bucketName, String region, String objectName, String fileName, String contentType, boolean legalHold, Retention retention) throws IOException {
        return uploadObject(bucketName, region, objectName, fileName, contentType, legalHold, retention, null);
    }

    /**
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param bucketName  存储桶名称
     * @param region      区域
     * @param objectName  对象名称
     * @param fileName    具体文件，完整的路径
     * @param contentType 内容类型
     * @param legalHold   是否保持
     * @param retention   保存设置
     * @param tags        标签
     * @return {@link ObjectWriteResponse}
     * @throws IOException 读取文件失败
     */
    public Mono<ObjectWriteResponse> uploadObject(String bucketName, String region, String objectName, String fileName, String contentType, boolean legalHold, Retention retention, Tags tags) throws IOException {
        return uploadObject(bucketName, region, objectName, fileName, contentType, legalHold, retention, tags, null);
    }

    /**
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param bucketName  存储桶名称
     * @param region      区域
     * @param objectName  对象名称
     * @param fileName    具体文件，完整的路径
     * @param contentType 内容类型
     * @param legalHold   是否保持
     * @param retention   保存设置
     * @param tags        标签
     * @param sse         服务加密
     * @return {@link ObjectWriteResponse}
     * @throws IOException 读取文件失败
     */
    public Mono<ObjectWriteResponse> uploadObject(String bucketName, String region, String objectName, String fileName, String contentType, boolean legalHold, Retention retention, Tags tags, ServerSideEncryption sse) throws IOException {
        return uploadObject(UploadObjectArgs.builder()
                .bucket(bucketName)
                .region(region)
                .object(objectName)
                .filename(fileName)
                .contentType(contentType)
                .sse(sse)
                .legalHold(legalHold)
                .tags(tags)
                .retention(retention)
                .build());
    }

    /**
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param uploadObjectArgs {@link UploadObjectArgs}
     * @return {@link ObjectWriteResponse}
     */
    public Mono<ObjectWriteResponse> uploadObject(UploadObjectArgs uploadObjectArgs) {
        return fromFuture("uploadObject", (client) -> client.uploadObject(uploadObjectArgs));
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     * <p>
     * 获取对象的数据。InputStream使用后返回必须关闭以释放网络资源。
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @return {@link GetObjectResponse}
     */
    public Mono<GetObjectResponse> getObject(String bucketName, String objectName) {
        return getObject(bucketName, null, objectName);
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     * <p>
     * 获取对象的数据。InputStream使用后返回必须关闭以释放网络资源。
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @return {@link GetObjectResponse}
     */
    public Mono<GetObjectResponse> getObject(String bucketName, String region, String objectName) {
        return getObject(bucketName, region, objectName, null);
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     * <p>
     * 获取对象的数据。InputStream使用后返回必须关闭以释放网络资源。
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param matchETag  匹配的 ETag
     * @return {@link GetObjectResponse}
     */
    public Mono<GetObjectResponse> getObject(String bucketName, String region, String objectName, String matchETag) {
        return getObject(bucketName, region, objectName, matchETag, null);
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     * <p>
     * 获取对象的数据。InputStream使用后返回必须关闭以释放网络资源。
     *
     * @param bucketName   存储桶名称
     * @param region       区域
     * @param objectName   对象名称
     * @param matchETag    匹配的 ETag
     * @param notMatchETag 不匹配的 ETag
     * @return {@link GetObjectResponse}
     */
    public Mono<GetObjectResponse> getObject(String bucketName, String region, String objectName, String matchETag, String notMatchETag) {
        return getObject(bucketName, region, objectName, matchETag, notMatchETag, null);
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     * <p>
     * 获取对象的数据。InputStream使用后返回必须关闭以释放网络资源。
     *
     * @param bucketName    存储桶名称
     * @param region        区域
     * @param objectName    对象名称
     * @param matchETag     匹配的 ETag
     * @param notMatchETag  不匹配的 ETag
     * @param modifiedSince 某个时间以后的
     * @return {@link GetObjectResponse}
     */
    public Mono<GetObjectResponse> getObject(String bucketName, String region, String objectName, String matchETag, String notMatchETag, ZonedDateTime modifiedSince) {
        return getObject(bucketName, region, objectName, matchETag, notMatchETag, modifiedSince, null);
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     * <p>
     * 获取对象的数据。InputStream使用后返回必须关闭以释放网络资源。
     *
     * @param bucketName      存储桶名称
     * @param region          区域
     * @param objectName      对象名称
     * @param matchETag       匹配的 ETag
     * @param notMatchETag    不匹配的 ETag
     * @param modifiedSince   某个时间以后的
     * @param unmodifiedSince 某个时间以前的
     * @return {@link GetObjectResponse}
     */
    public Mono<GetObjectResponse> getObject(String bucketName, String region, String objectName, String matchETag, String notMatchETag, ZonedDateTime modifiedSince, ZonedDateTime unmodifiedSince) {
        return getObject(bucketName, region, objectName, null, null, matchETag, notMatchETag, modifiedSince, unmodifiedSince);
    }


    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     * <p>
     * 获取对象的数据。InputStream使用后返回必须关闭以释放网络资源。
     *
     * @param bucketName      存储桶名称
     * @param region          区域
     * @param objectName      对象名称
     * @param offset          偏移
     * @param length          长度
     * @param matchETag       匹配的 ETag
     * @param notMatchETag    不匹配的 ETag
     * @param modifiedSince   某个时间以后的
     * @param unmodifiedSince 某个时间以前的
     * @return {@link GetObjectResponse}
     */
    public Mono<GetObjectResponse> getObject(String bucketName, String region, String objectName, Long offset, Long length, String matchETag, String notMatchETag, ZonedDateTime modifiedSince, ZonedDateTime unmodifiedSince) {
        return getObject(bucketName, region, objectName, offset, length, matchETag, notMatchETag, modifiedSince, unmodifiedSince, null);
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     * <p>
     * 获取对象的数据。InputStream使用后返回必须关闭以释放网络资源。
     *
     * @param bucketName                      存储桶名称
     * @param region                          区域
     * @param objectName                      对象名称
     * @param offset                          偏移
     * @param length                          长度
     * @param matchETag                       匹配的 ETag
     * @param notMatchETag                    不匹配的 ETag
     * @param modifiedSince                   某个时间以后的
     * @param unmodifiedSince                 某个时间以前的
     * @param serverSideEncryptionCustomerKey 服务端加密自定义KEY，目前 Minio 仅支持 256位 AES
     * @return {@link GetObjectResponse}
     */
    public Mono<GetObjectResponse> getObject(String bucketName, String region, String objectName, Long offset, Long length, String matchETag, String notMatchETag, ZonedDateTime modifiedSince, ZonedDateTime unmodifiedSince, ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey) {
        return getObject(bucketName, region, objectName, offset, length, matchETag, notMatchETag, modifiedSince, unmodifiedSince, serverSideEncryptionCustomerKey, null);
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     * <p>
     * 获取对象的数据。InputStream使用后返回必须关闭以释放网络资源。
     *
     * @param bucketName                      存储桶名称
     * @param region                          区域
     * @param objectName                      对象名称
     * @param offset                          偏移
     * @param length                          长度
     * @param matchETag                       匹配的 ETag
     * @param notMatchETag                    不匹配的 ETag
     * @param modifiedSince                   某个时间以后的
     * @param unmodifiedSince                 某个时间以前的
     * @param serverSideEncryptionCustomerKey 服务端加密自定义KEY，目前 Minio 仅支持 256位 AES.
     * @param versionId                       版本ID
     * @return {@link GetObjectResponse}
     */
    public Mono<GetObjectResponse> getObject(String bucketName, String region, String objectName, Long offset, Long length, String matchETag, String notMatchETag, ZonedDateTime modifiedSince, ZonedDateTime unmodifiedSince, ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey, String versionId) {
        return getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .region(region)
                .object(objectName)
                .offset(offset)
                .length(length)
                .matchETag(matchETag)
                .notMatchETag(notMatchETag)
                .modifiedSince(modifiedSince)
                .unmodifiedSince(unmodifiedSince)
                .ssec(serverSideEncryptionCustomerKey)
                .versionId(versionId)
                .build());
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     * <p>
     * 获取对象的数据。InputStream使用后返回必须关闭以释放网络资源。
     *
     * @param getObjectArgs {@link GetObjectArgs}
     * @return {@link GetObjectResponse}
     */
    public Mono<GetObjectResponse> getObject(GetObjectArgs getObjectArgs) {
        return fromFuture("getObject", (client) -> client.getObject(getObjectArgs));
    }

    /**
     * 上传文件
     * <p>
     * · 添加的Object大小不能超过5 TB。
     * · 默认情况下，如果已存在同名Object且对该Object有访问权限，则新添加的Object将覆盖原有的Object，并返回200 OK。
     * · OSS没有文件夹的概念，所有资源都是以文件来存储，但您可以通过创建一个以正斜线（/）结尾，大小为0的Object来创建模拟文件夹。
     *
     * @param bucketName  存储桶名称
     * @param objectName  对象名称
     * @param stream      文件流
     * @param objectSize  对象大小
     * @param contentType 内容类型
     * @return {@link ObjectWriteResponse}
     */
    public Mono<ObjectWriteResponse> putObject(String bucketName, String objectName, InputStream stream, long objectSize, String contentType) {
        return putObject(bucketName, null, objectName, stream, objectSize, -1, contentType);
    }

    /**
     * 上传文件
     * <p>
     * · 添加的Object大小不能超过5 TB。
     * · 默认情况下，如果已存在同名Object且对该Object有访问权限，则新添加的Object将覆盖原有的Object，并返回200 OK。
     * · OSS没有文件夹的概念，所有资源都是以文件来存储，但您可以通过创建一个以正斜线（/）结尾，大小为0的Object来创建模拟文件夹。
     *
     * @param bucketName  存储桶名称
     * @param objectName  对象名称
     * @param stream      文件流
     * @param objectSize  对象大小
     * @param partSize    分片大小
     * @param contentType 内容类型
     * @return {@link ObjectWriteResponse}
     */
    public Mono<ObjectWriteResponse> putObject(String bucketName, String objectName, InputStream stream, long objectSize, long partSize, String contentType) {
        return putObject(bucketName, null, objectName, stream, objectSize, partSize, contentType);
    }

    /**
     * 上传文件
     * <p>
     * · 添加的Object大小不能超过5 TB。
     * · 默认情况下，如果已存在同名Object且对该Object有访问权限，则新添加的Object将覆盖原有的Object，并返回200 OK。
     * · OSS没有文件夹的概念，所有资源都是以文件来存储，但您可以通过创建一个以正斜线（/）结尾，大小为0的Object来创建模拟文件夹。
     *
     * @param bucketName  存储桶名称
     * @param region      区域
     * @param objectName  对象名称
     * @param stream      文件流
     * @param objectSize  对象大小
     * @param partSize    分片大小
     * @param contentType 内容类型
     * @return {@link ObjectWriteResponse}
     */
    public Mono<ObjectWriteResponse> putObject(String bucketName, String region, String objectName, InputStream stream, long objectSize, long partSize, String contentType) {
        return putObject(bucketName, region, objectName, stream, objectSize, partSize, contentType, false);
    }

    /**
     * 上传文件
     * <p>
     * · 添加的Object大小不能超过5 TB。
     * · 默认情况下，如果已存在同名Object且对该Object有访问权限，则新添加的Object将覆盖原有的Object，并返回200 OK。
     * · OSS没有文件夹的概念，所有资源都是以文件来存储，但您可以通过创建一个以正斜线（/）结尾，大小为0的Object来创建模拟文件夹。
     *
     * @param bucketName  存储桶名称
     * @param region      区域
     * @param objectName  对象名称
     * @param stream      文件流
     * @param objectSize  对象大小
     * @param partSize    分片大小
     * @param contentType 内容类型
     * @param legalHold   是否保持
     * @return {@link ObjectWriteResponse}
     */
    public Mono<ObjectWriteResponse> putObject(String bucketName, String region, String objectName, InputStream stream, long objectSize, long partSize, String contentType, boolean legalHold) {
        return putObject(bucketName, region, objectName, stream, objectSize, partSize, contentType, legalHold, null);
    }

    /**
     * 上传文件
     * <p>
     * · 添加的Object大小不能超过5 TB。
     * · 默认情况下，如果已存在同名Object且对该Object有访问权限，则新添加的Object将覆盖原有的Object，并返回200 OK。
     * · OSS没有文件夹的概念，所有资源都是以文件来存储，但您可以通过创建一个以正斜线（/）结尾，大小为0的Object来创建模拟文件夹。
     *
     * @param bucketName  存储桶名称
     * @param region      区域
     * @param objectName  对象名称
     * @param stream      文件流
     * @param objectSize  对象大小
     * @param partSize    分片大小
     * @param contentType 内容类型
     * @param legalHold   是否保持
     * @param retention   保存设置
     * @return {@link ObjectWriteResponse}
     */
    public Mono<ObjectWriteResponse> putObject(String bucketName, String region, String objectName, InputStream stream, long objectSize, long partSize, String contentType, boolean legalHold, Retention retention) {
        return putObject(bucketName, region, objectName, stream, objectSize, partSize, contentType, legalHold, retention, null);
    }

    /**
     * 上传文件
     * <p>
     * · 添加的Object大小不能超过5 TB。
     * · 默认情况下，如果已存在同名Object且对该Object有访问权限，则新添加的Object将覆盖原有的Object，并返回200 OK。
     * · OSS没有文件夹的概念，所有资源都是以文件来存储，但您可以通过创建一个以正斜线（/）结尾，大小为0的Object来创建模拟文件夹。
     *
     * @param bucketName  存储桶名称
     * @param region      区域
     * @param objectName  对象名称
     * @param stream      文件流
     * @param objectSize  对象大小
     * @param partSize    分片大小
     * @param contentType 内容类型
     * @param legalHold   是否保持
     * @param retention   保存设置
     * @param tags        标签
     * @return {@link ObjectWriteResponse}
     */
    public Mono<ObjectWriteResponse> putObject(String bucketName, String region, String objectName, InputStream stream, long objectSize, long partSize, String contentType, boolean legalHold, Retention retention, Tags tags) {
        return putObject(bucketName, region, objectName, stream, objectSize, partSize, contentType, legalHold, retention, tags, null);
    }

    /**
     * 上传文件
     * <p>
     * · 添加的Object大小不能超过5 TB。
     * · 默认情况下，如果已存在同名Object且对该Object有访问权限，则新添加的Object将覆盖原有的Object，并返回200 OK。
     * · OSS没有文件夹的概念，所有资源都是以文件来存储，但您可以通过创建一个以正斜线（/）结尾，大小为0的Object来创建模拟文件夹。
     *
     * @param bucketName  存储桶名称
     * @param region      区域
     * @param objectName  对象名称
     * @param stream      文件流
     * @param objectSize  对象大小
     * @param partSize    分片大小
     * @param contentType 内容类型
     * @param legalHold   是否保持
     * @param retention   保存设置
     * @param tags        标签
     * @param sse         服务加密
     * @return {@link ObjectWriteResponse}
     */
    public Mono<ObjectWriteResponse> putObject(String bucketName, String region, String objectName, InputStream stream, long objectSize, long partSize, String contentType, boolean legalHold, Retention retention, Tags tags, ServerSideEncryption sse) {
        return putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .region(region)
                .object(objectName)
                .stream(stream, objectSize, partSize)
                .contentType(contentType)
                .sse(sse)
                .legalHold(legalHold)
                .tags(tags)
                .retention(retention)
                .build());
    }

    /**
     * 上传文件
     * <p>
     * · 添加的Object大小不能超过5 GB。
     * · 默认情况下，如果已存在同名Object且对该Object有访问权限，则新添加的Object将覆盖原有的Object，并返回200 OK。
     * · OSS没有文件夹的概念，所有资源都是以文件来存储，但您可以通过创建一个以正斜线（/）结尾，大小为0的Object来创建模拟文件夹。
     *
     * @param putObjectArgs {@link PutObjectArgs}
     * @return {@link ObjectWriteResponse}
     */
    public Mono<ObjectWriteResponse> putObject(PutObjectArgs putObjectArgs) {
        return fromFuture("putObject", (client) -> client.putObject(putObjectArgs));
    }
}
