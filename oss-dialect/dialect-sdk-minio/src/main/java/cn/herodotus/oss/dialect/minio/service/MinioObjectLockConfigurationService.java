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

package cn.herodotus.oss.dialect.minio.service;

import cn.herodotus.oss.core.minio.converter.retention.ObjectLockConfigurationToDomainConverter;
import cn.herodotus.oss.core.minio.domain.ObjectLockConfigurationDomain;
import cn.herodotus.oss.dialect.core.exception.*;
import cn.herodotus.oss.dialect.minio.definition.pool.MinioClientObjectPool;
import cn.herodotus.oss.dialect.minio.definition.service.BaseMinioService;
import io.minio.DeleteObjectLockConfigurationArgs;
import io.minio.GetObjectLockConfigurationArgs;
import io.minio.MinioClient;
import io.minio.SetObjectLockConfigurationArgs;
import io.minio.errors.*;
import io.minio.messages.ObjectLockConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ConnectException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>Description: Minio 对象锁定配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 16:04
 */
@Service
public class MinioObjectLockConfigurationService extends BaseMinioService {
    private static final Logger log = LoggerFactory.getLogger(MinioObjectLockConfigurationService.class);
    private final Converter<ObjectLockConfiguration, ObjectLockConfigurationDomain> toDo;

    public MinioObjectLockConfigurationService(MinioClientObjectPool minioClientObjectPool) {
        super(minioClientObjectPool);
        this.toDo = new ObjectLockConfigurationToDomainConverter();
    }

    /**
     * 获取对象锁定配置
     *
     * @param bucketName 存储桶名称
     * @return {@link ObjectLockConfiguration}
     */
    public ObjectLockConfiguration getObjectLockConfiguration(String bucketName) {
        return getObjectLockConfiguration(bucketName, null);
    }

    /**
     * 获取对象锁定配置
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @return {@link ObjectLockConfiguration}
     */
    public ObjectLockConfiguration getObjectLockConfiguration(String bucketName, String region) {
        return getObjectLockConfiguration(GetObjectLockConfigurationArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 获取对象锁定配置
     *
     * @param getObjectLockConfigurationArgs {@link GetObjectLockConfigurationArgs}
     * @return {@link ObjectLockConfiguration}
     */
    public ObjectLockConfiguration getObjectLockConfiguration(GetObjectLockConfigurationArgs getObjectLockConfigurationArgs) {
        String function = "getObjectLockConfiguration";
        MinioClient minioClient = getClient();

        try {
            return minioClient.getObjectLockConfiguration(getObjectLockConfigurationArgs);
        } catch (ErrorResponseException e) {
            // 如果没有设置过 ObjectLock getObjectLockConfiguration 方法会抛出 ErrorResponseException
            return null;
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new OssInsufficientDataException(e.getMessage());
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new OssInternalException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException(e.getMessage());
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new OssInvalidResponseException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException(e.getMessage());
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in createBucket.", e);
            throw new OssXmlParserException(e.getMessage());
        } finally {
            close(minioClient);
        }
    }

    /**
     * 设置对象锁定
     *
     * @param bucketName 存储桶名称
     * @param config     对象锁定配置 {@link ObjectLockConfiguration}
     */
    public void setObjectLockConfiguration(String bucketName, ObjectLockConfiguration config) {
        setObjectLockConfiguration(bucketName, null, config);
    }

    /**
     * 设置对象锁定
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param config     对象锁定配置 {@link ObjectLockConfiguration}
     */
    public void setObjectLockConfiguration(String bucketName, String region, ObjectLockConfiguration config) {
        setObjectLockConfiguration(SetObjectLockConfigurationArgs.builder().bucket(bucketName).region(region).config(config).build());
    }

    /**
     * 设置对象锁定
     *
     * @param setObjectLockConfigurationArgs {@link SetObjectLockConfigurationArgs}
     */
    public void setObjectLockConfiguration(SetObjectLockConfigurationArgs setObjectLockConfigurationArgs) {
        String function = "setObjectLockConfiguration";
        MinioClient minioClient = getClient();

        try {
            minioClient.setObjectLockConfiguration(setObjectLockConfigurationArgs);
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new OssErrorResponseException(e.getMessage());
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new OssInsufficientDataException(e.getMessage());
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new OssInternalException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException(e.getMessage());
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new OssInvalidResponseException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException(e.getMessage());
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in [{}].", function, e);
            throw new OssXmlParserException(e.getMessage());
        } finally {
            close(minioClient);
        }
    }

    /**
     * 删除对象锁定配置
     *
     * @param bucketName 存储桶名称
     */
    public void deleteObjectLockConfiguration(String bucketName) {
        deleteObjectLockConfiguration(bucketName, null);
    }

    /**
     * 删除对象锁定配置
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     */
    public void deleteObjectLockConfiguration(String bucketName, String region) {
        deleteObjectLockConfiguration(DeleteObjectLockConfigurationArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 删除对象锁定
     *
     * @param deleteObjectLockConfigurationArgs {@link DeleteObjectLockConfigurationArgs}
     */
    public void deleteObjectLockConfiguration(DeleteObjectLockConfigurationArgs deleteObjectLockConfigurationArgs) {
        String function = "deleteObjectLockConfiguration";
        MinioClient minioClient = getClient();

        try {
            minioClient.deleteObjectLockConfiguration(deleteObjectLockConfigurationArgs);
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new OssErrorResponseException(e.getMessage());
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new OssInsufficientDataException(e.getMessage());
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new OssInternalException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException(e.getMessage());
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new OssInvalidResponseException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException(e.getMessage());
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in [{}].", function, e);
            throw new OssXmlParserException(e.getMessage());
        } finally {
            close(minioClient);
        }
    }
}
