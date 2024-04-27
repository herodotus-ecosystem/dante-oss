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

package cn.herodotus.oss.core.minio.definition.pool;

import com.google.common.collect.Multimap;
import io.minio.*;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.XmlParserException;
import io.minio.messages.Part;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;

/**
 * <p>Description: 自定义扩展 Minio Client </p>
 * <p>
 * 扩展 MinioAsyncClient 主要为了解决部分分片上传方法为 protected 无法调用的问题。
 *
 * @author : gengwei.zheng
 * @date : 2022/7/3 4:31
 */
public class MinioAsyncClient extends io.minio.MinioAsyncClient {

    public MinioAsyncClient(io.minio.MinioAsyncClient client) {
        super(client);
    }

    /**
     * 创建分片上传
     *
     * @param bucketName       存储桶名称.
     * @param region           区域 (可选).
     * @param objectName       对象名称.
     * @param headers          额外消息头 (可选).
     * @param extraQueryParams 额外查询参数 (可选).
     * @return 创建分片上传响应对象 {@link CreateMultipartUploadResponse}
     * @throws InsufficientDataException 数据不足错误
     * @throws InternalException         内部错误
     * @throws InvalidKeyException       无效的Key错误
     * @throws IOException               IO错误
     * @throws NoSuchAlgorithmException  没有此算法错误
     * @throws XmlParserException        XML解析错误
     */
    @Override
    public CompletableFuture<CreateMultipartUploadResponse> createMultipartUploadAsync(String bucketName, String region, String objectName, Multimap<String, String> headers, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return super.createMultipartUploadAsync(bucketName, region, objectName, headers, extraQueryParams);
    }

    /**
     * 上传分片传
     *
     * @param bucketName       存储桶名称.
     * @param region           区域 (可选).
     * @param objectName       对象名称.
     * @param data             Object data must be InputStream, RandomAccessFile, byte[] or String.
     * @param length           上传对象数据长度.
     * @param uploadId         上传 ID.
     * @param partNumber       分片序号.
     * @param extraHeaders     额外消息头 (可选).
     * @param extraQueryParams 额外查询参数 (可选).
     * @return 上传分片传响应对象 {@link UploadPartResponse}
     * @throws InsufficientDataException 数据不足错误
     * @throws InternalException         内部错误
     * @throws InvalidKeyException       无效的Key错误
     * @throws IOException               IO错误
     * @throws NoSuchAlgorithmException  没有此算法错误
     * @throws XmlParserException        XML解析错误
     */
    @Override
    public CompletableFuture<UploadPartResponse> uploadPartAsync(String bucketName, String region, String objectName, Object data, long length, String uploadId, int partNumber, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return super.uploadPartAsync(bucketName, region, objectName, data, length, uploadId, partNumber, extraHeaders, extraQueryParams);
    }

    /**
     * 上传分片拷贝
     *
     * @param bucketName       存储桶名称.
     * @param region           区域 (可选).
     * @param objectName       对象名称.
     * @param uploadId         上传 ID.
     * @param partNumber       分片序号.
     * @param headers          额外消息头 (可选).
     * @param extraQueryParams 额外查询参数 (可选).
     * @return 上传分片拷贝传响应对象 {@link UploadPartCopyResponse}
     * @throws InsufficientDataException 数据不足错误
     * @throws InternalException         内部错误
     * @throws InvalidKeyException       无效的Key错误
     * @throws IOException               IO错误
     * @throws NoSuchAlgorithmException  没有此算法错误
     * @throws XmlParserException        XML解析错误
     */
    @Override
    public CompletableFuture<UploadPartCopyResponse> uploadPartCopyAsync(String bucketName, String region, String objectName, String uploadId, int partNumber, Multimap<String, String> headers, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return super.uploadPartCopyAsync(bucketName, region, objectName, uploadId, partNumber, headers, extraQueryParams);
    }

    /**
     * 中止分片上传
     *
     * @param bucketName       存储桶名称.
     * @param region           区域 (可选).
     * @param objectName       对象名称.
     * @param uploadId         上传 ID.
     * @param extraHeaders     额外消息头 (可选).
     * @param extraQueryParams 额外查询参数 (可选).
     * @return 完成分片上传响应对象 {@link AbortMultipartUploadResponse}
     * @throws InsufficientDataException 数据不足错误
     * @throws InternalException         内部错误
     * @throws InvalidKeyException       无效的Key错误
     * @throws IOException               IO错误
     * @throws NoSuchAlgorithmException  没有此算法错误
     * @throws XmlParserException        XML解析错误
     */
    @Override
    public CompletableFuture<AbortMultipartUploadResponse> abortMultipartUploadAsync(String bucketName, String region, String objectName, String uploadId, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return super.abortMultipartUploadAsync(bucketName, region, objectName, uploadId, extraHeaders, extraQueryParams);
    }

    /**
     * 完成分片上传
     *
     * @param bucketName       存储桶名称.
     * @param region           区域 (可选).
     * @param objectName       对象名称.
     * @param uploadId         上传 ID.
     * @param parts            分片数组.
     * @param extraHeaders     额外消息头 (可选).
     * @param extraQueryParams 额外查询参数 (可选).
     * @return 完成分片上传响应对象 {@link ObjectWriteResponse}
     * @throws InsufficientDataException 数据不足错误
     * @throws InternalException         内部错误
     * @throws InvalidKeyException       无效的Key错误
     * @throws IOException               IO错误
     * @throws NoSuchAlgorithmException  没有此算法错误
     * @throws XmlParserException        XML解析错误
     */
    @Override
    public CompletableFuture<ObjectWriteResponse> completeMultipartUploadAsync(String bucketName, String region, String objectName, String uploadId, Part[] parts, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return super.completeMultipartUploadAsync(bucketName, region, objectName, uploadId, parts, extraHeaders, extraQueryParams);
    }


    /**
     * 列出分片
     *
     * @param bucketName       存储桶名称.
     * @param region           区域 (可选).
     * @param objectName       对象名称.
     * @param maxParts         可以获取的最大分片书 (可选).
     * @param partNumberMarker 分片序号标记 (可选).
     * @param uploadId         上传 ID.
     * @param extraHeaders     额外消息头 (可选).
     * @param extraQueryParams 额外查询参数 (可选).
     * @return 列出分片响应对象 {@link ListPartsResponse}
     * @throws InsufficientDataException 数据不足错误
     * @throws InternalException         内部错误
     * @throws InvalidKeyException       无效的Key错误
     * @throws IOException               IO错误
     * @throws NoSuchAlgorithmException  没有此算法错误
     * @throws XmlParserException        XML解析错误
     */
    @Override
    public CompletableFuture<ListPartsResponse> listPartsAsync(String bucketName, String region, String objectName, Integer maxParts, Integer partNumberMarker, String uploadId, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return super.listPartsAsync(bucketName, region, objectName, maxParts, partNumberMarker, uploadId, extraHeaders, extraQueryParams);
    }


    /**
     * 列出正在进行的分片上传
     *
     * @param bucketName       Name of the bucket.
     * @param region           Region of the bucket (可选).
     * @param delimiter        分隔符 (可选).
     * @param encodingType     编码类型 (可选).
     * @param keyMarker        关键标记 (可选).
     * @param maxUploads       最大上传数量 (可选).
     * @param prefix           前缀 (Optional).
     * @param uploadIdMarker   Upload ID 标记 (可选).
     * @param extraHeaders     额外消息头 (可选).
     * @param extraQueryParams 额外查询参数 (可选).
     * @return 列出正在进行的分片上传响应对象 {@link ListMultipartUploadsResponse}
     * @throws InsufficientDataException 数据不足错误
     * @throws InternalException         内部错误
     * @throws InvalidKeyException       无效的Key错误
     * @throws IOException               IO错误
     * @throws NoSuchAlgorithmException  没有此算法错误
     * @throws XmlParserException        XML解析错误
     */
    @Override
    public CompletableFuture<ListMultipartUploadsResponse> listMultipartUploadsAsync(String bucketName, String region, String delimiter, String encodingType, String keyMarker, Integer maxUploads, String prefix, String uploadIdMarker, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return super.listMultipartUploadsAsync(bucketName, region, delimiter, encodingType, keyMarker, maxUploads, prefix, uploadIdMarker, extraHeaders, extraQueryParams);
    }
}
