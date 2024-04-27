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

package cn.herodotus.oss.dialect.reactive.s3.service;

import cn.herodotus.stirrup.core.definition.support.AbstractObjectPool;
import cn.herodotus.oss.dialect.reactive.s3.definition.service.BaseS3AsyncService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.*;

import java.nio.file.Path;

/**
 * <p>Description: S3 对象服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/2/22 16:47
 */
@Service
public class S3ObjectService extends BaseS3AsyncService {

    public S3ObjectService(AbstractObjectPool<S3AsyncClient> objectPool) {
        super(objectPool);
    }

    public Mono<CopyObjectResponse> copyObject(CopyObjectRequest request) {
        return fromFuture(client -> client.copyObject(request));
    }

    public Mono<DeleteObjectResponse> deleteObject(DeleteObjectRequest deleteObjectRequest) {
        return fromFuture(client -> client.deleteObject(deleteObjectRequest));
    }

    public Mono<DeleteObjectsResponse> deleteObjects(DeleteObjectsRequest deleteObjectsRequest) {
        return fromFuture(client -> client.deleteObjects(deleteObjectsRequest));
    }

    public Mono<GetObjectResponse> getObject(GetObjectRequest getObjectRequest, Path destinationPath) {
        return fromFuture(client -> client.getObject(getObjectRequest, destinationPath));
    }

    public Mono<ListObjectsResponse> listObjects(ListObjectsRequest listObjectsRequest) {
        return fromFuture(client -> client.listObjects(listObjectsRequest));
    }

    public Mono<ListObjectsV2Response> listObjectsV2(ListObjectsV2Request listObjectsV2Request) {
        return fromFuture(client -> client.listObjectsV2(listObjectsV2Request));
    }

    public Flux<ListObjectsV2Response> listObjectsV2Paginator(ListObjectsV2Request listObjectsV2Request) {
        return from(client -> client.listObjectsV2Paginator(listObjectsV2Request));
    }

    public Mono<ListObjectVersionsResponse> listObjectVersions(ListObjectVersionsRequest listObjectVersionsRequest) {
        return fromFuture(client -> client.listObjectVersions(listObjectVersionsRequest));
    }

    public Flux<ListObjectVersionsResponse> listObjectVersionsPaginator(ListObjectVersionsRequest listObjectVersionsRequest) {
        return from(client -> client.listObjectVersionsPaginator(listObjectVersionsRequest));
    }

    public Mono<PutObjectResponse> putObject(PutObjectRequest putObjectRequest, Path sourcePath) {
        return fromFuture(client -> client.putObject(putObjectRequest, sourcePath));
    }

    public Mono<RestoreObjectResponse> restoreObject(RestoreObjectRequest restoreObjectRequest) {
        return fromFuture(client -> client.restoreObject(restoreObjectRequest));
    }

    public Mono<Void> selectObjectContent(SelectObjectContentRequest selectObjectContentRequest, SelectObjectContentResponseHandler asyncResponseHandler) {
        return fromFuture(client -> client.selectObjectContent(selectObjectContentRequest, asyncResponseHandler));
    }

    public Mono<WriteGetObjectResponseResponse> writeGetObjectResponse(WriteGetObjectResponseRequest writeGetObjectResponseRequest, Path sourcePath) {
        return fromFuture(client -> client.writeGetObjectResponse(writeGetObjectResponseRequest, sourcePath));
    }
}
