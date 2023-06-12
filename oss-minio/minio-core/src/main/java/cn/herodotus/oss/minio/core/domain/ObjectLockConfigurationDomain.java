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

package cn.herodotus.oss.minio.core.domain;

import cn.herodotus.engine.assistant.core.definition.domain.Entity;
import cn.herodotus.oss.minio.core.enums.RetentionDurationEnums;
import cn.herodotus.oss.minio.core.enums.RetentionModeEnums;

/**
 * <p>Description: Minio ObjectLockConfiguration 对应 Domain Object </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/5 20:55
 */
public class ObjectLockConfigurationDomain implements Entity {

    /**
     * 保留模式
     */
    private RetentionModeEnums retentionMode;

    /**
     * 保留周期模式
     */
    private RetentionDurationEnums durationMode;

    /**
     * 保留时长
     */
    private Integer duration;

    public RetentionModeEnums getRetentionMode() {
        return retentionMode;
    }

    public void setRetentionMode(RetentionModeEnums retentionMode) {
        this.retentionMode = retentionMode;
    }

    public RetentionDurationEnums getDurationMode() {
        return durationMode;
    }

    public void setDurationMode(RetentionDurationEnums durationMode) {
        this.durationMode = durationMode;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
