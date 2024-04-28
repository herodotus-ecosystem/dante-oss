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
import io.minio.DeleteBucketEncryptionArgs;
import io.minio.GetBucketEncryptionArgs;
import io.minio.SetBucketEncryptionArgs;
import io.minio.messages.SseConfiguration;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>Description: Bucket 加密服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 15:31
 */
@Service
public class MinioBucketEncryptionService extends BaseMinioAsyncService {

    public MinioBucketEncryptionService(MinioAsyncClientObjectPool minioAsyncClientObjectPool) {
        super(minioAsyncClientObjectPool);
    }

    /**
     * 获取 Bucket 加密配置
     *
     * @param bucketName 存储桶名称
     * @return 自定义 SseConfiguration 枚举 {@link SseConfiguration}
     */
    public Mono<SseConfiguration> getBucketEncryption(String bucketName) {
        return getBucketEncryption(bucketName, null);
    }

    /**
     * 获取 Bucket 加密配置
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @return 自定义 SseConfiguration 枚举 {@link SseConfiguration}
     */
    public Mono<SseConfiguration> getBucketEncryption(String bucketName, String region) {
        return getBucketEncryption(GetBucketEncryptionArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 获取 Bucket 加密配置
     *
     * @param getBucketEncryptionArgs {@link GetBucketEncryptionArgs}
     */
    public Mono<SseConfiguration> getBucketEncryption(GetBucketEncryptionArgs getBucketEncryptionArgs) {
        return fromFuture("getBucketEncryption", (client) -> client.getBucketEncryption(getBucketEncryptionArgs));
    }

    /**
     * 设置 Bucket 加密
     *
     * @param bucketName 存储桶名称
     * @param config     加密配置 {@link SseConfiguration}
     */
    public Mono<Void> setBucketEncryption(String bucketName, SseConfiguration config) {
        return setBucketEncryption(bucketName, null, config);
    }

    /**
     * 设置 Bucket 加密
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param config     加密配置 {@link SseConfiguration}
     */
    public Mono<Void> setBucketEncryption(String bucketName, String region, SseConfiguration config) {
        return setBucketEncryption(SetBucketEncryptionArgs.builder().bucket(bucketName).region(region).config(config).build());
    }


    /**
     * 设置 Bucket 加密
     *
     * @param setBucketEncryptionArgs {@link SetBucketEncryptionArgs}
     */
    public Mono<Void> setBucketEncryption(SetBucketEncryptionArgs setBucketEncryptionArgs) {
        return fromFuture("setBucketEncryption", (client) -> client.setBucketEncryption(setBucketEncryptionArgs));
    }

    /**
     * 删除 Bucket 加密配置
     *
     * @param bucketName 存储桶名称
     */
    public Mono<Void> deleteBucketEncryption(String bucketName) {
        return deleteBucketEncryption(bucketName, null);
    }

    /**
     * 删除 Bucket 加密配置
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     */
    public Mono<Void> deleteBucketEncryption(String bucketName, String region) {
        return deleteBucketEncryption(DeleteBucketEncryptionArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 删除 Bucket 加密配置
     *
     * @param deleteBucketEncryptionArgs {@link DeleteBucketEncryptionArgs}
     */
    public Mono<Void> deleteBucketEncryption(DeleteBucketEncryptionArgs deleteBucketEncryptionArgs) {
        return fromFuture("deleteBucketEncryption", (client) -> client.deleteBucketEncryption(deleteBucketEncryptionArgs));
    }


}
