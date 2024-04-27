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
import io.minio.admin.GroupInfo;
import io.minio.admin.MinioAdminClient;
import io.minio.admin.Status;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * <p>Description: Minio Group 管理 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/25 14:01
 */
@Service
public class MinioAdminGroupService extends BaseMinioAdminService {

    public MinioAdminGroupService(MinioAdminClientObjectPool objectPool) {
        super(objectPool);
    }

    /**
     * 获取所有MinIO组的列表
     *
     * @return 组列表
     */
    public Flux<String> listGroups() {
        return just("listGroups", MinioAdminClient::listGroups)
                .flatMapMany(Flux::fromIterable);
    }

    /**
     * 获取指定MinIO组的组信息
     *
     * @param group 组
     * @return 组信息
     */
    public Mono<GroupInfo> getGroupInfo(String group) {
        return just("getGroupInfo", client -> client.getGroupInfo(group));
    }

    /**
     * 添加或者更新 Group
     *
     * @param group       用户组名称
     * @param groupStatus 用户组状态
     * @param members     组内成员
     */
    public Mono<Void> addUpdateGroup(@Nonnull String group, @Nullable Status groupStatus, @Nullable List<String> members) {
        return empty("addUpdateGroup", client -> client.addUpdateGroup(group, groupStatus, members));
    }

    /**
     * 移除组
     *
     * @param group 组名称
     */
    public Mono<Void> removeGroup(@Nonnull String group) {
        return empty("removeGroup", client -> client.removeGroup(group));
    }
}
