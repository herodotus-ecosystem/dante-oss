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
import cn.herodotus.oss.core.minio.enums.PolicyEnums;
import cn.herodotus.oss.dialect.reactive.minio.definition.service.BaseMinioAsyncService;
import com.google.common.base.Enums;
import io.minio.DeleteBucketPolicyArgs;
import io.minio.GetBucketPolicyArgs;
import io.minio.SetBucketPolicyArgs;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>Description: Bucket 访问策略 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 15:44
 */
@Service
public class MinioBucketPolicyService extends BaseMinioAsyncService {

    public MinioBucketPolicyService(MinioAsyncClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 获取 Bucket 访问策略配置
     *
     * @param bucketName 存储桶名称
     * @return 自定义策略枚举 {@link PolicyEnums}
     */
    public Mono<PolicyEnums> getBucketPolicy(String bucketName) {
        return getBucketPolicy(bucketName, null);
    }

    /**
     * 获取 Bucket 访问策略配置
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @return 自定义策略枚举 {@link PolicyEnums}
     */
    public Mono<PolicyEnums> getBucketPolicy(String bucketName, String region) {
        return getBucketPolicy(GetBucketPolicyArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 获取 Bucket 访问策略配置
     *
     * @param getBucketPolicyArgs {@link GetBucketPolicyArgs}
     */
    public Mono<PolicyEnums> getBucketPolicy(GetBucketPolicyArgs getBucketPolicyArgs) {
        return fromFuture("getBucketPolicy", (client) -> client.getBucketPolicy(getBucketPolicyArgs))
                .flatMap(policy -> Mono.just(Enums.getIfPresent(PolicyEnums.class, policy).or(PolicyEnums.PRIVATE)));
    }

    /**
     * 设置 Bucket 访问策略
     *
     * @param bucketName 存储桶名称
     * @param config     策略配置
     */
    public Mono<Void> setBucketPolicy(String bucketName, String config) {
        return setBucketPolicy(bucketName, null, config);
    }

    /**
     * 设置 Bucket 访问策略
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param config     策略配置
     */
    public Mono<Void> setBucketPolicy(String bucketName, String region, String config) {
        return setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucketName).region(region).config(config).build());
    }

    /**
     * 设置 Bucket 访问策略
     *
     * @param setBucketPolicyArgs {@link SetBucketPolicyArgs}
     */
    public Mono<Void> setBucketPolicy(SetBucketPolicyArgs setBucketPolicyArgs) {
        return fromFuture("setBucketPolicy", (client) -> client.setBucketPolicy(setBucketPolicyArgs));
    }

    /**
     * 删除 Bucket 访问策略
     *
     * @param bucketName 存储桶名称
     */
    public Mono<Void> deleteBucketPolicy(String bucketName) {
        return deleteBucketPolicy(bucketName, null);
    }

    /**
     * 删除 Bucket 访问策略
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     */
    public Mono<Void> deleteBucketPolicy(String bucketName, String region) {
        return deleteBucketPolicy(DeleteBucketPolicyArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 删除 Bucket 访问策略
     *
     * @param deleteBucketPolicyArgs {@link DeleteBucketPolicyArgs}
     */
    public Mono<Void> deleteBucketPolicy(DeleteBucketPolicyArgs deleteBucketPolicyArgs) {
        return fromFuture("deleteBucketPolicy", (client) -> client.deleteBucketPolicy(deleteBucketPolicyArgs));
    }
}
