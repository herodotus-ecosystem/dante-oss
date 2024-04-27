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

import cn.herodotus.oss.dialect.reactive.s3.definition.service.BaseS3AsyncService;
import cn.herodotus.stirrup.core.definition.support.AbstractObjectPool;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetBucketAccelerateConfigurationRequest;
import software.amazon.awssdk.services.s3.model.GetBucketAccelerateConfigurationResponse;
import software.amazon.awssdk.services.s3.model.PutBucketAccelerateConfigurationRequest;
import software.amazon.awssdk.services.s3.model.PutBucketAccelerateConfigurationResponse;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/2/22 17:00
 */
@Service
public class S3BucketAccelerateConfigurationService extends BaseS3AsyncService {

    public S3BucketAccelerateConfigurationService(AbstractObjectPool<S3AsyncClient> objectPool) {
        super(objectPool);
    }

    public Mono<GetBucketAccelerateConfigurationResponse> getBucketAccelerateConfiguration(GetBucketAccelerateConfigurationRequest getBucketAccelerateConfigurationRequest) {
        return fromFuture(client -> client.getBucketAccelerateConfiguration(getBucketAccelerateConfigurationRequest));
    }

    public Mono<PutBucketAccelerateConfigurationResponse> putBucketAccelerateConfiguration(PutBucketAccelerateConfigurationRequest putBucketAccelerateConfigurationRequest) {
        return fromFuture(client -> client.putBucketAccelerateConfiguration(putBucketAccelerateConfigurationRequest));
    }
}
