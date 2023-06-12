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

import cn.herodotus.oss.minio.core.exception.MinioConnectException;
import cn.herodotus.oss.minio.core.exception.MinioIOException;
import cn.herodotus.oss.minio.rest.definition.PutObjectBaseRequest;
import io.minio.UploadObjectArgs;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ConnectException;

/**
 * <p>Description: 上传对象请求参数实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/31 16:14
 */
@Schema(name = "上传对象请求参数实体")
public class UploadObjectRequest extends PutObjectBaseRequest<UploadObjectArgs.Builder, UploadObjectArgs> {

    private static final Logger log = LoggerFactory.getLogger(UploadObjectRequest.class);

    @Schema(name = "文件", description = "可以被访问到的，完整的文件路径")
    @NotEmpty(message = "filename 参数不能为空")
    private String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public void prepare(UploadObjectArgs.Builder builder) {
        try {
            builder.filename(getFilename(), getPartSize());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in UploadObjectRequest.", e);
            if (e instanceof ConnectException) {
                throw new MinioConnectException(e.getMessage());
            } else {
                throw new MinioIOException(e.getMessage());
            }
        }

        builder.contentType(getContentType());
        super.prepare(builder);
    }

    @Override
    public UploadObjectArgs.Builder getBuilder() {
        return UploadObjectArgs.builder();
    }
}
