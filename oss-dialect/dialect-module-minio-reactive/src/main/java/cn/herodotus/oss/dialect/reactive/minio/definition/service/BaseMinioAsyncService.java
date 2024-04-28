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

package cn.herodotus.oss.dialect.reactive.minio.definition.service;

import cn.herodotus.oss.core.minio.definition.pool.MinioAsyncClient;
import cn.herodotus.oss.dialect.core.exception.*;
import cn.herodotus.oss.dialect.core.service.BaseOssService;
import cn.herodotus.oss.dialect.reactive.minio.definition.function.MinioAsyncFunction;
import cn.herodotus.oss.dialect.reactive.minio.definition.function.MinioSyncFunction;
import cn.herodotus.stirrup.core.definition.support.AbstractObjectPool;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import io.minio.errors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.ConnectException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * <p>Description: Minio 基础异步服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/3 20:42
 */
public abstract class BaseMinioAsyncService extends BaseOssService<MinioAsyncClient> {

    private static final Logger log = LoggerFactory.getLogger(BaseMinioAsyncService.class);

    public BaseMinioAsyncService(AbstractObjectPool<MinioAsyncClient> objectPool) {
        super(objectPool);
    }

    private <T> CompletableFuture<T> template(String name, MinioAsyncFunction<MinioAsyncClient, CompletableFuture<T>> operate) {

        MinioAsyncClient client = getClient();

        try {
            return operate.apply(client);
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", name, e);
            throw new OssInsufficientDataException(e.getMessage());
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", name, e);
            throw new OssInternalException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", name, e);
            throw new OssInvalidKeyException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", name, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", name, e);
            throw new OssNoSuchAlgorithmException(e.getMessage());
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in in [{}].", name, e);
            throw new OssXmlParserException(e.getMessage());
        } finally {
            close(client);
        }
    }

    protected <T> Mono<T> fromFuture(String name, MinioAsyncFunction<MinioAsyncClient, CompletableFuture<T>> operate) {
        return Mono.fromFuture(template(name, operate));
    }

    private <T> T template(String name, MinioSyncFunction<MinioAsyncClient, T> operate) {

        MinioAsyncClient client = getClient();

        try {
            return operate.apply(client);
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio catch ErrorResponseException in [{}].", name, e);
            throw new OssErrorResponseException(e.getMessage());
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", name, e);
            throw new OssInsufficientDataException(e.getMessage());
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", name, e);
            throw new OssInternalException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", name, e);
            throw new OssInvalidKeyException(e.getMessage());
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", name, e);
            throw new OssInvalidResponseException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", name, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", name, e);
            throw new OssNoSuchAlgorithmException(e.getMessage());
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", name, e);
            throw new OssServerException(e.getMessage());
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in [{}].", name, e);
            throw new OssXmlParserException(e.getMessage());
        } finally {
            close(client);
        }
    }

    protected <T> Mono<T> just(String name, MinioSyncFunction<MinioAsyncClient, T> operate) {
        return Mono.just(template(name, operate));
    }

    protected void template(Consumer<MinioAsyncClient> operate) {
        MinioAsyncClient client = getClient();
        operate.accept(client);
        close(client);
    }

    protected Multimap<String, String> toMultimap(Map<String, String> map) {
        Multimap<String, String> multimap = HashMultimap.create();
        if (map != null) {
            multimap.putAll(Multimaps.forMap(map));
        }
        return Multimaps.unmodifiableMultimap(multimap);
    }
}
