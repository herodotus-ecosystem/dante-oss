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

package cn.herodotus.oss.dialect.minio.converter.arguments;

import cn.herodotus.oss.dialect.minio.definition.arguments.ArgumentsToBucketConverter;
import cn.herodotus.oss.specification.arguments.bucket.CreateBucketArguments;
import io.minio.MakeBucketArgs;
import org.apache.commons.lang3.ObjectUtils;

/**
 * <p>Description: 统一定义 CreateBucketArguments 转 Minio MakeBucketArgs 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/28 18:21
 */
public class ArgumentsToMakeBucketArgsConverter extends ArgumentsToBucketConverter<CreateBucketArguments, MakeBucketArgs, MakeBucketArgs.Builder> {

    @Override
    public void prepare(CreateBucketArguments arguments, MakeBucketArgs.Builder builder) {
        if (ObjectUtils.isNotEmpty(arguments.getObjectLock())) {
            builder.objectLock(arguments.getObjectLock());
        }
        super.prepare(arguments, builder);
    }

    @Override
    public MakeBucketArgs.Builder getBuilder() {
        return MakeBucketArgs.builder();
    }
}
