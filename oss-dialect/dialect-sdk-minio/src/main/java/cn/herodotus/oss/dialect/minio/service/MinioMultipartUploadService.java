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

package cn.herodotus.oss.dialect.minio.service;

import cn.herodotus.stirrup.kernel.definition.constants.SymbolConstants;
import cn.herodotus.oss.dialect.core.exception.*;
import cn.herodotus.oss.dialect.minio.definition.pool.MinioAsyncClient;
import cn.herodotus.oss.dialect.minio.definition.pool.MinioAsyncClientObjectPool;
import cn.herodotus.oss.dialect.minio.definition.service.BaseMinioAsyncService;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import io.minio.*;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.XmlParserException;
import io.minio.messages.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ConnectException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * <p>Description: 分片上传服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/3 20:45
 */
@Service
public class MinioMultipartUploadService extends BaseMinioAsyncService {

    private static final Logger log = LoggerFactory.getLogger(MinioMultipartUploadService.class);

    public MinioMultipartUploadService(MinioAsyncClientObjectPool minioAsyncClientObjectPool) {
        super(minioAsyncClientObjectPool);
    }

    protected Multimap<String, String> toMultimap(Map<String, String> map) {
        Multimap<String, String> multimap = HashMultimap.create();
        if (map != null) {
            multimap.putAll(Multimaps.forMap(map));
        }
        return Multimaps.unmodifiableMultimap(multimap);
    }

    /**
     * 创建分片上传
     *
     * @param bucketName 存储桶名称.
     * @param objectName 对象名称.
     * @return 创建分片上传响应对象 {@link CreateMultipartUploadResponse}
     */
    public CreateMultipartUploadResponse createMultipartUpload(String bucketName, String objectName) {
        return createMultipartUpload(bucketName, null, objectName);
    }

    /**
     * 创建分片上传
     *
     * @param bucketName 存储桶名称.
     * @param region     区域 (可选).
     * @param objectName 对象名称.
     * @return 创建分片上传响应对象 {@link CreateMultipartUploadResponse}
     */
    public CreateMultipartUploadResponse createMultipartUpload(String bucketName, String region, String objectName) {
        return createMultipartUpload(bucketName, region, objectName, null, null);
    }

    /**
     * 创建分片上传
     *
     * @param bucketName       存储桶名称.
     * @param region           区域 (可选).
     * @param objectName       对象名称.
     * @param extraHeaders     额外消息头 (可选).
     * @param extraQueryParams 额外查询参数 (可选).
     * @return 创建分片上传响应对象 {@link CreateMultipartUploadResponse}
     */
    public CreateMultipartUploadResponse createMultipartUpload(String bucketName, String region, String objectName, Map<String, String> extraHeaders, Map<String, String> extraQueryParams) {
        String function = "createMultipartUpload";
        MinioAsyncClient client = getClient();

        try {
            return client.createMultipartUploadAsync(bucketName, region, objectName, toMultimap(extraHeaders), toMultimap(extraQueryParams)).get();
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio async catch InsufficientDataException in [{}].", function, e);
            throw new OssInsufficientDataException("Minio async insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio async catch InternalException in [{}].", function, e);
            throw new OssInternalException("Minio async internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio async catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException("Minio async key invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio async catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException("Minio async no such algorithm.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio async catch XmlParserException in [{}].", function, e);
            throw new OssXmlParserException("Minio async xml parser error.");
        } catch (ExecutionException e) {
            log.error("[Herodotus] |- Minio async catch ExecutionException in [{}].", function, e);
            throw new OssExecutionException("Minio async execution error.");
        } catch (InterruptedException e) {
            log.error("[Herodotus] |- Minio async catch InterruptedException in [{}].", function, e);
            throw new OssInterruptedException("Minio async interrupted error.");
        } finally {
            close(client);
        }
    }


    /**
     * 上传分片传
     *
     * @param bucketName 存储桶名称.
     * @param objectName 对象名称.
     * @param data       Object data must be InputStream, RandomAccessFile, byte[] or String.
     * @param length     上传对象数据长度.
     * @param uploadId   上传 ID.
     * @param partNumber 分片序号.
     * @return 上传分片传响应对象 {@link UploadPartResponse}
     */
    public UploadPartResponse uploadPart(String bucketName, String objectName, Object data, long length, String uploadId, int partNumber) {
        return uploadPart(bucketName, null, objectName, data, length, uploadId, partNumber);
    }

    /**
     * 上传分片传
     *
     * @param bucketName 存储桶名称.
     * @param region     区域 (可选).
     * @param objectName 对象名称.
     * @param data       Object data must be InputStream, RandomAccessFile, byte[] or String.
     * @param length     上传对象数据长度.
     * @param uploadId   上传 ID.
     * @param partNumber 分片序号.
     * @return 上传分片传响应对象 {@link UploadPartResponse}
     */
    public UploadPartResponse uploadPart(String bucketName, String region, String objectName, Object data, long length, String uploadId, int partNumber) {
        return uploadPart(bucketName, region, objectName, data, length, uploadId, partNumber, null, null);
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
     */
    public UploadPartResponse uploadPart(String bucketName, String region, String objectName, Object data, long length, String uploadId, int partNumber, Map<String, String> extraHeaders, Map<String, String> extraQueryParams) {
        String function = "uploadPart";
        MinioAsyncClient client = getClient();

        try {
            return client.uploadPartAsync(bucketName, region, objectName, data, length, uploadId, partNumber, toMultimap(extraHeaders), toMultimap(extraQueryParams)).get();
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio async catch InsufficientDataException in [{}].", function, e);
            throw new OssInsufficientDataException("Minio async insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio async catch InternalException in [{}].", function, e);
            throw new OssInternalException("Minio async internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio async catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException("Minio async key invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio async catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException("Minio async no such algorithm.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio async catch XmlParserException in [{}].", function, e);
            throw new OssXmlParserException("Minio async xml parser error.");
        } catch (ExecutionException e) {
            log.error("[Herodotus] |- Minio async catch ExecutionException in [{}].", function, e);
            throw new OssExecutionException("Minio async execution error.");
        } catch (InterruptedException e) {
            log.error("[Herodotus] |- Minio async catch InterruptedException in [{}].", function, e);
            throw new OssInterruptedException("Minio async interrupted error.");
        } finally {
            close(client);
        }
    }

    /**
     * 上传分片拷贝
     *
     * @param bucketName 存储桶名称.
     * @param objectName 对象名称.
     * @param uploadId   上传 ID.
     * @param partNumber 分片序号.
     * @return 上传分片拷贝传响应对象 {@link UploadPartCopyResponse}
     */
    public UploadPartCopyResponse uploadPartCopy(String bucketName, String objectName, String uploadId, int partNumber) {
        return uploadPartCopy(bucketName, null, objectName, uploadId, partNumber);
    }

    /**
     * 上传分片拷贝
     *
     * @param bucketName 存储桶名称.
     * @param region     区域 (可选).
     * @param objectName 对象名称.
     * @param uploadId   上传 ID.
     * @param partNumber 分片序号.
     * @return 上传分片拷贝传响应对象 {@link UploadPartCopyResponse}
     */
    public UploadPartCopyResponse uploadPartCopy(String bucketName, String region, String objectName, String uploadId, int partNumber) {
        return uploadPartCopy(bucketName, region, objectName, uploadId, partNumber, null, null);
    }

    /**
     * 上传分片拷贝
     *
     * @param bucketName       存储桶名称.
     * @param region           区域 (可选).
     * @param objectName       对象名称.
     * @param uploadId         上传 ID.
     * @param partNumber       分片序号.
     * @param extraHeaders     额外消息头 (可选).
     * @param extraQueryParams 额外查询参数 (可选).
     * @return 上传分片拷贝传响应对象 {@link UploadPartCopyResponse}
     */
    public UploadPartCopyResponse uploadPartCopy(String bucketName, String region, String objectName, String uploadId, int partNumber, Map<String, String> extraHeaders, Map<String, String> extraQueryParams) {
        String function = "uploadPartCopy";
        MinioAsyncClient client = getClient();

        try {
            return client.uploadPartCopyAsync(bucketName, region, objectName, uploadId, partNumber, toMultimap(extraHeaders), toMultimap(extraQueryParams)).get();
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio async catch InsufficientDataException in [{}].", function, e);
            throw new OssInsufficientDataException("Minio async insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio async catch InternalException in [{}].", function, e);
            throw new OssInternalException("Minio async internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio async catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException("Minio async key invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio async catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException("Minio async no such algorithm.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio async catch XmlParserException in [{}].", function, e);
            throw new OssXmlParserException("Minio async xml parser error.");
        } catch (ExecutionException e) {
            log.error("[Herodotus] |- Minio async catch ExecutionException in [{}].", function, e);
            throw new OssExecutionException("Minio async execution error.");
        } catch (InterruptedException e) {
            log.error("[Herodotus] |- Minio async catch InterruptedException in [{}].", function, e);
            throw new OssInterruptedException("Minio async interrupted error.");
        } finally {
            close(client);
        }
    }

    /**
     * 中止分片上传
     *
     * @param bucketName 存储桶名称.
     * @param objectName 对象名称.
     * @param uploadId   上传 ID.
     * @return 完成分片上传响应对象 {@link AbortMultipartUploadResponse}
     */
    public AbortMultipartUploadResponse abortMultipartUpload(String bucketName, String objectName, String uploadId) {
        return abortMultipartUpload(bucketName, null, objectName, uploadId);
    }

    /**
     * 中止分片上传
     *
     * @param bucketName 存储桶名称.
     * @param region     区域 (可选).
     * @param objectName 对象名称.
     * @param uploadId   上传 ID.
     * @return 完成分片上传响应对象 {@link AbortMultipartUploadResponse}
     */
    public AbortMultipartUploadResponse abortMultipartUpload(String bucketName, String region, String objectName, String uploadId) {
        return abortMultipartUpload(bucketName, region, objectName, uploadId, null, null);
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
     */
    public AbortMultipartUploadResponse abortMultipartUpload(String bucketName, String region, String objectName, String uploadId, Map<String, String> extraHeaders, Map<String, String> extraQueryParams) {
        String function = "abortMultipartUpload";
        MinioAsyncClient client = getClient();

        try {
            return client.abortMultipartUploadAsync(bucketName, region, objectName, uploadId, toMultimap(extraHeaders), toMultimap(extraQueryParams)).get();
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio async catch InsufficientDataException in [{}].", function, e);
            throw new OssInsufficientDataException("Minio async insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio async catch InternalException in [{}].", function, e);
            throw new OssInternalException("Minio async internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio async catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException("Minio async key invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio async catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException("Minio async no such algorithm.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio async catch XmlParserException in [{}].", function, e);
            throw new OssXmlParserException("Minio async xml parser error.");
        } catch (ExecutionException e) {
            log.error("[Herodotus] |- Minio async catch ExecutionException in [{}].", function, e);
            throw new OssExecutionException("Minio async execution error.");
        } catch (InterruptedException e) {
            log.error("[Herodotus] |- Minio async catch InterruptedException in [{}].", function, e);
            throw new OssInterruptedException("Minio async interrupted error.");
        } finally {
            close(client);
        }
    }

    /**
     * 完成分片上传
     *
     * @param bucketName 存储桶名称.
     * @param objectName 对象名称.
     * @param uploadId   上传 ID.
     * @param parts      分片数组.
     * @return 完成分片上传响应对象 {@link ObjectWriteResponse}
     */
    public ObjectWriteResponse completeMultipartUpload(String bucketName, String objectName, String uploadId, Part[] parts) {
        return completeMultipartUpload(bucketName, null, objectName, uploadId, parts);
    }

    /**
     * 完成分片上传
     *
     * @param bucketName 存储桶名称.
     * @param region     区域 (可选).
     * @param objectName 对象名称.
     * @param uploadId   上传 ID.
     * @param parts      分片数组.
     * @return 完成分片上传响应对象 {@link ObjectWriteResponse}
     */
    public ObjectWriteResponse completeMultipartUpload(String bucketName, String region, String objectName, String uploadId, Part[] parts) {
        return completeMultipartUpload(bucketName, region, objectName, uploadId, parts, null, null);
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
     */
    public ObjectWriteResponse completeMultipartUpload(String bucketName, String region, String objectName, String uploadId, Part[] parts, Map<String, String> extraHeaders, Map<String, String> extraQueryParams) {
        String function = "listParts";
        MinioAsyncClient client = getClient();

        try {
            return client.completeMultipartUploadAsync(bucketName, region, objectName, uploadId, parts, toMultimap(extraHeaders), toMultimap(extraQueryParams)).get();
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio async catch InsufficientDataException in [{}].", function, e);
            throw new OssInsufficientDataException("Minio async insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio async catch InternalException in [{}].", function, e);
            throw new OssInternalException("Minio async internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio async catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException("Minio async key invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio async catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException("Minio async no such algorithm.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio async catch XmlParserException in [{}].", function, e);
            throw new OssXmlParserException("Minio async xml parser error.");
        } catch (ExecutionException e) {
            log.error("[Herodotus] |- Minio async catch ExecutionException in [{}].", function, e);
            throw new OssExecutionException("Minio async execution error.");
        } catch (InterruptedException e) {
            log.error("[Herodotus] |- Minio async catch InterruptedException in [{}].", function, e);
            throw new OssInterruptedException("Minio async interrupted error.");
        } finally {
            close(client);
        }
    }

    /**
     * 列出分片
     *
     * @param bucketName 存储桶名称.
     * @param objectName 对象名称.
     * @param uploadId   上传 ID.
     * @return 列出分片响应对象 {@link ListPartsResponse}
     */
    public ListPartsResponse listParts(String bucketName, String objectName, String uploadId) {
        return listParts(bucketName, objectName, null, uploadId);
    }

    /**
     * 列出分片
     *
     * @param bucketName       存储桶名称.
     * @param objectName       对象名称.
     * @param partNumberMarker 分片序号标记 (可选).
     * @param uploadId         上传 ID.
     * @return 列出分片响应对象 {@link ListPartsResponse}
     */
    public ListPartsResponse listParts(String bucketName, String objectName, Integer partNumberMarker, String uploadId) {
        return listParts(bucketName, objectName, null, partNumberMarker, uploadId);
    }

    /**
     * 列出分片
     *
     * @param bucketName       存储桶名称.
     * @param objectName       对象名称.
     * @param maxParts         可以获取的最大分片书 (可选).
     * @param partNumberMarker 分片序号标记 (可选).
     * @param uploadId         上传 ID.
     * @return 列出分片响应对象 {@link ListPartsResponse}
     */
    public ListPartsResponse listParts(String bucketName, String objectName, Integer maxParts, Integer partNumberMarker, String uploadId) {
        return listParts(bucketName, null, objectName, maxParts, partNumberMarker, uploadId);
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
     * @return 列出分片响应对象 {@link ListPartsResponse}
     */
    public ListPartsResponse listParts(String bucketName, String region, String objectName, Integer maxParts, Integer partNumberMarker, String uploadId) {
        return listParts(bucketName, region, objectName, maxParts, partNumberMarker, uploadId, null, null);
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
     */
    public ListPartsResponse listParts(String bucketName, String region, String objectName, Integer maxParts, Integer partNumberMarker, String uploadId, Map<String, String> extraHeaders, Map<String, String> extraQueryParams) {
        String function = "listParts";
        MinioAsyncClient client = getClient();

        try {
            return client.listPartsAsync(bucketName, region, objectName, maxParts, partNumberMarker, uploadId, toMultimap(extraHeaders), toMultimap(extraQueryParams)).get();
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio async catch InsufficientDataException in [{}].", function, e);
            throw new OssInsufficientDataException("Minio async insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio async catch InternalException in [{}].", function, e);
            throw new OssInternalException("Minio async internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio async catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException("Minio async key invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio async catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException("Minio async no such algorithm.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio async catch XmlParserException in [{}].", function, e);
            throw new OssXmlParserException("Minio async xml parser error.");
        } catch (ExecutionException e) {
            log.error("[Herodotus] |- Minio async catch ExecutionException in [{}].", function, e);
            throw new OssExecutionException("Minio async execution error.");
        } catch (InterruptedException e) {
            log.error("[Herodotus] |- Minio async catch InterruptedException in [{}].", function, e);
            throw new OssInterruptedException("Minio async interrupted error.");
        } finally {
            close(client);
        }
    }

    /**
     * 列出正在进行的分片上传
     *
     * @param bucketName Name of the bucket.
     * @param delimiter  分隔符 (可选).
     * @param maxUploads 最大上传数量 (可选).
     * @param prefix     前缀 (可选).
     * @return 列出正在进行的分片上传响应对象 {@link ListMultipartUploadsResponse}
     */
    public ListMultipartUploadsResponse listMultipartUploads(String bucketName, String delimiter, Integer maxUploads, String prefix) {
        return listMultipartUploads(bucketName, delimiter, null, maxUploads, prefix, null);
    }

    /**
     * 列出正在进行的分片上传
     *
     * @param bucketName Name of the bucket.
     * @return 列出正在进行的分片上传响应对象 {@link ListMultipartUploadsResponse}
     */
    public ListMultipartUploadsResponse listMultipartUploads(String bucketName) {
        return listMultipartUploads(bucketName, null);
    }

    /**
     * 列出正在进行的分片上传
     *
     * @param bucketName Name of the bucket.
     * @param prefix     前缀 (可选).
     * @return 列出正在进行的分片上传响应对象 {@link ListMultipartUploadsResponse}
     */
    public ListMultipartUploadsResponse listMultipartUploads(String bucketName, String prefix) {
        return listMultipartUploads(bucketName, SymbolConstants.FORWARD_SLASH, prefix);
    }

    /**
     * 列出正在进行的分片上传
     *
     * @param bucketName Name of the bucket.
     * @param delimiter  分隔符 (可选).
     * @param prefix     前缀 (可选).
     * @return 列出正在进行的分片上传响应对象 {@link ListMultipartUploadsResponse}
     */
    public ListMultipartUploadsResponse listMultipartUploads(String bucketName, String delimiter, String prefix) {
        return listMultipartUploads(bucketName, delimiter, null, prefix);
    }

    /**
     * 列出正在进行的分片上传
     *
     * @param bucketName     Name of the bucket.
     * @param delimiter      分隔符 (可选).
     * @param keyMarker      关键标记 (可选).
     * @param maxUploads     最大上传数量 (可选).
     * @param prefix         前缀 (可选).
     * @param uploadIdMarker Upload ID 标记 (可选).
     * @return 列出正在进行的分片上传响应对象 {@link ListMultipartUploadsResponse}
     */
    public ListMultipartUploadsResponse listMultipartUploads(String bucketName, String delimiter, String keyMarker, Integer maxUploads, String prefix, String uploadIdMarker) {
        return listMultipartUploads(bucketName, delimiter, null, keyMarker, maxUploads, prefix, uploadIdMarker);
    }

    /**
     * 列出正在进行的分片上传
     *
     * @param bucketName     Name of the bucket.
     * @param delimiter      分隔符 (可选).
     * @param encodingType   编码类型 (可选).
     * @param keyMarker      关键标记 (可选).
     * @param maxUploads     最大上传数量 (可选).
     * @param prefix         前缀 (可选).
     * @param uploadIdMarker Upload ID 标记 (可选).
     * @return 列出正在进行的分片上传响应对象 {@link ListMultipartUploadsResponse}
     */
    public ListMultipartUploadsResponse listMultipartUploads(String bucketName, String delimiter, String encodingType, String keyMarker, Integer maxUploads, String prefix, String uploadIdMarker) {
        return listMultipartUploads(bucketName, null, delimiter, encodingType, keyMarker, maxUploads, prefix, uploadIdMarker);
    }

    /**
     * 列出正在进行的分片上传
     *
     * @param bucketName     Name of the bucket.
     * @param region         Region of the bucket (可选).
     * @param delimiter      分隔符 (可选).
     * @param encodingType   编码类型 (可选).
     * @param keyMarker      关键标记 (可选).
     * @param maxUploads     最大上传数量 (可选).
     * @param prefix         前缀 (可选).
     * @param uploadIdMarker Upload ID 标记 (可选).
     * @return 列出正在进行的分片上传响应对象 {@link ListMultipartUploadsResponse}
     */
    public ListMultipartUploadsResponse listMultipartUploads(String bucketName, String region, String delimiter, String encodingType, String keyMarker, Integer maxUploads, String prefix, String uploadIdMarker) {
        return listMultipartUploads(bucketName, region, delimiter, encodingType, keyMarker, maxUploads, prefix, uploadIdMarker, null, null);
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
     * @param prefix           前缀 (可选).
     * @param uploadIdMarker   Upload ID 标记 (可选).
     * @param extraHeaders     额外消息头 (可选).
     * @param extraQueryParams 额外查询参数 (可选).
     * @return 列出正在进行的分片上传响应对象 {@link ListMultipartUploadsResponse}
     */
    public ListMultipartUploadsResponse listMultipartUploads(String bucketName, String region, String delimiter, String encodingType, String keyMarker, Integer maxUploads, String prefix, String uploadIdMarker, Map<String, String> extraHeaders, Map<String, String> extraQueryParams) {
        String function = "listMultipartUploads";
        MinioAsyncClient client = getClient();

        try {
            return client.listMultipartUploadsAsync(bucketName, region, delimiter, encodingType, keyMarker, maxUploads, prefix, uploadIdMarker, toMultimap(extraHeaders), toMultimap(extraQueryParams)).get();
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio async catch InsufficientDataException in [{}].", function, e);
            throw new OssInsufficientDataException("Minio async insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio async catch InternalException in [{}].", function, e);
            throw new OssInternalException("Minio async internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio async catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException("Minio async key invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio async catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException("Minio async no such algorithm.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio async catch XmlParserException in [{}].", function, e);
            throw new OssXmlParserException("Minio async xml parser error.");
        } catch (ExecutionException e) {
            log.error("[Herodotus] |- Minio async catch ExecutionException in [{}].", function, e);
            throw new OssExecutionException("Minio async execution error.");
        } catch (InterruptedException e) {
            log.error("[Herodotus] |- Minio async catch InterruptedException in [{}].", function, e);
            throw new OssInterruptedException("Minio async interrupted error.");
        } finally {
            close(client);
        }
    }
}
