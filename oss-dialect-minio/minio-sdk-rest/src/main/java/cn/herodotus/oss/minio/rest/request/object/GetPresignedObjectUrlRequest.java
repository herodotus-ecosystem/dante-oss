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

import cn.herodotus.engine.rest.core.annotation.EnumeratedValue;
import cn.herodotus.oss.minio.rest.definition.ObjectVersionRequest;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.http.Method;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>Description: GetPresignedObjectUrlDto </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/3 22:26
 */
@Schema(name = "预签名对象Url", title = "获取一个指定了 HTTP 方法、到期时间和自定义请求参数的对象URL地址，也就是返回带签名的URL，这个地址可以提供给没有登录的第三方共享访问或者上传对象")
public class GetPresignedObjectUrlRequest extends ObjectVersionRequest<GetPresignedObjectUrlArgs.Builder, GetPresignedObjectUrlArgs> {

    @EnumeratedValue(names = {"GET", "HEAD", "POST", "PUT", "DELETE"}, message = "预请求对象URL的值只能是大写   GET、HEAD、POST、PUT 和 DELETE")
    @Schema(name = "对象保留模式", title = "存储模式的值只能是大写 GOVERNANCE 或者 COMPLIANCE")
    private String method = "PUT";
    @Schema(name = "过期时间", type = "integer", title = "单位为秒，默认值为 7 天")
    private Integer expiry = GetPresignedObjectUrlArgs.DEFAULT_EXPIRY_TIME;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getExpiry() {
        return expiry;
    }

    public void setExpiry(Integer expiry) {
        this.expiry = expiry;
    }

    @Override
    public void prepare(GetPresignedObjectUrlArgs.Builder builder) {
        builder.method(Method.valueOf(getMethod()));
        builder.expiry(getExpiry());
        super.prepare(builder);
    }

    @Override
    public GetPresignedObjectUrlArgs.Builder getBuilder() {
        return GetPresignedObjectUrlArgs.builder();
    }
}
