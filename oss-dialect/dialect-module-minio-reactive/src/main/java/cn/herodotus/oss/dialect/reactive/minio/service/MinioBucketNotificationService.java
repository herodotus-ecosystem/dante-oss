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
import io.minio.DeleteBucketNotificationArgs;
import io.minio.GetBucketNotificationArgs;
import io.minio.SetBucketNotificationArgs;
import io.minio.messages.NotificationConfiguration;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>Description: Bucket 通知配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 15:42
 */
@Service
public class MinioBucketNotificationService extends BaseMinioAsyncService {

    public MinioBucketNotificationService(MinioAsyncClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 设置 Bucket 通知
     *
     * @param bucketName                bucketName
     * @param notificationConfiguration {@link NotificationConfiguration}
     */
    public Mono<Void> setBucketNotification(String bucketName, NotificationConfiguration notificationConfiguration) {
        return setBucketNotification(SetBucketNotificationArgs.builder().bucket(bucketName).config(notificationConfiguration).build());
    }

    /**
     * 设置 Bucket 通知
     *
     * @param bucketName                bucketName
     * @param region                    region
     * @param notificationConfiguration {@link NotificationConfiguration}
     */
    public Mono<Void> setBucketNotification(String bucketName, String region, NotificationConfiguration notificationConfiguration) {
        return setBucketNotification(SetBucketNotificationArgs.builder().bucket(bucketName).region(region).config(notificationConfiguration).build());
    }


    /**
     * 设置 Bucket 通知
     *
     * @param setBucketNotificationArgs {@link SetBucketNotificationArgs}
     */
    public Mono<Void> setBucketNotification(SetBucketNotificationArgs setBucketNotificationArgs) {
        return fromFuture("setBucketNotification", (client) -> client.setBucketNotification(setBucketNotificationArgs));
    }

    /**
     * 获取 Bucket 通知配置
     *
     * @param bucketName bucketName
     * @return {@link  NotificationConfiguration}
     */
    public Mono<NotificationConfiguration> getBucketNotification(String bucketName) {
        return getBucketNotification(GetBucketNotificationArgs.builder().bucket(bucketName).build());
    }

    /**
     * 获取 Bucket 通知配置
     *
     * @param bucketName bucketName
     * @param region     region
     * @return {@link  NotificationConfiguration}
     */
    public Mono<NotificationConfiguration> getBucketNotification(String bucketName, String region) {
        return getBucketNotification(GetBucketNotificationArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 获取 Bucket 通知配置
     *
     * @param getBucketNotificationArgs {@link GetBucketNotificationArgs}
     * @return {@link  NotificationConfiguration}
     */
    public Mono<NotificationConfiguration> getBucketNotification(GetBucketNotificationArgs getBucketNotificationArgs) {
        return fromFuture("getBucketNotification", (client) -> client.getBucketNotification(getBucketNotificationArgs));
    }

    /**
     * 删除 Bucket 通知配置
     *
     * @param bucketName bucketName
     */
    public Mono<Void> deleteBucketNotification(String bucketName) {
        return deleteBucketNotification(DeleteBucketNotificationArgs.builder().bucket(bucketName).build());
    }

    /**
     * 删除 Bucket 通知配置
     *
     * @param bucketName bucketName
     * @param region     region
     */
    public Mono<Void> deleteBucketNotification(String bucketName, String region) {
        return deleteBucketNotification(DeleteBucketNotificationArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 删除 Bucket 通知配置
     *
     * @param deleteBucketNotificationArgs {@link DeleteBucketNotificationArgs}
     */
    public Mono<Void> deleteBucketNotification(DeleteBucketNotificationArgs deleteBucketNotificationArgs) {
        return fromFuture("deleteBucketNotification", (client) -> client.deleteBucketNotification(deleteBucketNotificationArgs));
    }
}
