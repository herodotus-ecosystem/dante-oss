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

package cn.herodotus.oss.dialect.s3.service;

import cn.herodotus.oss.dialect.core.exception.OssServerException;
import cn.herodotus.oss.dialect.s3.definition.pool.S3ClientObjectPool;
import cn.herodotus.oss.dialect.s3.definition.service.BaseS3Service;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>Description: 分片上传 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/16 15:36
 */
@Service
public class S3MultipartUploadService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3MultipartUploadService.class);

    public S3MultipartUploadService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 拷贝分片
     *
     * @param request {@link CopyPartRequest}
     * @return {@link CopyPartResult}
     */
    public CopyPartResult copyPart(CopyPartRequest request) {
        String function = "copyPart";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.copyPart(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 创建分片上传
     *
     * @param request {@link InitiateMultipartUploadRequest}
     * @return {@link InitiateMultipartUploadResult}
     */
    public InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest request) {
        String function = "initiateMultipartUpload";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.initiateMultipartUpload(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 上传分片
     *
     * @param request {@link UploadPartRequest}
     * @return {@link UploadPartResult}
     */
    public UploadPartResult uploadPart(UploadPartRequest request) {
        String function = "uploadPart";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.uploadPart(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 分片列表
     *
     * @param request {@link ListPartsRequest}
     * @return {@link PartListing}
     */
    public PartListing listParts(ListPartsRequest request) {
        String function = "listParts";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.listParts(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 终止分片上传
     *
     * @param request {@link AbortMultipartUploadRequest}
     */
    public void abortMultipartUpload(AbortMultipartUploadRequest request) {
        String function = "abortMultipartUpload";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.abortMultipartUpload(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 完成分片上传
     *
     * @param request {@link CompleteMultipartUploadRequest}
     * @return {@link CompleteMultipartUploadResult}
     */
    public CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest request) {
        String function = "completeMultipartUpload";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.completeMultipartUpload(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 分片上传列表
     *
     * @param request {@link ListMultipartUploadsRequest}
     * @return {@link MultipartUploadListing}
     */
    public MultipartUploadListing listMultipartUploads(ListMultipartUploadsRequest request) {
        String function = "listMultipartUploads";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.listMultipartUploads(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }


}