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

import cn.herodotus.oss.minio.core.exception.MinioConnectException;
import cn.herodotus.oss.minio.core.exception.MinioIOException;
import cn.herodotus.oss.minio.core.exception.MinioInvalidKeyException;
import cn.herodotus.oss.minio.core.exception.MinioNoSuchAlgorithmException;
import cn.herodotus.oss.minio.logic.definition.pool.MinioAdminClientObjectPool;
import cn.herodotus.oss.minio.logic.definition.service.BaseMinioAdminClientService;
import io.minio.admin.MinioAdminClient;
import io.minio.admin.messages.DataUsageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ConnectException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>Description: Minio 管理服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/25 10:45
 */
@Service
public class MinioAdminService extends BaseMinioAdminClientService {

    private static final Logger log = LoggerFactory.getLogger(MinioAdminService.class);

    public MinioAdminService(MinioAdminClientObjectPool minioAdminClientObjectPool) {
        super(minioAdminClientObjectPool);
    }

    /**
     * 获取服务器/群集数据使用情况信息
     *
     * @return {@link DataUsageInfo}
     */
    public DataUsageInfo getDataUsageInfo() {
        String function = "getDataUsageInfo";

        MinioAdminClient minioAdminClient = getMinioAdminClient();

        try {
            return minioAdminClient.getDataUsageInfo();
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new MinioNoSuchAlgorithmException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new MinioInvalidKeyException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new MinioConnectException(e.getMessage());
            } else {
                throw new MinioIOException(e.getMessage());
            }
        } finally {
            close(minioAdminClient);
        }
    }


}
