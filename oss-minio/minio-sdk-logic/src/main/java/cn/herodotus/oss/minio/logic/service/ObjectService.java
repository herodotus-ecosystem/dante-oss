/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
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

package cn.herodotus.oss.minio.logic.service;

import cn.herodotus.oss.minio.logic.converter.DeleteErrorToEntityConverter;
import cn.herodotus.oss.minio.logic.converter.ObjectToEntityConverter;
import cn.herodotus.oss.minio.logic.definition.pool.MinioClientObjectPool;
import cn.herodotus.oss.minio.logic.definition.service.BaseMinioService;
import cn.herodotus.oss.minio.logic.entity.DeleteErrorEntity;
import cn.herodotus.oss.minio.logic.entity.ObjectEntity;
import cn.herodotus.oss.minio.core.exception.*;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import org.apache.commons.collections4.IterableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ConnectException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: Minio 对象操作服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 20:06
 */
@Service
public class ObjectService extends BaseMinioService {

    private static final Logger log = LoggerFactory.getLogger(ObjectService.class);

    public ObjectService(MinioClientObjectPool minioClientObjectPool) {
        super(minioClientObjectPool);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV1
     *
     * @param bucketName 存储桶名称
     * @return 自定义对象列表
     */
    public List<ObjectEntity> listObjects(String bucketName) {
        return listObjects(bucketName, null);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV1
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @return 自定义对象列表
     */
    public List<ObjectEntity> listObjects(String bucketName, String region) {
        return listObjects(bucketName, region, null);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV1
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param delimiter  分隔符
     * @return 自定义对象列表
     */
    public List<ObjectEntity> listObjects(String bucketName, String region, String delimiter) {
        return listObjects(bucketName, region, delimiter, false);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV1
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param delimiter  分隔符
     * @param recursive  是否递归
     * @return 自定义对象列表
     */
    public List<ObjectEntity> listObjects(String bucketName, String region, String delimiter, boolean recursive) {
        return listObjects(bucketName, region, delimiter, recursive, null);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV1
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param delimiter  分隔符
     * @param recursive  是否递归
     * @param keyMarker  关键字
     * @return 自定义对象列表
     */
    public List<ObjectEntity> listObjects(String bucketName, String region, String delimiter, boolean recursive, String keyMarker) {
        return listObjects(bucketName, region, delimiter, recursive, keyMarker, null);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV1
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param delimiter  分隔符
     * @param recursive  是否递归
     * @param keyMarker  关键字
     * @param prefix     前缀
     * @return 自定义对象列表
     */
    public List<ObjectEntity> listObjects(String bucketName, String region, String delimiter, boolean recursive, String keyMarker, String prefix) {
        return listObjects(bucketName, region, delimiter, recursive, true, keyMarker, prefix);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV1
     *
     * @param bucketName         存储桶名称
     * @param region             区域
     * @param delimiter          分隔符
     * @param recursive          是否递归
     * @param useUrlEncodingType 是否使用 UrlEncoding
     * @param keyMarker          关键字
     * @param prefix             前缀
     * @return 自定义对象列表
     */
    public List<ObjectEntity> listObjects(String bucketName, String region, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker, String prefix) {
        return listObjects(bucketName, region, delimiter, recursive, useUrlEncodingType, keyMarker, 1000, prefix);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV1
     *
     * @param bucketName         存储桶名称
     * @param region             区域
     * @param delimiter          分隔符
     * @param recursive          是否递归
     * @param useUrlEncodingType 是否使用 UrlEncoding
     * @param keyMarker          关键字
     * @param maxKeys            最大关键字
     * @param prefix             前缀
     * @return 自定义对象列表
     */
    public List<ObjectEntity> listObjects(String bucketName, String region, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker, int maxKeys, String prefix) {
        return listObjects(bucketName, region, delimiter, recursive, useUrlEncodingType, keyMarker, maxKeys, prefix, false);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV1
     *
     * @param bucketName         存储桶名称
     * @param region             区域
     * @param delimiter          分隔符
     * @param recursive          是否递归
     * @param useUrlEncodingType 是否使用 UrlEncoding
     * @param keyMarker          关键字
     * @param maxKeys            最大关键字
     * @param prefix             前缀
     * @param includeVersions    是否包含版本
     * @return 自定义对象列表
     */
    public List<ObjectEntity> listObjects(String bucketName, String region, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker, int maxKeys, String prefix, boolean includeVersions) {
        return listObjects(bucketName, region, delimiter, recursive, useUrlEncodingType, keyMarker, maxKeys, prefix, includeVersions, null);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV1
     *
     * @param bucketName         存储桶名称
     * @param region             区域
     * @param delimiter          分隔符
     * @param recursive          是否递归
     * @param useUrlEncodingType 是否使用 UrlEncoding
     * @param keyMarker          关键字
     * @param maxKeys            最大关键字
     * @param prefix             前缀
     * @param includeVersions    是否包含版本
     * @param versionIdMarker    版本关键字
     * @return 自定义对象列表
     */
    public List<ObjectEntity> listObjects(String bucketName, String region, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker, int maxKeys, String prefix, boolean includeVersions, String versionIdMarker) {
        return listObjects(ListObjectsArgs.builder()
                .bucket(bucketName)
                .region(region)
                .delimiter(delimiter)
                .recursive(recursive)
                .useUrlEncodingType(useUrlEncodingType)
                .keyMarker(keyMarker)
                .maxKeys(maxKeys)
                .prefix(prefix)
                .includeVersions(includeVersions)
                .versionIdMarker(versionIdMarker)
                .useApiVersion1(true)
                .build());
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName 存储桶名称
     * @return 自定义对象列表
     */
    public List<ObjectEntity> listObjectsV2(String bucketName) {
        return listObjectsV2(bucketName, null);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @return 自定义对象列表
     */
    public List<ObjectEntity> listObjectsV2(String bucketName, String region) {
        return listObjectsV2(bucketName, region, null);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param delimiter  分隔符
     * @return 自定义对象列表
     */
    public List<ObjectEntity> listObjectsV2(String bucketName, String region, String delimiter) {
        return listObjectsV2(bucketName, region, delimiter, false);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param delimiter  分隔符
     * @param recursive  是否递归
     * @return 自定义对象列表
     */
    public List<ObjectEntity> listObjectsV2(String bucketName, String region, String delimiter, boolean recursive) {
        return listObjectsV2(bucketName, region, delimiter, recursive, null);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param delimiter  分隔符
     * @param recursive  是否递归
     * @param keyMarker  关键字
     * @return 自定义对象列表
     */
    public List<ObjectEntity> listObjectsV2(String bucketName, String region, String delimiter, boolean recursive, String keyMarker) {
        return listObjectsV2(bucketName, region, delimiter, recursive, true, keyMarker);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName         存储桶名称
     * @param region             区域
     * @param delimiter          分隔符
     * @param recursive          是否递归
     * @param useUrlEncodingType 是否使用 UrlEncoding
     * @param keyMarker          关键字
     * @return 自定义对象列表
     */
    public List<ObjectEntity> listObjectsV2(String bucketName, String region, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker) {
        return listObjectsV2(bucketName, region, delimiter, recursive, useUrlEncodingType, keyMarker, 1000);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName         存储桶名称
     * @param region             区域
     * @param delimiter          分隔符
     * @param recursive          是否递归
     * @param useUrlEncodingType 是否使用 UrlEncoding
     * @param keyMarker          关键字
     * @param maxKeys            最大关键字
     * @return 自定义对象列表
     */
    public List<ObjectEntity> listObjectsV2(String bucketName, String region, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker, int maxKeys) {
        return listObjectsV2(bucketName, region, delimiter, recursive, useUrlEncodingType, keyMarker, maxKeys, null);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName         存储桶名称
     * @param region             区域
     * @param delimiter          分隔符
     * @param recursive          是否递归
     * @param useUrlEncodingType 是否使用 UrlEncoding
     * @param keyMarker          关键字
     * @param maxKeys            最大关键字
     * @param prefix             前缀
     * @return 自定义对象列表
     */
    public List<ObjectEntity> listObjectsV2(String bucketName, String region, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker, int maxKeys, String prefix) {
        return listObjectsV2(bucketName, region, delimiter, recursive, useUrlEncodingType, keyMarker, maxKeys, prefix, false);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName         存储桶名称
     * @param region             区域
     * @param delimiter          分隔符
     * @param recursive          是否递归
     * @param useUrlEncodingType 是否使用 UrlEncoding
     * @param keyMarker          关键字
     * @param maxKeys            最大关键字
     * @param prefix             前缀
     * @param includeVersions    是否包含版本
     * @return 自定义对象列表
     */
    public List<ObjectEntity> listObjectsV2(String bucketName, String region, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker, int maxKeys, String prefix, boolean includeVersions) {
        return listObjectsV2(bucketName, region, delimiter, recursive, useUrlEncodingType, keyMarker, maxKeys, prefix, includeVersions, null);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName         存储桶名称
     * @param region             区域
     * @param delimiter          分隔符
     * @param recursive          是否递归
     * @param useUrlEncodingType 是否使用 UrlEncoding
     * @param keyMarker          关键字
     * @param maxKeys            最大关键字
     * @param prefix             前缀
     * @param includeVersions    是否包含版本
     * @param versionIdMarker    版本关键字
     * @return 自定义对象列表
     */
    public List<ObjectEntity> listObjectsV2(String bucketName, String region, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker, int maxKeys, String prefix, boolean includeVersions, String versionIdMarker) {
        return listObjectsV2(bucketName, region, delimiter, recursive, useUrlEncodingType, keyMarker, maxKeys, prefix, includeVersions, versionIdMarker, null);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName         存储桶名称
     * @param region             区域
     * @param delimiter          分隔符
     * @param recursive          是否递归
     * @param useUrlEncodingType 是否使用 UrlEncoding
     * @param keyMarker          关键字
     * @param maxKeys            最大关键字
     * @param prefix             前缀
     * @param includeVersions    是否包含版本
     * @param versionIdMarker    版本关键字
     * @param continuationToken  持续集成 Token
     * @return 自定义对象列表
     */
    public List<ObjectEntity> listObjectsV2(String bucketName, String region, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker, int maxKeys, String prefix, boolean includeVersions, String versionIdMarker, String continuationToken) {
        return listObjectsV2(bucketName, region, delimiter, recursive, useUrlEncodingType, keyMarker, maxKeys, prefix, includeVersions, versionIdMarker, continuationToken, false);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName         存储桶名称
     * @param region             区域
     * @param delimiter          分隔符
     * @param recursive          是否递归
     * @param useUrlEncodingType 是否使用 UrlEncoding
     * @param keyMarker          关键字
     * @param maxKeys            最大关键字
     * @param prefix             前缀
     * @param includeVersions    是否包含版本
     * @param versionIdMarker    版本关键字
     * @param continuationToken  持续集成 Token
     * @param fetchOwner         获取 Owner
     * @return 自定义对象列表
     */
    public List<ObjectEntity> listObjectsV2(String bucketName, String region, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker, int maxKeys, String prefix, boolean includeVersions, String versionIdMarker, String continuationToken, boolean fetchOwner) {
        return listObjectsV2(bucketName, region, delimiter, recursive, useUrlEncodingType, keyMarker, maxKeys, prefix, includeVersions, versionIdMarker, continuationToken, fetchOwner, false);
    }

    /**
     * 列出桶的对象信息. 仅用于 only for ListObjectsV2
     *
     * @param bucketName          存储桶名称
     * @param region              区域
     * @param delimiter           分隔符
     * @param recursive           是否递归
     * @param useUrlEncodingType  是否使用 UrlEncoding
     * @param keyMarker           关键字
     * @param maxKeys             最大关键字
     * @param prefix              前缀
     * @param includeVersions     是否包含版本
     * @param versionIdMarker     版本关键字
     * @param continuationToken   持续集成 Token
     * @param fetchOwner          获取 Owner
     * @param includeUserMetadata 包含用户自定义信息
     * @return 自定义对象列表
     */
    public List<ObjectEntity> listObjectsV2(String bucketName, String region, String delimiter, boolean recursive, boolean useUrlEncodingType, String keyMarker, int maxKeys, String prefix, boolean includeVersions, String versionIdMarker, String continuationToken, boolean fetchOwner, boolean includeUserMetadata) {
        return listObjects(ListObjectsArgs.builder()
                .bucket(bucketName)
                .region(region)
                .delimiter(delimiter)
                .recursive(recursive)
                .useUrlEncodingType(useUrlEncodingType)
                .keyMarker(keyMarker)
                .maxKeys(maxKeys)
                .prefix(prefix)
                .includeVersions(includeVersions)
                .versionIdMarker(versionIdMarker)
                .useApiVersion1(false)
                .continuationToken(continuationToken)
                .fetchOwner(fetchOwner)
                .includeUserMetadata(includeUserMetadata)
                .build());
    }

    /**
     * listObjects列出桶的对象信息
     *
     * @param listObjectsArgs {@link ListObjectsArgs}
     * @return Iterable<Result < Item>>
     */
    public List<ObjectEntity> listObjects(ListObjectsArgs listObjectsArgs) {
        MinioClient minioClient = getMinioClient();
        Iterable<Result<Item>> results = minioClient.listObjects(listObjectsArgs);
        List<ObjectEntity> entities = toEntities(results, new ObjectToEntityConverter());
        close(minioClient);
        return entities;
    }

    /**
     * 懒惰地删除多个对象。它需要迭代返回的 Iterable 以执行删除
     *
     * @param bucketName 存储桶名称
     * @param objects    待删除对象
     * @return 自定义删除错误列表。列表 Size 为 0，表明全部正常删除；不为 0，则返回具体错误对象以及相关信息
     */
    public List<DeleteErrorEntity> removeObjects(String bucketName, Iterable<DeleteObject> objects) {
        return removeObjects(bucketName, null, objects);
    }

    /**
     * 懒惰地删除多个对象。它需要迭代返回的 Iterable 以执行删除
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objects    待删除对象
     * @return 自定义删除错误列表。列表 Size 为 0，表明全部正常删除；不为 0，则返回具体错误对象以及相关信息
     */
    public List<DeleteErrorEntity> removeObjects(String bucketName, String region, Iterable<DeleteObject> objects) {
        return removeObjects(bucketName, region, objects, false);
    }

    /**
     * 懒惰地删除多个对象。它需要迭代返回的 Iterable 以执行删除
     *
     * @param bucketName           存储桶名称
     * @param region               区域
     * @param objects              待删除对象
     * @param bypassGovernanceMode 使用 Governance 模式
     * @return 自定义删除错误列表。列表 Size 为 0，表明全部正常删除；不为 0，则返回具体错误对象以及相关信息
     */
    public List<DeleteErrorEntity> removeObjects(String bucketName, String region, Iterable<DeleteObject> objects, boolean bypassGovernanceMode) {
        return removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).region(region).objects(objects).bypassGovernanceMode(bypassGovernanceMode).build());
    }

    /**
     * 懒惰地删除多个对象。它需要迭代返回的 Iterable 以执行删除
     *
     * @param removeObjectsArgs {@link RemoveObjectsArgs}
     * @return 自定义删除错误列表。列表 Size 为 0，表明全部正常删除；不为 0，则返回具体错误对象以及相关信息
     */
    public List<DeleteErrorEntity> removeObjects(RemoveObjectsArgs removeObjectsArgs) {
        MinioClient minioClient = getMinioClient();
        Iterable<Result<DeleteError>> results = minioClient.removeObjects(removeObjectsArgs);
        List<DeleteErrorEntity> entities = toEntities(results, new DeleteErrorToEntityConverter());
        close(minioClient);
        return entities;
    }

    /**
     * 移除一个对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名
     */
    public void removeObject(String bucketName, String objectName) {
        removeObject(bucketName, null, objectName);
    }

    /**
     * 移除一个对象
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名
     */
    public void removeObject(String bucketName, String region, String objectName) {
        removeObject(bucketName, region, objectName, null);
    }

    /**
     * 移除一个对象
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名
     * @param versionId  版本ID
     */
    public void removeObject(String bucketName, String region, String objectName, String versionId) {
        removeObject(RemoveObjectArgs.builder().bucket(bucketName).region(region).object(objectName).versionId(versionId).build());
    }

    /**
     * 移除一个对象
     *
     * @param removeObjectArgs {@link RemoveObjectArgs}
     */
    public void removeObject(RemoveObjectArgs removeObjectArgs) {
        String function = "removeObject";
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.removeObject(removeObjectArgs);
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new MinioErrorResponseException(e.getMessage());
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new MinioInsufficientDataException(e.getMessage());
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new MinioInternalException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new MinioInvalidKeyException(e.getMessage());
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new MinioInvalidResponseException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new MinioConnectException(e.getMessage());
            } else {
                throw new MinioIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new MinioNoSuchAlgorithmException(e.getMessage());
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new MinioServerException(e.getMessage());
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in [{}].", function, e);
            throw new MinioXmlParserException(e.getMessage());
        } finally {
            close(minioClient);
        }
    }

    /**
     * 将对象的数据下载到文件。主要用于在服务端下载(非流方式)
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param fileName   具体保存的文件名，包括路径
     */
    public void downloadObject(String bucketName, String objectName, String fileName) {
        downloadObject(bucketName, objectName, fileName, false);
    }

    /**
     * 将对象的数据下载到文件。主要用于在服务端下载(非流方式)
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param fileName   具体保存的文件名，包括路径
     * @param overwrite  是否覆盖
     */
    public void downloadObject(String bucketName, String objectName, String fileName, boolean overwrite) {
        downloadObject(bucketName, null, objectName, fileName, overwrite);
    }

    /**
     * 将对象的数据下载到文件。主要用于在服务端下载(非流方式)
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param fileName   具体保存的文件名，包括路径
     * @param overwrite  是否覆盖
     */
    public void downloadObject(String bucketName, String region, String objectName, String fileName, boolean overwrite) {
        downloadObject(bucketName, region, objectName, fileName, overwrite, null);
    }

    /**
     * 将对象的数据下载到文件。主要用于在服务端下载(非流方式)
     *
     * @param bucketName                      存储桶名称
     * @param region                          区域
     * @param objectName                      对象名称
     * @param fileName                        具体保存的文件名，包括路径
     * @param overwrite                       是否覆盖
     * @param serverSideEncryptionCustomerKey 服务端加密自定义KEY，目前 Minio 仅支持 256位 AES.
     */
    public void downloadObject(String bucketName, String region, String objectName, String fileName, boolean overwrite, ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey) {
        downloadObject(bucketName, region, objectName, fileName, overwrite, serverSideEncryptionCustomerKey, null);
    }

    /**
     * 将对象的数据下载到文件。主要用于在服务端下载(非流方式)
     *
     * @param bucketName                      存储桶名称
     * @param region                          区域
     * @param objectName                      对象名称
     * @param fileName                        具体保存的文件名，包括路径
     * @param overwrite                       是否覆盖
     * @param serverSideEncryptionCustomerKey 服务端加密自定义KEY，目前 Minio 仅支持 256位 AES.
     * @param versionId                       版本ID
     */
    public void downloadObject(String bucketName, String region, String objectName, String fileName, boolean overwrite, ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey, String versionId) {
        downloadObject(DownloadObjectArgs.builder()
                .bucket(bucketName)
                .region(region)
                .object(objectName)
                .filename(fileName)
                .overwrite(overwrite)
                .ssec(serverSideEncryptionCustomerKey)
                .versionId(versionId)
                .build());
    }

    /**
     * 将对象的数据下载到文件。主要用于在服务端下载
     *
     * @param downloadObjectArgs {@link DownloadObjectArgs}
     */
    public void downloadObject(DownloadObjectArgs downloadObjectArgs) {
        String function = "downloadObject";
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.downloadObject(downloadObjectArgs);
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new MinioErrorResponseException(e.getMessage());
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new MinioInsufficientDataException(e.getMessage());
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new MinioInternalException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new MinioInvalidKeyException(e.getMessage());
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new MinioInvalidResponseException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new MinioConnectException(e.getMessage());
            } else {
                throw new MinioIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new MinioNoSuchAlgorithmException(e.getMessage());
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new MinioServerException(e.getMessage());
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in [{}].", function, e);
            throw new MinioXmlParserException(e.getMessage());
        } finally {
            close(minioClient);
        }
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     * <p>
     * 获取对象的数据。InputStream使用后返回必须关闭以释放网络资源。
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @return {@link GetObjectResponse}
     */
    public GetObjectResponse getObject(String bucketName, String objectName) {
        return getObject(bucketName, null, objectName);
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     * <p>
     * 获取对象的数据。InputStream使用后返回必须关闭以释放网络资源。
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @return {@link GetObjectResponse}
     */
    public GetObjectResponse getObject(String bucketName, String region, String objectName) {
        return getObject(bucketName, region, objectName, null);
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     * <p>
     * 获取对象的数据。InputStream使用后返回必须关闭以释放网络资源。
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param matchETag  匹配的 ETag
     * @return {@link GetObjectResponse}
     */
    public GetObjectResponse getObject(String bucketName, String region, String objectName, String matchETag) {
        return getObject(bucketName, region, objectName, matchETag, null);
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     * <p>
     * 获取对象的数据。InputStream使用后返回必须关闭以释放网络资源。
     *
     * @param bucketName   存储桶名称
     * @param region       区域
     * @param objectName   对象名称
     * @param matchETag    匹配的 ETag
     * @param notMatchETag 不匹配的 ETag
     * @return {@link GetObjectResponse}
     */
    public GetObjectResponse getObject(String bucketName, String region, String objectName, String matchETag, String notMatchETag) {
        return getObject(bucketName, region, objectName, matchETag, notMatchETag, null);
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     * <p>
     * 获取对象的数据。InputStream使用后返回必须关闭以释放网络资源。
     *
     * @param bucketName    存储桶名称
     * @param region        区域
     * @param objectName    对象名称
     * @param matchETag     匹配的 ETag
     * @param notMatchETag  不匹配的 ETag
     * @param modifiedSince 某个时间以后的
     * @return {@link GetObjectResponse}
     */
    public GetObjectResponse getObject(String bucketName, String region, String objectName, String matchETag, String notMatchETag, ZonedDateTime modifiedSince) {
        return getObject(bucketName, region, objectName, matchETag, notMatchETag, modifiedSince, null);
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     * <p>
     * 获取对象的数据。InputStream使用后返回必须关闭以释放网络资源。
     *
     * @param bucketName      存储桶名称
     * @param region          区域
     * @param objectName      对象名称
     * @param matchETag       匹配的 ETag
     * @param notMatchETag    不匹配的 ETag
     * @param modifiedSince   某个时间以后的
     * @param unmodifiedSince 某个时间以前的
     * @return {@link GetObjectResponse}
     */
    public GetObjectResponse getObject(String bucketName, String region, String objectName, String matchETag, String notMatchETag, ZonedDateTime modifiedSince, ZonedDateTime unmodifiedSince) {
        return getObject(bucketName, region, objectName, null, null, matchETag, notMatchETag, modifiedSince, unmodifiedSince);
    }


    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     * <p>
     * 获取对象的数据。InputStream使用后返回必须关闭以释放网络资源。
     *
     * @param bucketName      存储桶名称
     * @param region          区域
     * @param objectName      对象名称
     * @param offset          偏移
     * @param length          长度
     * @param matchETag       匹配的 ETag
     * @param notMatchETag    不匹配的 ETag
     * @param modifiedSince   某个时间以后的
     * @param unmodifiedSince 某个时间以前的
     * @return {@link GetObjectResponse}
     */
    public GetObjectResponse getObject(String bucketName, String region, String objectName, Long offset, Long length, String matchETag, String notMatchETag, ZonedDateTime modifiedSince, ZonedDateTime unmodifiedSince) {
        return getObject(bucketName, region, objectName, offset, length, matchETag, notMatchETag, modifiedSince, unmodifiedSince, null);
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     * <p>
     * 获取对象的数据。InputStream使用后返回必须关闭以释放网络资源。
     *
     * @param bucketName                      存储桶名称
     * @param region                          区域
     * @param objectName                      对象名称
     * @param offset                          偏移
     * @param length                          长度
     * @param matchETag                       匹配的 ETag
     * @param notMatchETag                    不匹配的 ETag
     * @param modifiedSince                   某个时间以后的
     * @param unmodifiedSince                 某个时间以前的
     * @param serverSideEncryptionCustomerKey 服务端加密自定义KEY，目前 Minio 仅支持 256位 AES
     * @return {@link GetObjectResponse}
     */
    public GetObjectResponse getObject(String bucketName, String region, String objectName, Long offset, Long length, String matchETag, String notMatchETag, ZonedDateTime modifiedSince, ZonedDateTime unmodifiedSince, ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey) {
        return getObject(bucketName, region, objectName, offset, length, matchETag, notMatchETag, modifiedSince, unmodifiedSince, serverSideEncryptionCustomerKey, null);
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     * <p>
     * 获取对象的数据。InputStream使用后返回必须关闭以释放网络资源。
     *
     * @param bucketName                      存储桶名称
     * @param region                          区域
     * @param objectName                      对象名称
     * @param offset                          偏移
     * @param length                          长度
     * @param matchETag                       匹配的 ETag
     * @param notMatchETag                    不匹配的 ETag
     * @param modifiedSince                   某个时间以后的
     * @param unmodifiedSince                 某个时间以前的
     * @param serverSideEncryptionCustomerKey 服务端加密自定义KEY，目前 Minio 仅支持 256位 AES.
     * @param versionId                       版本ID
     * @return {@link GetObjectResponse}
     */
    public GetObjectResponse getObject(String bucketName, String region, String objectName, Long offset, Long length, String matchETag, String notMatchETag, ZonedDateTime modifiedSince, ZonedDateTime unmodifiedSince, ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey, String versionId) {
        return getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .region(region)
                .object(objectName)
                .offset(offset)
                .length(length)
                .matchETag(matchETag)
                .notMatchETag(notMatchETag)
                .modifiedSince(modifiedSince)
                .unmodifiedSince(unmodifiedSince)
                .ssec(serverSideEncryptionCustomerKey)
                .versionId(versionId)
                .build());
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     * <p>
     * 获取对象的数据。InputStream使用后返回必须关闭以释放网络资源。
     *
     * @param getObjectArgs {@link GetObjectArgs}
     * @return {@link GetObjectResponse}
     */
    public GetObjectResponse getObject(GetObjectArgs getObjectArgs) {
        String function = "getObject";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.getObject(getObjectArgs);
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new MinioErrorResponseException(e.getMessage());
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new MinioInsufficientDataException(e.getMessage());
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new MinioInternalException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new MinioInvalidKeyException(e.getMessage());
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new MinioInvalidResponseException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new MinioConnectException(e.getMessage());
            } else {
                throw new MinioIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new MinioNoSuchAlgorithmException(e.getMessage());
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new MinioServerException(e.getMessage());
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in [{}].", function, e);
            throw new MinioXmlParserException(e.getMessage());
        } finally {
            close(minioClient);
        }
    }

    /**
     * 获取对象的对象信息和元数据
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @return {@link GetObjectResponse}
     */
    public StatObjectResponse statObject(String bucketName, String objectName) {
        return statObject(bucketName, null, objectName);
    }

    /**
     * 获取对象的对象信息和元数据
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @return {@link GetObjectResponse}
     */
    public StatObjectResponse statObject(String bucketName, String region, String objectName) {
        return statObject(bucketName, region, objectName, null);
    }


    /**
     * 获取对象的对象信息和元数据
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param matchETag  匹配的 ETag
     * @return {@link GetObjectResponse}
     */
    public StatObjectResponse statObject(String bucketName, String region, String objectName, String matchETag) {
        return statObject(bucketName, region, objectName, matchETag, null);
    }

    /**
     * 获取对象的对象信息和元数据
     *
     * @param bucketName   存储桶名称
     * @param region       区域
     * @param objectName   对象名称
     * @param matchETag    匹配的 ETag
     * @param notMatchETag 不匹配的 ETag
     * @return {@link GetObjectResponse}
     */
    public StatObjectResponse statObject(String bucketName, String region, String objectName, String matchETag, String notMatchETag) {
        return statObject(bucketName, region, objectName, matchETag, notMatchETag, null);
    }

    /**
     * 获取对象的对象信息和元数据
     *
     * @param bucketName    存储桶名称
     * @param region        区域
     * @param objectName    对象名称
     * @param matchETag     匹配的 ETag
     * @param notMatchETag  不匹配的 ETag
     * @param modifiedSince 某个时间以后的
     * @return {@link GetObjectResponse}
     */
    public StatObjectResponse statObject(String bucketName, String region, String objectName, String matchETag, String notMatchETag, ZonedDateTime modifiedSince) {
        return statObject(bucketName, region, objectName, matchETag, notMatchETag, modifiedSince, null);
    }

    /**
     * 获取对象的对象信息和元数据
     *
     * @param bucketName      存储桶名称
     * @param region          区域
     * @param objectName      对象名称
     * @param matchETag       匹配的 ETag
     * @param notMatchETag    不匹配的 ETag
     * @param modifiedSince   某个时间以后的
     * @param unmodifiedSince 某个时间以前的
     * @return {@link GetObjectResponse}
     */
    public StatObjectResponse statObject(String bucketName, String region, String objectName, String matchETag, String notMatchETag, ZonedDateTime modifiedSince, ZonedDateTime unmodifiedSince) {
        return statObject(bucketName, region, objectName, null, null, matchETag, notMatchETag, modifiedSince, unmodifiedSince, null);
    }

    /**
     * 获取对象的对象信息和元数据
     *
     * @param bucketName      存储桶名称
     * @param region          区域
     * @param objectName      对象名称
     * @param offset          偏移
     * @param length          长度
     * @param matchETag       匹配的 ETag
     * @param notMatchETag    不匹配的 ETag
     * @param modifiedSince   某个时间以后的
     * @param unmodifiedSince 某个时间以前的
     * @return {@link GetObjectResponse}
     */
    public StatObjectResponse statObject(String bucketName, String region, String objectName, Long offset, Long length, String matchETag, String notMatchETag, ZonedDateTime modifiedSince, ZonedDateTime unmodifiedSince) {
        return statObject(bucketName, region, objectName, offset, length, matchETag, notMatchETag, modifiedSince, unmodifiedSince, null);
    }

    /**
     * 获取对象的对象信息和元数据
     *
     * @param bucketName                      存储桶名称
     * @param region                          区域
     * @param objectName                      对象名称
     * @param offset                          偏移
     * @param length                          长度
     * @param matchETag                       匹配的 ETag
     * @param notMatchETag                    不匹配的 ETag
     * @param modifiedSince                   某个时间以后的
     * @param unmodifiedSince                 某个时间以前的
     * @param serverSideEncryptionCustomerKey 服务端加密自定义KEY，目前 Minio 仅支持 256位 AES
     * @return {@link GetObjectResponse}
     */
    public StatObjectResponse statObject(String bucketName, String region, String objectName, Long offset, Long length, String matchETag, String notMatchETag, ZonedDateTime modifiedSince, ZonedDateTime unmodifiedSince, ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey) {
        return statObject(bucketName, region, objectName, offset, length, matchETag, notMatchETag, modifiedSince, unmodifiedSince, serverSideEncryptionCustomerKey, null);
    }

    /**
     * 获取对象的对象信息和元数据
     *
     * @param bucketName                      存储桶名称
     * @param region                          区域
     * @param objectName                      对象名称
     * @param offset                          偏移
     * @param length                          长度
     * @param matchETag                       匹配的 ETag
     * @param notMatchETag                    不匹配的 ETag
     * @param modifiedSince                   某个时间以后的
     * @param unmodifiedSince                 某个时间以前的
     * @param serverSideEncryptionCustomerKey 服务端加密自定义KEY，目前 Minio 仅支持 256位 AES.
     * @param versionId                       版本ID
     * @return {@link StatObjectResponse}
     */
    public StatObjectResponse statObject(String bucketName, String region, String objectName, Long offset, Long length, String matchETag, String notMatchETag, ZonedDateTime modifiedSince, ZonedDateTime unmodifiedSince, ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey, String versionId) {
        return statObject(StatObjectArgs.builder()
                .bucket(bucketName)
                .region(region)
                .object(objectName)
                .offset(offset)
                .length(length)
                .matchETag(matchETag)
                .notMatchETag(notMatchETag)
                .modifiedSince(modifiedSince)
                .unmodifiedSince(unmodifiedSince)
                .ssec(serverSideEncryptionCustomerKey)
                .versionId(versionId)
                .build());
    }

    /**
     * 获取对象的对象信息和元数据
     *
     * @param statObjectArgs {@link StatObjectArgs}
     * @return {@link StatObjectResponse}
     */
    public StatObjectResponse statObject(StatObjectArgs statObjectArgs) {
        String function = "statObject";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.statObject(statObjectArgs);
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new MinioErrorResponseException(e.getMessage());
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new MinioInsufficientDataException(e.getMessage());
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new MinioInternalException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new MinioInvalidKeyException(e.getMessage());
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new MinioInvalidResponseException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new MinioConnectException(e.getMessage());
            } else {
                throw new MinioIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new MinioNoSuchAlgorithmException(e.getMessage());
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new MinioServerException(e.getMessage());
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in [{}].", function, e);
            throw new MinioXmlParserException(e.getMessage());
        } finally {
            close(minioClient);
        }
    }

    /**
     * 通过使用服务器端副本组合来自不同源对象的数据来创建对象，比如可以将文件分片上传，然后将他们合并为一个文件
     *
     * @param composeObjectArgs {@link ComposeObjectArgs}
     * @return {@link ObjectWriteResponse}
     */
    public ObjectWriteResponse composeObject(ComposeObjectArgs composeObjectArgs) {
        String function = "composeObject";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.composeObject(composeObjectArgs);
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new MinioErrorResponseException(e.getMessage());
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new MinioInsufficientDataException(e.getMessage());
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new MinioInternalException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new MinioInvalidKeyException(e.getMessage());
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new MinioInvalidResponseException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new MinioConnectException(e.getMessage());
            } else {
                throw new MinioIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new MinioNoSuchAlgorithmException(e.getMessage());
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new MinioServerException(e.getMessage());
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in [{}].", function, e);
            throw new MinioXmlParserException(e.getMessage());
        } finally {
            close(minioClient);
        }
    }


    /**
     * 上传文件
     * <p>
     * · 添加的Object大小不能超过5 GB。
     * · 默认情况下，如果已存在同名Object且对该Object有访问权限，则新添加的Object将覆盖原有的Object，并返回200 OK。
     * · OSS没有文件夹的概念，所有资源都是以文件来存储，但您可以通过创建一个以正斜线（/）结尾，大小为0的Object来创建模拟文件夹。
     *
     * @param putObjectArgs {@link PutObjectArgs}
     * @return {@link ObjectWriteResponse}
     */
    public ObjectWriteResponse putObject(PutObjectArgs putObjectArgs) {
        String function = "putObject";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.putObject(putObjectArgs);
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new MinioErrorResponseException(e.getMessage());
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new MinioInsufficientDataException(e.getMessage());
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new MinioInternalException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new MinioInvalidKeyException(e.getMessage());
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new MinioInvalidResponseException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new MinioConnectException(e.getMessage());
            } else {
                throw new MinioIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new MinioNoSuchAlgorithmException(e.getMessage());
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new MinioServerException(e.getMessage());
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in [{}].", function, e);
            throw new MinioXmlParserException(e.getMessage());
        } finally {
            close(minioClient);
        }
    }


    /**
     * 通过服务器端从另一个对象复制数据来创建一个对象
     *
     * @param copyObjectArgs {@link CopyObjectArgs}
     * @return {@link ObjectWriteResponse}
     */
    public ObjectWriteResponse copyObject(CopyObjectArgs copyObjectArgs) {
        String function = "copyObject";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.copyObject(copyObjectArgs);
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new MinioErrorResponseException(e.getMessage());
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new MinioInsufficientDataException(e.getMessage());
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new MinioInternalException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new MinioInvalidKeyException(e.getMessage());
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new MinioInvalidResponseException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new MinioConnectException(e.getMessage());
            } else {
                throw new MinioIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new MinioNoSuchAlgorithmException(e.getMessage());
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new MinioServerException(e.getMessage());
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in [{}].", function, e);
            throw new MinioXmlParserException(e.getMessage());
        } finally {
            close(minioClient);
        }
    }

    /**
     * 恢复对象
     *
     * @param args {@link RestoreObjectArgs}
     */
    public void restoreObject(RestoreObjectArgs args) {
        String function = "restoreObject";
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.restoreObject(args);
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new MinioErrorResponseException(e.getMessage());
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new MinioInsufficientDataException(e.getMessage());
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new MinioInternalException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new MinioInvalidKeyException(e.getMessage());
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new MinioInvalidResponseException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new MinioConnectException(e.getMessage());
            } else {
                throw new MinioIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new MinioNoSuchAlgorithmException(e.getMessage());
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new MinioServerException(e.getMessage());
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in [{}].", function, e);
            throw new MinioXmlParserException(e.getMessage());
        } finally {
            close(minioClient);
        }
    }

    /**
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param uploadObjectArgs {@link UploadObjectArgs}
     * @return {@link ObjectWriteResponse}
     */
    public ObjectWriteResponse uploadObject(UploadObjectArgs uploadObjectArgs) {
        String function = "uploadObject";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.uploadObject(uploadObjectArgs);
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new MinioErrorResponseException(e.getMessage());
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new MinioInsufficientDataException(e.getMessage());
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new MinioInternalException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new MinioInvalidKeyException(e.getMessage());
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new MinioInvalidResponseException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new MinioConnectException(e.getMessage());
            } else {
                throw new MinioIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new MinioNoSuchAlgorithmException(e.getMessage());
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new MinioServerException(e.getMessage());
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in [{}].", function, e);
            throw new MinioXmlParserException(e.getMessage());
        } finally {
            close(minioClient);
        }
    }

    /**
     * 通过 SQL 表达式选择对象的内容
     *
     * @param selectObjectContentArgs {@link SelectObjectContentArgs}
     * @return {@link SelectResponseStream}
     */
    public SelectResponseStream selectObjectContent(SelectObjectContentArgs selectObjectContentArgs) {
        String function = "selectObjectContent";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.selectObjectContent(selectObjectContentArgs);
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new MinioErrorResponseException(e.getMessage());
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new MinioInsufficientDataException(e.getMessage());
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new MinioInternalException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new MinioInvalidKeyException(e.getMessage());
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new MinioInvalidResponseException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new MinioConnectException(e.getMessage());
            } else {
                throw new MinioIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new MinioNoSuchAlgorithmException(e.getMessage());
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new MinioServerException(e.getMessage());
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in [{}].", function, e);
            throw new MinioXmlParserException(e.getMessage());
        } finally {
            close(minioClient);
        }
    }

    private <T, R> List<R> toEntities(Iterable<Result<T>> results, Converter<Result<T>, R> toResponse) {
        List<R> responses = new ArrayList<>();
        if (!IterableUtils.isEmpty(results)) {
            for (Result<T> result : results) {
                R response = toResponse.convert(result);
                responses.add(response);
            }
        }
        return responses;
    }
}
