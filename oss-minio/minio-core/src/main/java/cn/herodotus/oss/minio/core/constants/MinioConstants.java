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

package cn.herodotus.oss.minio.core.constants;

import cn.herodotus.engine.assistant.core.definition.constants.BaseConstants;

/**
 * <p>Description: 对象存储常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/19 16:26
 */
public interface MinioConstants extends BaseConstants {

    String MINIO_CHUNK_UPLOAD_REQUEST_MAPPING = "/oss/minio/chunk";
    String MINIO_PRESIGNED_OBJECT_REQUEST_MAPPING = "/presigned";
    String MINIO_PRESIGNED_OBJECT_PROXY = MINIO_PRESIGNED_OBJECT_REQUEST_MAPPING + "/*/*";
    String PRESIGNED_OBJECT_URL_PROXY = MINIO_CHUNK_UPLOAD_REQUEST_MAPPING + MINIO_PRESIGNED_OBJECT_REQUEST_MAPPING;

    String PROPERTY_OSS_MINIO = PROPERTY_PREFIX_OSS + ".minio";

    String ITEM_MINIO_ENDPOINT = PROPERTY_OSS_MINIO + ".endpoint";
    String ITEM_MINIO_ACCESSKEY = PROPERTY_OSS_MINIO + ".access-key";
    String ITEM_MINIO_SECRETKEY = PROPERTY_OSS_MINIO + ".secret-key";
}
