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

import cn.herodotus.oss.core.domain.bucket.BucketDomain;
import cn.herodotus.oss.core.minio.converter.domain.BucketToDomainConverter;
import cn.herodotus.oss.core.minio.definition.pool.MinioAsyncClientObjectPool;
import cn.herodotus.oss.dialect.core.utils.ConverterUtils;
import cn.herodotus.oss.dialect.reactive.minio.definition.service.BaseMinioAsyncService;
import io.minio.BucketExistsArgs;
import io.minio.ListBucketsArgs;
import io.minio.MakeBucketArgs;
import io.minio.RemoveBucketArgs;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <p>Description: Minio Bucket 存储通基础操作服务 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 13:26
 */
@Service
public class MinioBucketService extends BaseMinioAsyncService {

    public MinioBucketService(MinioAsyncClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 查询所有存储桶
     *
     * @param listBucketsArgs {@link ListBucketsArgs}
     * @return Bucket 列表
     */
    public Mono<List<BucketDomain>> listBuckets(ListBucketsArgs listBucketsArgs) {
        return fromFuture("listBuckets", (client) -> ObjectUtils.isNotEmpty(listBucketsArgs) ? client.listBuckets(listBucketsArgs) : client.listBuckets())
                .map(items -> ConverterUtils.toDomains(items, new BucketToDomainConverter()));
    }

    /**
     * 存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return 是否存在，true 存在，false 不存在
     */
    public Mono<Boolean> bucketExists(String bucketName) {
        return bucketExists(bucketName, null);
    }

    /**
     * 存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @return 是否存在，true 存在，false 不存在
     */
    public Mono<Boolean> bucketExists(String bucketName, String region) {
        return bucketExists(BucketExistsArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 存储桶是否存在
     *
     * @param bucketExistsArgs {@link BucketExistsArgs}
     * @return true 存在，false 不存在
     */
    public Mono<Boolean> bucketExists(BucketExistsArgs bucketExistsArgs) {
        return fromFuture("bucketExists", (client) -> client.bucketExists(bucketExistsArgs));
    }

    /**
     * 创建存储桶
     *
     * @param bucketName 存储桶名称
     */
    public Mono<Void> makeBucket(String bucketName) {
        return makeBucket(bucketName, null);
    }

    /**
     * 创建存储桶
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     */
    public Mono<Void> makeBucket(String bucketName, String region) {
        return makeBucket(MakeBucketArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 创建存储桶
     * <p>
     * 该方法仅仅是 Minio 原始方法的封装，不包含校验等操作。
     *
     * @param makeBucketArgs {@link MakeBucketArgs}
     */
    public Mono<Void> makeBucket(MakeBucketArgs makeBucketArgs) {
        return fromFuture("makeBucket", (client) -> client.makeBucket(makeBucketArgs));
    }

    /**
     * 删除一个空的存储桶 如果存储桶存在对象不为空时，删除会报错。
     *
     * @param bucketName 存储桶名称
     */
    public Mono<Void> removeBucket(String bucketName) {
        return removeBucket(bucketName, null);
    }

    /**
     * 删除一个空的存储桶 如果存储桶存在对象不为空时，删除会报错。
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     */
    public Mono<Void> removeBucket(String bucketName, String region) {
        return removeBucket(RemoveBucketArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 删除一个空的存储桶 如果存储桶存在对象不为空时，删除会报错。
     *
     * @param removeBucketArgs {@link RemoveBucketArgs}
     */
    public Mono<Void> removeBucket(RemoveBucketArgs removeBucketArgs) {
        return fromFuture("removeBucket", (client) -> client.removeBucket(removeBucketArgs));
    }
}
