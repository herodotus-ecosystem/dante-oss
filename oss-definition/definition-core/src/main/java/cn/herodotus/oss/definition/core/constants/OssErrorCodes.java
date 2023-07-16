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

package cn.herodotus.oss.definition.core.constants;

import cn.herodotus.engine.assistant.core.definition.constants.ErrorCodes;

/**
 * <p>Description: 对象存储错误代码 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/30 13:15
 */
public interface OssErrorCodes extends ErrorCodes {

    int OSS_CLIENT_POOL_ERROR = OSS_MODULE_500_BEGIN + 1;
    int MINIO_ERROR_RESPONSE = OSS_CLIENT_POOL_ERROR + 1;
    int MINIO_INSUFFICIENT_DATA = MINIO_ERROR_RESPONSE + 1;
    int MINIO_INTERNAL = MINIO_INSUFFICIENT_DATA + 1;
    int MINIO_INVALID_KEY = MINIO_INTERNAL + 1;
    int MINIO_INVALID_RESPONSE = MINIO_INVALID_KEY + 1;
    int MINIO_IO = MINIO_INVALID_RESPONSE + 1;
    int MINIO_NO_SUCH_ALGORITHM = MINIO_IO + 1;
    int MINIO_SERVER = MINIO_NO_SUCH_ALGORITHM + 1;
    int MINIO_XML_PARSER = MINIO_SERVER + 1;
    int MINIO_EXECUTION = MINIO_XML_PARSER + 1;
    int MINIO_INTERRUPTED = MINIO_EXECUTION + 1;
    int MINIO_BUCKET_POLICY_TOO_LARGE = MINIO_INTERRUPTED + 1;
    int MINIO_INVALID_CIPHER_TEXT = MINIO_BUCKET_POLICY_TOO_LARGE + 1;

    int MINIO_CONNECTION = OSS_MODULE_503_BEGIN + 1;
}
