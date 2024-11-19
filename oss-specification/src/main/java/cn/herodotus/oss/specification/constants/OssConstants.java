/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante OSS Licensed under the Apache License, Version 2.0 (the "License");
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

package cn.herodotus.oss.specification.constants;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/17 14:14
 */
public interface OssConstants {

    // allowed maximum object size is 5TiB.
    /**
     * 允许的对象大小，最大为 5T
     */
    long MAX_OBJECT_SIZE = 5L * 1024 * 1024 * 1024 * 1024;
    // allowed minimum part size is 5MiB in multipart upload.
    /**
     * 分片上传中，允许的分片大小最小为 5M
     */
    int MIN_MULTIPART_SIZE = 5 * 1024 * 1024;
    /**
     * 分片上传中，允许的分片大小最大为 5G
     */
    long MAX_PART_SIZE = 5L * 1024 * 1024 * 1024;
    /**
     * 分片上传中，允许的最大分片数量为 1000
     */
    int MAX_MULTIPART_COUNT = 10000;
}
