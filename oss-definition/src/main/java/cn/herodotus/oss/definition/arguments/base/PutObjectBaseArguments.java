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

package cn.herodotus.oss.definition.arguments.base;

import cn.herodotus.oss.definition.constants.OssConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * <p>Description: 基础 PutObjectBase 请求参数实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/15 15:16
 */
public abstract class PutObjectBaseArguments extends ObjectWriteArguments {

    /**
     * Sets stream to upload. Two ways to provide object/part sizes.
     * If object size is unknown, pass -1 to objectSize and pass valid partSize.
     * If object size is known, pass -1 to partSize for auto detect; else pass valid partSize to control memory usage and no. of parts in upload.
     * If partSize is greater than objectSize, objectSize is used as partSize.
     * A valid part size is between 5MiB to 5GiB (both limits inclusive).
     */
    @Schema(name = "对象的大小", description = "对象的大小最大不能超过 5T")
    @NotNull(message = "必须设置对象大小")
    @Max(value = OssConstants.MAX_OBJECT_SIZE, message = "对象允许的最大 Size 为 5TiB")
    protected Long objectSize;

    @Schema(name = "分片的大小", description = "分片的大小只能 >= 5M 同时 <= 5G")
    @Min(value = OssConstants.MIN_MULTIPART_SIZE, message = "分片最小Size不能小于 5MiB")
    @Max(value = OssConstants.MAX_PART_SIZE, message = "分片最小Size不能超过 is 5GiB ")
    protected Long partSize;

    @Schema(name = "Content Type", description = "Minio Content Type 获取途径：1.本参数；2. Header 中的 Content-Type 头；3. 从 file 的 content type; 4. 默认为 application/octet-stream ")
    protected String contentType;

    public Long getObjectSize() {
        return objectSize;
    }

    public void setObjectSize(Long objectSize) {
        this.objectSize = objectSize;
    }

    public Long getPartSize() {
        return partSize;
    }

    public void setPartSize(Long partSize) {
        this.partSize = partSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
