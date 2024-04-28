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

package cn.herodotus.oss.dialect.reactive.s3.definition.service;

import cn.herodotus.oss.dialect.core.service.BaseOssService;
import cn.herodotus.stirrup.core.definition.support.AbstractObjectPool;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.s3.S3AsyncClient;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * <p>Description: Amazon S3 异步操作基础服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/14 16:57
 */
public abstract class BaseS3AsyncService extends BaseOssService<S3AsyncClient> {

    public BaseS3AsyncService(AbstractObjectPool<S3AsyncClient> objectPool) {
        super(objectPool);
    }

    protected <T> Flux<T> from(Function<S3AsyncClient, Publisher<T>> operate) {
        S3AsyncClient client = getClient();
        Publisher<T> publisher = operate.apply(client);
        close(client);
        return Flux.from(publisher);
    }

    private <T> CompletableFuture<T> template(Function<S3AsyncClient, CompletableFuture<T>> operate) {
        S3AsyncClient client = getClient();
        CompletableFuture<T> future = operate.apply(client);
        close(client);
        return future;
    }

    protected <T> Mono<T> fromFuture(Function<S3AsyncClient, CompletableFuture<T>> operate) {
        return Mono.fromFuture(template(operate));
    }
}
