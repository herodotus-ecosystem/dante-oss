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
import io.minio.DeleteObjectLockConfigurationArgs;
import io.minio.GetObjectLockConfigurationArgs;
import io.minio.SetObjectLockConfigurationArgs;
import io.minio.messages.ObjectLockConfiguration;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>Description: Minio 对象锁定配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 16:04
 */
@Service
public class MinioObjectLockConfigurationService extends BaseMinioAsyncService {

    public MinioObjectLockConfigurationService(MinioAsyncClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 获取对象锁定配置
     *
     * @param bucketName 存储桶名称
     * @return {@link ObjectLockConfiguration}
     */
    public Mono<ObjectLockConfiguration> getObjectLockConfiguration(String bucketName) {
        return getObjectLockConfiguration(bucketName, null);
    }

    /**
     * 获取对象锁定配置
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @return {@link ObjectLockConfiguration}
     */
    public Mono<ObjectLockConfiguration> getObjectLockConfiguration(String bucketName, String region) {
        return getObjectLockConfiguration(GetObjectLockConfigurationArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 获取对象锁定配置
     *
     * @param getObjectLockConfigurationArgs {@link GetObjectLockConfigurationArgs}
     * @return {@link ObjectLockConfiguration}
     */
    public Mono<ObjectLockConfiguration> getObjectLockConfiguration(GetObjectLockConfigurationArgs getObjectLockConfigurationArgs) {
        return fromFuture("getObjectLockConfiguration", (client) -> client.getObjectLockConfiguration(getObjectLockConfigurationArgs));
    }

    /**
     * 设置对象锁定
     *
     * @param bucketName 存储桶名称
     * @param config     对象锁定配置 {@link ObjectLockConfiguration}
     */
    public Mono<Void> setObjectLockConfiguration(String bucketName, ObjectLockConfiguration config) {
        return setObjectLockConfiguration(bucketName, null, config);
    }

    /**
     * 设置对象锁定
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param config     对象锁定配置 {@link ObjectLockConfiguration}
     */
    public Mono<Void> setObjectLockConfiguration(String bucketName, String region, ObjectLockConfiguration config) {
        return setObjectLockConfiguration(SetObjectLockConfigurationArgs.builder().bucket(bucketName).region(region).config(config).build());
    }

    /**
     * 设置对象锁定
     *
     * @param setObjectLockConfigurationArgs {@link SetObjectLockConfigurationArgs}
     */
    public Mono<Void> setObjectLockConfiguration(SetObjectLockConfigurationArgs setObjectLockConfigurationArgs) {
        return fromFuture("setObjectLockConfiguration", (client) -> client.setObjectLockConfiguration(setObjectLockConfigurationArgs));
    }

    /**
     * 删除对象锁定配置
     *
     * @param bucketName 存储桶名称
     */
    public Mono<Void> deleteObjectLockConfiguration(String bucketName) {
        return deleteObjectLockConfiguration(bucketName, null);
    }

    /**
     * 删除对象锁定配置
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     */
    public Mono<Void> deleteObjectLockConfiguration(String bucketName, String region) {
        return deleteObjectLockConfiguration(DeleteObjectLockConfigurationArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 删除对象锁定
     *
     * @param deleteObjectLockConfigurationArgs {@link DeleteObjectLockConfigurationArgs}
     */
    public Mono<Void> deleteObjectLockConfiguration(DeleteObjectLockConfigurationArgs deleteObjectLockConfigurationArgs) {
        return fromFuture("deleteObjectLockConfiguration", (client) -> client.deleteObjectLockConfiguration(deleteObjectLockConfigurationArgs));

    }
}
