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
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * <p>Description: Minio 屏蔽策略服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/25 10:55
 */
@Service
public class MinioAdminPolicyService extends BaseMinioAdminService {

    public MinioAdminPolicyService(MinioAdminClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 获取屏蔽策略列表
     *
     * @return 屏蔽策略列表
     */
    public Mono<Map<String, String>> listCannedPolicies() {
        return just("listCannedPolicies", MinioAdminClient::listCannedPolicies);
    }

    /**
     * 创建屏蔽策略
     *
     * @param name   策略名称
     * @param policy 策略
     */
    public Mono<Void> addCannedPolicy(@Nonnull String name, @Nonnull String policy) {
        return empty("addCannedPolicy", client -> client.addCannedPolicy(name, policy));
    }

    /**
     * 移除屏蔽策略
     *
     * @param name 策略名称
     */
    public Mono<Void> removeCannedPolicy(@Nonnull String name) {
        return empty("removeCannedPolicy", client -> client.removeCannedPolicy(name));
    }

    /**
     * 设置屏蔽策略
     *
     * @param userOrGroupName 用户名或组名
     * @param isGroup         是否是组
     * @param policyName      策略名称
     */
    public Mono<Void> setPolicy(@Nonnull String userOrGroupName, boolean isGroup, @Nonnull String policyName) {
        return empty("setPolicy", client -> client.setPolicy(userOrGroupName, isGroup, policyName));
    }
}
