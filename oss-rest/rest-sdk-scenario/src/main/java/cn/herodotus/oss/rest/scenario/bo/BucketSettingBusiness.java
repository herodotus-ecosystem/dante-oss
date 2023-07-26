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

package cn.herodotus.oss.rest.scenario.bo;


import cn.herodotus.engine.assistant.core.definition.domain.Entity;
import cn.herodotus.oss.dialect.minio.domain.ObjectLockConfigurationDomain;
import cn.herodotus.oss.dialect.minio.domain.VersioningConfigurationDomain;
import cn.herodotus.oss.dialect.minio.enums.PolicyEnums;
import cn.herodotus.oss.dialect.minio.enums.SseConfigurationEnums;
import com.google.common.base.MoreObjects;

import java.util.Map;

/**
 * <p>Description: 存储桶基础信息返回实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/5 20:41
 */
public class BucketSettingBusiness implements Entity {

    /**
     * 服务端加密方式
     */
    private SseConfigurationEnums sseConfiguration;

    private PolicyEnums policy;
    /**
     * 标签
     */
    private Map<String, String> tags;

    /**
     * 对象锁定是否开启
     */
    private ObjectLockConfigurationDomain objectLock;

    /**
     * 配额大小
     */
    private Long quota;
    /**
     * 版本设置配置
     */
    private VersioningConfigurationDomain versioning;

    public SseConfigurationEnums getSseConfiguration() {
        return sseConfiguration;
    }

    public void setSseConfiguration(SseConfigurationEnums sseConfiguration) {
        this.sseConfiguration = sseConfiguration;
    }

    public PolicyEnums getPolicy() {
        return policy;
    }

    public void setPolicy(PolicyEnums policy) {
        this.policy = policy;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public ObjectLockConfigurationDomain getObjectLock() {
        return objectLock;
    }

    public void setObjectLock(ObjectLockConfigurationDomain objectLock) {
        this.objectLock = objectLock;
    }

    public Long getQuota() {
        return quota;
    }

    public void setQuota(Long quota) {
        this.quota = quota;
    }

    public VersioningConfigurationDomain getVersioning() {
        return versioning;
    }

    public void setVersioning(VersioningConfigurationDomain versioning) {
        this.versioning = versioning;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("sseConfiguration", sseConfiguration)
                .add("policy", policy)
                .add("tags", tags)
                .add("objectLock", objectLock)
                .add("quota", quota)
                .add("versioning", versioning)
                .toString();
    }
}
