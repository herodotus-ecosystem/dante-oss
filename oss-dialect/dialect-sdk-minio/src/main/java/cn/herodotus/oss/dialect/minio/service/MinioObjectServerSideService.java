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

import cn.herodotus.oss.dialect.core.exception.*;
import cn.herodotus.oss.dialect.minio.definition.pool.MinioClientObjectPool;
import cn.herodotus.oss.dialect.minio.definition.service.BaseMinioService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Retention;
import io.minio.messages.Tags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ConnectException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>Description: Minio 服务端上传下载 </p>
 * <p>
 * Minio downloadObject 和 uploadObject 只能接收 filename 参数，一般为路径地址或URL。
 * 这就决定这两个方法只能在应用服务端进行使用，特别是filename为文件路径的情况下。
 * <p>
 * 这更倾向于在“后端”进行一定的业务逻辑操作。
 *
 * @author : gengwei.zheng
 * @date : 2023/6/12 11:36
 */
@Service
public class MinioObjectServerSideService extends BaseMinioService {

    private static final Logger log = LoggerFactory.getLogger(MinioObjectService.class);

    public MinioObjectServerSideService(MinioClientObjectPool minioClientObjectPool) {
        super(minioClientObjectPool);
    }

    /**
     * 将对象的数据下载到文件。主要用于在服务端下载(非流方式)
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param fileName   具体保存的文件名，包括路径
     */
    public void downloadObject(String bucketName, String objectName, String fileName) {
        downloadObject(bucketName, objectName, fileName, false);
    }

    /**
     * 将对象的数据下载到文件。主要用于在服务端下载(非流方式)
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param fileName   具体保存的文件名，包括路径
     * @param overwrite  是否覆盖
     */
    public void downloadObject(String bucketName, String objectName, String fileName, boolean overwrite) {
        downloadObject(bucketName, null, objectName, fileName, overwrite);
    }

    /**
     * 将对象的数据下载到文件。主要用于在服务端下载(非流方式)
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param fileName   具体保存的文件名，包括路径
     * @param overwrite  是否覆盖
     */
    public void downloadObject(String bucketName, String region, String objectName, String fileName, boolean overwrite) {
        downloadObject(bucketName, region, objectName, fileName, overwrite, null);
    }

    /**
     * 将对象的数据下载到文件。主要用于在服务端下载(非流方式)
     *
     * @param bucketName                      存储桶名称
     * @param region                          区域
     * @param objectName                      对象名称
     * @param fileName                        具体保存的文件名，包括路径
     * @param overwrite                       是否覆盖
     * @param serverSideEncryptionCustomerKey 服务端加密自定义KEY，目前 Minio 仅支持 256位 AES.
     */
    public void downloadObject(String bucketName, String region, String objectName, String fileName, boolean overwrite, ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey) {
        downloadObject(bucketName, region, objectName, fileName, overwrite, serverSideEncryptionCustomerKey, null);
    }

    /**
     * 将对象的数据下载到文件。主要用于在服务端下载(非流方式)
     *
     * @param bucketName                      存储桶名称
     * @param region                          区域
     * @param objectName                      对象名称
     * @param fileName                        具体保存的文件名，包括路径
     * @param overwrite                       是否覆盖
     * @param serverSideEncryptionCustomerKey 服务端加密自定义KEY，目前 Minio 仅支持 256位 AES.
     * @param versionId                       版本ID
     */
    public void downloadObject(String bucketName, String region, String objectName, String fileName, boolean overwrite, ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey, String versionId) {
        downloadObject(DownloadObjectArgs.builder()
                .bucket(bucketName)
                .region(region)
                .object(objectName)
                .filename(fileName)
                .overwrite(overwrite)
                .ssec(serverSideEncryptionCustomerKey)
                .versionId(versionId)
                .build());
    }

    /**
     * 将对象的数据下载到文件。主要用于在服务端下载
     *
     * @param downloadObjectArgs {@link DownloadObjectArgs}
     */
    public void downloadObject(DownloadObjectArgs downloadObjectArgs) {
        String function = "downloadObject";
        MinioClient minioClient = getClient();

        try {
            minioClient.downloadObject(downloadObjectArgs);
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new OssErrorResponseException(e.getMessage());
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new OssInsufficientDataException(e.getMessage());
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new OssInternalException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException(e.getMessage());
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new OssInvalidResponseException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException(e.getMessage());
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in [{}].", function, e);
            throw new OssXmlParserException(e.getMessage());
        } finally {
            close(minioClient);
        }
    }

    /**
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param fileName   具体文件，完整的路径
     * @return {@link ObjectWriteResponse}
     * @throws IOException 读取文件失败
     */
    public ObjectWriteResponse uploadObject(String bucketName, String objectName, String fileName) throws IOException {
        return uploadObject(bucketName, null, objectName, fileName);
    }

    /**
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param bucketName 存储桶名称
     * @param region     区域
     * @param objectName 对象名称
     * @param fileName   具体文件，完整的路径
     * @return {@link ObjectWriteResponse}
     * @throws IOException 读取文件失败
     */
    public ObjectWriteResponse uploadObject(String bucketName, String region, String objectName, String fileName) throws IOException {
        return uploadObject(bucketName, region, objectName, fileName, null);
    }

    /**
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param bucketName  存储桶名称
     * @param region      区域
     * @param objectName  对象名称
     * @param fileName    具体文件，完整的路径
     * @param contentType 内容类型
     * @return {@link ObjectWriteResponse}
     * @throws IOException 读取文件失败
     */
    public ObjectWriteResponse uploadObject(String bucketName, String region, String objectName, String fileName, String contentType) throws IOException {
        return uploadObject(bucketName, region, objectName, fileName, contentType, false);
    }

    /**
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param bucketName  存储桶名称
     * @param region      区域
     * @param objectName  对象名称
     * @param fileName    具体文件，完整的路径
     * @param contentType 内容类型
     * @param legalHold   是否保持
     * @return {@link ObjectWriteResponse}
     * @throws IOException 读取文件失败
     */
    public ObjectWriteResponse uploadObject(String bucketName, String region, String objectName, String fileName, String contentType, boolean legalHold) throws IOException {
        return uploadObject(bucketName, region, objectName, fileName, contentType, legalHold, null);
    }

    /**
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param bucketName  存储桶名称
     * @param region      区域
     * @param objectName  对象名称
     * @param fileName    具体文件，完整的路径
     * @param contentType 内容类型
     * @param legalHold   是否保持
     * @param retention   保存设置
     * @return {@link ObjectWriteResponse}
     * @throws IOException 读取文件失败
     */
    public ObjectWriteResponse uploadObject(String bucketName, String region, String objectName, String fileName, String contentType, boolean legalHold, Retention retention) throws IOException {
        return uploadObject(bucketName, region, objectName, fileName, contentType, legalHold, retention, null);
    }

    /**
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param bucketName  存储桶名称
     * @param region      区域
     * @param objectName  对象名称
     * @param fileName    具体文件，完整的路径
     * @param contentType 内容类型
     * @param legalHold   是否保持
     * @param retention   保存设置
     * @param tags        标签
     * @return {@link ObjectWriteResponse}
     * @throws IOException 读取文件失败
     */
    public ObjectWriteResponse uploadObject(String bucketName, String region, String objectName, String fileName, String contentType, boolean legalHold, Retention retention, Tags tags) throws IOException {
        return uploadObject(bucketName, region, objectName, fileName, contentType, legalHold, retention, tags, null);
    }

    /**
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param bucketName  存储桶名称
     * @param region      区域
     * @param objectName  对象名称
     * @param fileName    具体文件，完整的路径
     * @param contentType 内容类型
     * @param legalHold   是否保持
     * @param retention   保存设置
     * @param tags        标签
     * @param sse         服务加密
     * @return {@link ObjectWriteResponse}
     * @throws IOException 读取文件失败
     */
    public ObjectWriteResponse uploadObject(String bucketName, String region, String objectName, String fileName, String contentType, boolean legalHold, Retention retention, Tags tags, ServerSideEncryption sse) throws IOException {
        return uploadObject(UploadObjectArgs.builder()
                .bucket(bucketName)
                .region(region)
                .object(objectName)
                .filename(fileName)
                .contentType(contentType)
                .sse(sse)
                .legalHold(legalHold)
                .tags(tags)
                .retention(retention)
                .build());
    }

    /**
     * 将文件中的内容作为存储桶中的对象上传
     *
     * @param uploadObjectArgs {@link UploadObjectArgs}
     * @return {@link ObjectWriteResponse}
     */
    public ObjectWriteResponse uploadObject(UploadObjectArgs uploadObjectArgs) {
        String function = "uploadObject";
        MinioClient minioClient = getClient();

        try {
            return minioClient.uploadObject(uploadObjectArgs);
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new OssErrorResponseException(e.getMessage());
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new OssInsufficientDataException(e.getMessage());
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new OssInternalException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException(e.getMessage());
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new OssInvalidResponseException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException(e.getMessage());
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in [{}].", function, e);
            throw new OssXmlParserException(e.getMessage());
        } finally {
            close(minioClient);
        }
    }
}
