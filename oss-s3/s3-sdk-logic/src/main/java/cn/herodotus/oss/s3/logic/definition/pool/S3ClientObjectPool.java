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

package cn.herodotus.oss.s3.logic.definition.pool;

import cn.herodotus.oss.definition.core.exception.OssClientPoolErrorException;
import cn.herodotus.oss.s3.logic.properties.S3Properties;
import com.amazonaws.services.s3.AmazonS3;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <p>Description: Amazon S3 Client 对象池 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/14 16:33
 */
@Component
public class S3ClientObjectPool {

    private static final Logger log = LoggerFactory.getLogger(S3ClientObjectPool.class);

    private final GenericObjectPool<AmazonS3> genericObjectPool;

    public S3ClientObjectPool(S3Properties minioProperties) {

        S3ClientPooledObjectFactory factory = new S3ClientPooledObjectFactory(minioProperties);

        GenericObjectPoolConfig<AmazonS3> config = new GenericObjectPoolConfig<>();
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

    public AmazonS3 getAmazonS3() {
        try {
            AmazonS3 minioClient = genericObjectPool.borrowObject();
            log.debug("[Herodotus] |- Fetch amazon s3 client from object pool.");
            return minioClient;
        } catch (Exception e) {
            log.error("[Herodotus] |- Can not fetch amazon s3 client from pool.", e);
            throw new OssClientPoolErrorException("Can not fetch amazon s3 client from pool.");
        }
    }

    public void close(AmazonS3 minioClient) {
        if (ObjectUtils.isNotEmpty(minioClient)) {
            log.debug("[Herodotus] |- Close minio client.");
            genericObjectPool.returnObject(minioClient);
        }
    }
}
