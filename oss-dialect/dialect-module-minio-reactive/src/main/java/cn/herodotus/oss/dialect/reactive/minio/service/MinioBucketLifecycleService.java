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
import io.minio.DeleteBucketLifecycleArgs;
import io.minio.GetBucketLifecycleArgs;
import io.minio.SetBucketLifecycleArgs;
import io.minio.messages.LifecycleConfiguration;
import io.minio.messages.LifecycleRule;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <p>Description: Bucket 生命周期配置服务 </p>
 * <p>
 * 生命周期管理可适用于以下典型场景：
 * · 周期性上传的日志文件，可能只需要保留一个星期或一个月。到期后要删除它们。
 * · 某些文档在一段时间内经常访问，但是超过一定时间后便可能不再访问了。这些文档需要在一定时间后转化为低频访问存储，归档存储或者删除
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 15:39
 */
@Service
public class MinioBucketLifecycleService extends BaseMinioAsyncService {

    public MinioBucketLifecycleService(MinioAsyncClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 设置 Bucket 生命周期配置
     *
     * @param bucketName     bucketName
     * @param lifecycleRules {@link LifecycleRule}
     */
    public Mono<Void> setBucketLifecycle(String bucketName, List<LifecycleRule> lifecycleRules) {
        return setBucketLifecycle(bucketName, new LifecycleConfiguration(lifecycleRules));
    }

    /**
     * 置 Bucket 生命周期配置
     *
     * @param bucketName     bucketName
     * @param region         region
     * @param lifecycleRules {@link LifecycleRule}
     */
    public Mono<Void> setBucketLifecycle(String bucketName, String region, List<LifecycleRule> lifecycleRules) {
        return setBucketLifecycle(bucketName, region, new LifecycleConfiguration(lifecycleRules));
    }

    /**
     * 设置 Bucket 生命周期
     *
     * @param bucketName             bucketName
     * @param lifecycleConfiguration {@link LifecycleConfiguration}
     */
    public Mono<Void> setBucketLifecycle(String bucketName, LifecycleConfiguration lifecycleConfiguration) {
        return setBucketLifecycle(SetBucketLifecycleArgs.builder().bucket(bucketName).config(lifecycleConfiguration).build());
    }

    /**
     * 设置 Bucket 生命周期
     *
     * @param bucketName             bucketName
     * @param region                 region
     * @param lifecycleConfiguration @link LifecycleConfiguration}
     */
    public Mono<Void> setBucketLifecycle(String bucketName, String region, LifecycleConfiguration lifecycleConfiguration) {
        return setBucketLifecycle(SetBucketLifecycleArgs.builder().bucket(bucketName).region(region).config(lifecycleConfiguration).build());
    }

    /**
     * 设置 Bucket 生命周期
     *
     * @param setBucketLifecycleArgs {@link SetBucketLifecycleArgs}
     */
    public Mono<Void> setBucketLifecycle(SetBucketLifecycleArgs setBucketLifecycleArgs) {
        return fromFuture("setBucketLifecycle", (client) -> client.setBucketLifecycle(setBucketLifecycleArgs));
    }

    /**
     * 获取 Bucket 生命周期配置
     *
     * @param bucketName bucketName
     * @return {@link LifecycleConfiguration}
     */
    public Mono<LifecycleConfiguration> getBucketLifecycle(String bucketName) {
        return getBucketLifecycle(GetBucketLifecycleArgs.builder().bucket(bucketName).build());
    }

    /**
     * 获取 Bucket 生命周期配置
     *
     * @param bucketName bucketName
     * @param region     region
     * @return {@link LifecycleConfiguration}
     */
    public Mono<LifecycleConfiguration> getBucketLifecycle(String bucketName, String region) {
        return getBucketLifecycle(GetBucketLifecycleArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 获取 Bucket 生命周期配置
     *
     * @param getBucketLifecycleArgs {@link GetBucketLifecycleArgs}
     */
    public Mono<LifecycleConfiguration> getBucketLifecycle(GetBucketLifecycleArgs getBucketLifecycleArgs) {
        return fromFuture("getBucketLifecycle", (client) -> client.getBucketLifecycle(getBucketLifecycleArgs));
    }

    /**
     * 删除 Bucket 生命周期配置
     *
     * @param bucketName bucketName
     */
    public Mono<Void> deleteBucketLifecycle(String bucketName) {
        return deleteBucketLifecycle(DeleteBucketLifecycleArgs.builder().bucket(bucketName).build());
    }

    /**
     * 删除 Bucket 生命周期配置
     *
     * @param bucketName bucketName
     * @param region     region
     */
    public Mono<Void> deleteBucketLifecycle(String bucketName, String region) {
        return deleteBucketLifecycle(DeleteBucketLifecycleArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 删除 Bucket 生命周期配置
     *
     * @param deleteBucketLifecycleArgs {@link DeleteBucketLifecycleArgs}
     */
    public Mono<Void> deleteBucketLifecycle(DeleteBucketLifecycleArgs deleteBucketLifecycleArgs) {
        return fromFuture("deleteBucketLifecycle", (client) -> client.deleteBucketLifecycle(deleteBucketLifecycleArgs));
    }
}
