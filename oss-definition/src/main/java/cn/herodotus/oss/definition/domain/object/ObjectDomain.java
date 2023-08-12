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

package cn.herodotus.oss.definition.domain.object;

import cn.herodotus.oss.definition.domain.bucket.BucketDomain;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

/**
 * <p>Description: 对象 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/28 22:46
 */
@Schema(name = "对象")
public class ObjectDomain extends BucketDomain {

    /**
     * 存储此对象的密钥
     */
    @Schema(name = "存储此对象的密钥")
    private String objectName;
    /**
     * ETag。此对象内容的十六进制编码MD5哈希
     */
    @Schema(name = "ETag", description = "此对象内容的十六进制编码MD5哈希")
    private String eTag;
    /**
     * 此对象的大小，以字节为单位
     */
    @Schema(name = "对象大小", description = "以字节为单位")
    private Long size;
    /**
     * 对象最后一次被修改的日期
     */
    @Schema(name = "对象最后一次被修改的日期")
    private Date lastModified;
    /**
     * 存储此对象的存储类
     */
    @Schema(name = "存储此对象的存储类")
    private String storageClass;

    @Schema(name = "是否为文件夹")
    private Boolean isDir;

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getETag() {
        return eTag;
    }

    public void setETag(String eTag) {
        this.eTag = eTag;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getStorageClass() {
        return storageClass;
    }

    public void setStorageClass(String storageClass) {
        this.storageClass = storageClass;
    }

    public Boolean getDir() {
        return isDir;
    }

    public void setDir(Boolean dir) {
        isDir = dir;
    }
}
