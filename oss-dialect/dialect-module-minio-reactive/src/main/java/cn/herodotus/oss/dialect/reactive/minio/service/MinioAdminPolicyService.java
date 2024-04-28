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
