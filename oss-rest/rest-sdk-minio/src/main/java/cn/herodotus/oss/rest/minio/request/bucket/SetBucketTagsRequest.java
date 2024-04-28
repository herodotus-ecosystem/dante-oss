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

package cn.herodotus.oss.rest.minio.request.bucket;

import cn.herodotus.oss.core.minio.definition.request.BucketRequest;
import io.minio.SetBucketTagsArgs;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Map;

/**
 * <p>Description: 设置存储桶标签请求参数实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/6 22:32
 */
@Schema(name = "设置存储桶标签请求参数实体", title = "设置存储桶标签请求参数实体")
public class SetBucketTagsRequest extends BucketRequest<SetBucketTagsArgs.Builder, SetBucketTagsArgs> {

    @Schema(name = "存储桶标签", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "存储桶标签不能为空")
    private Map<String, String> tags;

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    @Override
    public void prepare(SetBucketTagsArgs.Builder builder) {
        if (ObjectUtils.isNotEmpty(getTags())) {
            builder.tags(getTags());
        }
        super.prepare(builder);
    }

    @Override
    public SetBucketTagsArgs.Builder getBuilder() {
        return SetBucketTagsArgs.builder();
    }
}
