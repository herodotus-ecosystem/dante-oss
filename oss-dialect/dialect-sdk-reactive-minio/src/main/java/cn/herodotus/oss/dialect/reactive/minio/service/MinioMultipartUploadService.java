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

import cn.herodotus.stirrup.core.definition.constants.SymbolConstants;
import cn.herodotus.oss.core.minio.definition.pool.MinioAsyncClientObjectPool;
import cn.herodotus.oss.dialect.reactive.minio.definition.service.BaseMinioAsyncService;
import io.minio.*;
import io.minio.messages.Part;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * <p>Description: 分片上传服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/3 20:45
 */
@Service
public class MinioMultipartUploadService extends BaseMinioAsyncService {

    public MinioMultipartUploadService(MinioAsyncClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 创建分片上传
     *
     * @param bucketName 存储桶名称.
     * @param objectName 对象名称.
     * @return 创建分片上传响应对象 {@link CreateMultipartUploadResponse}
     */
    public Mono<CreateMultipartUploadResponse> createMultipartUpload(String bucketName, String objectName) {
        return createMultipartUpload(bucketName, null, objectName);
    }

    /**
     * 创建分片上传
     *
     * @param bucketName 存储桶名称.
     * @param region     区域 (可选).
     * @param objectName 对象名称.
     * @return 创建分片上传响应对象 {@link CreateMultipartUploadResponse}
     */
    public Mono<CreateMultipartUploadResponse> createMultipartUpload(String bucketName, String region, String objectName) {
        return createMultipartUpload(bucketName, region, objectName, null, null);
    }

    /**
     * 创建分片上传
     *
     * @param bucketName       存储桶名称.
     * @param region           区域 (可选).
     * @param objectName       对象名称.
     * @param extraHeaders     额外消息头 (可选).
     * @param extraQueryParams 额外查询参数 (可选).
     * @return 创建分片上传响应对象 {@link CreateMultipartUploadResponse}
     */
    public Mono<CreateMultipartUploadResponse> createMultipartUpload(String bucketName, String region, String objectName, Map<String, String> extraHeaders, Map<String, String> extraQueryParams) {
        return fromFuture("createMultipartUpload", (client) -> client.createMultipartUploadAsync(bucketName, region, objectName, toMultimap(extraHeaders), toMultimap(extraQueryParams)));
    }


    /**
     * 上传分片传
     *
     * @param bucketName 存储桶名称.
     * @param objectName 对象名称.
     * @param data       Object data must be InputStream, RandomAccessFile, byte[] or String.
     * @param length     上传对象数据长度.
     * @param uploadId   上传 ID.
     * @param partNumber 分片序号.
     * @return 上传分片传响应对象 {@link UploadPartResponse}
     */
    public Mono<UploadPartResponse> uploadPart(String bucketName, String objectName, Object data, long length, String uploadId, int partNumber) {
        return uploadPart(bucketName, null, objectName, data, length, uploadId, partNumber);
    }

    /**
     * 上传分片传
     *
     * @param bucketName 存储桶名称.
     * @param region     区域 (可选).
     * @param objectName 对象名称.
     * @param data       Object data must be InputStream, RandomAccessFile, byte[] or String.
     * @param length     上传对象数据长度.
     * @param uploadId   上传 ID.
     * @param partNumber 分片序号.
     * @return 上传分片传响应对象 {@link UploadPartResponse}
     */
    public Mono<UploadPartResponse> uploadPart(String bucketName, String region, String objectName, Object data, long length, String uploadId, int partNumber) {
        return uploadPart(bucketName, region, objectName, data, length, uploadId, partNumber, null, null);
    }

    /**
     * 上传分片传
     *
     * @param bucketName       存储桶名称.
     * @param region           区域 (可选).
     * @param objectName       对象名称.
     * @param data             Object data must be InputStream, RandomAccessFile, byte[] or String.
     * @param length           上传对象数据长度.
     * @param uploadId         上传 ID.
     * @param partNumber       分片序号.
     * @param extraHeaders     额外消息头 (可选).
     * @param extraQueryParams 额外查询参数 (可选).
     * @return 上传分片传响应对象 {@link UploadPartResponse}
     */
    public Mono<UploadPartResponse> uploadPart(String bucketName, String region, String objectName, Object data, long length, String uploadId, int partNumber, Map<String, String> extraHeaders, Map<String, String> extraQueryParams) {
        return fromFuture("uploadPart", (client) -> client.uploadPartAsync(bucketName, region, objectName, data, length, uploadId, partNumber, toMultimap(extraHeaders), toMultimap(extraQueryParams)));
    }

    /**
     * 上传分片拷贝
     *
     * @param bucketName 存储桶名称.
     * @param objectName 对象名称.
     * @param uploadId   上传 ID.
     * @param partNumber 分片序号.
     * @return 上传分片拷贝传响应对象 {@link UploadPartCopyResponse}
     */
    public Mono<UploadPartCopyResponse> uploadPartCopy(String bucketName, String objectName, String uploadId, int partNumber) {
        return uploadPartCopy(bucketName, null, objectName, uploadId, partNumber);
    }

    /**
     * 上传分片拷贝
     *
     * @param bucketName 存储桶名称.
     * @param region     区域 (可选).
     * @param objectName 对象名称.
     * @param uploadId   上传 ID.
     * @param partNumber 分片序号.
     * @return 上传分片拷贝传响应对象 {@link UploadPartCopyResponse}
     */
    public Mono<UploadPartCopyResponse> uploadPartCopy(String bucketName, String region, String objectName, String uploadId, int partNumber) {
        return uploadPartCopy(bucketName, region, objectName, uploadId, partNumber, null, null);
    }

    /**
     * 上传分片拷贝
     *
     * @param bucketName       存储桶名称.
     * @param region           区域 (可选).
     * @param objectName       对象名称.
     * @param uploadId         上传 ID.
     * @param partNumber       分片序号.
     * @param extraHeaders     额外消息头 (可选).
     * @param extraQueryParams 额外查询参数 (可选).
     * @return 上传分片拷贝传响应对象 {@link UploadPartCopyResponse}
     */
    public Mono<UploadPartCopyResponse> uploadPartCopy(String bucketName, String region, String objectName, String uploadId, int partNumber, Map<String, String> extraHeaders, Map<String, String> extraQueryParams) {
        return fromFuture("uploadPartCopy", (client) -> client.uploadPartCopyAsync(bucketName, region, objectName, uploadId, partNumber, toMultimap(extraHeaders), toMultimap(extraQueryParams)));
    }

    /**
     * 中止分片上传
     *
     * @param bucketName 存储桶名称.
     * @param objectName 对象名称.
     * @param uploadId   上传 ID.
     * @return 完成分片上传响应对象 {@link AbortMultipartUploadResponse}
     */
    public Mono<AbortMultipartUploadResponse> abortMultipartUpload(String bucketName, String objectName, String uploadId) {
        return abortMultipartUpload(bucketName, null, objectName, uploadId);
    }

    /**
     * 中止分片上传
     *
     * @param bucketName 存储桶名称.
     * @param region     区域 (可选).
     * @param objectName 对象名称.
     * @param uploadId   上传 ID.
     * @return 完成分片上传响应对象 {@link AbortMultipartUploadResponse}
     */
    public Mono<AbortMultipartUploadResponse> abortMultipartUpload(String bucketName, String region, String objectName, String uploadId) {
        return abortMultipartUpload(bucketName, region, objectName, uploadId, null, null);
    }

    /**
     * 中止分片上传
     *
     * @param bucketName       存储桶名称.
     * @param region           区域 (可选).
     * @param objectName       对象名称.
     * @param uploadId         上传 ID.
     * @param extraHeaders     额外消息头 (可选).
     * @param extraQueryParams 额外查询参数 (可选).
     * @return 完成分片上传响应对象 {@link AbortMultipartUploadResponse}
     */
    public Mono<AbortMultipartUploadResponse> abortMultipartUpload(String bucketName, String region, String objectName, String uploadId, Map<String, String> extraHeaders, Map<String, String> extraQueryParams) {
        return fromFuture("abortMultipartUpload", (client) -> client.abortMultipartUploadAsync(bucketName, region, objectName, uploadId, toMultimap(extraHeaders), toMultimap(extraQueryParams)));
    }

    /**
     * 完成分片上传
     *
     * @param bucketName 存储桶名称.
     * @param objectName 对象名称.
     * @param uploadId   上传 ID.
     * @param parts      分片数组.
     * @return 完成分片上传响应对象 {@link ObjectWriteResponse}
     */
    public Mono<ObjectWriteResponse> completeMultipartUpload(String bucketName, String objectName, String uploadId, Part[] parts) {
        return completeMultipartUpload(bucketName, null, objectName, uploadId, parts);
    }

    /**
     * 完成分片上传
     *
     * @param bucketName 存储桶名称.
     * @param region     区域 (可选).
     * @param objectName 对象名称.
     * @param uploadId   上传 ID.
     * @param parts      分片数组.
     * @return 完成分片上传响应对象 {@link ObjectWriteResponse}
     */
    public Mono<ObjectWriteResponse> completeMultipartUpload(String bucketName, String region, String objectName, String uploadId, Part[] parts) {
        return completeMultipartUpload(bucketName, region, objectName, uploadId, parts, null, null);
    }

    /**
     * 完成分片上传
     *
     * @param bucketName       存储桶名称.
     * @param region           区域 (可选).
     * @param objectName       对象名称.
     * @param uploadId         上传 ID.
     * @param parts            分片数组.
     * @param extraHeaders     额外消息头 (可选).
     * @param extraQueryParams 额外查询参数 (可选).
     * @return 完成分片上传响应对象 {@link ObjectWriteResponse}
     */
    public Mono<ObjectWriteResponse> completeMultipartUpload(String bucketName, String region, String objectName, String uploadId, Part[] parts, Map<String, String> extraHeaders, Map<String, String> extraQueryParams) {
        return fromFuture("completeMultipartUpload", (client) -> client.completeMultipartUploadAsync(bucketName, region, objectName, uploadId, parts, toMultimap(extraHeaders), toMultimap(extraQueryParams)));
    }

    /**
     * 列出分片
     *
     * @param bucketName 存储桶名称.
     * @param objectName 对象名称.
     * @param uploadId   上传 ID.
     * @return 列出分片响应对象 {@link ListPartsResponse}
     */
    public Mono<ListPartsResponse> listParts(String bucketName, String objectName, String uploadId) {
        return listParts(bucketName, objectName, null, uploadId);
    }

    /**
     * 列出分片
     *
     * @param bucketName       存储桶名称.
     * @param objectName       对象名称.
     * @param partNumberMarker 分片序号标记 (可选).
     * @param uploadId         上传 ID.
     * @return 列出分片响应对象 {@link ListPartsResponse}
     */
    public Mono<ListPartsResponse> listParts(String bucketName, String objectName, Integer partNumberMarker, String uploadId) {
        return listParts(bucketName, objectName, null, partNumberMarker, uploadId);
    }

    /**
     * 列出分片
     *
     * @param bucketName       存储桶名称.
     * @param objectName       对象名称.
     * @param maxParts         可以获取的最大分片书 (可选).
     * @param partNumberMarker 分片序号标记 (可选).
     * @param uploadId         上传 ID.
     * @return 列出分片响应对象 {@link ListPartsResponse}
     */
    public Mono<ListPartsResponse> listParts(String bucketName, String objectName, Integer maxParts, Integer partNumberMarker, String uploadId) {
        return listParts(bucketName, null, objectName, maxParts, partNumberMarker, uploadId);
    }

    /**
     * 列出分片
     *
     * @param bucketName       存储桶名称.
     * @param region           区域 (可选).
     * @param objectName       对象名称.
     * @param maxParts         可以获取的最大分片书 (可选).
     * @param partNumberMarker 分片序号标记 (可选).
     * @param uploadId         上传 ID.
     * @return 列出分片响应对象 {@link ListPartsResponse}
     */
    public Mono<ListPartsResponse> listParts(String bucketName, String region, String objectName, Integer maxParts, Integer partNumberMarker, String uploadId) {
        return listParts(bucketName, region, objectName, maxParts, partNumberMarker, uploadId, null, null);
    }

    /**
     * 列出分片
     *
     * @param bucketName       存储桶名称.
     * @param region           区域 (可选).
     * @param objectName       对象名称.
     * @param maxParts         可以获取的最大分片书 (可选).
     * @param partNumberMarker 分片序号标记 (可选).
     * @param uploadId         上传 ID.
     * @param extraHeaders     额外消息头 (可选).
     * @param extraQueryParams 额外查询参数 (可选).
     * @return 列出分片响应对象 {@link ListPartsResponse}
     */
    public Mono<ListPartsResponse> listParts(String bucketName, String region, String objectName, Integer maxParts, Integer partNumberMarker, String uploadId, Map<String, String> extraHeaders, Map<String, String> extraQueryParams) {
        return fromFuture("listParts", (client) -> client.listPartsAsync(bucketName, region, objectName, maxParts, partNumberMarker, uploadId, toMultimap(extraHeaders), toMultimap(extraQueryParams)));
    }

    /**
     * 列出正在进行的分片上传
     *
     * @param bucketName Name of the bucket.
     * @param delimiter  分隔符 (可选).
     * @param maxUploads 最大上传数量 (可选).
     * @param prefix     前缀 (可选).
     * @return 列出正在进行的分片上传响应对象 {@link ListMultipartUploadsResponse}
     */
    public Mono<ListMultipartUploadsResponse> listMultipartUploads(String bucketName, String delimiter, Integer maxUploads, String prefix) {
        return listMultipartUploads(bucketName, delimiter, null, maxUploads, prefix, null);
    }

    /**
     * 列出正在进行的分片上传
     *
     * @param bucketName Name of the bucket.
     * @return 列出正在进行的分片上传响应对象 {@link ListMultipartUploadsResponse}
     */
    public Mono<ListMultipartUploadsResponse> listMultipartUploads(String bucketName) {
        return listMultipartUploads(bucketName, null);
    }

    /**
     * 列出正在进行的分片上传
     *
     * @param bucketName Name of the bucket.
     * @param prefix     前缀 (可选).
     * @return 列出正在进行的分片上传响应对象 {@link ListMultipartUploadsResponse}
     */
    public Mono<ListMultipartUploadsResponse> listMultipartUploads(String bucketName, String prefix) {
        return listMultipartUploads(bucketName, SymbolConstants.FORWARD_SLASH, prefix);
    }

    /**
     * 列出正在进行的分片上传
     *
     * @param bucketName Name of the bucket.
     * @param delimiter  分隔符 (可选).
     * @param prefix     前缀 (可选).
     * @return 列出正在进行的分片上传响应对象 {@link ListMultipartUploadsResponse}
     */
    public Mono<ListMultipartUploadsResponse> listMultipartUploads(String bucketName, String delimiter, String prefix) {
        return listMultipartUploads(bucketName, delimiter, null, prefix);
    }

    /**
     * 列出正在进行的分片上传
     *
     * @param bucketName     Name of the bucket.
     * @param delimiter      分隔符 (可选).
     * @param keyMarker      关键标记 (可选).
     * @param maxUploads     最大上传数量 (可选).
     * @param prefix         前缀 (可选).
     * @param uploadIdMarker Upload ID 标记 (可选).
     * @return 列出正在进行的分片上传响应对象 {@link ListMultipartUploadsResponse}
     */
    public Mono<ListMultipartUploadsResponse> listMultipartUploads(String bucketName, String delimiter, String keyMarker, Integer maxUploads, String prefix, String uploadIdMarker) {
        return listMultipartUploads(bucketName, delimiter, null, keyMarker, maxUploads, prefix, uploadIdMarker);
    }

    /**
     * 列出正在进行的分片上传
     *
     * @param bucketName     Name of the bucket.
     * @param delimiter      分隔符 (可选).
     * @param encodingType   编码类型 (可选).
     * @param keyMarker      关键标记 (可选).
     * @param maxUploads     最大上传数量 (可选).
     * @param prefix         前缀 (可选).
     * @param uploadIdMarker Upload ID 标记 (可选).
     * @return 列出正在进行的分片上传响应对象 {@link ListMultipartUploadsResponse}
     */
    public Mono<ListMultipartUploadsResponse> listMultipartUploads(String bucketName, String delimiter, String encodingType, String keyMarker, Integer maxUploads, String prefix, String uploadIdMarker) {
        return listMultipartUploads(bucketName, null, delimiter, encodingType, keyMarker, maxUploads, prefix, uploadIdMarker);
    }

    /**
     * 列出正在进行的分片上传
     *
     * @param bucketName     Name of the bucket.
     * @param region         Region of the bucket (可选).
     * @param delimiter      分隔符 (可选).
     * @param encodingType   编码类型 (可选).
     * @param keyMarker      关键标记 (可选).
     * @param maxUploads     最大上传数量 (可选).
     * @param prefix         前缀 (可选).
     * @param uploadIdMarker Upload ID 标记 (可选).
     * @return 列出正在进行的分片上传响应对象 {@link ListMultipartUploadsResponse}
     */
    public Mono<ListMultipartUploadsResponse> listMultipartUploads(String bucketName, String region, String delimiter, String encodingType, String keyMarker, Integer maxUploads, String prefix, String uploadIdMarker) {
        return listMultipartUploads(bucketName, region, delimiter, encodingType, keyMarker, maxUploads, prefix, uploadIdMarker, null, null);
    }

    /**
     * 列出正在进行的分片上传
     *
     * @param bucketName       Name of the bucket.
     * @param region           Region of the bucket (可选).
     * @param delimiter        分隔符 (可选).
     * @param encodingType     编码类型 (可选).
     * @param keyMarker        关键标记 (可选).
     * @param maxUploads       最大上传数量 (可选).
     * @param prefix           前缀 (可选).
     * @param uploadIdMarker   Upload ID 标记 (可选).
     * @param extraHeaders     额外消息头 (可选).
     * @param extraQueryParams 额外查询参数 (可选).
     * @return 列出正在进行的分片上传响应对象 {@link ListMultipartUploadsResponse}
     */
    public Mono<ListMultipartUploadsResponse> listMultipartUploads(String bucketName, String region, String delimiter, String encodingType, String keyMarker, Integer maxUploads, String prefix, String uploadIdMarker, Map<String, String> extraHeaders, Map<String, String> extraQueryParams) {
        return fromFuture("listMultipartUploads", (client) -> client.listMultipartUploadsAsync(bucketName, region, delimiter, encodingType, keyMarker, maxUploads, prefix, uploadIdMarker, toMultimap(extraHeaders), toMultimap(extraQueryParams)));
    }
}
