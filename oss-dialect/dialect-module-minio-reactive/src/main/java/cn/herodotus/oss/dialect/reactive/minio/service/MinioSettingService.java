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
