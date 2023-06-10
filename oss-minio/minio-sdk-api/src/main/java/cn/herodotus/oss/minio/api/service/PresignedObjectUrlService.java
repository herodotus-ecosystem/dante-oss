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
import cn.herodotus.oss.minio.core.exception.*;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PostPolicy;
import io.minio.errors.*;
import io.minio.http.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ConnectException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>Description: Presigned 相关操作 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/4/16 15:57
 */
@Service
public class PresignedObjectUrlService extends BaseMinioService {

    private static final Logger log = LoggerFactory.getLogger(PresignedObjectUrlService.class);

    public PresignedObjectUrlService(MinioClientObjectPool minioClientObjectPool) {
        super(minioClientObjectPool);
    }

    /**
     * 使用此方法，获取对象的上传策略（包含签名、文件信息、路径等），然后使用这些信息采用POST 方法的表单数据上传数据。也就是可以生成一个临时上传的信息对象，第三方可以使用这些信息，就可以上传文件。
     * <p>
     * 一般可用于，前端请求一个上传策略，后端返回给前端，前端使用Post请求+访问策略去上传文件，这可以用于JS+SDK的混合方式集成
     *
     * @param postPolicy {@link PostPolicy}
     * @return {@link  Map}
     */
    public Map<String, String> getPresignedPostFormData(PostPolicy postPolicy) {
        String function = "getPresignedPostFormData";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.getPresignedPostFormData(postPolicy);
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
     * 获取一个指定了 HTTP 方法、到期时间和自定义请求参数的对象URL地址，也就是返回带签名的URL，这个地址可以提供给没有登录的第三方共享访问或者上传对象。
     * <p>
     * 默认有效期 7 天, GET 类型 URL
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @return url string
     */
    public String getPresignedObjectUrl(String bucketName, String objectName) {
        return getPresignedObjectUrl(bucketName, null, objectName);
    }

    /**
     * 获取一个指定了 HTTP 方法、到期时间和自定义请求参数的对象URL地址，也就是返回带签名的URL，这个地址可以提供给没有登录的第三方共享访问或者上传对象。
     * <p>
     * 默认有效期 7 天, GET 类型 URL
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @return url string
     */
    public String getPresignedObjectUrl(String bucketName, String region, String objectName) {
        return getPresignedObjectUrl(bucketName, region, objectName, Method.GET);
    }

    /**
     * 获取一个指定了 HTTP 方法、到期时间和自定义请求参数的对象URL地址，也就是返回带签名的URL，这个地址可以提供给没有登录的第三方共享访问或者上传对象。
     * <p>
     * 默认有效期 7 天
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param method     方法类型 {@link Method}
     * @return url string
     */
    public String getPresignedObjectUrl(String bucketName, String region, String objectName, Method method) {
        return getPresignedObjectUrl(bucketName, region, objectName, method, 7, TimeUnit.DAYS);
    }

    /**
     * 获取一个指定了 HTTP 方法、到期时间和自定义请求参数的对象URL地址，也就是返回带签名的URL，这个地址可以提供给没有登录的第三方共享访问或者上传对象。
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param method     方法类型 {@link Method}
     * @param duration   过期时间
     * @param unit       过期时间单位
     * @return url string
     */
    public String getPresignedObjectUrl(String bucketName, String region, String objectName, Method method, int duration, TimeUnit unit) {
        return getPresignedObjectUrl(bucketName, region, objectName, method, duration, unit, null);
    }

    /**
     * 获取一个指定了 HTTP 方法、到期时间和自定义请求参数的对象URL地址，也就是返回带签名的URL，这个地址可以提供给没有登录的第三方共享访问或者上传对象。
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param method     方法类型 {@link Method}
     * @param duration   过期时间
     * @param unit       过期时间单位
     * @param versionId  版本ID
     * @return url string
     */
    public String getPresignedObjectUrl(String bucketName, String region, String objectName, Method method, int duration, TimeUnit unit, String versionId) {
        return getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .region(region)
                .object(objectName)
                .method(method)
                .expiry(duration, unit)
                .versionId(versionId)
                .build());
    }

    /**
     * 获取一个指定了 HTTP 方法、到期时间和自定义请求参数的对象URL地址，也就是返回带签名的URL，这个地址可以提供给没有登录的第三方共享访问或者上传对象。
     *
     * @param getPresignedObjectUrlArgs {@link GetPresignedObjectUrlArgs}
     * @return url string
     */
    public String getPresignedObjectUrl(GetPresignedObjectUrlArgs getPresignedObjectUrlArgs) {
        String function = "getPresignedObjectUrl";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
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
