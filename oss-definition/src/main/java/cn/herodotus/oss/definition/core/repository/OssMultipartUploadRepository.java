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

package cn.herodotus.oss.definition.core.repository;

import cn.herodotus.oss.definition.arguments.multipart.*;
import cn.herodotus.oss.definition.domain.multipart.*;

/**
 * <p>Description: 兼容 S3 协议的各类 OSS 分片操作抽象定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/12 21:56
 */
public interface OssMultipartUploadRepository {

    /**
     * 创建分片上传请求, 返回 UploadId
     * <p>
     * 启动一个分片上传并返回一个包含 UploadId 的 InitiateMultipartUploadResult。
     * 该UploadId将特定上传中的所有部分关联起来，并在您随后的每个 uploadPart（UploadPartRequest）请求中使用。您还可以在最终请求中包含此UploadId，以完成或中止分片上载请求。
     *
     * @param arguments 创建分片上传请求参数实体 {@link InitiateMultipartUploadArguments}
     * @return 创建分片上传结果 {@link InitiateMultipartUploadDomain}
     */
    InitiateMultipartUploadDomain initiateMultipartUpload(InitiateMultipartUploadArguments arguments);

    /**
     * 在分片上传中上传一个部分。必须先启动分片上传，然后才能上传任何部分。
     * <p>
     * 您的 UploadPart请求必须包括上传 ID、分片号和分片尺寸。上传ID是Amazon S3在响应您的Initiate Multipart upload请求时返回的ID。
     * 分片号可以是介于1和10000之间的任何数字，包括1和10000。分片号唯一标识分片，还定义其在上载对象中的位置。如果使用与上载上一个分片时指定的分片号相同的分片号上载新分片，则会覆盖先前上载的分片
     *
     * @param arguments 部分上传请求参数实体 {@link UploadPartArguments}
     * @return 部分上传复制结果域对象 {@link UploadPartDomain}
     */
    UploadPartDomain uploadPart(UploadPartArguments arguments);

    /**
     * 将源对象复制到分片上传的一部分
     *
     * @param arguments 部分上传复制请求参数实体 {@link UploadPartCopyArguments}
     * @return 部分上传复制结果域对象 {@link InitiateMultipartUploadDomain}
     */
    UploadPartCopyDomain uploadPartCopy(UploadPartCopyArguments arguments);

    /**
     * 通过组装以前上传的部分来完成分片上传。
     *
     * @param arguments 完成分片上传请求参数实体 {@link CompleteMultipartUploadArguments}
     * @return 完成分片上传域对象 {@link CompleteMultipartUploadDomain}
     */
    CompleteMultipartUploadDomain completeMultipartUpload(CompleteMultipartUploadArguments arguments);

    /**
     * 中止分片上载。中止分片上传后，无法使用该上传ID上传任何其他部分。之前上传的任何部分所消耗的存储空间都将被释放。但是，如果当前正在进行任何分片上载，则这些分片上载可能成功，也可能不成功。因此，可能需要多次中止给定的分片上传，以便完全释放所有部分消耗的所有存储。
     *
     * @param arguments 完成分片上传请求参数实体 {@link AbortMultipartUploadArguments}
     * @return 完成分片上传域对象 {@link AbortMultipartUploadDomain}
     */
    AbortMultipartUploadDomain abortMultipartUpload(AbortMultipartUploadArguments arguments);

    /**
     * 获取分片列表
     *
     * @param arguments 获取分片列表请求参数实体 {@link ListPartsArguments}
     * @return 分片列表结果 {@link ListPartsDomain}
     */
    ListPartsDomain listParts(ListPartsArguments arguments);


    /**
     * 列出正在进行的分片上传。进行中的分片上传是指已使用InitiateMultipartUpload请求启动但尚未完成或中止的分片上传
     * <p>
     * 默认情况下，此操作在响应中最多返回1000个分片上传。可以使用请求参数上的MaxUploads属性进一步限制分片上传的数量。
     * 如果有其他满足列表条件的分片上传，则响应将包含一个值设置为true的IsTruncated属性。要列出额外的分片上传，请在请求参数上使用KeyMarker和UploadIdMarker属性。
     *
     * @param arguments 列出正在进行的分片上传请求参数实体 {@link ListMultipartUploadsArguments}
     * @return 列出正在进行的分片上传结果 {@link ListMultipartUploadsDomain}
     */
    ListMultipartUploadsDomain listMultipartUploads(ListMultipartUploadsArguments arguments);


}
