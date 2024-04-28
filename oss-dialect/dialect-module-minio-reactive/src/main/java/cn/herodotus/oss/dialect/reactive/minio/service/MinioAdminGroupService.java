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

package cn.herodotus.oss.dialect.reactive.minio.service;

import cn.herodotus.oss.core.minio.definition.pool.MinioAdminClientObjectPool;
import cn.herodotus.oss.dialect.reactive.minio.definition.service.BaseMinioAdminService;
import io.minio.admin.GroupInfo;
import io.minio.admin.MinioAdminClient;
import io.minio.admin.Status;
import org.springframework.stereotype.Service;
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
    public Mono<List<String>> listGroups() {
        return just("listGroups", MinioAdminClient::listGroups);
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
