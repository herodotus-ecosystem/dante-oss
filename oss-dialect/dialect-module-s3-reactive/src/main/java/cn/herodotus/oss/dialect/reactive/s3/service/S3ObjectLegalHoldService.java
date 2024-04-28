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
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetObjectLegalHoldRequest;
import software.amazon.awssdk.services.s3.model.GetObjectLegalHoldResponse;
import software.amazon.awssdk.services.s3.model.PutObjectLegalHoldRequest;
import software.amazon.awssdk.services.s3.model.PutObjectLegalHoldResponse;

import java.util.function.Consumer;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/2/22 17:05
 */
@Service
public class S3ObjectLegalHoldService extends BaseS3AsyncService {
    public S3ObjectLegalHoldService(AbstractObjectPool<S3AsyncClient> objectPool) {
        super(objectPool);
    }

    public Mono<GetObjectLegalHoldResponse> getObjectLegalHold(Consumer<GetObjectLegalHoldRequest.Builder> getObjectLegalHoldRequest) {
        return fromFuture(client -> client.getObjectLegalHold(getObjectLegalHoldRequest));
    }

    public Mono<PutObjectLegalHoldResponse> putObjectLegalHold(Consumer<PutObjectLegalHoldRequest.Builder> putObjectLegalHoldRequest) {
        return fromFuture(client -> client.putObjectLegalHold(putObjectLegalHoldRequest));
    }

}
