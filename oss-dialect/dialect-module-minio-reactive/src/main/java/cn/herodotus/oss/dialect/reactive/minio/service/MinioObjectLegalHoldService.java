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
import io.minio.DisableObjectLegalHoldArgs;
import io.minio.EnableObjectLegalHoldArgs;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>Description: Object 合法持有 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/11 9:46
 */
@Service
public class MinioObjectLegalHoldService extends BaseMinioAsyncService {

    public MinioObjectLegalHoldService(MinioAsyncClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 启用对对象的合法保留
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     */
    public Mono<Void> enableObjectLegalHold(String bucketName, String objectName) {
        return enableObjectLegalHold(bucketName, null, objectName);
    }

    /**
     * 启用对对象的合法保留
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     */
    public Mono<Void> enableObjectLegalHold(String bucketName, String region, String objectName) {
        return enableObjectLegalHold(bucketName, region, objectName, null);
    }

    /**
     * 启用对对象的合法保留
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param versionId  版本ID
     */
    public Mono<Void> enableObjectLegalHold(String bucketName, String region, String objectName, String versionId) {
        return enableObjectLegalHold(EnableObjectLegalHoldArgs.builder().bucket(bucketName).region(region).object(objectName).versionId(versionId).build());
    }

    /**
     * 启用对对象的合法保留
     *
     * @param enableObjectLegalHoldArgs {@link EnableObjectLegalHoldArgs}
     */
    public Mono<Void> enableObjectLegalHold(EnableObjectLegalHoldArgs enableObjectLegalHoldArgs) {
        return fromFuture("enableObjectLegalHold", (client) -> client.enableObjectLegalHold(enableObjectLegalHoldArgs));
    }

    /**
     * 禁用对对象的合法保留。
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     */
    public Mono<Void> disableObjectLegalHold(String bucketName, String objectName) {
        return disableObjectLegalHold(bucketName, null, objectName);
    }

    /**
     * 禁用对对象的合法保留。
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     */
    public Mono<Void> disableObjectLegalHold(String bucketName, String region, String objectName) {
        return disableObjectLegalHold(bucketName, region, objectName, null);
    }

    /**
     * 禁用对对象的合法保留。
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param versionId  版本ID
     */
    public Mono<Void> disableObjectLegalHold(String bucketName, String region, String objectName, String versionId) {
        return disableObjectLegalHold(DisableObjectLegalHoldArgs.builder().bucket(bucketName).region(region).object(objectName).versionId(versionId).build());
    }

    /**
     * 禁用对对象的合法保留。
     *
     * @param disableObjectLegalHoldArgs {@link DisableObjectLegalHoldArgs}
     */
    public Mono<Void> disableObjectLegalHold(DisableObjectLegalHoldArgs disableObjectLegalHoldArgs) {
        return fromFuture("disableObjectLegalHold", (client) -> client.disableObjectLegalHold(disableObjectLegalHoldArgs));
    }
}
