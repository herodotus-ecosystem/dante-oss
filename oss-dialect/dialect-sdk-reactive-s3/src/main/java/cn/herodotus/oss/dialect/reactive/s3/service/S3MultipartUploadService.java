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
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/2/22 16:46
 */
@Service
public class S3MultipartUploadService extends BaseS3AsyncService {

    public S3MultipartUploadService(AbstractObjectPool<S3AsyncClient> objectPool) {
        super(objectPool);
    }

    public Mono<AbortMultipartUploadResponse> abortMultipartUpload(AbortMultipartUploadRequest abortMultipartUploadRequest) {
        return fromFuture(client -> client.abortMultipartUpload(abortMultipartUploadRequest));
    }

    public Mono<CompleteMultipartUploadResponse> completeMultipartUpload(CompleteMultipartUploadRequest completeMultipartUploadRequest) {
        return fromFuture(client -> client.completeMultipartUpload(completeMultipartUploadRequest));
    }

    public Mono<ListMultipartUploadsResponse> listMultipartUploads(ListMultipartUploadsRequest listMultipartUploadsRequest) {
        return fromFuture(client -> client.listMultipartUploads(listMultipartUploadsRequest));
    }

    public Flux<ListMultipartUploadsResponse> listMultipartUploadsPaginator(ListMultipartUploadsRequest listMultipartUploadsRequest) {
        return from(client -> client.listMultipartUploadsPaginator(listMultipartUploadsRequest));
    }

    public Mono<ListPartsResponse> listParts(ListPartsRequest listPartsRequest) {
        return fromFuture(client -> client.listParts(listPartsRequest));
    }

    public Flux<ListPartsResponse> listPartsPaginator(ListPartsRequest listPartsRequest) {
        return from(client -> client.listPartsPaginator(listPartsRequest));
    }

    public Mono<UploadPartResponse> uploadPart(UploadPartRequest uploadPartRequest, Path sourcePath) {
        return fromFuture(client -> client.uploadPart(uploadPartRequest, sourcePath));
    }

    public Mono<UploadPartCopyResponse> uploadPartCopy(UploadPartCopyRequest uploadPartCopyRequest) {
        return fromFuture(client -> client.uploadPartCopy(uploadPartCopyRequest));
    }
}
