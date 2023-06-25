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

import cn.herodotus.oss.minio.core.exception.*;
import cn.herodotus.oss.minio.logic.definition.pool.MinioClientObjectPool;
import cn.herodotus.oss.minio.logic.definition.service.BaseMinioClientService;
import io.minio.DeleteObjectTagsArgs;
import io.minio.GetObjectTagsArgs;
import io.minio.MinioClient;
import io.minio.SetObjectTagsArgs;
import io.minio.errors.*;
import io.minio.messages.Tags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ConnectException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>Description: Minio 对象标签服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 21:10
 */
@Service
public class ObjectTagsService extends BaseMinioClientService {

    private static final Logger log = LoggerFactory.getLogger(ObjectTagsService.class);

    public ObjectTagsService(MinioClientObjectPool minioClientObjectPool) {
        super(minioClientObjectPool);
    }

    /**
     * 获取对象的标签
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @return {@link Tags}
     */
    public Tags getObjectTags(String bucketName, String objectName) {
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
    public Tags getObjectTags(String bucketName, String region, String objectName) {
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
    public Tags getObjectTags(String bucketName, String region, String objectName, String versionId) {
        return getObjectTags(GetObjectTagsArgs.builder().bucket(bucketName).object(objectName).region(region).versionId(versionId).build());
    }

    /**
     * 获取对象的标签。
     *
     * @param getObjectTagsArgs {@link GetObjectTagsArgs}
     * @return {@link Tags}
     */
    public Tags getObjectTags(GetObjectTagsArgs getObjectTagsArgs) {
        String function = "getObjectTags";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.getObjectTags(getObjectTagsArgs);
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
     * 为对象设置标签
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param tags       标签 {@link Tags}
     */
    public void setObjectTags(String bucketName, String objectName, Tags tags) {
        setObjectTags(bucketName, null, objectName, tags);
    }

    /**
     * 为对象设置标签
     *
     * @param bucketName 存储桶名称
     * @param region     存储桶区域
     * @param objectName 对象名称
     * @param tags       标签 {@link Tags}
     */
    public void setObjectTags(String bucketName, String region, String objectName, Tags tags) {
        setObjectTags(bucketName, region, objectName, tags, null);
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
    public void setObjectTags(String bucketName, String region, String objectName, Tags tags, String versionId) {
        setObjectTags(SetObjectTagsArgs.builder().bucket(bucketName).object(objectName).region(region).versionId(versionId).tags(tags).build());
    }

    /**
     * 为对象设置标签
     *
     * @param setObjectTagsArgs {@link SetObjectTagsArgs}
     */
    public void setObjectTags(SetObjectTagsArgs setObjectTagsArgs) {
        String function = "setObjectTags";
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.setObjectTags(setObjectTagsArgs);
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
     * 清空对象设置标签
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     */
    public void deleteObjectTags(String bucketName, String objectName) {
        deleteObjectTags(bucketName, null, objectName);
    }

    /**
     * 清空对象设置标签
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     */
    public void deleteObjectTags(String bucketName, String region, String objectName) {
        deleteObjectTags(bucketName, region, objectName, null);
    }


    /**
     * 清空对象设置标签
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param versionId  版本ID
     */
    public void deleteObjectTags(String bucketName, String region, String objectName, String versionId) {
        deleteObjectTags(DeleteObjectTagsArgs.builder().bucket(bucketName).object(objectName).region(region).versionId(versionId).build());
    }

    /**
     * 清空对象设置标签
     *
     * @param deleteObjectTagsArgs {@link DeleteObjectTagsArgs}
     */
    public void deleteObjectTags(DeleteObjectTagsArgs deleteObjectTagsArgs) {
        String function = "deleteObjectTags";
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.deleteObjectTags(deleteObjectTagsArgs);
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
}
