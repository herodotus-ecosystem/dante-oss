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

import cn.herodotus.oss.dialect.core.exception.*;
import cn.herodotus.oss.dialect.core.service.BaseOssService;
import cn.herodotus.oss.dialect.reactive.minio.definition.function.MinioAdminConsumer;
import cn.herodotus.oss.dialect.reactive.minio.definition.function.MinioAdminFunction;
import cn.herodotus.stirrup.core.definition.support.AbstractObjectPool;
import io.minio.admin.MinioAdminClient;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.ConnectException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>Description: Minio Admin 基础服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/25 10:37
 */
public abstract class BaseMinioAdminService extends BaseOssService<MinioAdminClient> {

    private static final Logger log = LoggerFactory.getLogger(BaseMinioAdminService.class);

    public BaseMinioAdminService(AbstractObjectPool<MinioAdminClient> ossClientObjectPool) {
        super(ossClientObjectPool);
    }

    private <T> T template(String name, MinioAdminFunction<MinioAdminClient, T> action) {
        MinioAdminClient client = getClient();

        try {
            return action.apply(client);
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", name, e);
            throw new OssNoSuchAlgorithmException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", name, e);
            throw new OssInvalidKeyException(e.getMessage());
        } catch (InvalidCipherTextException e) {
            log.error("[Herodotus] |- Minio catch InvalidCipherTextException in [{}].", name, e);
            throw new OssInvalidCipherTextException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", name, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } finally {
            close(client);
        }
    }

    private void template(String name, MinioAdminConsumer<MinioAdminClient> action) {
        MinioAdminClient client = getClient();
        try {
            action.accept(client);
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", name, e);
            throw new OssNoSuchAlgorithmException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", name, e);
            throw new OssInvalidKeyException(e.getMessage());
        } catch (InvalidCipherTextException e) {
            log.error("[Herodotus] |- Minio catch InvalidCipherTextException in [{}].", name, e);
            throw new OssInvalidCipherTextException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", name, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } finally {
            close(client);
        }
    }

    protected <T> Mono<T> just(String function, MinioAdminFunction<MinioAdminClient, T> action) {
        return Mono.just(template(function, action));
    }

    protected Mono<Void> empty(String function, MinioAdminConsumer<MinioAdminClient> action) {
        template(function, action);
        return Mono.empty();
    }
}
