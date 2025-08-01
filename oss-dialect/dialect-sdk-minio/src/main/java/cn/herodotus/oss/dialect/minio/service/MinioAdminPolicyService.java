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

import cn.herodotus.oss.dialect.core.exception.OssConnectException;
import cn.herodotus.oss.dialect.core.exception.OssIOException;
import cn.herodotus.oss.dialect.core.exception.OssInvalidKeyException;
import cn.herodotus.oss.dialect.core.exception.OssNoSuchAlgorithmException;
import cn.herodotus.oss.dialect.minio.definition.pool.MinioAdminClientObjectPool;
import cn.herodotus.oss.dialect.minio.definition.service.BaseMinioAdminService;
import cn.herodotus.oss.dialect.minio.domain.policy.PolicyDomain;
import io.minio.admin.MinioAdminClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ConnectException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * <p>Description: Minio 屏蔽策略服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/25 10:55
 */
@Service
public class MinioAdminPolicyService extends BaseMinioAdminService {

    private static final Logger log = LoggerFactory.getLogger(MinioAdminPolicyService.class);

    public MinioAdminPolicyService(MinioAdminClientObjectPool minioAdminClientObjectPool) {
        super(minioAdminClientObjectPool);
    }

    /**
     * 获取屏蔽策略列表
     *
     * @return 屏蔽策略列表
     */
    public Map<String, String> listCannedPolicies() {
        String function = "listCannedPolicies";

        MinioAdminClient minioAdminClient = getClient();

        try {
            return minioAdminClient.listCannedPolicies();
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

    /**
     * 创建屏蔽策略
     *
     * @param name   策略名称
     * @param policy 策略 {@link PolicyDomain}
     */
    public void addCannedPolicy(String name, String policy) {
        String function = "addCannedPolicy";

        MinioAdminClient minioAdminClient = getClient();

        try {
            minioAdminClient.addCannedPolicy(name, policy);
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

    /**
     * 移除屏蔽策略
     *
     * @param name 策略名称
     */
    public void removeCannedPolicy(String name) {
        String function = "removeCannedPolicy";

        MinioAdminClient minioAdminClient = getClient();

        try {
            minioAdminClient.removeCannedPolicy(name);
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

    /**
     * 设置屏蔽策略
     *
     * @param userOrGroupName 用户名或组名
     * @param isGroup         是否是组
     * @param policyName      策略名称
     */
    public void setPolicy(String userOrGroupName, boolean isGroup, String policyName) {
        String function = "setPolicy";

        MinioAdminClient minioAdminClient = getClient();

        try {
            minioAdminClient.setPolicy(userOrGroupName, isGroup, policyName);
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
