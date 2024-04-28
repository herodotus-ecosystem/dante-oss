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
import io.minio.DeleteBucketReplicationArgs;
import io.minio.GetBucketReplicationArgs;
import io.minio.SetBucketReplicationArgs;
import io.minio.messages.ReplicationConfiguration;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>Description: Minio Bucket Replication </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 15:55
 */
@Service
public class MinioBucketReplicationService extends BaseMinioAsyncService {

    public MinioBucketReplicationService(MinioAsyncClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 设置 Bucket 策略
     *
     * @param setBucketReplicationArgs {@link SetBucketReplicationArgs}
     */
    public Mono<Void> setBucketReplication(SetBucketReplicationArgs setBucketReplicationArgs) {
        return fromFuture("setBucketReplication", (client) -> client.setBucketReplication(setBucketReplicationArgs));
    }

    /**
     * 获取 Bucket 通知配置
     *
     * @param getBucketReplicationArgs {@link GetBucketReplicationArgs}
     */
    public Mono<ReplicationConfiguration> getBucketReplication(GetBucketReplicationArgs getBucketReplicationArgs) {
        return fromFuture("getBucketReplication", (client) -> client.getBucketReplication(getBucketReplicationArgs));
    }

    public Mono<Void> deleteBucketReplication(String bucketName) {
        return deleteBucketReplication(DeleteBucketReplicationArgs.builder().bucket(bucketName).build());
    }

    public Mono<Void> deleteBucketReplication(DeleteBucketReplicationArgs deleteBucketReplicationArgs) {
        return fromFuture("deleteBucketReplication", (client) -> client.deleteBucketReplication(deleteBucketReplicationArgs));
    }
}
