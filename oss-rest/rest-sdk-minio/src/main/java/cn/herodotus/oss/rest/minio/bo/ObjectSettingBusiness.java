/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante OSS licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante OSS 是 Dante Cloud 对象存储组件库 采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante OSS 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.oss.rest.minio.bo;

import cn.herodotus.engine.assistant.definition.domain.base.Entity;
import cn.herodotus.oss.dialect.minio.enums.RetentionModeEnums;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

/**
 * <p>Description: 存储桶基础信息返回实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/11 10:02
 */
public class ObjectSettingBusiness implements Entity {

    @Schema(name = "标签")
    private Map<String, String> tags;
    @Schema(name = "保留模式")
    private RetentionModeEnums retentionMode;
    @Schema(name = "保留截止日期")
    private String retentionRetainUntilDate;
    @Schema(name = "是否合规持有")
    private Boolean legalHold;
    @Schema(name = "是否标记删除")
    private Boolean deleteMarker;
    @Schema(name = "ETag")
    private String etag;
    @Schema(name = "最后修改时间")
    private String lastModified;
    @Schema(name = "对象大小")
    private Long size;
    @Schema(name = "用户自定义元数据")
    private Map<String, String> userMetadata;

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public RetentionModeEnums getRetentionMode() {
        return retentionMode;
    }

    public void setRetentionMode(RetentionModeEnums retentionMode) {
        this.retentionMode = retentionMode;
    }

    public String getRetentionRetainUntilDate() {
        return retentionRetainUntilDate;
    }

    public void setRetentionRetainUntilDate(String retentionRetainUntilDate) {
        this.retentionRetainUntilDate = retentionRetainUntilDate;
    }

    public Boolean getLegalHold() {
        return legalHold;
    }

    public void setLegalHold(Boolean legalHold) {
        this.legalHold = legalHold;
    }

    public Boolean getDeleteMarker() {
        return deleteMarker;
    }

    public void setDeleteMarker(Boolean deleteMarker) {
        this.deleteMarker = deleteMarker;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Map<String, String> getUserMetadata() {
        return userMetadata;
    }

    public void setUserMetadata(Map<String, String> userMetadata) {
        this.userMetadata = userMetadata;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("retentionMode", retentionMode)
                .add("retentionRetainUntilDate", retentionRetainUntilDate)
                .add("legalHold", legalHold)
                .add("deleteMarker", deleteMarker)
                .add("etag", etag)
                .add("lastModified", lastModified)
                .add("size", size)
                .toString();
    }
}
