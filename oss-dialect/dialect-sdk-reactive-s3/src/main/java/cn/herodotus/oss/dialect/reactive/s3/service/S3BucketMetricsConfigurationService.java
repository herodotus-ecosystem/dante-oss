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
import software.amazon.awssdk.services.s3.model.*;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/2/22 16:56
 */
@Service
public class S3BucketMetricsConfigurationService extends BaseS3AsyncService {
    public S3BucketMetricsConfigurationService(AbstractObjectPool<S3AsyncClient> objectPool) {
        super(objectPool);
    }

    public Mono<DeleteBucketMetricsConfigurationResponse> deleteBucketMetricsConfiguration(DeleteBucketMetricsConfigurationRequest deleteBucketMetricsConfigurationRequest) {
        return fromFuture(client -> client.deleteBucketMetricsConfiguration(deleteBucketMetricsConfigurationRequest));
    }

    public Mono<GetBucketMetricsConfigurationResponse> getBucketMetricsConfiguration(GetBucketMetricsConfigurationRequest getBucketMetricsConfigurationRequest) {
        return fromFuture(client -> client.getBucketMetricsConfiguration(getBucketMetricsConfigurationRequest));
    }

    public Mono<ListBucketMetricsConfigurationsResponse> listBucketMetricsConfigurations(ListBucketMetricsConfigurationsRequest listBucketMetricsConfigurationsRequest) {
        return fromFuture(client -> client.listBucketMetricsConfigurations(listBucketMetricsConfigurationsRequest));
    }

    public Mono<PutBucketMetricsConfigurationResponse> putBucketMetricsConfiguration(PutBucketMetricsConfigurationRequest putBucketMetricsConfigurationRequest) {
        return fromFuture(client -> client.putBucketMetricsConfiguration(putBucketMetricsConfigurationRequest));
    }

}
