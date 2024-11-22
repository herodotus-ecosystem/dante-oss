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

package cn.herodotus.oss.dialect.core.constants;

import cn.herodotus.engine.assistant.definition.feedback.InternalServerErrorFeedback;
import cn.herodotus.engine.assistant.definition.feedback.ServiceUnavailableFeedback;

/**
 * <p>Description: 对象存储错误代码 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 13:15
 */
public interface OssErrorCodes {

    InternalServerErrorFeedback OSS_CLIENT_POOL_ERROR = new InternalServerErrorFeedback("无法从Oss对象池中获取对象");
    InternalServerErrorFeedback OSS_ERROR_RESPONSE = new InternalServerErrorFeedback("对象存储服务器返回错误响应");
    InternalServerErrorFeedback OSS_INSUFFICIENT_DATA = new InternalServerErrorFeedback("对象存储服务器返回数据不足");
    InternalServerErrorFeedback OSS_INTERNAL = new InternalServerErrorFeedback("对象存储服务器内部错误");
    InternalServerErrorFeedback OSS_INVALID_KEY = new InternalServerErrorFeedback("对象存储使用无效的秘钥");
    InternalServerErrorFeedback OSS_INVALID_RESPONSE = new InternalServerErrorFeedback("对象存储返回无效的响应");
    InternalServerErrorFeedback OSS_IO = new InternalServerErrorFeedback("对象存储出现IO错误");
    InternalServerErrorFeedback OSS_NO_SUCH_ALGORITHM = new InternalServerErrorFeedback("使用对象存储不支持算法错误");
    InternalServerErrorFeedback OSS_SERVER = new InternalServerErrorFeedback("对象存储服务器出现错误");
    InternalServerErrorFeedback OSS_XML_PARSER = new InternalServerErrorFeedback("对象存储 XML 解析出现错误");
    InternalServerErrorFeedback OSS_EXECUTION = new InternalServerErrorFeedback("对象存储服务器异步执行错误");
    InternalServerErrorFeedback OSS_INTERRUPTED = new InternalServerErrorFeedback("对象存储服务器异步执行中断错误");
    InternalServerErrorFeedback OSS_BUCKET_POLICY_TOO_LARGE = new InternalServerErrorFeedback("存储桶访问策略过大");
    InternalServerErrorFeedback OSS_INVALID_CIPHER_TEXT = new InternalServerErrorFeedback("无效密码文本错误");

    ServiceUnavailableFeedback OSS_CONNECTION = new ServiceUnavailableFeedback("Minio 服务器无法访问或未启动");
}
