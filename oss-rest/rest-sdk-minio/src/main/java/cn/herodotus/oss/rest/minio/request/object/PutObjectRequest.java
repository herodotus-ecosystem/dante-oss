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

package cn.herodotus.oss.rest.minio.request.object;

import cn.herodotus.oss.rest.minio.definition.PutObjectBaseRequest;
import io.minio.PutObjectArgs;
import io.minio.PutObjectBaseArgs;
import org.apache.commons.lang3.ObjectUtils;

import java.io.BufferedInputStream;

/**
 * <p>Description: PutObjectDto </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/2 22:31
 */
public class PutObjectRequest extends PutObjectBaseRequest<PutObjectArgs.Builder, PutObjectArgs> {

    private BufferedInputStream stream;

    public BufferedInputStream getStream() {
        return stream;
    }

    public void setStream(BufferedInputStream stream) {
        this.stream = stream;
    }

    /**
     * Sets stream to upload. Two ways to provide object/part sizes.
     * <p>
     * · If object size is unknown, pass -1 to objectSize and pass valid partSize.
     * · If object size is known, pass -1 to partSize for auto detect; else pass valid partSize to control memory usage and no. of parts in upload.
     * · If partSize is greater than objectSize, objectSize is used as partSize.
     * <p>
     * A valid part size is between 5MiB to 5GiB (both limits inclusive).
     *
     * @param builder Minio 参数构造器
     */
    @Override
    public void prepare(PutObjectArgs.Builder builder) {

        if (ObjectUtils.isNotEmpty(getObjectSize())) {
            if (ObjectUtils.isNotEmpty(getPartSize())) {
                if (getPartSize() > getObjectSize()) {
                    builder.stream(getStream(), getObjectSize(), getObjectSize());
                } else {
                    builder.stream(getStream(), getObjectSize(), getPartSize());
                }
            } else {
                builder.stream(getStream(), getObjectSize(), -1);
            }
        } else {
            builder.stream(getStream(), -1, PutObjectBaseArgs.MAX_PART_SIZE);
        }

        super.prepare(builder);
    }

    @Override
    public PutObjectArgs.Builder getBuilder() {
        return PutObjectArgs.builder();
    }
}
