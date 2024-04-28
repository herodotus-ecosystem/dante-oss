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

import cn.herodotus.oss.core.minio.converter.retention.RetentionToDomainConverter;
import cn.herodotus.oss.core.minio.definition.pool.MinioAsyncClientObjectPool;
import cn.herodotus.oss.core.minio.domain.RetentionDomain;
import cn.herodotus.oss.dialect.reactive.minio.definition.service.BaseMinioAsyncService;
import io.minio.GetObjectRetentionArgs;
import io.minio.SetObjectRetentionArgs;
import io.minio.messages.Retention;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>Description: Minio 对象保留配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 21:08
 */
@Service
public class MinioObjectRetentionService extends BaseMinioAsyncService {

    private final Converter<Retention, RetentionDomain> toDo;

    public MinioObjectRetentionService(MinioAsyncClientObjectPool objectPool) {
        super(objectPool);
        this.toDo = new RetentionToDomainConverter();
    }

    /**
     * 获取对象的保留配置
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @return 自定义保留域对象
     */
    public Mono<RetentionDomain> getObjectRetention(String bucketName, String objectName) {
        return getObjectRetention(bucketName, null, objectName);
    }

    /**
     * 获取对象的保留配置
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @return 自定义保留域对象
     */
    public Mono<RetentionDomain> getObjectRetention(String bucketName, String region, String objectName) {
        return getObjectRetention(bucketName, region, objectName, null);
    }

    /**
     * 获取对象的保留配置
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param versionId  版本ID
     * @return 自定义保留域对象
     */
    public Mono<RetentionDomain> getObjectRetention(String bucketName, String region, String objectName, String versionId) {
        return getObjectRetention(GetObjectRetentionArgs.builder().bucket(bucketName).region(region).object(objectName).versionId(versionId).build());
    }

    /**
     * 获取对象的保留配置
     *
     * @param getObjectRetentionArgs {@link GetObjectRetentionArgs}
     * @return {@link RetentionDomain}
     */
    public Mono<RetentionDomain> getObjectRetention(GetObjectRetentionArgs getObjectRetentionArgs) {
        return fromFuture("getObjectRetention", (client) -> client.getObjectRetention(getObjectRetentionArgs))
                .flatMap(retention -> Mono.justOrEmpty(toDo.convert(retention)));
    }

    /**
     * 添加对象的保留配置，存储桶需要设置为对象锁定模式，并且没有开启版本控制，否则会报错收蠕虫保护。
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param config     保留配置 {@link Retention}
     */
    public Mono<Void> setObjectRetention(String bucketName, String objectName, Retention config) {
        return setObjectRetention(bucketName, objectName, config, false);
    }

    /**
     * 添加对象的保留配置，存储桶需要设置为对象锁定模式，并且没有开启版本控制，否则会报错收蠕虫保护。
     *
     * @param bucketName           存储桶名称
     * @param objectName           对象名称
     * @param config               保留配置 {@link Retention}
     * @param bypassGovernanceMode 使用 Governance 模式
     */
    public Mono<Void> setObjectRetention(String bucketName, String objectName, Retention config, boolean bypassGovernanceMode) {
        return setObjectRetention(bucketName, null, objectName, config, bypassGovernanceMode);
    }

    /**
     * 添加对象的保留配置，存储桶需要设置为对象锁定模式，并且没有开启版本控制，否则会报错收蠕虫保护。
     *
     * @param bucketName           存储桶名称
     * @param region               区域
     * @param objectName           对象名称
     * @param config               保留配置 {@link Retention}
     * @param bypassGovernanceMode 使用 Governance 模式
     */
    public Mono<Void> setObjectRetention(String bucketName, String region, String objectName, Retention config, boolean bypassGovernanceMode) {
        return setObjectRetention(bucketName, region, objectName, config, bypassGovernanceMode, null);
    }

    /**
     * 添加对象的保留配置，存储桶需要设置为对象锁定模式，并且没有开启版本控制，否则会报错收蠕虫保护。
     *
     * @param bucketName           存储桶名称
     * @param region               区域
     * @param objectName           对象名称
     * @param config               保留配置 {@link Retention}
     * @param bypassGovernanceMode 使用 Governance 模式
     * @param versionId            版本ID
     */
    public Mono<Void> setObjectRetention(String bucketName, String region, String objectName, Retention config, boolean bypassGovernanceMode, String versionId) {
        return setObjectRetention(SetObjectRetentionArgs.builder()
                .bucket(bucketName)
                .region(region)
                .object(objectName)
                .config(config)
                .bypassGovernanceMode(bypassGovernanceMode)
                .versionId(versionId)
                .build());
    }

    /**
     * 添加对象的保留配置，存储桶需要设置为对象锁定模式，并且没有开启版本控制，否则会报错收蠕虫保护。
     *
     * @param setObjectRetentionArgs {@link SetObjectRetentionArgs}
     */
    public Mono<Void> setObjectRetention(SetObjectRetentionArgs setObjectRetentionArgs) {
        return fromFuture("setObjectRetention", (client) -> client.setObjectRetention(setObjectRetentionArgs));
    }
}
