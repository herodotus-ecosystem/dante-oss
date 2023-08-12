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

package cn.herodotus.oss.definition.domain.bucket;

import cn.herodotus.engine.assistant.core.definition.constants.DefaultConstants;
import cn.herodotus.oss.definition.domain.base.OssDomain;
import cn.herodotus.oss.definition.domain.base.OwnerDomain;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

/**
 * <p>Description: 统一存储桶域对象定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/27 15:44
 */
@Schema(name = "存储桶")
public class BucketDomain implements OssDomain {

    /**
     * 存储桶名称
     */
    @Schema(name = "存储桶名称")
    private String bucketName;

    /**
     * 存储桶所有者信息
     */
    @Schema(name = "存储桶所有者信息", description = "Minio listBuckets API 返回的 Bucket 信息中不包含 Owner 信息")
    private OwnerDomain owner;

    /**
     * 存储桶创建时间
     */
    @Schema(name = "存储桶创建时间")
    @JsonFormat(pattern = DefaultConstants.DATE_TIME_FORMAT)
    private Date creationDate;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public OwnerDomain getOwner() {
        return owner;
    }

    public void setOwner(OwnerDomain owner) {
        this.owner = owner;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", bucketName)
                .add("owner", owner)
                .add("creationDate", creationDate)
                .toString();
    }
}
