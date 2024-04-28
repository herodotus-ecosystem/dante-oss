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
import io.minio.DeleteObjectTagsArgs;
import io.minio.GetObjectTagsArgs;
import io.minio.SetObjectTagsArgs;
import io.minio.messages.Tags;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>Description: Minio 对象标签服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 21:10
 */
@Service
public class MinioObjectTagsService extends BaseMinioAsyncService {

    public MinioObjectTagsService(MinioAsyncClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 获取对象的标签
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @return {@link Tags}
     */
    public Mono<Tags> getObjectTags(String bucketName, String objectName) {
        return getObjectTags(bucketName, null, objectName);
    }

    /**
     * 获取对象的标签
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param region     region
     * @return {@link Tags}
     */
    public Mono<Tags> getObjectTags(String bucketName, String region, String objectName) {
        return getObjectTags(bucketName, region, objectName, null);
    }

    /**
     * 获取对象的标签
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param region     region
     * @param versionId  versionId
     * @return {@link Tags}
     */
    public Mono<Tags> getObjectTags(String bucketName, String region, String objectName, String versionId) {
        return getObjectTags(GetObjectTagsArgs.builder().bucket(bucketName).object(objectName).region(region).versionId(versionId).build());
    }

    /**
     * 获取对象的标签。
     *
     * @param getObjectTagsArgs {@link GetObjectTagsArgs}
     * @return {@link Tags}
     */
    public Mono<Tags> getObjectTags(GetObjectTagsArgs getObjectTagsArgs) {
        return fromFuture("getObjectTags", (client) -> client.getObjectTags(getObjectTagsArgs));
    }

    /**
     * 为对象设置标签
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param tags       标签 {@link Tags}
     */
    public Mono<Void> setObjectTags(String bucketName, String objectName, Tags tags) {
        return setObjectTags(bucketName, null, objectName, tags);
    }

    /**
     * 为对象设置标签
     *
     * @param bucketName 存储桶名称
     * @param region     存储桶区域
     * @param objectName 对象名称
     * @param tags       标签 {@link Tags}
     */
    public Mono<Void> setObjectTags(String bucketName, String region, String objectName, Tags tags) {
        return setObjectTags(bucketName, region, objectName, tags, null);
    }

    /**
     * 为对象设置标签
     *
     * @param bucketName 存储桶名称
     * @param region     存储桶区域
     * @param objectName 对象名称
     * @param tags       标签 {@link Tags}
     * @param versionId  版本ID
     */
    public Mono<Void> setObjectTags(String bucketName, String region, String objectName, Tags tags, String versionId) {
        return setObjectTags(SetObjectTagsArgs.builder().bucket(bucketName).object(objectName).region(region).versionId(versionId).tags(tags).build());
    }

    /**
     * 为对象设置标签
     *
     * @param setObjectTagsArgs {@link SetObjectTagsArgs}
     */
    public Mono<Void> setObjectTags(SetObjectTagsArgs setObjectTagsArgs) {
        return fromFuture("setObjectTags", (client) -> client.setObjectTags(setObjectTagsArgs));
    }

    /**
     * 清空对象设置标签
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     */
    public Mono<Void> deleteObjectTags(String bucketName, String objectName) {
        return deleteObjectTags(bucketName, null, objectName);
    }

    /**
     * 清空对象设置标签
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     */
    public Mono<Void> deleteObjectTags(String bucketName, String region, String objectName) {
        return deleteObjectTags(bucketName, region, objectName, null);
    }


    /**
     * 清空对象设置标签
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param versionId  版本ID
     */
    public Mono<Void> deleteObjectTags(String bucketName, String region, String objectName, String versionId) {
        return deleteObjectTags(DeleteObjectTagsArgs.builder().bucket(bucketName).object(objectName).region(region).versionId(versionId).build());
    }

    /**
     * 清空对象设置标签
     *
     * @param deleteObjectTagsArgs {@link DeleteObjectTagsArgs}
     */
    public Mono<Void> deleteObjectTags(DeleteObjectTagsArgs deleteObjectTagsArgs) {
        return fromFuture("deleteObjectTags", (client) -> client.deleteObjectTags(deleteObjectTagsArgs));
    }
}
