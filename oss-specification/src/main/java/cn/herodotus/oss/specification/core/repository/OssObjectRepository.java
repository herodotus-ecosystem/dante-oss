/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante OSS licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante OSS 是 Dante Cloud 对象存储组件库 采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante OSS 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.oss.specification.core.repository;

import cn.herodotus.oss.specification.arguments.object.*;
import cn.herodotus.oss.specification.domain.base.ObjectWriteDomain;
import cn.herodotus.oss.specification.domain.object.*;
import cn.herodotus.oss.specification.enums.HttpMethod;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.util.List;

/**
 * <p>Description: Dante Java OSS API 对象操作抽象定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/24 16:39
 */
public interface OssObjectRepository {

    /**
     * 根据存储桶名称获取对象列表
     *
     * @param bucketName 存储桶名称
     * @return 对象列表结果 {@link ListObjectsDomain}
     */
    default ListObjectsDomain listObjects(String bucketName) {
        return listObjects(bucketName, null);
    }

    /**
     * 根据存储桶名称和前缀获取对象列表
     *
     * @param bucketName 存储桶名
     * @param prefix     前缀
     * @return 对象列表结果 {@link ListObjectsDomain}
     */
    default ListObjectsDomain listObjects(String bucketName, String prefix) {
        ListObjectsArguments arguments = new ListObjectsArguments();
        arguments.setBucketName(bucketName);
        if (StringUtils.isNotBlank(prefix)) {
            arguments.setPrefix(prefix);
        }

        return listObjects(arguments);
    }

    /**
     * 获取对象列表
     *
     * @param arguments 对象列表请求参数 {@link ListObjectsArguments}
     * @return 对象列表结果 {@link ListObjectsDomain}
     */
    ListObjectsDomain listObjects(ListObjectsArguments arguments);

    /**
     * 根据存储桶名称和前缀获取对象列表V2
     *
     * @param bucketName 存储桶名
     * @return 对象列表结果 {@link ListObjectsDomain}
     */
    default ListObjectsV2Domain listObjectsV2(String bucketName) {
        return listObjectsV2(bucketName, null);
    }

    /**
     * 根据存储桶名称和前缀获取对象列表V2
     *
     * @param bucketName 存储桶名
     * @param prefix     前缀
     * @return 对象列表结果 {@link ListObjectsV2Domain}
     */
    default ListObjectsV2Domain listObjectsV2(String bucketName, String prefix) {
        ListObjectsV2Arguments arguments = new ListObjectsV2Arguments();
        arguments.setBucketName(bucketName);
        if (StringUtils.isNotBlank(prefix)) {
            arguments.setPrefix(prefix);
        }

        return listObjectsV2(arguments);
    }

    /**
     * 获取对象列表V2
     *
     * @param arguments 对象列表请求参数 {@link ListObjectsV2Arguments}
     * @return 对象列表结果 {@link ListObjectsV2Domain}
     */
    ListObjectsV2Domain listObjectsV2(ListObjectsV2Arguments arguments);

    /**
     * 删除一个对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     */
    default void deleteObject(String bucketName, String objectName) {
        DeleteObjectArguments arguments = new DeleteObjectArguments();
        arguments.setBucketName(bucketName);
        arguments.setObjectName(objectName);
        deleteObject(arguments);
    }

    /**
     * 删除一个对象
     *
     * @param arguments 删除对象请求参数 {@link DeleteObjectArguments}
     */
    void deleteObject(DeleteObjectArguments arguments);

    /**
     * 批量删除对象
     *
     * @param arguments 批量删除对象请求参数 {@link DeleteObjectsArguments}
     * @return 批量删除对象结果对象
     */
    List<DeleteObjectDomain> deleteObjects(DeleteObjectsArguments arguments);

    /**
     * 获取对象元信息
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @return 对象元信息结果对象 {@link ObjectMetadataDomain}
     */
    default ObjectMetadataDomain getObjectMetadata(String bucketName, String objectName) {
        GetObjectMetadataArguments arguments = new GetObjectMetadataArguments();
        arguments.setBucketName(bucketName);
        arguments.setObjectName(objectName);
        return getObjectMetadata(arguments);
    }

    /**
     * 获取对象元信息
     *
     * @param arguments 获取对象元信息 {@link DeleteObjectsArguments}
     * @return 对象元信息结果对象 {@link ObjectMetadataDomain}
     */
    ObjectMetadataDomain getObjectMetadata(GetObjectMetadataArguments arguments);

    /**
     * 获取（下载）对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @return 获取（下载）对象结果域对象 {@link GetObjectDomain}
     */
    default GetObjectDomain getObject(String bucketName, String objectName) {
        GetObjectArguments arguments = new GetObjectArguments();
        arguments.setBucketName(bucketName);
        arguments.setObjectName(objectName);
        return getObject(arguments);
    }

    /**
     * 获取（下载）对象
     *
     * @param arguments 获取（下载）对象请求参数实体 {@link GetObjectArguments}
     * @return 获取（下载）对象结果域对象 {@link GetObjectDomain}
     */
    GetObjectDomain getObject(GetObjectArguments arguments);

    /**
     * 放置（上传）对象
     *
     * @param arguments 放置（上传）对象请求参数实体 {@link PutObjectArguments}
     * @return 放置（上传）对象结果域对象 {@link PutObjectDomain}
     */
    PutObjectDomain putObject(PutObjectArguments arguments);


    /**
     * 创建预签名 URL
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param expiration 中止时间
     * @return 预签名地址
     */
    default String generatePresignedUrl(String bucketName, String objectName, Duration expiration) {
        return generatePresignedUrl(bucketName, objectName, expiration, HttpMethod.GET);
    }


    /**
     * 创建预签名 URL
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param expiration 中止时间
     * @param method     http 请求类型
     * @return 预签名地址
     */
    default String generatePresignedUrl(String bucketName, String objectName, Duration expiration, HttpMethod method) {
        GeneratePresignedUrlArguments arguments = new GeneratePresignedUrlArguments();
        arguments.setBucketName(bucketName);
        arguments.setObjectName(objectName);
        arguments.setExpiration(expiration);
        arguments.setMethod(method);
        return generatePresignedUrl(arguments);
    }

    /**
     * 创建预签名 URL
     *
     * @param arguments 创建预签名 URL 请求参数 {@link GeneratePresignedUrlArguments}
     * @return 预签名地址
     */
    String generatePresignedUrl(GeneratePresignedUrlArguments arguments);

    /**
     * 下载对象
     * <p>
     * 该方法与<code>getObject</code>不同，该方法要指明具体的文件{@link java.io.File} 或者文件名。这就意味着可以把该方法理解为服务端下载操作。
     *
     * @param arguments 下载对象请求参数实体 {@link DownloadObjectArguments}
     * @return 下载对象结果域对象 {@link ObjectMetadataDomain}
     */
    ObjectMetadataDomain download(DownloadObjectArguments arguments);

    /**
     * 上传对象
     * <p>
     * 该方法与<code>putObject</code>不同，该方法要指明具体的文件{@link java.io.File} 或者文件名。这就意味着可以把该方法理解为服务端下载操作
     *
     * @param arguments 下载对象请求参数实体 {@link UploadObjectArguments}
     * @return 下载对象结果域对象 {@link ObjectWriteDomain}
     */
    ObjectWriteDomain upload(UploadObjectArguments arguments);
}
