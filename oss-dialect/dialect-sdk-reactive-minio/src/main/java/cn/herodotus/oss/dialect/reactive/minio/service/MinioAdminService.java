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

package cn.herodotus.oss.dialect.reactive.minio.service;

import cn.herodotus.oss.core.minio.definition.pool.MinioAdminClientObjectPool;
import cn.herodotus.oss.dialect.reactive.minio.definition.service.BaseMinioAdminService;
import io.minio.admin.MinioAdminClient;
import io.minio.admin.messages.DataUsageInfo;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>Description: Minio 管理服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/25 10:45
 */
@Service
public class MinioAdminService extends BaseMinioAdminService {

    public MinioAdminService(MinioAdminClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 获取服务器/群集数据使用情况信息
     *
     * @return {@link DataUsageInfo}
     */
    public Mono<DataUsageInfo> getDataUsageInfo() {
        return just("getDataUsageInfo", MinioAdminClient::getDataUsageInfo);
    }
}
