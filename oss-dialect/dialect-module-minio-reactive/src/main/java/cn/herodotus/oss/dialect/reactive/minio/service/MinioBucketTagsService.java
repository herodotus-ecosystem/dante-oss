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
import io.minio.DeleteBucketTagsArgs;
import io.minio.GetBucketTagsArgs;
import io.minio.SetBucketTagsArgs;
import io.minio.messages.Tags;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>Description: Bucket 标签服务 </p>
 * 当为桶添加标签时，该桶上所有请求产生的计费话单里都会带上这些标签，从而可以针对话单报表做分类筛选，进行更详细的成本分析。例如：某个应用程序在运行过程会往桶里上传数据，我们可以用应用名称作为标签，设置到被使用的桶上。在分析话单时，就可以通过应用名称的标签来分析此应用的成本
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 15:58
 */
@Service
public class MinioBucketTagsService extends BaseMinioAsyncService {

    public MinioBucketTagsService(MinioAsyncClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 获取 Bucket 标签配置
     *
     * @param bucketName 存储桶名称
     * @return {@link Tags}
     */
    public Mono<Tags> getBucketTags(String bucketName) {
        return getBucketTags(bucketName, null);
    }

    /**
     * 获取 Bucket 标签配置
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @return {@link Tags}
     */
    public Mono<Tags> getBucketTags(String bucketName, String region) {
        return getBucketTags(GetBucketTagsArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 获取 Bucket 标签配置
     *
     * @param getBucketTagsArgs {@link GetBucketTagsArgs}
     * @return {@link Tags}
     */
    public Mono<Tags> getBucketTags(GetBucketTagsArgs getBucketTagsArgs) {
        return fromFuture("getBucketTags", (client) -> client.getBucketTags(getBucketTagsArgs));
    }

    /**
     * 设置 Bucket 标签
     *
     * @param bucketName 存储桶名称
     * @param tags       标签
     */
    public Mono<Void> setBucketTags(String bucketName, Tags tags) {
        return setBucketTags(bucketName, null, tags);
    }

    /**
     * 设置 Bucket 标签
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param tags       标签
     */
    public Mono<Void> setBucketTags(String bucketName, String region, Tags tags) {
        return setBucketTags(SetBucketTagsArgs.builder().bucket(bucketName).region(region).tags(tags).build());
    }

    /**
     * 设置 Bucket 标签
     *
     * @param setBucketTagsArgs {@link SetBucketTagsArgs}
     */
    public Mono<Void> setBucketTags(SetBucketTagsArgs setBucketTagsArgs) {
        return fromFuture("setBucketTags", (client) -> client.setBucketTags(setBucketTagsArgs));
    }

    /**
     * 删除 Bucket 标签配置
     *
     * @param bucketName 存储桶名称
     */
    public Mono<Void> deleteBucketTags(String bucketName) {
        return deleteBucketTags(bucketName, null);
    }

    /**
     * 删除 Bucket 标签配置
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     */
    public Mono<Void> deleteBucketTags(String bucketName, String region) {
        return deleteBucketTags(DeleteBucketTagsArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 删除 Bucket 标签
     *
     * @param deleteBucketTagsArgs {@link DeleteBucketTagsArgs}
     */
    public Mono<Void> deleteBucketTags(DeleteBucketTagsArgs deleteBucketTagsArgs) {
        return fromFuture("deleteBucketTags", (client) -> client.deleteBucketTags(deleteBucketTagsArgs));
    }
}
