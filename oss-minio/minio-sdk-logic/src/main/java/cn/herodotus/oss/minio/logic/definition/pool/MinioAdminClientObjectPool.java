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

package cn.herodotus.oss.minio.logic.definition.pool;

import cn.herodotus.oss.minio.core.exception.MinioClientPoolErrorException;
import cn.herodotus.oss.minio.logic.properties.MinioProperties;
import io.minio.admin.MinioAdminClient;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Description: Minio Admin Client 对象池 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/24 17:46
 */
public class MinioAdminClientObjectPool {

    private static final Logger log = LoggerFactory.getLogger(MinioAdminClientObjectPool.class);

    private final GenericObjectPool<MinioAdminClient> genericObjectPool;

    public MinioAdminClientObjectPool(MinioProperties minioProperties) {
        MinioAdminClientPooledObjectFactory factory = new MinioAdminClientPooledObjectFactory(minioProperties);

        GenericObjectPoolConfig<MinioAdminClient> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(minioProperties.getPool().getMaxTotal());
        config.setMaxIdle(minioProperties.getPool().getMaxIdle());
        config.setMinIdle(minioProperties.getPool().getMinIdle());
        config.setMaxWait(minioProperties.getPool().getMaxWait());
        config.setMinEvictableIdleTime(minioProperties.getPool().getMinEvictableIdleTime());
        config.setSoftMinEvictableIdleTime(minioProperties.getPool().getSoftMinEvictableIdleTime());
        config.setLifo(minioProperties.getPool().getLifo());
        config.setBlockWhenExhausted(minioProperties.getPool().getBlockWhenExhausted());
        genericObjectPool = new GenericObjectPool<>(factory, config);
    }

    public MinioAdminClient getMinioAdminClient() {
        try {
            MinioAdminClient minioAdminClient = genericObjectPool.borrowObject();
            log.debug("[Herodotus] |- Fetch minio admin client from object pool.");
            return minioAdminClient;
        } catch (Exception e) {
            log.error("[Herodotus] |- Can not fetch minio admin client from pool.", e);
            throw new MinioClientPoolErrorException("Can not fetch minio admin client from pool.");
        }
    }


    public void close(MinioAdminClient minioAdminClient) {
        if (ObjectUtils.isNotEmpty(minioAdminClient)) {
            log.debug("[Herodotus] |- Close minio admin client.");
            genericObjectPool.returnObject(minioAdminClient);
        }
    }
}
