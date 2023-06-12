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

import cn.herodotus.oss.minio.core.enums.PolicyEnums;
import cn.herodotus.oss.minio.core.exception.*;
import cn.herodotus.oss.minio.logic.definition.pool.MinioClientObjectPool;
import cn.herodotus.oss.minio.logic.definition.service.BaseMinioService;
import com.google.common.base.Enums;
import io.minio.DeleteBucketPolicyArgs;
import io.minio.GetBucketPolicyArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import io.minio.errors.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ConnectException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>Description: Bucket 访问策略 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 15:44
 */
@Service
public class BucketPolicyService extends BaseMinioService {

    private static final Logger log = LoggerFactory.getLogger(BucketPolicyService.class);

    public BucketPolicyService(MinioClientObjectPool minioClientObjectPool) {
        super(minioClientObjectPool);
    }

    /**
     * 获取 Bucket 访问策略配置
     *
     * @param bucketName 存储桶名称
     * @return 自定义策略枚举 {@link PolicyEnums}
     */
    public PolicyEnums getBucketPolicy(String bucketName) {
        return getBucketPolicy(bucketName, null);
    }

    /**
     * 获取 Bucket 访问策略配置
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @return 自定义策略枚举 {@link PolicyEnums}
     */
    public PolicyEnums getBucketPolicy(String bucketName, String region) {
        return getBucketPolicy(GetBucketPolicyArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 获取 Bucket 访问策略配置
     *
     * @param getBucketPolicyArgs {@link GetBucketPolicyArgs}
     */
    public PolicyEnums getBucketPolicy(GetBucketPolicyArgs getBucketPolicyArgs) {
        String function = "getBucketPolicy";
        MinioClient minioClient = getMinioClient();

        try {
            String policy = minioClient.getBucketPolicy(getBucketPolicyArgs);
            if (StringUtils.isNotBlank(policy)) {
                return Enums.getIfPresent(PolicyEnums.class, policy).or(PolicyEnums.PRIVATE);
            } else {
                return PolicyEnums.PRIVATE;
            }
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
        } catch (BucketPolicyTooLargeException e) {
            log.error("[Herodotus] |- Minio catch BucketPolicyTooLargeException in [{}].", function, e);
            throw new MinioBucketPolicyTooLargeException(e.getMessage());
        } finally {
            close(minioClient);
        }
    }

    /**
     * 设置 Bucket 访问策略
     *
     * @param bucketName 存储桶名称
     * @param config     策略配置
     */
    public void setBucketPolicy(String bucketName, String config) {
        setBucketPolicy(bucketName, null, config);
    }

    /**
     * 设置 Bucket 访问策略
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param config     策略配置
     */
    public void setBucketPolicy(String bucketName, String region, String config) {
        setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucketName).region(region).config(config).build());
    }

    /**
     * 设置 Bucket 访问策略
     *
     * @param setBucketPolicyArgs {@link SetBucketPolicyArgs}
     */
    public void setBucketPolicy(SetBucketPolicyArgs setBucketPolicyArgs) {
        String function = "setBucketPolicy";
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.setBucketPolicy(setBucketPolicyArgs);
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
            log.error("[Herodotus] |- Minio catch XmlParserException in createBucket.", e);
            throw new MinioXmlParserException(e.getMessage());
        } finally {
            close(minioClient);
        }
    }

    /**
     * 删除 Bucket 访问策略
     *
     * @param bucketName 存储桶名称
     */
    public void deleteBucketPolicy(String bucketName) {
        deleteBucketPolicy(bucketName, null);
    }

    /**
     * 删除 Bucket 访问策略
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     */
    public void deleteBucketPolicy(String bucketName, String region) {
        deleteBucketPolicy(DeleteBucketPolicyArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 删除 Bucket 访问策略
     *
     * @param deleteBucketPolicyArgs {@link DeleteBucketPolicyArgs}
     */
    public void deleteBucketPolicy(DeleteBucketPolicyArgs deleteBucketPolicyArgs) {
        String function = "deleteBucketPolicy";
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.deleteBucketPolicy(deleteBucketPolicyArgs);
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
            log.error("[Herodotus] |- Minio catch XmlParserException in createBucket.", e);
            throw new MinioXmlParserException(e.getMessage());
        } finally {
            close(minioClient);
        }
    }
}
