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

import cn.herodotus.oss.definition.core.exception.*;
import cn.herodotus.oss.minio.logic.definition.pool.MinioAdminClientObjectPool;
import cn.herodotus.oss.minio.logic.definition.service.BaseMinioAdminService;
import io.minio.admin.MinioAdminClient;
import io.minio.admin.UserInfo;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.net.ConnectException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: Minio 用户管理 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/25 14:01
 */
@Service
public class MinioAdminUserService extends BaseMinioAdminService {

    private static final Logger log = LoggerFactory.getLogger(MinioAdminUserService.class);

    public MinioAdminUserService(MinioAdminClientObjectPool minioAdminClientObjectPool) {
        super(minioAdminClientObjectPool);
    }

    /**
     * 获取 Minio 用户列表
     *
     * @return Map<String, UserInfo>
     */
    public Map<String, UserInfo> listUsers() {
        String function = "listUsers";

        MinioAdminClient minioAdminClient = getClient();

        try {
            return minioAdminClient.listUsers();
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } catch (InvalidCipherTextException e) {
            log.error("[Herodotus] |- Minio catch InvalidCipherTextException in [{}].", function, e);
            throw new OssInvalidCipherTextException(e.getMessage());
        } finally {
            close(minioAdminClient);
        }
    }

    /**
     * 获取指定MinIO用户的用户信息
     *
     * @param accessKey 访问密钥
     * @return {@link UserInfo}
     */
    public UserInfo getUserInfo(String accessKey) {
        String function = "getUserInfo";

        MinioAdminClient minioAdminClient = getClient();

        try {
            return minioAdminClient.getUserInfo(accessKey);
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } finally {
            close(minioAdminClient);
        }
    }

    public void addUser(@Nonnull String accessKey, @Nonnull UserInfo.Status status, @Nullable String secretKey, @Nullable String policyName, @Nullable List<String> memberOf) {
        String function = "addUser";

        MinioAdminClient minioAdminClient = getClient();

        try {
            minioAdminClient.addUser(accessKey, status, secretKey, policyName, memberOf);
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } catch (InvalidCipherTextException e) {
            log.error("[Herodotus] |- Minio catch InvalidCipherTextException in [{}].", function, e);
            throw new OssInvalidCipherTextException(e.getMessage());
        } finally {
            close(minioAdminClient);
        }
    }

    /**
     * 通过用户的访问密钥删除用户
     *
     * @param accessKey 访问密钥
     */
    public void deleteUser(@Nonnull String accessKey) {
        String function = "deleteUser";

        MinioAdminClient minioAdminClient = getClient();

        try {
            minioAdminClient.deleteUser(accessKey);
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } finally {
            close(minioAdminClient);
        }
    }
}
