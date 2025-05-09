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

package cn.herodotus.oss.dialect.minio.service;

import cn.herodotus.oss.dialect.core.exception.*;
import cn.herodotus.oss.dialect.minio.definition.pool.MinioClientObjectPool;
import cn.herodotus.oss.dialect.minio.definition.service.BaseMinioService;
import io.minio.DisableObjectLegalHoldArgs;
import io.minio.EnableObjectLegalHoldArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ConnectException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>Description: Object 合法持有 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/11 9:46
 */
@Service
public class MinioObjectLegalHoldService extends BaseMinioService {

    private static final Logger log = LoggerFactory.getLogger(MinioObjectLegalHoldService.class);

    public MinioObjectLegalHoldService(MinioClientObjectPool minioClientObjectPool) {
        super(minioClientObjectPool);
    }

    /**
     * 启用对对象的合法保留
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     */
    public void enableObjectLegalHold(String bucketName, String objectName) {
        enableObjectLegalHold(bucketName, null, objectName);
    }

    /**
     * 启用对对象的合法保留
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     */
    public void enableObjectLegalHold(String bucketName, String region, String objectName) {
        enableObjectLegalHold(bucketName, region, objectName, null);
    }

    /**
     * 启用对对象的合法保留
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param versionId  版本ID
     */
    public void enableObjectLegalHold(String bucketName, String region, String objectName, String versionId) {
        enableObjectLegalHold(EnableObjectLegalHoldArgs.builder().bucket(bucketName).region(region).object(objectName).versionId(versionId).build());
    }

    /**
     * 启用对对象的合法保留
     *
     * @param enableObjectLegalHoldArgs {@link EnableObjectLegalHoldArgs}
     */
    public void enableObjectLegalHold(EnableObjectLegalHoldArgs enableObjectLegalHoldArgs) {
        String function = "enableObjectLegalHold";
        MinioClient minioClient = getClient();

        try {
            minioClient.enableObjectLegalHold(enableObjectLegalHoldArgs);
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
     * 禁用对对象的合法保留。
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     */
    public void disableObjectLegalHold(String bucketName, String objectName) {
        disableObjectLegalHold(bucketName, null, objectName);
    }

    /**
     * 禁用对对象的合法保留。
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     */
    public void disableObjectLegalHold(String bucketName, String region, String objectName) {
        disableObjectLegalHold(bucketName, region, objectName, null);
    }

    /**
     * 禁用对对象的合法保留。
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param versionId  版本ID
     */
    public void disableObjectLegalHold(String bucketName, String region, String objectName, String versionId) {
        disableObjectLegalHold(DisableObjectLegalHoldArgs.builder().bucket(bucketName).region(region).object(objectName).versionId(versionId).build());
    }

    /**
     * 禁用对对象的合法保留。
     *
     * @param disableObjectLegalHoldArgs {@link DisableObjectLegalHoldArgs}
     */
    public void disableObjectLegalHold(DisableObjectLegalHoldArgs disableObjectLegalHoldArgs) {
        String function = "disableObjectLegalHold";
        MinioClient minioClient = getClient();

        try {
            minioClient.disableObjectLegalHold(disableObjectLegalHoldArgs);
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
