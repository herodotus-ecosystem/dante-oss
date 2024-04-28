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

import cn.herodotus.oss.core.minio.definition.pool.MinioAdminClientObjectPool;
import cn.herodotus.oss.dialect.core.exception.OssConnectException;
import cn.herodotus.oss.dialect.core.exception.OssIOException;
import cn.herodotus.oss.dialect.core.exception.OssInvalidKeyException;
import cn.herodotus.oss.dialect.core.exception.OssNoSuchAlgorithmException;
import cn.herodotus.oss.dialect.minio.definition.service.BaseMinioAdminService;
import io.minio.admin.MinioAdminClient;
import io.minio.admin.QuotaUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.ConnectException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>Description: Minio User 管理服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/25 10:53
 */
@Service
public class MinioBucketQuotaService extends BaseMinioAdminService {

    private static final Logger log = LoggerFactory.getLogger(MinioBucketQuotaService.class);

    public MinioBucketQuotaService(MinioAdminClientObjectPool minioAdminClientObjectPool) {
        super(minioAdminClientObjectPool);
    }

    /**
     * 设置存储桶配额
     *
     * @param bucketName 存储桶名称
     * @param size       配额大小
     * @param unit       配额单位
     */
    public void setBucketQuota(@Nonnull String bucketName, long size, @Nonnull QuotaUnit unit) {
        String function = "setBucketQuota";

        MinioAdminClient minioAdminClient = getClient();

        try {
            minioAdminClient.setBucketQuota(bucketName, size, unit);
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
     * 清除存储桶配额
     *
     * @param bucketName 存储桶名称
     */
    public void clearBucketQuota(@Nonnull String bucketName) {
        setBucketQuota(bucketName, 0, QuotaUnit.KB);
    }

    /**
     * 获取存储桶配额大小
     *
     * @param bucketName 存储桶名称
     * @return 配额大小
     */
    public long getBucketQuota(String bucketName) {
        String function = "getBucketQuota";

        MinioAdminClient minioAdminClient = getClient();

        try {
            return minioAdminClient.getBucketQuota(bucketName);
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
