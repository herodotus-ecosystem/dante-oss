/*
 * Copyright (c) 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante OSS 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/herodotus/dante-oss>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/herodotus/dante-oss>
 * 6.若您的项目无法满足以上几点，可申请商业授权
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
