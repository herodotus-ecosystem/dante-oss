/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus Cloud.
 *
 * Herodotus Cloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus Cloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.vip>.
 */

package cn.herodotus.oss.dialect.reactive.minio.config;

import cn.herodotus.oss.core.minio.definition.pool.MinioAdminClientObjectPool;
import cn.herodotus.oss.core.minio.definition.pool.MinioAdminClientPooledObjectFactory;
import cn.herodotus.oss.core.minio.definition.pool.MinioAsyncClientObjectPool;
import cn.herodotus.oss.core.minio.definition.pool.MinioAsyncClientPooledObjectFactory;
import cn.herodotus.oss.core.minio.properties.MinioProperties;
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
        log.debug("[Herodotus] |- Module [Minio Client] Auto Configure.");
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
