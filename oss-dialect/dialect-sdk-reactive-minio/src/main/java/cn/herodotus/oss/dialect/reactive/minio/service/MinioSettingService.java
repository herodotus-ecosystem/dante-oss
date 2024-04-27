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
import io.minio.S3Base;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>Description: 设置相关操作 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/4/16 16:10
 */
@Service
public class MinioSettingService extends BaseMinioAsyncService {

    public MinioSettingService(MinioAsyncClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * Disables dual-stack endpoint for Amazon S3 endpoint.
     */
    public Mono<Void> disableDualStackEndpoint() {
        template(S3Base::disableDualStackEndpoint);
        return Mono.empty();
    }

    /**
     * Enables dual-stack endpoint for Amazon S3 endpoint.
     */
    public Mono<Void> enableDualStackEndpoint() {
        template(S3Base::enableDualStackEndpoint);
        return Mono.empty();
    }

    /**
     * Disables virtual-style endpoint
     */
    public Mono<Void> disableVirtualStyleEndpoint() {
        template(S3Base::disableVirtualStyleEndpoint);
        return Mono.empty();
    }

    /**
     * Enables virtual-style endpoint.
     */
    public Mono<Void> enableVirtualStyleEndpoint() {
        template(S3Base::enableVirtualStyleEndpoint);
        return Mono.empty();
    }

    /**
     * Sets HTTP connect, write and read timeouts. A value of 0 means no timeout, otherwise values
     * must be between 1 and Integer.MAX_VALUE when converted to milliseconds.
     *
     * <pre>Example:{@code
     * minioClient.setTimeout(TimeUnit.SECONDS.toMillis(10), TimeUnit.SECONDS.toMillis(10),
     *     TimeUnit.SECONDS.toMillis(30));
     * }</pre>
     *
     * @param connectTimeout HTTP connect timeout in milliseconds.
     * @param writeTimeout   HTTP write timeout in milliseconds.
     * @param readTimeout    HTTP read timeout in milliseconds.
     */
    public Mono<Void> setTimeout(long connectTimeout, long writeTimeout, long readTimeout) {
        template(client -> client.setTimeout(connectTimeout, writeTimeout, readTimeout));
        return Mono.empty();
    }

    /**
     * Sets application's name/version to user agent. For more information about user agent refer <a
     * href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html">#rfc2616</a>.
     *
     * @param name    Your application name.
     * @param version Your application version.
     */
    public Mono<Void> setAppInfo(String name, String version) {
        template(client -> client.setAppInfo(name, version));
        return Mono.empty();
    }
}
