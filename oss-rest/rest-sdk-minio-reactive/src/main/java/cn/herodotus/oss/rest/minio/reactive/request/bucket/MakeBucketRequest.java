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

package cn.herodotus.oss.rest.minio.reactive.request.bucket;

import cn.herodotus.oss.core.minio.definition.request.BucketRequest;
import io.minio.MakeBucketArgs;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.ObjectUtils;

/**
 * <p>Description: 创建桶是参数实体 </p>
 * <p>
 * 与 Create Bucket 不同，这里仅是创建，Create Bucket 包含桶是否存在检查
 *
 * @author : gengwei.zheng
 * @date : 2022/7/2 0:09
 */
@Schema(name = "创建桶是参数实体", title = "创建桶是参数实体")
public class MakeBucketRequest extends BucketRequest<MakeBucketArgs.Builder, MakeBucketArgs> {

    @Schema(name = "开启对象锁定")
    private Boolean objectLock;

    public Boolean getObjectLock() {
        return objectLock;
    }

    public void setObjectLock(Boolean objectLock) {
        this.objectLock = objectLock;
    }

    @Override
    public void prepare(MakeBucketArgs.Builder builder) {
        if (ObjectUtils.isNotEmpty(getObjectLock())) {
            builder.objectLock(getObjectLock());
        }
        super.prepare(builder);
    }

    @Override
    public MakeBucketArgs.Builder getBuilder() {
        return MakeBucketArgs.builder();
    }
}
