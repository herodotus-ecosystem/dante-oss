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

package cn.herodotus.oss.minio.rest.request.object;

import cn.herodotus.oss.minio.rest.definition.ObjectReadRequest;
import io.minio.DownloadObjectArgs;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.ObjectUtils;

/**
 * <p>Description: DownloadObjectRequest </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/30 23:49
 */
@Schema(name = "下载对象请求参数实体", title = "下载对象请求参数实体")
public class DownloadObjectRequest extends ObjectReadRequest<DownloadObjectArgs.Builder, DownloadObjectArgs> {

    @NotBlank(message = "filename 不能为空")
    @Schema(name = "文件名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String filename;

    @Schema(name = "是否覆盖")
    private Boolean overwrite;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Boolean getOverwrite() {
        return overwrite;
    }

    public void setOverwrite(Boolean overwrite) {
        this.overwrite = overwrite;
    }

    @Override
    public void prepare(DownloadObjectArgs.Builder builder) {
        builder.filename(getFilename());
        if (ObjectUtils.isNotEmpty(getOverwrite())) {
            builder.overwrite(getOverwrite());
        }
        super.prepare(builder);
    }

    @Override
    public DownloadObjectArgs.Builder getBuilder() {
        return DownloadObjectArgs.builder();
    }
}
