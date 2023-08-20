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

package cn.herodotus.oss.specification.domain.multipart;

import cn.herodotus.oss.specification.core.domain.OssDomain;
import cn.herodotus.oss.specification.domain.base.OwnerDomain;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

/**
 * <p>Description: 分片上传列表返回条目域对象 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/13 20:53
 */
@Schema(name = "分片上传列表返回条目域对象", title = "分片上传列表返回条目域对象")
public class UploadDomain implements OssDomain {

    /**
     * 存储此upload的密钥
     */
    @Schema(name = "对象标记")
    private String key;

    /**
     * 此分片上传的唯一ID
     */
    @Schema(name = "上传ID")
    private String uploadId;

    /**
     * 此分片上传的拥有者
     */
    @Schema(name = "分片上传的拥有者")
    private OwnerDomain owner;

    /**
     * 此分片上传的发起者
     */
    @Schema(name = "分片上传的发起者")
    private OwnerDomain initiator;

    /**
     * 存储类，指示如何存储此分片上传中的数据.
     */
    @Schema(name = "存储类", description = "指示如何存储此分片上传中的数据")
    private String storageClass;

    /**
     * 启动此分片上传的时间
     */
    @Schema(name = "启动此分片上传的时间")
    private Date initiated;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public OwnerDomain getOwner() {
        return owner;
    }

    public void setOwner(OwnerDomain owner) {
        this.owner = owner;
    }

    public OwnerDomain getInitiator() {
        return initiator;
    }

    public void setInitiator(OwnerDomain initiator) {
        this.initiator = initiator;
    }

    public String getStorageClass() {
        return storageClass;
    }

    public void setStorageClass(String storageClass) {
        this.storageClass = storageClass;
    }

    public Date getInitiated() {
        return initiated;
    }

    public void setInitiated(Date initiated) {
        this.initiated = initiated;
    }
}
