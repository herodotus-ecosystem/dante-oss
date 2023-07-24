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

package cn.herodotus.oss.dialect.minio.configuration;

import cn.herodotus.oss.dialect.minio.definition.pool.*;
import cn.herodotus.oss.dialect.minio.properties.MinioProperties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: Minio Client 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/24 17:53
 */
@Configuration(proxyBeanMethods = false)
public class MinioClientConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MinioClientConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Minio Client] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public MinioClientObjectPool minioClientPool(MinioProperties minioProperties) {
        MinioClientPooledObjectFactory factory = new MinioClientPooledObjectFactory(minioProperties);
        MinioClientObjectPool pool = new MinioClientObjectPool(factory);
        log.trace("[Herodotus] |- Bean [Minio Client Pool] Auto Configure.");
        return pool;
    }

    @Bean
    @ConditionalOnMissingBean
    public MinioAsyncClientObjectPool minioAsyncClientPool(MinioProperties minioProperties) {
        MinioAsyncClientPooledObjectFactory factory = new MinioAsyncClientPooledObjectFactory(minioProperties);
        MinioAsyncClientObjectPool pool = new MinioAsyncClientObjectPool(factory);
        log.trace("[Herodotus] |- Bean [Minio Async Client Pool] Auto Configure.");
        return pool;
    }

    @Bean
    @ConditionalOnMissingBean
    public MinioAdminClientObjectPool minioAdminClientPool(MinioProperties minioProperties) {
        MinioAdminClientPooledObjectFactory factory = new MinioAdminClientPooledObjectFactory(minioProperties);
        MinioAdminClientObjectPool pool = new MinioAdminClientObjectPool(factory);
        log.trace("[Herodotus] |- Bean [Minio Admin Client Pool] Auto Configure.");
        return pool;
    }
}
