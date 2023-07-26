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

package cn.herodotus.oss.dialect.minio.domain;

import cn.herodotus.engine.assistant.core.definition.domain.Entity;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

/**
 * <p>Description: Minio Item </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/11/8 15:18
 */
public class ObjectDomain implements Entity {

    @Schema(name = "ETag")
    private String etag;
    @Schema(name = "对象名称")
    private String objectName;
    @Schema(name = "最后修改时间")
    private String lastModified;
    @Schema(name = "用户ID")
    private String ownerId;
    @Schema(name = "显示的用户名")
    private String ownerDisplayName;
    @Schema(name = "对象大小")
    private Long size;
    @Schema(name = "存储类型")
    private String storageClass;
    @Schema(name = "是否最新")
    private Boolean latest;
    @Schema(name = "用户自定义元数据")
    private Map<String, String> userMetadata;
    @Schema(name = "是否文件夹")
    private Boolean dir;

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerDisplayName() {
        return ownerDisplayName;
    }

    public void setOwnerDisplayName(String ownerDisplayName) {
        this.ownerDisplayName = ownerDisplayName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getStorageClass() {
        return storageClass;
    }

    public void setStorageClass(String storageClass) {
        this.storageClass = storageClass;
    }

    public Boolean getLatest() {
        return latest;
    }

    public void setLatest(Boolean latest) {
        this.latest = latest;
    }

    public Map<String, String> getUserMetadata() {
        return userMetadata;
    }

    public void setUserMetadata(Map<String, String> userMetadata) {
        this.userMetadata = userMetadata;
    }

    public Boolean getDir() {
        return dir;
    }

    public void setDir(Boolean dir) {
        this.dir = dir;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("etag", etag)
                .add("objectName", objectName)
                .add("lastModified", lastModified)
                .add("ownerId", ownerId)
                .add("ownerDisplayName", ownerDisplayName)
                .add("size", size)
                .add("storageClass", storageClass)
                .add("latest", latest)
                .add("dir", dir)
                .toString();
    }
}
