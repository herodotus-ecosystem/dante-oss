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
import io.minio.admin.UserInfo;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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

    public MinioAdminUserService(MinioAdminClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 获取 Minio 用户列表
     *
     * @return Map<String, UserInfo>
     */
    public Mono<Map<String, UserInfo>> listUsers() {
        return just("listUsers", MinioAdminClient::listUsers);
    }

    /**
     * 获取指定MinIO用户的用户信息
     *
     * @param accessKey 访问密钥
     * @return {@link UserInfo}
     */
    public Mono<UserInfo> getUserInfo(String accessKey) {
        return just("getUserInfo", client -> client.getUserInfo(accessKey));
    }

    public Mono<Void> addUser(@Nonnull String accessKey, @Nonnull UserInfo.Status status, @Nullable String secretKey, @Nullable String policyName, @Nullable List<String> memberOf) {
        return empty("addUser", client -> client.addUser(accessKey, status, secretKey, policyName, memberOf));
    }

    /**
     * 通过用户的访问密钥删除用户
     *
     * @param accessKey 访问密钥
     */
    public Mono<Void> deleteUser(@Nonnull String accessKey) {
        return empty("deleteUser", client -> client.deleteUser(accessKey));
    }
}
