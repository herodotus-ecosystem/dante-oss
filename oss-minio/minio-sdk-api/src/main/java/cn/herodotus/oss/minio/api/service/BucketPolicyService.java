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

package cn.herodotus.oss.minio.api.service;

import cn.herodotus.oss.minio.api.definition.pool.MinioClientObjectPool;
import cn.herodotus.oss.minio.api.definition.service.BaseMinioService;
import cn.herodotus.oss.minio.core.converter.PolicyToDoConverter;
import cn.herodotus.oss.minio.core.enums.PolicyEnums;
import cn.herodotus.oss.minio.core.exception.*;
import io.minio.DeleteBucketPolicyArgs;
import io.minio.GetBucketPolicyArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import io.minio.errors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 15:44
 */
public class BucketPolicyService extends BaseMinioService {

    private static final Logger log = LoggerFactory.getLogger(BucketPolicyService.class);
    private final Converter<String, PolicyEnums> toDo;

    public BucketPolicyService(MinioClientObjectPool minioClientObjectPool) {
        super(minioClientObjectPool);
        this.toDo = new PolicyToDoConverter();
    }

    /**
     * 获取 Bucket 通知配置
     *
     * @param getBucketPolicyArgs {@link GetBucketPolicyArgs}
     */
    public PolicyEnums getBucketPolicy(GetBucketPolicyArgs getBucketPolicyArgs) {
        String function = "getBucketPolicy";
        MinioClient minioClient = getMinioClient();

        try {
            return this.toDo.convert(minioClient.getBucketPolicy(getBucketPolicyArgs));
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new MinioErrorResponseException("Minio response error.");
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new MinioInsufficientDataException("Minio insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new MinioInternalException("Minio internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new MinioInvalidKeyException("Minio key invalid.");
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new MinioInvalidResponseException("Minio response invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            throw new MinioIOException("Minio io error.");
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new MinioNoSuchAlgorithmException("Minio no such algorithm.");
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new MinioServerException("Minio server error.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in createBucket.", e);
            throw new MinioXmlParserException("Minio xml parser error.");
        } catch (BucketPolicyTooLargeException e) {
            throw new RuntimeException(e);
        } finally {
            close(minioClient);
        }
    }

    /**
     * 设置 Bucket 策略
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
            throw new MinioErrorResponseException("Minio response error.");
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new MinioInsufficientDataException("Minio insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new MinioInternalException("Minio internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new MinioInvalidKeyException("Minio key invalid.");
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new MinioInvalidResponseException("Minio response invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            throw new MinioIOException("Minio io error.");
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new MinioNoSuchAlgorithmException("Minio no such algorithm.");
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new MinioServerException("Minio server error.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in createBucket.", e);
            throw new MinioXmlParserException("Minio xml parser error.");
        } finally {
            close(minioClient);
        }
    }

    public void deleteBucketPolicy(String bucketName) {
        deleteBucketPolicy(DeleteBucketPolicyArgs.builder().bucket(bucketName).build());
    }

    public void deleteBucketPolicy(DeleteBucketPolicyArgs deleteBucketPolicyArgs) {
        String function = "deleteBucketPolicy";
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.deleteBucketPolicy(deleteBucketPolicyArgs);
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new MinioErrorResponseException("Minio response error.");
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new MinioInsufficientDataException("Minio insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new MinioInternalException("Minio internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new MinioInvalidKeyException("Minio key invalid.");
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new MinioInvalidResponseException("Minio response invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            throw new MinioIOException("Minio io error.");
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new MinioNoSuchAlgorithmException("Minio no such algorithm.");
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new MinioServerException("Minio server error.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in createBucket.", e);
            throw new MinioXmlParserException("Minio xml parser error.");
        } finally {
            close(minioClient);
        }
    }
}
