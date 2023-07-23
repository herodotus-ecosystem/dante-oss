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

package cn.herodotus.oss.s3.logic.service;

import cn.herodotus.oss.definition.core.exception.OssServerException;
import cn.herodotus.oss.s3.logic.definition.pool.S3ClientObjectPool;
import cn.herodotus.oss.s3.logic.definition.service.BaseS3ClientService;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>Description: Amazon S3 对象管理 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/16 16:48
 */
@Service
public class S3ObjectService extends BaseS3ClientService {

    private static final Logger log = LoggerFactory.getLogger(S3ObjectService.class);

    public S3ObjectService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 获取对象详细信息
     *
     * @param request {@link GetObjectMetadataRequest }
     * @return {@link ObjectMetadata}
     */
    public ObjectMetadata getObjectMetadata(GetObjectMetadataRequest request) {
        String function = "getObjectMetadata";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getObjectMetadata(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取对象
     *
     * @param request {@link GetObjectRequest}
     * @return {@link S3Object}
     */
    public S3Object getObject(GetObjectRequest request) {
        String function = "getObject";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getObject(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 上传对象
     *
     * @param request {@link PutObjectRequest }
     * @return {@link PutObjectResult}
     */
    public PutObjectResult putObject(PutObjectRequest request) {
        String function = "putObject";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.putObject(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 复制对象
     *
     * @param request {@link CopyObjectRequest}
     * @return {@link CopyObjectResult}
     */
    public CopyObjectResult copyObject(CopyObjectRequest request) {
        String function = "copyObject";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.copyObject(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 删除对象
     *
     * @param request {@link DeleteObjectRequest}
     */
    public void deleteObject(DeleteObjectRequest request) {
        String function = "deleteObject";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.deleteObject(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 删除多个对象
     *
     * @param request {@link DeleteObjectsRequest}
     * @return {@link DeleteObjectsResult}
     */
    public DeleteObjectsResult deleteObjects(DeleteObjectsRequest request) {
        String function = "deleteObjects";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.deleteObjects(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 删除对象指定版本
     *
     * @param request {@link DeleteVersionRequest}
     */
    public void deleteObjects(DeleteVersionRequest request) {
        String function = "deleteObjects";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.deleteVersion(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 删除对象指定版本
     *
     * @param request {@link RestoreObjectRequest}
     * @return {@link RestoreObjectResult}
     */
    public RestoreObjectResult restoreObject(RestoreObjectRequest request) {
        String function = "restoreObjectV2";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.restoreObjectV2(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 删除对象指定版本
     *
     * @param request {@link SelectObjectContentRequest}
     * @return {@link SelectObjectContentResult}
     */
    public SelectObjectContentResult selectObjectContent(SelectObjectContentRequest request) {
        String function = "selectObjectContent";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.selectObjectContent(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 删除对象指定版本
     *
     * @param request {@link WriteGetObjectResponseRequest}
     * @return {@link WriteGetObjectResponseResult}
     */
    public WriteGetObjectResponseResult writeGetObjectResponse(WriteGetObjectResponseRequest request) {
        String function = "writeGetObjectResponse";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.writeGetObjectResponse(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取对象列表
     *
     * @param request {@link ListObjectsRequest }
     * @return {@link ObjectListing}
     */
    public ObjectListing listObjects(ListObjectsRequest request) {
        String function = "listObjects";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.listObjects(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取对象列表 V2
     *
     * @param request {@link ListObjectsV2Request }
     * @return {@link ListObjectsV2Result}
     */
    public ListObjectsV2Result listObjectsV2(ListObjectsV2Request request) {
        String function = "listObjectsV2";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.listObjectsV2(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 列出下一批对象
     *
     * @param request {@link ListNextBatchOfObjectsRequest }
     * @return {@link ObjectListing}
     */
    public ObjectListing listNextBatchOfObjects(ListNextBatchOfObjectsRequest request) {
        String function = "listNextBatchOfObjects";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.listNextBatchOfObjects(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }


}
